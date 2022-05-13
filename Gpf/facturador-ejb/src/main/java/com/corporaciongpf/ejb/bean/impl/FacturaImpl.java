package com.corporaciongpf.ejb.bean.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.corporaciongpf.ejb.bean.local.FacturaLocal;
import com.corporaciongpf.ejb.utilitario.Conexion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.corporaciongpf.adm.dao.FacturaDAO;
import com.corporaciongpf.adm.dao.FarmaciaDAO;
import com.corporaciongpf.adm.dao.LogFacturaDAO;
import com.corporaciongpf.adm.dao.NotaCreditoDAO;
import com.corporaciongpf.adm.dao.ParametroDAO;
import com.corporaciongpf.adm.dao.PersonaDAO;
import com.corporaciongpf.adm.dao.TipoIdentificacionClienteDAO;
import com.corporaciongpf.adm.dao.UsuarioDAO;
import com.corporaciongpf.adm.dao.UsuarioRolDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.excepcion.GizloPersistException;
import com.corporaciongpf.adm.excepcion.GizloUpdateException;
import com.corporaciongpf.adm.modelo.CredencialDS;
import com.corporaciongpf.adm.modelo.Factura;
import com.corporaciongpf.adm.modelo.LogFactura;
import com.corporaciongpf.adm.modelo.NotaCredito;
import com.corporaciongpf.adm.modelo.Persona;
import com.corporaciongpf.adm.modelo.Usuario;
import com.corporaciongpf.adm.modelo.UsuarioRol;
import com.corporaciongpf.adm.utilitario.DocumentUtil;
import com.corporaciongpf.adm.vo.VOCreditoDevolucion;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VOFarmaciaDetalleFormaPago;
import com.corporaciongpf.adm.vo.VOFarmaciaFactura;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaAdicional;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaDetalle;
import com.corporaciongpf.adm.vo.VOFarmaciaNotaCredito;
import com.corporaciongpf.adm.vo.VOPayment;
import com.corporaciongpf.adm.vo.VOProduct;
import com.corporaciongpf.adm.vo.VOResponse;
import com.corporaciongpf.adm.vo.VOSecuenciaElectronica;
import com.corporaciongpf.ejb.utilitario.Constantes;

import org.jboss.security.auth.spi.Util;

@Stateless
public class FacturaImpl implements FacturaLocal {

	private static Logger log = Logger.getLogger(FacturaImpl.class.getName());

	@EJB(lookup = "java:global/facturador-adm/NotaCreditoDAOImpl!com.corporaciongpf.adm.dao.NotaCreditoDAO")
	NotaCreditoDAO notaCreditoDAO;	
	
	@EJB(lookup = "java:global/facturador-adm/FacturaDAOImpl!com.corporaciongpf.adm.dao.FacturaDAO")
	FacturaDAO facturaDAO;	
	
	@EJB(lookup = "java:global/facturador-adm/LogFacturaDAOImpl!com.corporaciongpf.adm.dao.LogFacturaDAO")
	LogFacturaDAO logfacturaDAO;		
	
	@EJB(lookup = "java:global/facturador-adm/PersonaDAOImpl!com.corporaciongpf.adm.dao.PersonaDAO")
	PersonaDAO personaDAO;		

	
	@EJB(lookup = "java:global/facturador-adm/UsuarioDAOImpl!com.corporaciongpf.adm.dao.UsuarioDAO")
	UsuarioDAO usuarioDAO;	

	@EJB(lookup = "java:global/facturador-adm/UsuarioRolDAOImpl!com.corporaciongpf.adm.dao.UsuarioRolDAO")
	UsuarioRolDAO usuarioRolDAO;	

	
	@EJB(lookup = "java:global/facturador-adm/FarmaciaDAOImpl!com.corporaciongpf.adm.dao.FarmaciaDAO")
	FarmaciaDAO farmaciaDAO;	
	
	
	@EJB(lookup = "java:global/facturador-adm/ParametroDAOImpl!com.corporaciongpf.adm.dao.ParametroDAO")
	ParametroDAO parametroDAO;

	@EJB(lookup = "java:global/facturador-adm/TipoIdentificacionClienteDAOImpl!com.corporaciongpf.adm.dao.TipoIdentificacionClienteDAO")
	TipoIdentificacionClienteDAO tipoIdentificacionClienteDAO;
	
	

	
	
	public String test(String name) {
		return "EJB Remoto: " + name;
	}


	public VOResponse crearFactura( VOFactura facturaRequest  ) throws GizloPersistException, GizloException, GizloUpdateException, SQLException, IOException  {

		LogFactura logFacturaDocumento= new LogFactura();
		Date date = new Date(); 
		VOResponse respuesta= new VOResponse();
		respuesta.setCode("400");
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
       // Log Factura Inicial
        
        
        
        
        
        
        
        
        
  		//Conexión PRS6 por jdbc
        
		CredencialDS credencialDSOfi = new CredencialDS();
		credencialDSOfi.setDatabaseId(Constantes.BASE_PRS6);
		credencialDSOfi.setUsuario(Constantes.USUARIO_PRS6);
		credencialDSOfi.setClave(Constantes.CLAVE_PRS6);
		credencialDSOfi.setRegion(Constantes.REGION_PRS6);
		credencialDSOfi.setDirectorio(Constantes.DIRECTORIO_TS_NAME_PRS6);				
		Connection connPrs6 = null;						
		connPrs6 = Conexion.obtenerConexionFybeca(credencialDSOfi);			

		if (connPrs6==null){
			return respuesta.setResponse(respuesta,"501",
					"Error conexión a base de datos PRS6");
		}
	
		respuesta = validacionesCamposRequest(facturaRequest, respuesta,connPrs6);
		if (!respuesta.getCode().equalsIgnoreCase("400")){
			return respuesta;
		}
		
		//Se inicializan los objetos que van a realizar persistencia

		Factura factura = new Factura();
		NotaCredito notaCreditoGinvoice = new NotaCredito();

		
		String sid= parametroDAO.obtenerFarmacia(connPrs6,Long.parseLong(facturaRequest.getCompany()));
		log.info("SID  del pdv");
		log.info(sid);

		
		//extrayendo de la base
		CredencialDS credencialDS = new CredencialDS();
		String sidAmbiente= parametroDAO.obtenerParametro(connPrs6,"sid_ambiente");
		String regionSidAmbiente= parametroDAO.obtenerParametro(connPrs6,"sid_region");
		String directorioAmbienteFarmacia= parametroDAO.obtenerParametro(connPrs6,"directorio_ambiente_farmacia");
		
		
		
		credencialDS.setDatabaseId(sid+sidAmbiente);
		
		//Conexión Farmacias por jdbc
		
		String usuarioFarmaciaRequest= parametroDAO.obtenerParametro(connPrs6,"user_farmacia_request");
		String passwordFarmaciaRequest= parametroDAO.obtenerParametro(connPrs6,"password_farmacia_request");
		credencialDS.setUsuario(usuarioFarmaciaRequest);
		credencialDS.setClave(passwordFarmaciaRequest);
		credencialDS.setDirectorio(directorioAmbienteFarmacia);
		credencialDS.setRegion(regionSidAmbiente);
		credencialDS.setDatabaseId(sid+sidAmbiente);
		Connection connFarmacia = null;						
		
		
		connFarmacia = Conexion.obtenerConexionFybeca(credencialDS);		
		if (connFarmacia==null){
			return respuesta.setResponse(respuesta,"501",
					"Error conexión a base de datos Farmacias " +sidAmbiente );
		}
			
		try {
			//Persistencia de la Factura
			if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F)) {
				crearFacturaPersistencia(factura ,  facturaRequest, connPrs6);

				}

