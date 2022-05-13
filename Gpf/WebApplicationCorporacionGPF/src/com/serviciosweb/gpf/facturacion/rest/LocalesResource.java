/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.rest;

import static com.gpf.serviciosweb.facturacion.farmacias.InformacionPadron.getInformacionPadron;
import static com.gpf.serviciosweb.facturacion.farmacias.PropuestaNivel.getPropuestaNivel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.serviciosweb.facturacion.farmacias.AutenticacionWbUsuarios;
import com.gpf.serviciosweb.facturacion.farmacias.AutorizarCheque;
import com.gpf.serviciosweb.facturacion.farmacias.ClubPersona;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaAcuPmc;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaArticuloInterCompany;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaCupoCompany;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaCupoDisponible;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaDetalle;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaGrupo;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaLocalesCercanos;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaPdvCercanosInterCompany;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaPersona;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaPmcNew;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaSaldo;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaSistemaSiebel;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaVademecum;
import com.gpf.serviciosweb.facturacion.farmacias.CreditoCorp;
import com.gpf.serviciosweb.facturacion.farmacias.CupoVitalCard;
import com.gpf.serviciosweb.facturacion.farmacias.DetallesAcumulados;
import com.gpf.serviciosweb.facturacion.farmacias.PagoAgilAutenticacion;
import com.gpf.serviciosweb.facturacion.farmacias.Puntomatico;
import com.serviciosweb.gizlo.modelos.Request;
import com.serviciosweb.gizlo.utilitarios.ConsumoWS;
import com.serviciosweb.gpf.facturacion.farmacias.aseguradoraAbf.AseguradoraAbf;
import com.serviciosweb.gpf.facturacion.farmacias.pfizer.ServiciosPfizer;
import com.serviciosweb.gpf.facturacion.servicios.AbfTrx50Services;
import com.serviciosweb.gpf.facturacion.servicios.CrmPopUpServices;
import com.serviciosweb.gpf.facturacion.servicios.GestionaService;
import com.serviciosweb.gpf.facturacion.servicios.ImagenPromociones;
import com.serviciosweb.gpf.facturacion.servicios.TransferenciasIntercompanyServices;

/**
 * REST Web Service
 *
 * @author mftellor
 */

@Path("Locales")
public class LocalesResource {
	@Context
	private UriInfo context;
	Logger log = Logger.getLogger(this.getClass().getName());

	/** Creates a new instance of LocalesResource */
	public LocalesResource() {
	}

	/**
	 * Retrieves representation of an instance of
	 * com.serviciosweb.gpf.facturacion.farmacias.LocalesResource
	 * 
	 * @return an instance of java.lang.String
	 */
	@GET
	@Produces("application/xml")
	public String getXml() {
		// TODO return proper representation object
		throw new UnsupportedOperationException();
	}

	/**
	 * PUT method for updating or creating an instance of LocalesResource
	 * 
	 * @param content
	 *            representation for the resource
	 * @return an HTTP response with content of the updated or created resource.
	 */
	@PUT
	@Consumes("application/xml")
	public void putXml(String content) {
	}