			//Creacion de Persona si no existe en la tabla TB_PERSONA		
		
			Boolean existePersona = personaDAO.obtenerPorParametros(facturaRequest.getClient().getClientId());
			log.info("Cliente existen en Ginvoice");
			log.info(existePersona);
			if ( Boolean.FALSE.equals(existePersona)) {
				Persona persona= new Persona();
				persona.setApellidos(facturaRequest.getClient().getFirstSurname()+ facturaRequest.getClient().getSecondSurname() );		
				persona.setNombres(facturaRequest.getClient().getFirstName()+ facturaRequest.getClient().getSecondName() );
				persona.setCorreoElectronico(facturaRequest.getClient().getEmail());
				persona.setIdentificacion(facturaRequest.getClient().getClientId());

				Persona nuevaPersona = null ;
				nuevaPersona=personaDAO.persist3(persona);
				log.info("SE CREA LA PERSISTENCIA PERSONA");
				log.info(nuevaPersona.getId());
				Usuario usuario = new Usuario();
				usuario.setTipo("EXT");
				usuario.setEstado("ACT");
				usuario.setEsClvAutogenerada("S");
				
				usuario.setUsername(facturaRequest.getClient().getClientId());
				
				String createPasswordHash = Util.createPasswordHash("SHA-256",
						"hex", null, null, facturaRequest.getClient().getClientId());
				usuario.setPassword(createPasswordHash.toLowerCase());
				
				
				usuario.setTbPersona(nuevaPersona);
				Usuario nuevoUsuario;
				
				log.info("SE CREA LA PERSISTENCIA USUARIO");
				nuevoUsuario=usuarioDAO.persist3(usuario);
				log.info(nuevoUsuario.getId());
				UsuarioRol usuarioRol = new UsuarioRol();
				usuarioRol.setIdRol(new BigDecimal("2"));
				usuarioRol.setUsuario(nuevoUsuario);
				log.info("SE CREA LA PERSISTENCIA USUARIO ROL");
				usuarioRolDAO.persist3(usuarioRol);
				log.info(usuarioRol.getId());
				}

			log.info("Se inicia el proceso para  crear los registros en las tablas del pdv");
			if(facturaRequest.getCompany() != null && !facturaRequest.getCompany().isEmpty()){

				
	
				
				VOFarmaciaFactura farmaciaFactura= new VOFarmaciaFactura();	
				farmaciaFactura.setFarmacia( Long.parseLong(facturaRequest.getCompany()));
				log.info("Conexión Farmacia");			
				log.info(connFarmacia);			
			
				//Sacar tabla ad_parametro
				
				String secuenciaAnterior = farmaciaDAO.obtenerSecuenciaDocumentoVenta(connFarmacia);

				log.info("SECUENCIA ANTERIOR DEL PDV");
				log.info(secuenciaAnterior);
				farmaciaFactura.setDonacion("N");
				
				farmaciaFactura.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
				Long secuenciaNueva= Long.parseLong(secuenciaAnterior)+1;

				log.info("SECUENCIA NUEVA DEL PDV");
				log.info(Long.parseLong(secuenciaAnterior)+1);
				
				if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F)) {
				
					factura.setClaveInterna(secuenciaNueva.toString());
					factura.setSecOriginal(sid);

					
				//	facturaDAO.update(factura);


				}
				
				if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
					crearNotaCreditoPersistencia(notaCreditoGinvoice ,  facturaRequest, connPrs6);
					
					String documentoVentaPadre = farmaciaDAO.obtenerDocumentoVentaPorCorrelative(connFarmacia, facturaRequest.getCorrelativeAssociatedTX(),"DOCUMENTO_VENTA")	;
					
					String numeroSri= farmaciaDAO.obtenerNumeroSRIDeDocumentoVenta(connFarmacia,documentoVentaPadre);
					notaCreditoGinvoice.setNumDocModificado(numeroSri);


					

					notaCreditoGinvoice.setClaveInterna(secuenciaNueva.toString());

					


				}				


		        Date fecha = parseDatetime(facturaRequest.getBusinessDate());
                Timestamp fechaBusiness=new Timestamp(fecha.getTime());  
		        
		        
				farmaciaFactura.setFecha(fechaBusiness);
				farmaciaFactura.setNumeroSRI(facturaRequest.getAuthorizationCode().substring(24, 28)+"-"+facturaRequest.getAuthorizationCode().substring(27, 31)+"-"+facturaRequest.getAuthorizationCode().substring(30, 40) );
				farmaciaFactura.setCostoTotalFactura(facturaRequest.getTotalInvoiceCost());
				farmaciaFactura.setPvpTotalFactura(facturaRequest.getTotalInvoicePrice());
				farmaciaFactura.setVentaTotalFactura(facturaRequest.getTotal());
				farmaciaFactura.setCanalVenta("W");
				farmaciaFactura.setValorIva(new BigDecimal(facturaRequest.getTaxTotal()));
				
				if(facturaRequest.getTaxTotal()>0) {
					farmaciaFactura.setIncluyeIva("S");
				}
				else {
					farmaciaFactura.setIncluyeIva("N");
				}
				
				
				
				farmaciaFactura.setCancelada("S");
				
				if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
					farmaciaFactura.setTipoDocumento("N");
					
				}
				else {
					farmaciaFactura.setTipoDocumento("F");
				}
				
				farmaciaFactura.setTomaPedidoDomicilio(null);
				
				Long caja = null;
				
				caja = farmaciaDAO.obtenerCaja(connFarmacia,Long.parseLong(facturaRequest.getCompany()) );

				
				log.info("CAJA DEL PUNTO DE VENTA");
				log.info(caja);
				
				farmaciaFactura.setCaja((caja));

				if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
					farmaciaFactura.setTipoMovimiento("02");
					farmaciaFactura.setGeneraNotaCredito("S");
					String farmaciaPadre = farmaciaDAO.obtenerDocumentoVentaPorCorrelative(connFarmacia, facturaRequest.getCorrelativeAssociatedTX(),"FARMACIA");
					farmaciaFactura.setFarmaciaPadre(Long.parseLong(farmaciaPadre));					
				}
				else
				{

					farmaciaFactura.setTipoMovimiento("01");
					farmaciaFactura.setGeneraNotaCredito("N");
				}
				
				
				
				farmaciaFactura.setClasificacionMovimiento("01");
				farmaciaFactura.setUsuario(facturaRequest.getIdUser());
				
				Long persona= farmaciaDAO.obtenerPersona(connFarmacia,facturaRequest.getClient().getClientId());
				log.info("CLIENTE DEL PUNTO DE VENTA");
				log.info(persona);				
				
				farmaciaFactura.setPersona(persona);
			
				
				
				farmaciaFactura.setPrimerApellido(facturaRequest.getClient().getFirstSurname());
				farmaciaFactura.setSegundoApellido(facturaRequest.getClient().getSecondSurname());
				farmaciaFactura.setNombres(facturaRequest.getClient().getFirstName()+" "+facturaRequest.getClient().getSecondName());
				farmaciaFactura.setIdentificacion(facturaRequest.getClient().getClientId());
				farmaciaFactura.setDireccion(facturaRequest.getClient().getAddress());
				

									
				farmaciaFactura.setEmpleadoRealiza(Long.parseLong(facturaRequest.getIdUser()));
				farmaciaFactura.setEmpleadoCobra(Long.parseLong(facturaRequest.getIdUser()));
				farmaciaFactura.setEmpleadoEntrega(Long.parseLong(facturaRequest.getIdUser()));
				farmaciaFactura.setTratamientoContinuo("N");
				log.info("Se inserta el registro en la tabla FA_FACTURAS");
				
				farmaciaDAO.insertarFacturaFarmacia(connFarmacia, farmaciaFactura);

	
	
				List<VOProduct> detalleFactura= facturaRequest.getProducts();
				BigDecimal costoFactura = new BigDecimal("0");
				
				Long contador=new Long("1");
				log.info("Se inicia el proceso para insertar el detalle de la factura");
		        for (VOProduct detalle : detalleFactura) {
		        	VOFarmaciaFacturaDetalle farmaciaDetalle = new VOFarmaciaFacturaDetalle();
		        	farmaciaDetalle.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
		        	farmaciaDetalle.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
		        	 
		        	farmaciaDetalle.setCodigo(contador);
		        	farmaciaDetalle.setCantidad(detalle.getQuantity());
		        	
		        	
		        	farmaciaDetalle.setListaDespacho(null);
		        	farmaciaDetalle.setVenta(detalle.getPriceList());
		        	
		        	
		        	
		        	farmaciaDetalle.setPorcentajeIva(detalle.getIvaPercent());
		        	farmaciaDetalle.setItem(Long.parseLong(detalle.getSku()));
		        	
		        	Long seccion = null;
					
					seccion = Long.parseLong(parametroDAO.obtenerDatoSecciones(connPrs6,Long.parseLong(detalle.getSku()),"seccion_contable"));

		
					farmaciaDetalle.setSeccion(seccion);
					log.info("Se obtiene seccion del producto");
					log.info(seccion);	
	
					String tipoNegocio = null;
		        	
		        	tipoNegocio= parametroDAO.obtenerDatoSecciones(connPrs6,Long.parseLong(detalle.getSku()),"tipo_negocio");
					farmaciaDetalle.setTipoNegocio(tipoNegocio);

					log.info("Se obtiene TipoNegocio");
					log.info(tipoNegocio);		        	
		        	
	        		BigDecimal costoProducto= farmaciaDAO.obtenerPrecioPrItemAutorizado(connFarmacia, farmaciaDetalle.getItem(), Long.parseLong(facturaRequest.getCompany()),"COSTO");
					costoFactura= costoFactura.add(costoProducto);
					farmaciaDetalle.setCosto(costoProducto);					
					log.info(" Se obtiene CostoFactura");
					log.info(costoFactura);						


	        		BigDecimal obtenerPvpProducto= farmaciaDAO.obtenerPrecioPrItemAutorizado(connFarmacia, farmaciaDetalle.getCodigo(), Long.parseLong(facturaRequest.getCompany()),"PVP_SIN_IVA");
					farmaciaDetalle.setPvp(obtenerPvpProducto);
				
					log.info("Se obtiene PvpProducto");
					log.info(obtenerPvpProducto);	
	        	


	        		BigDecimal precioFybeca= farmaciaDAO.obtenerPrecioPrItemAutorizado(connFarmacia, farmaciaDetalle.getCodigo(), Long.parseLong(facturaRequest.getCompany()),"COMISARIATO_SIN_IVA");
					farmaciaDetalle.setPrecioFybeca(precioFybeca);
					log.info("Se obtiene precioFybeca");
					log.info(precioFybeca);		        	

	        		BigDecimal unidades= farmaciaDAO.obtenerPrecioPrItemAutorizado(connFarmacia, farmaciaDetalle.getCodigo(), Long.parseLong(facturaRequest.getCompany()),"UNIDAD_VENTA");
	        		
	        		farmaciaDetalle.setUnidades( unidades.longValue());

					
					
					
					
					farmaciaDAO.insertarFacturaDetalleFarmacia(connFarmacia, farmaciaDetalle);

		        	contador=contador+1;
		        	
		        }
	
		        
		        VOFarmaciaFacturaAdicional facturaAdicional= new VOFarmaciaFacturaAdicional();
				long fechaEmision = parseDate(facturaRequest.getBusinessDate()).getTime();
		        java.sql.Date fechaEmisionSRI = new java.sql.Date(fechaEmision);					
						
		        
		        facturaAdicional.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
		        facturaAdicional.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
		        facturaAdicional.setTipoImpresion("T");
		        

				facturaAdicional.setCp_num1(farmaciaDAO.ejecutarProcesoSRI(connFarmacia,fechaEmisionSRI,Long.parseLong(facturaRequest.getCompany())));

				log.info("Se obtiene CPNUM1");
				
				log.info(facturaAdicional.getCp_num1());
				

				farmaciaDAO.insertarFacturaFarmaciaAdicional(connFarmacia, facturaAdicional);

				log.info("insercion de factura  adicional");
	
				List<VOPayment> detallePagos= facturaRequest.getPayments();
				
		        for (VOPayment pago : detallePagos) {
		        	VOFarmaciaDetalleFormaPago farmaciaPago = new VOFarmaciaDetalleFormaPago();
	
		        	farmaciaPago.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
		        	farmaciaPago.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
		        	farmaciaPago.setFormaPago(pago.getPaymentMethod()); 			        
		            BigDecimal total = facturaRequest.getTotal().subtract(facturaRequest.getDiscountTotal());
		        	
		            farmaciaPago.setNumeroTarjeta(pago.getCreditCardNumber());
		            farmaciaPago.setNumeroAutorizacion(pago.getAuthorizationCode());		
		            farmaciaPago.setPvpFactura(total) ;	
		            farmaciaPago.setInteres(pago.getInterest());
		            farmaciaPago.setNumeroCuotas(Long.parseLong(pago.getQuota()));
		        	farmaciaPago.setFormaPago(pago.getPaymentMethod()); 			        
		        	farmaciaPago.setVentaFactura(facturaRequest.getTotal());
		        	farmaciaPago.setCostoFactura(costoFactura);
		        	farmaciaPago.setNumeroAutorizacion(pago.getAuthorizationCode());
		        	farmaciaPago.setNumeroAutorizacionBoletin(pago.getAuthorizationBulletin());
		        	
		        	
		        	String codigoMetodoPagoFybeca = "";

					codigoMetodoPagoFybeca = parametroDAO.obtenerCodigoMetodoPagoFarmacia(connPrs6,pago.getPayment(),pago.getPaymentMethod());

					log.info("codigoMetodoPagoFybeca ");
					log.info(codigoMetodoPagoFybeca);
		        	if (codigoMetodoPagoFybeca.isEmpty()) {
			        	farmaciaPago.setFormaPago("NA");
		        		
		        	}
		        	else {
		        		if (codigoMetodoPagoFybeca.equalsIgnoreCase("CE") && facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
							log.info("Ingresa validacion nota de credito");
		        			
							farmaciaDAO.actualizarCupoTarjetaConvenioEmpresarial(connFarmacia, facturaRequest.getClient().getClientId(),pago.getAmount());
							log.info("Actualizacion exitosa para actualizar cupo");

		        			
		        			
		        		}
			        	farmaciaPago.setFormaPago(codigoMetodoPagoFybeca);
		        	}
		        	if (facturaRequest.getClient().getParticularNumber()!= null) {
			        	if (!facturaRequest.getClient().getParticularNumber().isEmpty() ){
			        		farmaciaPago.setTarjetaDescuento("-");
			        		farmaciaPago.setTarjetaDt("N");
			        		farmaciaPago.setMedioDescuento("N");
			        	}
			        	else {
			        		String tarjetaDescuento = null;
							
							tarjetaDescuento = farmaciaDAO.obtenerTarjetaDescuento(connFarmacia, facturaRequest.getClient().getClientId());
	
							log.info("tarjetaDescuento ");
							log.info(tarjetaDescuento);
		
							farmaciaPago.setTarjetaDescuento(tarjetaDescuento);
			        		farmaciaPago.setTarjetaDt("D");
			        		farmaciaPago.setMedioDescuento("F");
	
			        	}
		        	}
					farmaciaDAO.insertarFacturaFarmaciaFormaPago(connFarmacia, farmaciaPago);

		        }
		        
		        if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
		        	VOFarmaciaNotaCredito notaCredito= new VOFarmaciaNotaCredito();
		        	notaCredito.setCodigo(parametroDAO.obtenerSecuenciaNotaCredito(connPrs6));
		        	
		        	
		        	
		        	notaCredito.setCancelada("S");
		        	notaCredito.setUsuario("USER");
		        	notaCredito.setValor(facturaRequest.getUntaxedTotal());
		        	notaCredito.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
		        	notaCredito.setFormaPago("NC");
		        	notaCredito.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
		        	String persona1="0";
		        	
					persona1 = farmaciaDAO.obtenerPersonaRRHH(connPrs6, facturaRequest.getUser());

		        	notaCredito.setEmpleadoCobra(Long.parseLong(persona1));
		        	notaCredito.setFarmaciaCanje(Long.parseLong("02"));
		        	notaCredito.setDocumentoCancelacion(Long.parseLong(facturaRequest.getCorrelativeAssociatedTX()));
		        
					
					farmaciaDAO.insertarFarmaciaNotaCredito(connFarmacia, notaCredito);
	
		        }
		        if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
			        for (VOPayment pago : detallePagos) {
		
			        	String codigoMetodoPagoFybeca = "";
						
						codigoMetodoPagoFybeca = parametroDAO.obtenerCodigoMetodoPagoFarmacia(connPrs6,pago.getPayment(),pago.getPaymentMethod());
						if (codigoMetodoPagoFybeca.equalsIgnoreCase("CE")) {
							log.info("Ingreso registro de creditos");
				        	//Registrar Credito Devolucion
				        	VOCreditoDevolucion notaCreditoDevolucion = new VOCreditoDevolucion();
				        	
	
							String documentoPadre = farmaciaDAO.obtenerDocumentoVentaPorCorrelative(connFarmacia, facturaRequest.getCorrelativeAssociatedTX(),"DOCUMENTO_VENTA");
					        notaCreditoDevolucion.setReferencia(Long.parseLong(documentoPadre));
	
				        	
				        	notaCreditoDevolucion.setTotalVenta(facturaRequest.getTotal().subtract(facturaRequest.getDiscountTotal()));
				        	notaCreditoDevolucion.setTotalPvp(facturaRequest.getTotal());
				        	
							long fechaNotaCreditoDevolucion = parseDate(facturaRequest.getBusinessDate()).getTime();
					        java.sql.Date fechaNotaCreditoDevolucionSQL = new java.sql.Date(fechaNotaCreditoDevolucion);					
											        	
				        	notaCreditoDevolucion.setFecha(fechaNotaCreditoDevolucionSQL);
				        	notaCreditoDevolucion.setFormaPago(codigoMetodoPagoFybeca);
				        	notaCreditoDevolucion.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
				        	notaCreditoDevolucion.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
							
							Long cliente= farmaciaDAO.obtenerPersona(connFarmacia,facturaRequest.getClient().getClientId());
							log.info("Cliente");
							log.info(cliente);				
							
				        	notaCreditoDevolucion.setCliente(cliente);
	
			        	
				        	
							notaCreditoDevolucion.setClasificacionMovimiento("01");
	
				        	
				        	
				        	notaCreditoDevolucion.setTipoMovimiento("02");
							
							farmaciaDAO.insertarFarmaciaCreditoDevolucion(connFarmacia, notaCreditoDevolucion);
						}
		
			        	
					}
		        }
				

	    		
	    		VOSecuenciaElectronica secuenciaElectronica = new VOSecuenciaElectronica();
	    		
	    		secuenciaElectronica.setDocumentoVenta(Long.parseLong(secuenciaAnterior)+1);
	    		secuenciaElectronica.setFarmacia(Long.parseLong(facturaRequest.getCompany()));
	    		secuenciaElectronica.setCpVar1(facturaRequest.getCorrelationTX());
	    		secuenciaElectronica.setCpVar2(facturaRequest.getOrderId());

	    		secuenciaElectronica.setClasificacionDocumento("01");
	    		secuenciaElectronica.setTipoMovimiento("01");
	    		secuenciaElectronica.setTipodocumento(new Long("1"));
	    		

				farmaciaDAO.insertarFarmaciaSecuenciaElectronica(connFarmacia, secuenciaElectronica);



			}
			
	        if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
				log.info("Se crea persitencia de la nota de credito");
				notaCreditoDAO.persist(notaCreditoGinvoice);
				log.info(notaCreditoGinvoice.getId());						
	        	respuesta.setMsg("La Nota de credito se guardo de manera exitosa");
	        	
	        }
	        else {
				log.info("Se crea persitencia de la factura ");
				facturaDAO.persist(factura);
				log.info(factura.getId());	        	
				respuesta.setMsg("La factura se guardo de manera exitosa");
	        }			
	
	//Log Factura		
			respuesta.setCode("200");

			
			logFacturaDocumento.setCodigo("200");
			logFacturaDocumento.setError("N");
			
			
			logFacturaDocumento.setFechaInserta(date);
			logFacturaDocumento.setIntentos(new BigDecimal("1"));
	
	        // Converting the Java object into a JSON string  
	        
			jsonStr = mapper.writeValueAsString(facturaRequest);

	    	logFacturaDocumento.setJson(jsonStr);
	
			logFacturaDocumento.setMensaje("Ingreso con Exito");
			logFacturaDocumento.setReintegrar("N");
			logFacturaDocumento.setUsuarioInserta(facturaRequest.getUser());
	
			
			logfacturaDAO.persist(logFacturaDocumento);

		}

		catch (Error e) {
			connPrs6.rollback();
			connFarmacia.rollback();
			

			
			logFacturaDocumento.setCodigo("500");
			logFacturaDocumento.setError("S");
			
			
			logFacturaDocumento.setFechaInserta(date);
			logFacturaDocumento.setIntentos(new BigDecimal("1"));

	        // Converting the Java object into a JSON string  
	        
			jsonStr = mapper.writeValueAsString(facturaRequest);

	    	logFacturaDocumento.setJson(jsonStr);

			logFacturaDocumento.setMensaje("Error:" + e.getMessage());
			logFacturaDocumento.setReintegrar("S");
			logFacturaDocumento.setUsuarioInserta(facturaRequest.getUser());


			logfacturaDAO.persist(logFacturaDocumento);
	
			respuesta.setResponse(respuesta,"500",
					"Error al generar el consumo:  "+e.getMessage());				
			
			
		}
		finally {
			connPrs6.commit();
			connFarmacia.commit();
			connFarmacia.close();
			connPrs6.close();			
		}
		
		return respuesta;


	}

    
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");
    
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss");

    public static Date parseDatetime(String value) {
    	
    	value=value.replace("+00:00", "");
        Date result = null;//from w  w  w.  j  a va2s  . c om
        try {
            result = DATETIME_FORMAT.parse(value);
        } catch (ParseException ex) {
            // do nothing; we will just return null
    		log.info(ex.getMessage());
        	
        }
        return result;
    }	
	
    public static Date parseDate(String value) {
    	
    	value=value.replace("+00:00", "");
        Date result = null;//from w  w  w.  j  a va2s  . c om
        try {
            result = DATE_FORMAT.parse(value);
        } catch (ParseException ex) {
            // do nothing; we will just return null
        }
        return result;
    }		
	
	
    public void crearFacturaPersistencia(Factura factura, VOFactura facturaRequest, Connection conn) {
    

		factura.setAgencia(facturaRequest.getCompany());
		String directorio= parametroDAO.obtenerParametro(conn,"directorio");
		factura.setArchivo(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".xml" );
		factura.setArchivoLegible(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".pdf" );
	
		try {
			DocumentUtil.createDirectory(directorio + facturaRequest.getAuthorizationCode().substring(0, 8));
		} catch (FileNotFoundException e3) {

			log.error(e3.getStackTrace());
		}
		try {
			DocumentUtil.crearArchivoDirectorio(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".xml",facturaRequest.getXmlUrl() );
		} catch (IOException e3) {

			log.error(e3.getStackTrace());
		}
	
		factura.setBaseCero(facturaRequest.getBase0());
		factura.setBaseDoce(facturaRequest.getBase12());
		factura.setBaseIce(BigDecimal.ZERO);
		factura.setBaseIrbp(BigDecimal.ZERO);
		factura.setClaveAcceso(facturaRequest.getAuthorizationCode());
		factura.setClaveInterna("");
		String codigoDocumento="";
		if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F)) {
			codigoDocumento="01";
		}
		
		if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
			codigoDocumento="04";
		}			
	
		factura.setCodDoc(codigoDocumento);
		
		factura.setCodPuntoEmision(facturaRequest.getAuthorizationCode().substring(27, 31));
		factura.setCodSecuencial(facturaRequest.getAuthorizationCode().substring(30, 40));
		factura.setContribuyenteEspecial(facturaRequest.getSpecialTaxpayer());
		
		factura.setCorreoNotificacion(facturaRequest.getClient().getEmail());
		factura.setDirEstablecimiento(facturaRequest.getClient().getAddress());
		String estadoFactura="";
		if (facturaRequest.getState().equalsIgnoreCase("AUT")) {
			estadoFactura= "AUTORIZADO";
			
		}			
		factura.setEstado(estadoFactura);
		
		
		
		factura.setFechaAutorizacion(parseDatetime(facturaRequest.getTransactiondate()));
		factura.setFechaEmisionBase(parseDate(facturaRequest.getBusinessDate()));
		factura.setFechaEmisionTmp(null);
		factura.setFechaEntReim(null);
		factura.setFechaLecTrad(null);
		factura.setFechaRecepMail(null);
		factura.setGuiaRemision(null);
		factura.setIce(BigDecimal.ZERO);
		factura.setIdentificacionComprador(facturaRequest.getClient().getClientId());
		factura.setIdentificadorUsuario(facturaRequest.getClient().getClientId());
		factura.setIdLote(null);
		factura.setImporteTotal(facturaRequest.getTotal());
		factura.setInfoAdicional(null);
		factura.setIrbp(BigDecimal.ZERO);
		factura.setIsOferton("N");
		factura.setIva(facturaRequest.getIva());
		factura.setMensajeReim(null);
		factura.setModoEnvio("OFFLINE");
		String moneda ="";
		if (facturaRequest.getCurrency().equalsIgnoreCase("USD")) {
			moneda= "DOLAR";
			
		}
		
		factura.setMoneda(moneda);
		
		
		factura.setNumeroAutorizacion(facturaRequest.getAuthorizationCode());
		factura.setObligadoContabilidad(facturaRequest.getRequiresAccounting());
		factura.setObservacionCancelacion(null);
		
		
		factura.setOrdenCompra(facturaRequest.getPurchaseOrder());
		factura.setProceso(null);
		factura.setPropina(facturaRequest.getTip());
		
		factura.setPtoEmision(facturaRequest.getAuthorizationCode().substring(27, 31));
		factura.setRazonSocialComprador(facturaRequest.getClient().getFirstName() + facturaRequest.getClient().getFirstSurname() );
		factura.setRequiereCancelacion(null);
		factura.setRuc(facturaRequest.getIssuerRuc());
		
		
		
		factura.setSecOriginal(null);
		factura.setTarea(facturaRequest.getState());
		factura.setTipoAmbiente(facturaRequest.getEnvironment());
		factura.setTipoEjecucion("SEC");
		factura.setTipoEmision(facturaRequest.getEmissionType());
		factura.setTipoGeneracion("EMI");
		factura.setTipoIdComprador(facturaRequest.getIdTypeClient());
		factura.setTotalDescuento(facturaRequest.getDiscountTotal());
		factura.setTotalSinImpuestos(facturaRequest.getUntaxedTotal());
		


    }
    
    
	
	
    public void crearNotaCreditoPersistencia(NotaCredito notaCredito, VOFactura facturaRequest, Connection conn) {
    

    	notaCredito.setAgencia(facturaRequest.getCompany());
		String directorio= parametroDAO.obtenerParametro(conn,"directorio");
		notaCredito.setArchivo(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".xml" );
		notaCredito.setArchivoLegible(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".pdf" );
	
		try {
			DocumentUtil.createDirectory(directorio + facturaRequest.getAuthorizationCode().substring(0, 8));
		} catch (FileNotFoundException e3) {

			log.error(e3.getStackTrace());
		}
		try {
			DocumentUtil.crearArchivoDirectorio(directorio + facturaRequest.getAuthorizationCode().substring(0, 8) +"/" + facturaRequest.getAuthorizationCode()+".xml",facturaRequest.getXmlUrl() );
		} catch (IOException e3) {

			log.error(e3.getStackTrace());
		}
	

		notaCredito.setClaveAcceso(facturaRequest.getAuthorizationCode());
		notaCredito.setClaveInterna("");
		notaCredito.setCodDocModificado("01");
		String codigoDocumento="";
		if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F)) {
			codigoDocumento="01";
		}
		
		if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
			codigoDocumento="04";
		}			
	
		notaCredito.setCodDoc(codigoDocumento);
		
		notaCredito.setCodPuntoEmision(facturaRequest.getAuthorizationCode().substring(27, 31));
		notaCredito.setCodSecuencial(facturaRequest.getAuthorizationCode().substring(30, 40));
		notaCredito.setContribuyenteEspecial(facturaRequest.getSpecialTaxpayer());
		
		notaCredito.setCorreoNotificacion(facturaRequest.getClient().getEmail());
		notaCredito.setDirEstablecimiento(facturaRequest.getClient().getAddress());
		String estadoFactura="";
		if (facturaRequest.getState().equalsIgnoreCase("AUT")) {
			estadoFactura= "AUTORIZADO";
			
		}			
		notaCredito.setEstado(estadoFactura);
		
		
		
		notaCredito.setFechaAutorizacion(parseDatetime(facturaRequest.getTransactiondate()));
		notaCredito.setFechaEntReim(null);
		notaCredito.setFechaLecTrad(null);
		notaCredito.setFechaRecepMail(null);
		notaCredito.setIdentificacionComprador(facturaRequest.getClient().getClientId());
		notaCredito.setIdentificadorUsuario(facturaRequest.getClient().getClientId());
		notaCredito.setIdLote(null);
		notaCredito.setInfoAdicional(null);
		notaCredito.setIsOferton("N");
		notaCredito.setMensajeReim(null);
		notaCredito.setModoEnvio("OFFLINE");
		String moneda ="";
		if (facturaRequest.getCurrency().equalsIgnoreCase("USD")) {
			moneda= "DOLAR";
			
		}
		
		notaCredito.setMoneda(moneda);
		
		
		notaCredito.setNumeroAutorizacion(facturaRequest.getAuthorizationCode());
		notaCredito.setObligadoContabilidad(facturaRequest.getRequiresAccounting());
		
		
		notaCredito.setOrdenCompra(facturaRequest.getPurchaseOrder());
		notaCredito.setProceso(null);
		notaCredito.setPtoEmision(facturaRequest.getAuthorizationCode().substring(27, 31));
		notaCredito.setRazonSocialComprador(facturaRequest.getClient().getFirstName() + facturaRequest.getClient().getFirstSurname() );
		notaCredito.setRuc(facturaRequest.getIssuerRuc());
		
		
		
		notaCredito.setTarea(facturaRequest.getState());
		notaCredito.setTipoAmbiente(facturaRequest.getEnvironment());
		notaCredito.setTipoEjecucion("SEC");
		notaCredito.setTipoEmision(facturaRequest.getEmissionType());
		notaCredito.setTipoGeneracion("EMI");
		notaCredito.setTipoIdComprador(facturaRequest.getIdTypeClient());
		notaCredito.setTotalSinImpuestos(facturaRequest.getUntaxedTotal());
		


    }
       
    
    
    
    
    
    
    
    
    
    
    
	
	public VOResponse validacionesCamposRequest(VOFactura facturaRequest, VOResponse respuesta, Connection conn) throws SQLException, GizloException, IOException {
		
		List<String> claveCabecera = new ArrayList<String>();
		claveCabecera.add("currency");
		claveCabecera.add("orderId");
		claveCabecera.add("emissionType");
		claveCabecera.add("user");
		claveCabecera.add("discountTotal");
		claveCabecera.add("untaxedTotal");
		claveCabecera.add("base0");
		claveCabecera.add("base12");
		claveCabecera.add("iva");
		claveCabecera.add("state");
		claveCabecera.add("issuerRuc");
		claveCabecera.add("requiresAccounting");
		claveCabecera.add("idTypeClient");
		claveCabecera.add("tip");
		claveCabecera.add("purchaseOrder");
		claveCabecera.add("totalInvoiceCost");
		claveCabecera.add("totalInvoicePrice");
		claveCabecera.add("total");
		claveCabecera.add("taxTotal");
		claveCabecera.add("exempt");
		claveCabecera.add("posNumber");
		claveCabecera.add("client");
		claveCabecera.add("payments");
		claveCabecera.add("products");
		claveCabecera.add("billUrl");
		claveCabecera.add("xmlUrl");
		claveCabecera.add("emmitedDocument");

		
		ObjectMapper mapper = new ObjectMapper();
        String jsonStrCabecera = mapper.writeValueAsString(facturaRequest);
		JsonNode jsonNodeCabecera = mapper.readTree(jsonStrCabecera);

		validacionCamposRequeridos(claveCabecera,jsonNodeCabecera,respuesta);
		if (respuesta.getCode().equals("401")){
			return respuesta;
		}
		

		if (facturaRequest.getCompany() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo company correspondiente al numero de pdv");
			
		}
		
		if (facturaRequest.getSpecialTaxpayer() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo specialTaxPrayer correspondiente al código del contribuyente");
			
		}		
		
		if (facturaRequest.getAuthorizationCode() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo authorizationCode correspondiente al numero de la factura");
			
		}		
		
		
		
		if (facturaRequest.getIdUser() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo idUser correspondiente a la id del usuario");
			
		}			
		
		if (facturaRequest.getTransactiondate() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo transactionDate correspondiente a la fecha de la transacción");
			
		}				
		if (facturaRequest.getBusinessDate() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo businessDate correspondiente a la fecha de emision");
			
		}				
		
		if (facturaRequest.getEnvironment() == null) {
			return respuesta.setResponse(respuesta,"401",
					"No se envia el campo environment correspondiente a la ID del ambiente \n produccion( 1 )y pruebas (2)");
			
		}				
				
		
		
		
		
		
		String sid= parametroDAO.obtenerFarmacia(conn,Long.parseLong(facturaRequest.getCompany()));

		
		CredencialDS credencialDS = new CredencialDS();
		String sidAmbiente= parametroDAO.obtenerParametro(conn,"sid_ambiente");
		String regionSidAmbiente= parametroDAO.obtenerParametro(conn,"sid_region");
		String directorioAmbienteFarmacia= parametroDAO.obtenerParametro(conn,"directorio_ambiente_farmacia");
		
		
		
		credencialDS.setDatabaseId(sid+sidAmbiente);
		
		//Conexión Farmacias por jdbc
		
		String usuarioFarmaciaRequest= parametroDAO.obtenerParametro(conn,"user_farmacia_request");
		String passwordFarmaciaRequest= parametroDAO.obtenerParametro(conn,"password_farmacia_request");
		credencialDS.setUsuario(usuarioFarmaciaRequest);
		credencialDS.setClave(passwordFarmaciaRequest);
		credencialDS.setDirectorio(directorioAmbienteFarmacia);
		credencialDS.setRegion(regionSidAmbiente);
		credencialDS.setDatabaseId(sid+sidAmbiente);
		Connection connFarmacia = null;						
		
		
		connFarmacia = Conexion.obtenerConexionFybeca(credencialDS);
			

		
		try {
			
			String correlativeTx= farmaciaDAO.obtenerDocumentoVentaPorCorrelative(connFarmacia, facturaRequest.getCorrelationTX(), "DOCUMENTO_VENTA");
			if (Boolean.FALSE.equals(correlativeTx.isEmpty())) {
				return respuesta.setResponse(respuesta,"402",
						"El codigo correlativeTx ya se ha ingresado en la base de Datos.");	

			}
			
			String estado= facturaRequest.getState();
			
			if (!estado.equalsIgnoreCase("AUT")) {
				return respuesta.setResponse(respuesta,"403",
						"Solo se debe subir comprobante autorizado");			
			}
			
			
			Date fechaAutorizacion;
			Date fechaEmisionBase;

			fechaAutorizacion=parseDatetime(facturaRequest.getTransactiondate());
			fechaEmisionBase=parseDatetime(facturaRequest.getBusinessDate());
			

			
			if (fechaAutorizacion == null) {
				return respuesta.setResponse(respuesta,"404",
						"Se debe aplicar la mascará: yyyy-MM-dd hh:mm para Transactiondate");
				
			}
			if (fechaEmisionBase == null) {
				return respuesta.setResponse(respuesta,"404",
						"Se debe aplicar la mascará: dd/MM/yyyy para BusinessDate");
				
			}				
			
			if (! (facturaRequest.getEnvironment().equalsIgnoreCase("1") || facturaRequest.getEnvironment().equalsIgnoreCase("2"))) {
				return respuesta.setResponse(respuesta,"405",
						"Solo se aceptan ambientes de pruebas(2) o produccion (1)");
				
			}		
			Boolean existeTipo =tipoIdentificacionClienteDAO.obtenerIdentificacion(conn,facturaRequest.getIdTypeClient());
			
					
			if (Boolean.FALSE.equals(existeTipo) ) {
				return respuesta.setResponse(respuesta,"406",
						"El tipo de identificacion del cliente no esta acorde a la ficha tecnica del SRI");
				
			}
	
			
			if (facturaRequest.getCorrelationTX()== null && facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F) ){
				return respuesta.setResponse(respuesta,"401",
						"El campo correlationTX es requerido para la factura");			
				}
			

			if (!(facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F) || facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC))) {
				return respuesta.setResponse(respuesta,"407",
						"Solo puede enviar los valores BILL o CREDIT_NOTE_BILL en el campo emmitedDocument  en el json.");		
				}

			if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_NC)) {
				Boolean existeNotaCredito =notaCreditoDAO.existeNotaCredito(facturaRequest.getAuthorizationCode());
				
				
				if (Boolean.TRUE.equals(existeNotaCredito)) {
					return respuesta.setResponse(respuesta,"408",
							"La Nota de crédito ya fue ingresada.");
					
				}					
				
				if (facturaRequest.getCorrelativeAssociatedTX()== null){
					return respuesta.setResponse(respuesta,"401",
							"El campo correlativeAssociatedTX es requerido para Nota de crédito");			
					}
				

				String idCorrelative= farmaciaDAO.obtenerDocumentoVentaPorCorrelative(connFarmacia,facturaRequest.getCorrelativeAssociatedTX(), "CP_VAR1");
				if (idCorrelative.isEmpty()){
					return respuesta.setResponse(respuesta,"409",
							"No se ha ingresado el campo correlativeAssociatedTX como Factura en el sistema");			
					}


				
				
			}			
			if (facturaRequest.getEmmitedDocument().toUpperCase().equalsIgnoreCase(Constantes.CODIGO_DOCUMENTO_F))	{
				Boolean existeFactura =facturaDAO.existeFactura(facturaRequest.getAuthorizationCode());
				
				
				if (Boolean.TRUE.equals(existeFactura)) {
					return respuesta.setResponse(respuesta,"410",
							"La Factura ya fue ingresada.");
					
				}					
			}
			
			
			
			