	@GET
	@Path("/ConsultaAcuPmc/{p_persona}/{p_promo}/{p_item}/{empresa}")
	@Produces("text/xml")
	public String ConsultaAcuPmc(@PathParam("p_persona") String p_persona, @PathParam("p_promo") String p_promo,
			@PathParam("p_item") String p_item, @PathParam("empresa") String empresa,
			@Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaAcuPmc:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaAcuPmc consultaAcuPmc = new ConsultaAcuPmc(p_persona, p_promo, p_item, empresa);
			return consultaAcuPmc.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaPmcAcu/{p_persona}/{p_promo}/{p_item}/{empresa}")
	@Produces("text/xml")
	public String ConsultaPmcAcu(@PathParam("p_persona") String p_persona, @PathParam("p_promo") String p_promo,
			@PathParam("p_item") String p_item, @PathParam("empresa") String empresa,
			@Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaAcuPmc:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaAcuPmc consultaAcuPmc = new ConsultaAcuPmc(p_persona, p_promo, p_item, new Integer(empresa));
			return consultaAcuPmc.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Produces("text/xml")
	@Path("/ConsultaSaldo/{vnCuenta}")
	public String ConsultaSaldo(@PathParam("vnCuenta") String vnCuenta, @Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaSaldo:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaSaldo consultaSaldo = new ConsultaSaldo(vnCuenta);
			return consultaSaldo.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaPmcNew/{p_persona}/{p_item}/{p_tipo_id}/{p_cod_club}/{empresa}")
	@Produces("text/xml")
	public String consultaPmcNew(@PathParam("p_persona") String p_persona, @PathParam("p_item") String p_item,
			@PathParam("p_tipo_id") String p_tipo_id, @PathParam("p_cod_club") String p_cod_club,
			@PathParam("empresa") String empresa, @Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaPmcNew:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaPmcNew consultaPmcNew = new ConsultaPmcNew(p_persona, p_item, p_tipo_id, p_cod_club,
					Integer.decode(empresa), null);
			// log.info("RESULTADO consultaPmcNew
			// "+consultaPmcNew.getResultadoXml());
			return consultaPmcNew.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaDetalle/{pbodega}/{pdocumento}")
	@Produces("text/xml")
	public String ConsultaDetalle(@PathParam("pbodega") String pbodega, @PathParam("pdocumento") String pdocumento,
			@Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaDetalle:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaDetalle consultaDetalle = new ConsultaDetalle(pbodega, pdocumento);
			// log.info("RESULTADO consultaDetalle
			// "+consultaDetalle.getResultadoXml());
			return consultaDetalle.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaGrupo/{pbodega}/{pdocumento}")
	@Produces("text/xml")
	public String ConsultaGrupo(@PathParam("pbodega") String pbodega, @PathParam("pdocumento") String pdocumento,
			@Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaGrupo:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaGrupo consultaGrupo = new ConsultaGrupo(pbodega, pdocumento);
			// log.info("RESULTADO ConsultaGrupo
			// "+consultaGrupo.getResultadoXml());
			return consultaGrupo.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaGrupo/{idFarmacia}/{fechaInicio}/{fechaFin}")
	@Produces("text/xml")
	public String ConsultaGrupo(@PathParam("idFarmacia") String idFarmacia,
			@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin,
			@Context HttpServletRequest requestContext) {
		try {
			// log.info("IP SOLICITA ConsultaGrupo NEW:
			// "+requestContext.getRemoteAddr().toString());
			ConsultaGrupo consultaGrupo = new ConsultaGrupo(idFarmacia, fechaInicio, fechaFin);
			// log.info("RESULTADO ConsultaGrupo NEW
			// "+consultaGrupo.getResultadoXml());
			return consultaGrupo.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaCupoDisponible/{idPersona}")
	@Produces("text/xml")
	public String getConsultaCupoDisponible(@PathParam("idPersona") String idPersona) {
		ConsultaCupoDisponible consultaCupoDisponible = new ConsultaCupoDisponible(idPersona);
		return consultaCupoDisponible.getResultadoXml();
	}

	@GET
	@Path("/CopiarImagenesProdLocales")
	@Produces("text/html")
	public String CopiarImagenesProdLocales() {

		return "INICIA COPIA IMAGENES";
	}

	@GET
	@Path("/AutorizarCheque/{tipocedula}/{cedula}/{anyoNacimiento}/{banco}/{numerocuenta}/{numerocheque}/{monto}/{codestablecimiento}/{sexo}/{telefono}")
	@Produces("text/xml")
	public String AutorizarCheque(@PathParam("tipocedula") String tipocedula, @PathParam("cedula") String cedula,
			@PathParam("anyoNacimiento") String anyoNacimiento, @PathParam("banco") String banco,
			@PathParam("numerocuenta") String numerocuenta, @PathParam("numerocheque") String numerocheque,
			@PathParam("monto") String monto, @PathParam("codestablecimiento") String codestablecimiento,
			@PathParam("sexo") String sexo, @PathParam("telefono") String telefono,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA AutorizarCheque:
		// "+requestContext.getRemoteAddr().toString());
		try {
			AutorizarCheque autorizarCheque = new AutorizarCheque(tipocedula, cedula, anyoNacimiento, banco,
					numerocuenta, numerocheque, monto, codestablecimiento, sexo, telefono);
			return autorizarCheque.getResponse();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConfirmarAutorizacionCheque/{cedula}/{banco}/{numerocuenta}/{numerocheque}/{resultado}/{codigoC}")
	@Produces("text/xml")
	public String ConfirmarAutorizacionCheque(@PathParam("cedula") String cedula, @PathParam("banco") String banco,
			@PathParam("numerocuenta") String numerocuenta, @PathParam("numerocheque") String numerocheque,
			@PathParam("resultado") String resultado, @PathParam("codigoC") String codigoC,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA ConfirmarAutorizacionCheque:
		// "+requestContext.getRemoteAddr().toString());
		try {
			AutorizarCheque confirmarAutorizacionCheque = new AutorizarCheque(cedula, banco, numerocuenta, numerocheque,
					resultado, codigoC);
			return confirmarAutorizacionCheque.getResponse();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// ------------------------CAMBIO ANULACION CHEQUE --------

	@GET
	@Path("/AnularAutorizacionCheque/{cedula}/{numerocuenta}/{numerocheque}/{resultadodoc}/{codigoC}")
	@Produces("text/xml")
	public String AnularAutorizacionCheque(@PathParam("cedula") String cedulaC,
			@PathParam("numerocuenta") String numeroCuentaC, @PathParam("numerocheque") String numeroChequeC,
			@PathParam("resultadodoc") String resultadoDoc, @PathParam("codigoC") String codigoC,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA AnularAutorizacionCheque:
		// "+requestContext.getRemoteAddr().toString());
		try {
			AutorizarCheque confirmarAutorizacionCheque = new AutorizarCheque(cedulaC, numeroCuentaC, numeroChequeC,
					resultadoDoc, codigoC);
			return confirmarAutorizacionCheque.getResponse();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// --------------------------------------------------------
	@GET
	@Path("/VerificarCedula/{cedula}")
	@Produces("text/xml")
	public String VerificarCedula(@PathParam("cedula") String cedula) {
		com.gpf.serviciosweb.facturacion.farmacias.VerificarCedula verificarCedula = new com.gpf.serviciosweb.facturacion.farmacias.VerificarCedula(
				cedula);
		return verificarCedula.getResultadoXml();

	}

	// PFIZER
	@GET
	@Path("/ProcesarCompra/{programaIntld}/{cadenaIntld}/{sucursalIntld}/{terminalIntld}/{tarjeta}/{usuarioStrId}/{usuarioCapturaStrId}/{itemsCantidad}")
	@Produces("text/xml")
	public String ProcesarCompra(@PathParam("programaIntld") String programaIntld,
			@PathParam("cadenaIntld") String cadenaIntld, @PathParam("sucursalIntld") String sucursalIntld,
			@PathParam("terminalIntld") String terminalIntld, @PathParam("tarjeta") String tarjeta,
			@PathParam("usuarioStrId") String usuarioStrId,
			@PathParam("usuarioCapturaStrId") String usuarioCapturaStrId,
			@PathParam("itemsCantidad") String itemsCantidad, @Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA ProcesarCompra:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.procesarCompra(programaIntld, cadenaIntld, sucursalIntld, terminalIntld, tarjeta,
					usuarioStrId, usuarioCapturaStrId, itemsCantidad);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/CerrarCompra/{cotizacionId}/{tarjeta}")
	@Produces("text/xml")
	public String CerrarCompra(@PathParam("cotizacionId") String cotizacionId, @PathParam("tarjeta") String tarjeta,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA CerrarCompra:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.cerrarCompra(cotizacionId, tarjeta);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/CancelarCompra/{cotizacionId}/{tarjeta}")
	@Produces("text/xml")
	public String CancelarCompra(@PathParam("cotizacionId") String cotizacionId, @PathParam("tarjeta") String tarjeta,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA CancelarCompra:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.cancelarCompra(cotizacionId, tarjeta);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/ConsultarDevolucion/{programaIntld}/{cadenaIntld}/{sucursalIntld}/{terminalIntld}/{tarjeta}/{usuarioStrId}/{cotizacionIntId}/{referTransactionDevIntId}")
	@Produces("text/xml")
	public String ConsultarDevolucion(@PathParam("programaIntld") String programaIntld,
			@PathParam("cadenaIntld") String cadenaIntld, @PathParam("sucursalIntld") String sucursalIntld,
			@PathParam("terminalIntld") String terminalIntld, @PathParam("tarjeta") String tarjeta,
			@PathParam("usuarioStrId") String usuarioStrId, @PathParam("cotizacionIntId") String cotizacionIntId,
			@PathParam("referTransactionDevIntId") String referTransactionDevIntId,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA ConsultarDevolucion:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.consultarDevolucion(programaIntld, cadenaIntld, sucursalIntld, terminalIntld,
					tarjeta, usuarioStrId, referTransactionDevIntId, cotizacionIntId);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/DevolverCompra/{programaIntld}/{cadenaIntld}/{sucursalIntld}/{terminalIntld}/{tarjeta}/{usuarioStrId}/{cotizacionId}/{referTransactionDevIntId}/{itemsCantidad}")
	@Produces("text/xml")
	public String DevolverCompra(@PathParam("programaIntld") String programaIntld,
			@PathParam("cadenaIntld") String cadenaIntld, @PathParam("sucursalIntld") String sucursalIntld,
			@PathParam("terminalIntld") String terminalIntld, @PathParam("tarjeta") String tarjeta,
			@PathParam("usuarioStrId") String usuarioStrId, @PathParam("cotizacionId") String cotizacionId,
			@PathParam("referTransactionDevIntId") String referTransactionDevIntId,
			@PathParam("itemsCantidad") String itemsCantidad, @Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA DevolverCompra:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.DevolverCompra(programaIntld, cadenaIntld, sucursalIntld, terminalIntld, tarjeta,
					usuarioStrId, cotizacionId, referTransactionDevIntId, itemsCantidad);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/ConsultaTransacciones/{programaID}/{cadenaid}/{fechaInicial}/{fechaFinal}")
	@Produces("text/xml")
	public String ConsultaTransacciones(@PathParam("programaID") String programaID,
			@PathParam("cadenaid") String cadenaid, @PathParam("fechaInicial") String fechaInicial,
			@PathParam("fechaFinal") String fechaFinal, @Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA ConsultaTransacciones:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.ConsultaTransacciones(programaID, cadenaid, fechaInicial, fechaFinal);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	// PFIZER
	@GET
	@Path("/ConsultaTarjeta/{programaIntId}/{cadenaIntId}/{sucursalIntId}/{terminalIntId}/{usuarioStrId}/{tarjeta}/{cedula}/{sku}")
	@Produces("text/xml")
	public String ConsultaTarjeta(@PathParam("programaIntId") String programaIntId,
			@PathParam("cadenaIntId") String cadenaIntId, @PathParam("sucursalIntId") String sucursalIntId,
			@PathParam("terminalIntId") String terminalIntId, @PathParam("usuarioStrId") String usuarioStrId,
			@PathParam("tarjeta") String tarjeta, @PathParam("cedula") String cedula, @PathParam("sku") String sku,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA ConsultaTarjeta:
		// "+requestContext.getRemoteAddr().toString());
		try {
			ServiciosPfizer serviciosPfizer = new ServiciosPfizer();
			serviciosPfizer.setIpInvoca(requestContext.getRemoteAddr().toString());
			return serviciosPfizer.ConsultaTarjeta(programaIntId, cadenaIntId, sucursalIntId, terminalIntId,
					usuarioStrId, tarjeta, cedula, sku);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}

	}

	// AUTENTICACION PAGO AGIL
	@GET
	@Path("/PagoAgilAutenticacion/{usuario}")
	@Produces("text/html")
	public String PagoAgilAutenticacionRest(@PathParam("usuario") String usuario,
			@Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA PagoAgilAutenticacion:
		// "+requestContext.getRemoteAddr().toString());
		// System.gc();
		// log.info("PagoAgilAutenticacion "+usuario);
		return new PagoAgilAutenticacion(usuario).getUrl();
	}

	@GET
	@Path("/CupoVitalCard/{numeroTarjeta}/{valorConsumo}/{banderaConsumo}/{pn_farmacia}/{pv_numero_autorizacion}/{pn_documento_venta}")
	@Produces("text/xml")
	public String CupoVitalCardRest(@PathParam("numeroTarjeta") String numeroTarjeta,
			@PathParam("valorConsumo") String valorConsumo, @PathParam("banderaConsumo") String banderaConsumo,
			@PathParam("pn_farmacia") String pn_farmacia,
			@PathParam("pv_numero_autorizacion") String pv_numero_autorizacion,
			@PathParam("pn_documento_venta") String pn_documento_venta, @Context HttpServletRequest requestContext) {
		// log.info("IP SOLICITA CupoVitalCard:
		// "+requestContext.getRemoteAddr().toString());
		try {
			String respuesta = new CupoVitalCard(numeroTarjeta, valorConsumo, banderaConsumo, pn_farmacia,
					pv_numero_autorizacion, pn_documento_venta).getResultadoXML();
			return Constantes.respuestaXmlObjeto("OK", respuesta, null);
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaSaldo/{cedula}/{empresa}")
	@Produces("text/xml")
	public String ConsultaSaldoRest(@PathParam("cedula") String cedula, @PathParam("empresa") String empresa,
			@Context HttpServletRequest requestContext) {
		// System.gc();
		return new ConsultaPersona(cedula, empresa).getResultadoXml();
	}

	@GET
	@Path("/ConsultaDatosPersona/{cedula}")
	@Produces("text/xml")
	public String ConsultaDatosPersona(@PathParam("cedula") String cedula, @Context HttpServletRequest requestContext) {
		// System.gc();
		return new ConsultaPersona(cedula, null).getResultadoXml();
	}

	@GET
	@Path("/ConsultaLocalesCercanos/{idLocal}/{item}/{empresa}/{incluirOtrasCadenas}")
	@Produces("text/xml")
	public String ConsultaLocalesCercanosRest(@PathParam("idLocal") String idLocal, @PathParam("item") String item,
			@PathParam("empresa") String empresa, @PathParam("incluirOtrasCadenas") String incluirOtrasCadenas,
			@Context HttpServletRequest requestContext) {
		ConsultaLocalesCercanos consultaLocalesCercanos = new ConsultaLocalesCercanos(idLocal, item, empresa,
				incluirOtrasCadenas);
		return consultaLocalesCercanos.getResultadoXml();
	}

	@GET
	@Path("/DetallesAcumulados/{persona}/{promocion}/{item}/{empresa}")
	@Produces("text/xml")
	public String DetallesAcumuladosRest(@PathParam("item") String item, @PathParam("persona") String persona,
			@PathParam("promocion") String promocion, @PathParam("empresa") String empresa) {
		return new DetallesAcumulados(item, persona, promocion, empresa).getResultadoXml();
	}

	@GET
	@Path("/ClubPersonaGrabar/{persona}/{club}")
	@Produces("text/xml")
	public String ClubPersonaGrabarRest(@PathParam("persona") String persona, @PathParam("club") String club) {
		return new ClubPersona().setClubPersona(persona, club);
	}

	@GET
	@Path("/ClubPersonaObtener/{persona}/{club}")
	@Produces("text/xml")
	public String ClubPersonaObteherRest(@PathParam("persona") String persona, @PathParam("club") String club) {
		return new ClubPersona().getClubPersona(persona, club);
	}

	@GET
	@Path("/Puntomatico/{fechaTransaccion}/{codigoLocal}/{codigoUsuario}/{valor}/{tipo}/{idTransaccion}")
	@Produces("text/xml")
	public String PuntomaticoRest(@PathParam("fechaTransaccion") String fechaTransaccion,
			@PathParam("codigoLocal") String codigoLocal, @PathParam("codigoUsuario") String codigoUsuario,
			@PathParam("valor") String valor, @PathParam("tipo") String tipo,
			@PathParam("idTransaccion") String idTransaccion) {
		return new Puntomatico(fechaTransaccion, codigoLocal, codigoUsuario, valor, tipo, idTransaccion)
				.getResultadoXml();
	}

	@GET
	@Path("/AutenticacionWbUsuarios/{usuario}/{clave}")
	@Produces("text/xml")
	public String AutenticacionWbUsuariosRest(@PathParam("usuario") String usuario, @PathParam("clave") String clave) {
		return new AutenticacionWbUsuarios(usuario, clave).getResultadoXml();
	}

	@GET
	@Path("/InformacionPadron/{cedula}")
	@Produces("text/xml")
	public String InformacionPadronRest(@PathParam("cedula") String cedula) {
		return getInformacionPadron(cedula).getResultadoXml();
	}

	@GET
	@Path("/PropuestaNivel/{codigoLocal}")
	@Produces("text/xml")
	public String PropuestaNivelRest(@PathParam("codigoLocal") String codigoLocal) {
		String resultado = getPropuestaNivel(codigoLocal);
		if (resultado == null)
			return Constantes.respuestaXmlObjeto("ERROR", "ERROR FATAL AL COPIAR INFORMACION DEL PROD AL LOCAL");
		else if (resultado.equals("S"))
			return Constantes.respuestaXmlObjeto("OK", "SE COPIA INFORMACION DEL PROD AL LOCAL " + codigoLocal);
		else {
			String resultadoXml = Constantes.respuestaXmlObjeto("ERROR",
					"LA INFORMACION ESTA INCOMPLETA, REINTE EN UNOS MINUTOS");
			resultadoXml = resultadoXml.replace("504", "404");
			return resultadoXml;
		}
	}

	// servicios rest aseguradoras

	@GET
	@Path("/ValidaPlanAbf/{codigoDependiente}/{identificacion}/{contrato}")
	@Produces("text/xml")
	public String ValidaPlanAbf(@PathParam("codigoDependiente") String codigoDependiente,
			@PathParam("identificacion") String identificacion, @PathParam("contrato") String contrato) {
		log.info("ValidaPlanAbf: " + identificacion + ":" + contrato + ":" + codigoDependiente + ":" + new Date());
		return new AseguradoraAbf(identificacion, contrato, codigoDependiente, "codigoAseguradora").getResultadoXml();
	}

	@GET
	@Path("/ValidaCarenciaAbf/{codigoDependiente}/{identificacion}/{contrato}/{aseguradora}/{diagnostico}")
	@Produces("text/xml")
	public String ValidaCarenciaAbf(@PathParam("codigoDependiente") String codigoDependiente,
			@PathParam("identificacion") String identificacion, @PathParam("contrato") String contrato,
			@PathParam("aseguradora") String aseguradora, @PathParam("diagnostico") String diagnostico) {
		log.info("ValidaCarenciaAbf: " + identificacion + ":" + contrato + ":" + codigoDependiente + ":" + diagnostico
				+ ":" + new Date());
		return new AseguradoraAbf(identificacion, contrato, codigoDependiente, aseguradora, diagnostico)
				.getResultadoXml();
	}

	@GET
	@Path("/ValidaTopesAbf/{contrato}/{aseguradora}/{valorCompra}")
	@Produces("text/xml")
	public String ValidaTopesAbf(@PathParam("contrato") String contrato, @PathParam("aseguradora") String aseguradora,
			@PathParam("valorCompra") String valorCompra) {
		log.info("ValidaTopesAbf: " + contrato + ":" + aseguradora + ":" + valorCompra + ":" + new Date());
		return new AseguradoraAbf(contrato, aseguradora, valorCompra).getResultadoXml();
	}

	@GET
	@Path("/ValidaCupones/{codigoCliente}/{codigoBono}")
	@Produces("text/xml")
	public String ValidaCuponesRest(@PathParam("codigoCliente") String codigoCliente,
			@PathParam("codigoBono") String codigoBono) {
		return new ClubPersona(codigoCliente, codigoBono, "").getResultadoXml();
	}

	@GET
	@Path("/ValidaClientes/{codigoBono}/{identificacion}")
	@Produces("text/xml")
	public String ValidarClienteCupon(@PathParam("codigoBono") String codigoBono,
			@PathParam("identificacion") String identificacion) {
		return new ClubPersona(codigoBono, identificacion).getResultadoXml();
	}

	@GET
	@Path("/ModificarCupones/{codigoCliente}/{codigoBono}/{codigoBarra}")
	@Produces("text/xml")
	public String ModificarCupones(@PathParam("codigoCliente") String codigoCliente,
			@PathParam("codigoBono") String codigoBono, @PathParam("codigoBarra") String codigoBarra) {
		return new ClubPersona(codigoCliente, codigoBono, codigoBarra, "update").getResultadoXml();
	}

	@GET
	@Path("/imgCupones/{codigoItem}/")
	@Produces("image/jpeg")
	public byte[] imgCupones(@PathParam("codigoItem") String codigoItem) {
		return new ImagenPromociones(codigoItem).resultadoByte;
	}

	@GET
	@Path("/ConsultaVademecum/{itemID}/{itemDescription}")
	@Produces("text/xml")
	public String ConsultaVademecum(@PathParam("itemID") String itemID,
			@PathParam("itemDescription") String itemDescription, @Context HttpServletRequest requestContext) {
		try {

			ConsultaVademecum consultaVademecum = new ConsultaVademecum(itemID, itemDescription);
			return consultaVademecum.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/consultaSistema/{customerID}/{storeID}")
	@Produces("text/xml")
	public String ConsultaVademecumPrincipioAtivo(@PathParam("customerID") String customerID,
			@PathParam("storeID") String storeID, @Context HttpServletRequest requestContext) {
		try {

			ConsultaSistemaSiebel consultaSistemaSiebel = new ConsultaSistemaSiebel(customerID, storeID);
			return consultaSistemaSiebel.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "FALLO EL WS", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaPdvCercanosInterCompany/{idLocal}/{empresa}/{ambiente}")
	@Produces("text/xml")
	public String ConsultaPdvCercanosInterComp(@PathParam("idLocal") String idLocal,
			@PathParam("empresa") String empresa, @PathParam("ambiente") String ambiente, @Context HttpServletRequest requestContext) {
		try {
			ConsultaPdvCercanosInterCompany consultaLocalesCercanos = new ConsultaPdvCercanosInterCompany(idLocal,	empresa, ambiente);
			return consultaLocalesCercanos.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA MOSTRAR", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaArticuloInterCompany/{item}/{pdv}/{sid}/{tipo}")
	@Produces("text/xml")
	public String ConsultaArticuloInterComp(@PathParam("item") String item, @PathParam("pdv") String pdv,
			@PathParam("sid") String sid, @PathParam("tipo") String tipo, @Context HttpServletRequest requestContext) {
		try {
			ConsultaArticuloInterCompany consultaArticuloInterCompany = new ConsultaArticuloInterCompany(item, pdv, sid,
					tipo);
			return consultaArticuloInterCompany.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA MOSTRAR", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/ConsultaCupoComp/{codigocc}")
	@Produces("text/xml")
	public String ConsultaCupoComp(@PathParam("codigocc") String codigocc, @Context HttpServletRequest requestContext) {
		try {
			ConsultaCupoCompany consultaCupoComp = new ConsultaCupoCompany(codigocc);
			return consultaCupoComp.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA MOSTRAR", null);
		} finally {
			// System.gc();
		}
	}

	@GET
	@Path("/CreditoCorp/{codigo_convenio_corporativo}/{tipo_movimiento}/{clasificacion_movimiento}/{farmacia}/{documento}/{numero_factura}/{valor}/{valor_iva}/{fecha_factura}/{llevado}/{estado}/{usuario}")
	@Produces("text/xml")
	// String CODIGO,String CODIGO_CONENIO_CORPORATIVO,Integer FARMACIA,String
	// DOCUMENTO,String NUMERO_FACTURA, Double VALOR, Double VALOR_IVA, Date
	// FECHA_TRANSACCION, Date FECHA_FACTURA, String USUARIO
	public String CreditoCorp(@PathParam("codigo_convenio_corporativo") String codigo_convenio_corporativo,
			@PathParam("tipo_movimiento") String tipo_movimiento,
			@PathParam("clasificacion_movimiento") String clasificacion_movimiento,
			@PathParam("farmacia") String farmacia, @PathParam("documento") String documento,
			@PathParam("numero_factura") String numero_factura, @PathParam("valor") String valor,
			@PathParam("valor_iva") String valor_iva, @PathParam("fecha_factura") String fecha_factura,
			@PathParam("llevado") String llevado, @PathParam("estado") String estado,
			@PathParam("usuario") String usuario, @Context HttpServletRequest requestContext) {

		try {
			CreditoCorp creditoCorp = new CreditoCorp(codigo_convenio_corporativo, tipo_movimiento,
					clasificacion_movimiento, farmacia, documento, numero_factura, valor, valor_iva, fecha_factura,
					llevado, estado, usuario);
			return creditoCorp.getResultadoXml();
		} catch (Exception e) {
			return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA MOSTRAR", null);
		} finally {
			// System.gc();
		}

	}

	@GET
	@Path("/AutorizarChequeSanaSana/{tipocedula}/{cedula}/{annac}/{banco}/{numerocuenta}/{numerocheque}/{monto}/{codestablecimiento}/{sexo}/{telefono}")
	@Produces("text/xml")
	public String chequesGestiona(@PathParam("tipocedula") String tipocedula, @PathParam("cedula") String cedula,
			@PathParam("annac") String annac, @PathParam("banco") String banco,
			@PathParam("numerocuenta") String numerocuenta, @PathParam("numerocheque") String numerocheque,
			@PathParam("monto") String monto, @PathParam("codestablecimiento") String codestablecimiento,
			@PathParam("sexo") String sexo, @PathParam("telefono") String telefono,
			@Context HttpServletRequest requestContext) {

		try {

			HashMap<String, Object> mapa = new HashMap<String, Object>();

			mapa.put("tipocedula", tipocedula);
			mapa.put("cedula", cedula);
			mapa.put("annac", annac);
			mapa.put("banco", banco);
			mapa.put("numerocuenta", numerocuenta);
			mapa.put("numerocheque", numerocheque);
			mapa.put("monto", monto);
			mapa.put("codestablecimiento", codestablecimiento);
			mapa.put("sexo", sexo);
			mapa.put("telefono", telefono);

			GestionaService gestiona = new GestionaService();
			return gestiona.consultaCheque(mapa);
		} catch (Exception e) {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<Transaccion><Respuesta>Los datos enviados no son correctos</Respuesta></Transaccion>";
		}

	}

	@GET
	@Path("/copiaPdvCrmPopUp/{empresa}")
	@Produces("text/xml")
	public String copiaPdvCrmPopUp(@PathParam("empresa") Integer empresa, @Context HttpServletRequest requestContext) {

		try {
			CrmPopUpServices crmPopUpServices = new CrmPopUpServices(empresa);
			return crmPopUpServices.copiaPdv();
		} catch (Exception e) {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<Transaccion><Respuesta>Los datos enviados no son correctos</Respuesta></Transaccion>";
		}

	}
	
	@GET
	@Path("/copiaTransItercomACentral/{empresaOrigen}/{empresaDestino}/{farmacia}/{documento}")
	@Produces("text/xml")
	public String copiaTransItercomACentral(@PathParam("empresaOrigen") Integer empresaOrigen, @PathParam("empresaDestino") Integer empresaDestino , @PathParam("farmacia") Integer farmacia, @PathParam("documento") Long documento,  @Context HttpServletRequest requestContext) {
		StringBuilder respuestaXml = new StringBuilder();
		respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		respuestaXml.append("<Transaccion><Respuesta>El proceso esta siendo ejecutado</Respuesta></Transaccion>");
		try {
			TransferenciasIntercompanyServices transferenciasIntercompanyServices = new TransferenciasIntercompanyServices(empresaOrigen, empresaDestino, documento, farmacia);
			final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
			executor.schedule(transferenciasIntercompanyServices, 0, TimeUnit.MINUTES);
			return respuestaXml.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<Transaccion><Respuesta>Los datos enviados no son correctos</Respuesta></Transaccion>";
		}

	}
	
	
	@GET
	@Path("/abfTrx50Services/{prIdenCade}/{prIdenSucu}/{prCA1}/{prNumeFact}")
	@Produces("text/xml")
	public String abfTrx50Services(@PathParam("prIdenCade") String prIdenCade
								 , @PathParam("prIdenSucu") String prIdenSucu
								 , @PathParam("prCA1") String prCA1
								 , @PathParam("prNumeFact") String prNumeFact,  @Context HttpServletRequest requestContext) {
		StringBuilder respuestaXml = new StringBuilder();
		respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		respuestaXml.append("<Transaccion><Respuesta>El proceso esta siendo ejecutado</Respuesta></Transaccion>");
		try {
			AbfTrx50Services abfTrx50Services = new AbfTrx50Services( prIdenCade ,
					 prIdenSucu ,
					 prCA1      ,
					 prNumeFact);
			return abfTrx50Services.abfTrx50ServicesResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<Transaccion><Respuesta>Los datos enviados no son correctos</Respuesta></Transaccion>";
		}

	}

	@GET
	@Path("/consumoWebService/{amount}/{storeCode}/{correlationTX}")
	@Produces("text/xml")
	public String consumoWebService(@PathParam("amount") BigDecimal amount
								 , @PathParam("storeCode") String storeCode
								 , @PathParam("correlationTX") String correlationTX, @Context HttpServletRequest requestContext) {
		
		System.out.println(amount);
		System.out.println(storeCode);
		System.out.println(correlationTX);
		


		try {
			Request request = new Request();
			request.setAmount(amount);
			request.setStoreCode(storeCode);
			request.setCorrelationTX(correlationTX);

			ConsumoWS consumo = new ConsumoWS();
			consumo.ConsumoWSCancelacion(request);
			
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<transaccion>"
			+ "<httpStatus>104</httpStatus>"
			+ "<mensaje>"
			+ consumo.respuesta
			+ "</mensaje></transaccion>";
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<transaccion><httpStatus>502</httpStatus><mensaje>"+e.getMessage()+"</mensaje></transaccion>";
		}		
		
		
		
		
		

	}		
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