//Validacion de campos  Cliente			
			List<String> claveClientes = new ArrayList<String>();
			claveClientes.add("clientId");
			claveClientes.add("firstName");
			claveClientes.add("secondName");
			claveClientes.add("firstSurname");
			claveClientes.add("secondSurname");
			claveClientes.add("address");
			claveClientes.add("email");
			
	        ObjectMapper mapper2 = new ObjectMapper();
	        String jsonStrClient = mapper2.writeValueAsString(facturaRequest.getClient());
			JsonNode jsonNode = mapper.readTree(jsonStrClient);
 
			validacionCamposRequeridos(claveClientes,jsonNode,respuesta);
			if (respuesta.getCode().equals("401")){
				return respuesta;
			}
			

//Validacion de campos en el detalle Productos			
			List<String> claveProductos = new ArrayList<String>();
			claveProductos.add("quantity");
			claveProductos.add("priceList");
			claveProductos.add("ivaPercent");
			claveProductos.add("sku");

			
	        String jsonStrProducts = mapper.writeValueAsString(facturaRequest.getProducts());
			JsonNode jsonNodeProducts = mapper.readTree(jsonStrProducts);
 
			validacionCamposRequeridosLista(claveProductos,jsonNodeProducts,respuesta,"products");
			if (respuesta.getCode().equals("401")){
				return respuesta;
			}
			
			
//Validacion de campos en el detalle Pagos			
			List<String> clavePagos = new ArrayList<String>();
			clavePagos.add("payment");
			clavePagos.add("paymentMethod");
			clavePagos.add("authorizationCode");
			clavePagos.add("authorizationBulletin");
			clavePagos.add("interest");
			clavePagos.add("quota");
			clavePagos.add("amount");

			
	        String jsonStrPayments = mapper.writeValueAsString(facturaRequest.getPayments());
			JsonNode jsonNodePayments = mapper.readTree(jsonStrPayments);
 
			validacionCamposRequeridosLista(clavePagos,jsonNodePayments,respuesta,"payments");
			if (respuesta.getCode().equals("401")){
				return respuesta;
			}			
			
			for (VOPayment detalle : facturaRequest.getPayments()) {
	        	String codigoMetodoPagoFybeca = "";

				codigoMetodoPagoFybeca = parametroDAO.obtenerCodigoMetodoPagoFarmacia(conn,detalle.getPayment(),detalle.getPaymentMethod());

				if (codigoMetodoPagoFybeca.equalsIgnoreCase("-")){
					respuesta.setCode("411");
					respuesta.setMsg("Se debe parametrizar la forma de pago acorde a lo establecido en base.");
					return respuesta;
				}
				
				if (detalle.getPaymentMethod().equalsIgnoreCase("TCR") && (detalle.getCreditCardNumber()==null || detalle.getCreditCardNumber().isEmpty() )){
					respuesta.setCode("412");
					respuesta.setMsg("Si el metodo de pago es TCR debe ingresar el numero de tarjeta");
					return respuesta;
				}
				
				
				
			}			
			
			
			
			
			
			
			
			
			
			
			
			return respuesta;
			
//Validacion Forma de Pago

		

			
		}
		catch (Error e ){
			return respuesta.setResponse(respuesta,"500",
					"Error insperado en la validación");			
			}		
		finally {
			connFarmacia.close();
			
		}

	}
	
	public void validacionCamposRequeridos(List<String> claveClientes , JsonNode jsonNode, VOResponse respuesta)  {
	
	
		for (String valor : claveClientes){
			if (jsonNode.get(valor).isNull()) {
				respuesta.setCode("401");
				respuesta.setMsg("No se ha ingresado el campo "+valor + " en el request");
			}
			if (jsonNode.get(valor).isArray() && jsonNode.get(valor).size()==0) {
					respuesta.setCode("401");
					respuesta.setMsg("No se ha ingresado el detalle del  campo "+valor + " en el request");
			}
		}
	
	}
	
	public void validacionCamposRequeridosLista(List<String> claveClientes , JsonNode jsonNode, VOResponse respuesta, String nombreCampo)  {
		
		String campos="";
		for (JsonNode json : jsonNode) {
			for (String valor : claveClientes){
				if (json.get(valor).isNull()) {
					campos=campos+valor+", ";
				}
			}
		}
		
		if (Boolean.FALSE.equals(campos.isEmpty())) {
			respuesta.setCode("401");
			respuesta.setMsg("No se ha ingresado el campo "+campos + "correspondientes al key "
					+ nombreCampo
					+ " en el request");			}
	
	
	}
	
	
	
	
	
	
}	



