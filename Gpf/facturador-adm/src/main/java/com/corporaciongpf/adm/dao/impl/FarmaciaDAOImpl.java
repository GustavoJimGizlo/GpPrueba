package com.corporaciongpf.adm.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.corporaciongpf.adm.dao.FarmaciaDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.vo.VOCreditoDevolucion;
import com.corporaciongpf.adm.vo.VOFacturasMas;
import com.corporaciongpf.adm.vo.VOFarmaciaDetalleFormaPago;
import com.corporaciongpf.adm.vo.VOFarmaciaFactura;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaAdicional;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaDetalle;
import com.corporaciongpf.adm.vo.VOFarmaciaNotaCredito;
import com.corporaciongpf.adm.vo.VOSecuenciaElectronica;



@Stateless
public class FarmaciaDAOImpl implements FarmaciaDAO {
	private static Logger log = Logger.getLogger(FarmaciaDAOImpl.class.getName());

	
	public String obtenerClasificacionMovimiento(Connection conn,String tipoMovimiento) throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  select  CODIGO from AD_CLASIFICACION_MOVIMIENTOS where TIPO_MOVIMIENTO =?  ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, tipoMovimiento);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("CODIGO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
	}
	
	
	
	
	
	
	
	
	
	

	public void actualizarCupoTarjetaConvenioEmpresarial(Connection conn, String identificacion, BigDecimal monto)throws GizloException{
		
		List<BigDecimal> respuesta = new ArrayList<BigDecimal>();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  select cupo from vc_tarjetas  where "
				+ "codigo_persona =  (select codigo from ab_personas where identificacion = ?)    "
				+ " and PRODUCTO_VITALCARD = 'VCON'");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, identificacion);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getBigDecimal("cupo"));
			}
			
			if (respuesta.size()>0) {
				BigDecimal cupo = respuesta.get(0);	 
				log.info("Cupo ");
				log.info(cupo);
				
				BigDecimal nuevocupo= cupo.subtract(monto);
				log.info("Cupo Substraido");
				log.info(nuevocupo);			
				
				StringBuilder sqlStringCupo= new StringBuilder();
				sqlStringCupo.append("update vc_tarjetas set cupo= ? where  "
						+ "codigo_persona =  (select codigo from ab_personas where identificacion = ?)    "
						+ " and PRODUCTO_VITALCARD = 'VCON' ");					
				ps2 = conn.prepareStatement(sqlStringCupo.toString());
				ps2.setBigDecimal(1, nuevocupo);			
				ps2.setString(2, identificacion);			
				ps2.executeUpdate();

		
				
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long ejecutarProcesoSRI(Connection conn, Date fecha, Long farmacia)throws GizloException{
		
		
        String query = "{call FARMACIAS.fa_pkg_procedures.fa_pr_r_auto_sri(?,?,?,?)}";
        try {
            CallableStatement cs = conn.prepareCall(query);
            cs.setDate(1,fecha);
            
            cs.registerOutParameter(2, Types.DATE);
            cs.registerOutParameter(3, Types.DATE);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            String resultado = cs.getString(4);
            log.info(resultado);
            cs.close();

            return Long.parseLong(resultado);
        } catch (SQLException ex) {
        	ex.printStackTrace();

            return Long.parseLong("0");
        }		
		

	}
	
	public String obtenerNumeroSRIDeDocumentoVenta(Connection conn, String documentoVenta )throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT  "
				+ "NUMERO_SRI"
				+ " FROM  FARMACIAS.FA_FACTURAS WHERE DOCUMENTO_VENTA = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, documentoVenta);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("NUMERO_SRI"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
		return respuesta.get(0);			
		}
		else {
			return "";			
		}
	}
	
	
	
	
	
	
	public String obtenerDocumentoVentaPorCorrelative(Connection conn, String correlative, String columna)throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT  "
				+ columna
				+ " FROM  FARMACIAS.fa_secuencias_fact_elec WHERE CP_VAR1 = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, correlative);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString(columna));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
			return respuesta.get(0);			
		}
		else {
			return "";			
		}
	}
		
	
	
	
	
	

	
	
	
	
	public String obtenerPersonaRRHH(Connection conn, String user)throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT  PERSONA  FROM  RRHH.RH_EMPLEADOS WHERE USUARIO = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, user);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("PERSONA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
		
	}
	









	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public void actualizarSecuenciaDocumentoVenta(Connection conn, String nueva_secuencia)throws GizloException{
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("update ad_parametros set documento=? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, nueva_secuencia);			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public BigDecimal obtenerPrecioPrItemAutorizado(Connection conn, Long item, Long farmacia, String campo)throws GizloException{
		List<BigDecimal> respuesta = new ArrayList<BigDecimal>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT  "
				+ campo
				+ " FROM PR_ITEMS_AUTORIZADOS WHERE ITEM = ? AND FARMACIA  = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, item);
			ps.setLong(2, farmacia);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getBigDecimal(campo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (respuesta.size()>0) {
			return respuesta.get(0);			
		}
		else {
			BigDecimal valor= new BigDecimal("0");
			return valor;			
		}
	
	}




	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public String obtenerTarjetaDescuento(Connection conn, String identificacion)throws GizloException{
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT NUMERO_TARJETA FROM VITALCARD.VC_TARJETAS where CODIGO_PERSONA = ( SELECT codigo FROM AB_PERSONAS where identificacion = ?) ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, identificacion);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("NUMERO_TARJETA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
			return respuesta.get(0);			
		}
		else {
			return "-";			
			
		}
	}			
		
	
	
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public String obtenerSecuenciaDocumentoVenta(Connection conn)throws GizloException{
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select documento from ad_parametros ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getString("documento"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);			
	}		
	
	
	
	
	
	
	
	
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public Long obtenerPersona(Connection conn,String identificacion)throws GizloException{
		List<Long> respuesta = new ArrayList<Long>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" SELECT codigo as codigo FROM AB_PERSONAS where IDENTIFICACION = ? ");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, identificacion);
			
			
			
			
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getLong("codigo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
			
			return respuesta.get(0);			
		}
		else {
			return new Long("1234567890");			
		}
	}	

	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public Long obtenerCaja(Connection conn, Long farmacia)throws GizloException{
		
		List<Long> respuesta = new ArrayList<Long>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("SELECT codigo FROM FA_CAJAS "
				+ "WHERE "
				+ "    FARMACIA =? AND "
				+ "    EMITE_FACTURA = 'S' AND"
				+ "    rol_codigo =165 and  "
				+ "    NUMERO_PUNTO_VENTA is not null  ");
		
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, farmacia);

			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getLong("codigo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info(respuesta.get(0));	
		if ( respuesta.size()>0){
			
			return respuesta.get(0);
			
		}
		else {
			return new Long("0");
			
		}
		
		
		
		
		
	};	

	
	
	
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String listaSidGeneral(Connection conn, Long codigo)
			throws GizloException {
		// TODO Auto-generated method stub
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS'  and codigo = ? ");
		sqlString.append("ORDER BY EMPRESA,CODIGO asc");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setLong(1, codigo);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo")+"&"+set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.get(0);
	}
	
	
	
	public void insertarFacturaFarmacia(Connection conn, VOFarmaciaFactura factura)
			throws GizloException {
		
		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_FACTURAS("
						+ "FARMACIA, "
						+ "DOCUMENTO_VENTA, "
						+ "FECHA, "
						+ "COSTO_TOTAL_FACTURA,"
						+ "PVP_TOTAL_FACTURA,"
						+ "VENTA_TOTAL_FACTURA,"
						+ "CANAL_VENTA,"
						+ "VALOR_IVA,"
						+ "CANCELADA,"
						+ "TIPO_DOCUMENTO,"
						+ "CAJA,"
						+ "TIPO_MOVIMIENTO,"
						+ "CLASIFICACION_MOVIMIENTO,"
						+ "NUMERO_SRI,"
						+ "CLIENTE,"
						+ "DOCUMENTO_VENTA_PADRE,"
						+ "FARMACIA_PADRE,"
						+ "USUARIO,"
						+ "EMPLEADO_REALIZA,"
						+ "EMPLEADO_COBRA,"
						+ "PERSONA,"
						+ "PRIMER_APELLIDO,"
						+ "SEGUNDO_APELLIDO,"
						+ "NOMBRES,"
						+ "IDENTIFICACION,"
						+ "DIRECCION,"
						+ "FECHA_SISTEMA,"
						+ "DONACION,"
						+ "TRATAMIENTO_CONTINUO,"
						+ "EMPLEADO_ENTREGA,"
						+ "INCLUYEIVA,"
					//	+ "DIRECCION_ENVIO,"
				//		+ "GENERO_NOTA_CREDITO,"
						+ "GENERO_NOTA_CREDITO)");
		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());

			ps.setLong(1, factura.getFarmacia());
			ps.setLong(2, factura.getDocumentoVenta());
			ps.setTimestamp(3, factura.getFecha());
			
			
			
			ps.setBigDecimal(4, factura.getCostoTotalFactura());
			ps.setBigDecimal(5, factura.getPvpTotalFactura());
			ps.setBigDecimal(6, factura.getVentaTotalFactura());
			ps.setString(7, factura.getCanalVenta());
			ps.setBigDecimal(8, factura.getValorIva());
			ps.setString(9, factura.getCancelada());
			ps.setString(10, factura.getTipoDocumento());
			ps.setLong(11, factura.getCaja());
			ps.setString(12, factura.getTipoMovimiento());
			ps.setString(13, factura.getClasificacionMovimiento());
			ps.setString(14, factura.getNumeroSRI());
			ps.setNull(15, Types.NUMERIC);

			
			if (factura.getDocumentoVentaPadre() != null) {
				ps.setLong(16, factura.getDocumentoVentaPadre());
				
			}
			else {
				ps.setNull(16, Types.NUMERIC);
				
			}

			if (factura.getDocumentoVentaPadre() != null) {
				ps.setLong(17, factura.getFarmaciaPadre());
				
			}
			else {
				ps.setNull(17, Types.NUMERIC);
				
			}

			ps.setString(18, factura.getUsuario());
			ps.setLong(19, factura.getEmpleadoRealiza());
			ps.setLong(20, factura.getEmpleadoCobra());
			ps.setLong(21, factura.getPersona());
			ps.setString(22, factura.getPrimerApellido());
			ps.setString(23, factura.getSegundoApellido());
			ps.setString(24, factura.getNombres());
			ps.setString(25, factura.getIdentificacion());
			ps.setString(26, factura.getDireccion());
			ps.setTimestamp(27, java.sql.Timestamp.from(java.time.Instant.now()));
			ps.setString(28, factura.getDonacion());
			ps.setString(29, factura.getTratamientoContinuo());
			ps.setLong(30, factura.getEmpleadoEntrega());
			ps.setString(31, factura.getIncluyeIva());
			
			//ps.setLong(32, factura.getTomaPedidoDomicilio());
		//	ps.setInt(32, factura.getDireccionDomicilio());
			ps.setString(32, factura.getGeneraNotaCredito());
			//ps.setString(33, factura.getDireccion());			
			ps.executeUpdate();
			actualizarSecuenciaDocumentoVenta(conn,factura.getDocumentoVenta().toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		

		
	}
	

	
	public void insertarFacturaFarmaciaAdicional(Connection conn, VOFarmaciaFacturaAdicional factura)
			throws GizloException {
		
		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_FACTURA_ADICIONAL("
						+ "FARMACIA, "
						+ "DOCUMENTO_VENTA, "
						+ "TIPO_IMPRESION, "
						+ "CP_NUM1)");

		sqlString.append(" Values(?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());

			ps.setLong(1, factura.getFarmacia());
			ps.setLong(2, factura.getDocumentoVenta());
			ps.setString(3, factura.getTipoImpresion());
			ps.setLong(4, factura.getCp_num1());
//			ps.setLong(5, factura.getCp_num2());
//			ps.setString(6, factura.getCpVar3());
//			ps.setString(7, factura.getCpVar4());
//			ps.setString(8, factura.getCpVar5());
//			ps.setString(9, factura.getCpVar6());
//			ps.setString(10, factura.getCpVar7());
//			ps.setDate(11, factura.getCpDate1());
//			ps.setDate(12, factura.getCpDate2());
			

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}	
	
	
	
	
	public void insertarFacturaFarmaciaFormaPago(Connection conn, VOFarmaciaDetalleFormaPago formaPago)
			throws GizloException {
		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;
		sqlString
				.append("Insert into FARMACIAS.FA_DETALLES_FORMAS_PAGO("
						+ "FARMACIA, "
						+ "DOCUMENTO_VENTA, "
						+ "FORMA_PAGO, "
						+ "PVP_FACTURA,"
						+ "VENTA_FACTURA,"
						+ "NUMERO_TARJETA,"
						+ "NUMERO_ODA,"
						+ "NUMERO_AUTORIZACION,"
						+ "NUMERO_AUTORIZACION_BOLETIN,"
						+ "NUMERO_AUTORIZACION_FYBECA,"
						+ "INTERES,"
						+ "NUMERO_CUOTAS,"		
						+ "TARJETA_DESCUENTO,"
						+ "TARJETADT,"
						+ "TARJETAHABIENTE,"
						+ "FECHA_CADUCIDAD,"
						+ "USUARIO,"
						+ "TELEFONO,"
						+ "MEDIO_DESCUENTO,"
						+ "COSTO_FACTURA)");
					//	+ "CREDITO_EMPLEADO"
					//	+ "EMPRESA"
					//	+ "EMPLEADO"
					//	+ "CLIENTE"
					//	+ "PLAN_CREDITO"
					//	+ "CHEQUE_RECIBIDO"
					//	+ "REMISION"
					//	+ "TIPO_CREDITO"
					//	+ "TARJETA_VITALCARD"
					//	+ "TIPO_SERVICIO"
					//	+ "AUTORIZACION_FVC


		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());

			ps.setLong(1, formaPago.getFarmacia());
			ps.setLong(2, formaPago.getDocumentoVenta());
			ps.setString(3, formaPago.getFormaPago());
			ps.setBigDecimal(4, formaPago.getPvpFactura());
			ps.setBigDecimal(5, formaPago.getVentaFactura());
			
			if (formaPago.getNumeroTarjeta()!= null) {
				ps.setString(6, formaPago.getNumeroTarjeta());
			}
			else {
				ps.setNull(6, Types.VARCHAR);
			}
			
			ps.setString(7, formaPago.getNumeroOda());
			ps.setString(8, formaPago.getNumeroAutorizacion());
			ps.setString(9, formaPago.getNumeroAutorizacionBoletin());
			ps.setString(10, formaPago.getNumeroAutorizacionFybeca());
			ps.setBigDecimal(11, formaPago.getInteres());
			ps.setLong(12, formaPago.getNumeroCuotas());
			
			
			ps.setString(13, formaPago.getTarjetaDescuento());
			ps.setString(14, formaPago.getTarjetaDt());
			ps.setString(15, formaPago.getTarjetaHabiente());
			ps.setDate(16, formaPago.getFechaCaducidad());
			ps.setString(17, formaPago.getUsuario());

			ps.setString(18, formaPago.getTelefono());
			ps.setString(19, formaPago.getMedioDescuento());
		//	ps.setLong(20, formaPago.getCreditoEmpleado());
		//	ps.setLong(20, formaPago.getEmpresa());
		//	ps.setLong(21, formaPago.getEmpleado());
		//	ps.setLong(22, formaPago.getCliente());
		//	ps.setString(23, formaPago.getPlanCredito());
		//	ps.setLong(24, formaPago.getChequeRecibido());
		//	ps.setLong(25, formaPago.getRemision());
		//	ps.setString(26, formaPago.getTipoCredito());
		//	ps.setString(27, formaPago.getTarjetaVitalcard());
		//	ps.setString(28, formaPago.getTipoServicio());
			ps.setBigDecimal(20, formaPago.getCostoFactura());
		//	ps.setString(30, formaPago.getAutorizacionFvc());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		

		
	}		
	
	public void insertarFarmaciaNotaCredito(Connection conn, VOFarmaciaNotaCredito notaCredito)
			throws GizloException {
		
		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_NOTAS_CREDITO("
						+ "CODIGO, "
						+ "FECHA_CANCELACION, "
						+ "FECHA, "
						+ "CANCELADA,"
						+ "USUARIO,"
						+ "VALOR,"
						+ "DOCUMENTO_VENTA,"
						+ "FORMA_PAGO,"
						+ "FARMACIA,"
						+ "EMPLEADO_COBRA,"
						+ "TIPO_CANCELACION,"
						+ "FARMACIA_CANJE,"
						+ "DOCUMENTO_CANCELACION)");

		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());

			ps.setLong(1, notaCredito.getCodigo());
			ps.setDate(2, notaCredito.getFechaCancelacion());
			ps.setTimestamp(3, java.sql.Timestamp.from(java.time.Instant.now()));
			ps.setString(4, notaCredito.getCancelada());
			ps.setString(5, notaCredito.getUsuario());
			ps.setBigDecimal(6, notaCredito.getValor());
			ps.setLong(7, notaCredito.getDocumentoVenta());
			ps.setString(8, notaCredito.getFormaPago());
			ps.setLong(9, notaCredito.getFarmacia());
			ps.setLong(10, notaCredito.getEmpleadoCobra());
			ps.setString(11, notaCredito.getTipoCancelacion());
			ps.setLong(12, notaCredito.getFarmaciaCanje());
			ps.setLong(13, notaCredito.getDocumentoCancelacion());
			

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}		
	
	
	
	public void insertarFacturaDetalleFarmacia(Connection conn, VOFarmaciaFacturaDetalle detalleFactura)
			throws GizloException {
		
		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_DETALLES_FACTURA("
						+ "FARMACIA, "
						+ "DOCUMENTO_VENTA, "
						+ "CODIGO, "
						+ "CANTIDAD,"
						+ "COSTO,"
						+ "PVP,"
						+ "PRECIO_FYBECA,"
						+ "VENTA,"
						+ "UNIDADES,"
						+ "PORCENTAJE_IVA,"
					//	+ "MEDICO,"
						+ "ITEM,"
					//	+ "SECUENCIAL,"
					//	+ "LISTA_DESPACHO,"
						+ "SECCION,"
						+ "PROMOCION,"
						+ "TIPO_NEGOCIO,"
					//	+ "DOCUMENTO_ABONO,"
					//	+ "CUPON,"
						+ "TRANSACCION_FVC,"
					//	+ "USUARIO_REALIZA,"
				//		+ "USUARIO_CALLCENTER,"
						+ "DESCUENTO_TC)");

		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		
		try {
			ps = conn.prepareStatement(sqlString.toString());

			ps.setLong(1, detalleFactura.getFarmacia());
			ps.setLong(2, detalleFactura.getDocumentoVenta());
			ps.setLong(3, detalleFactura.getCodigo());
			ps.setLong(4, detalleFactura.getCantidad());
			ps.setBigDecimal(5, detalleFactura.getCosto());
			ps.setBigDecimal(6, detalleFactura.getPvp());
			ps.setBigDecimal(7, detalleFactura.getPrecioFybeca());
			ps.setBigDecimal(8, detalleFactura.getVenta());
			ps.setLong(9, detalleFactura.getUnidades());
			
			ps.setBigDecimal(10, detalleFactura.getPorcentajeIva());
			//ps.setLong(11, detalleFactura.getMedico());
			ps.setLong(11, detalleFactura.getItem());
			//ps.setLong(12, detalleFactura.getSecuencial());
			//ps.setLong(12, detalleFactura.getListaDespacho());
			ps.setLong(12, detalleFactura.getSeccion());
			ps.setString(13, detalleFactura.getPromocion());
			ps.setString(14, detalleFactura.getTipoNegocio());
		//	ps.setLong(15, detalleFactura.getDocumentoAbono());

	//		ps.setLong(15, detalleFactura.getCupon());
			ps.setString(15, detalleFactura.getTransaccion_fvc());
	//		ps.setLong(16, detalleFactura.getUsuarioRealiza());
	//		ps.setLong(17, detalleFactura.getUsuarioCallCenter());
			ps.setBigDecimal(16, detalleFactura.getDescuentoTc());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		

		
	}	
	
	
	public void insertarFarmaciaCreditoDevolucion(Connection conn, VOCreditoDevolucion notaCredito)	throws GizloException {


		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA_CREDITOS("
						+ "REFERENCIA, "
						+ "TOTAL_VENTA, "
						+ "TOTAL_PVP, "
						+ "FECHA, "
						+ "CANCELADA, "
						+ "CLIENTE, "
						+ "FORMA_PAGO, "
						+ "FARMACIA, "
						+ "DOCUMENTO_VENTA, "
						+ "CLASIFICACION_MOVIMIENTO, "
						+ "TIPO_MOVIMIENTO )");
					//	+ "EMPLEADO, "
					//	+ "EMPRESA)");

		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());


			ps.setLong(1, notaCredito.getReferencia());
			ps.setBigDecimal(2, notaCredito.getTotalVenta());
			ps.setBigDecimal(3, notaCredito.getTotalPvp());
			ps.setDate(4, notaCredito.getFecha());
			ps.setString(5, notaCredito.getCancelada());
			ps.setLong(6, notaCredito.getCliente());
			ps.setString(7, notaCredito.getFormaPago());
			ps.setLong(8, notaCredito.getFarmacia());
			ps.setLong(9, notaCredito.getDocumentoVenta());
			ps.setString(10, notaCredito.getClasificacionMovimiento());
			ps.setString(11, notaCredito.getTipoMovimiento());
			//ps.setLong(12, notaCredito.getEmpleado());
		//	ps.setLong(13, notaCredito.getEmpresa());

		
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertarFarmaciaSecuenciaElectronica(Connection conn, VOSecuenciaElectronica factura)	throws GizloException {


		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.fa_secuencias_fact_elec("
						+ "DOCUMENTO_VENTA, "
						+ "FARMACIA, "
						+ "CLASIFICACION_DOCUMENTO, "
						+ "TIPO_DOCUMENTO, "
						+ "CP_VAR1, "
						+ "CP_VAR2)");


		sqlString.append(" Values(?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());


			
			ps.setLong(1, factura.getDocumentoVenta());
			ps.setLong(2, factura.getFarmacia());
			ps.setString(3, factura.getClasificacionDocumento());
			ps.setLong(4, factura.getTipodocumento());
			ps.setString(5, factura.getCpVar1());
			ps.setString(6, factura.getCpVar2());

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//METODOS POR REVISAR//
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long obtenerDocumentoVentaFM(Connection conn) throws GizloException{
		List<Long> listarDocumento = new ArrayList<Long>();
		PreparedStatement ps= null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select FARMACIA, DOCUMENTO_VENTA from FA_FACTURAS ");
		try {
			ps=conn.prepareStatement(sqlString.toString());
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				listarDocumento.add(set.getLong("FARMACIA, DOCUMENTO_VENTA"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return listarDocumento.get(0);
	}
	
	public String obtenerDocumentoVentaYFarm(Connection conn, String documentoVentayFarmacia )throws GizloException{
			
			List<String> respuesta = new ArrayList<String>();
			PreparedStatement ps = null;
			
			StringBuilder sqlString = new StringBuilder();
			sqlString.append("  SELECT DOCUMENTO_VENTA, FARMACIA FROM  FARMACIAS.FA_FACTURAS");
			try {
				ps = conn.prepareStatement(sqlString.toString());
				ps.setString(1, documentoVentayFarmacia );
				ResultSet set = ps.executeQuery();
				while (set.next()) { 
					respuesta.add(set.getString("DOCUMENTO_VENTA, FARMACIA"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (respuesta.size()>0) {
			return respuesta.get(0);			
			}
			else {
				return "";			
			}
		}
	
	public String obtenerObservacion(Connection conn, String NombreObs )throws GizloException{
		
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("  SELECT NOMBRE FROM  FARMACIAS.FA_MOTIVOS_DEVOLUCION");
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setString(1, NombreObs );
			ResultSet set = ps.executeQuery();
			while (set.next()) { 
				respuesta.add(set.getString("NOMBRE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (respuesta.size()>0) {
		return respuesta.get(0);			
		}
		else {
			return "";			
		}
	}

	
	public String obtenerCodigocpVar(Connection conn, String Codigo )throws GizloException{
			
			List<String> respuesta = new ArrayList<String>();
			PreparedStatement ps = null;
			
			StringBuilder sqlString = new StringBuilder();
			sqlString.append("  SELECT CODIGO FROM FARMACIAS.FA_MOTIVOS_DEVOLUCION");
			try {
				ps = conn.prepareStatement(sqlString.toString());
				ps.setString(1, Codigo );
				ResultSet set = ps.executeQuery();
				while (set.next()) { 
					respuesta.add(set.getString("CODIGO"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (respuesta.size()>0) {
			return respuesta.get(0);			
			}
			else {
				return "";			
			}
		}
	
	public void insertarDocumentosVenta(Connection conn, VOFacturasMas datos)	throws GizloException {


		StringBuilder sqlString = new StringBuilder();
		PreparedStatement ps = null;


		sqlString
				.append("Insert into FARMACIAS.FA.FACTURAS_MAS("
						+ "CP_VAR1, "
						+ "PERSONA_ABF, "
						+ "AUTORIZACION_ABF, "
						+ "CP_NUM2, "
						+ "CP_NUM1, "
						+ "CP_CHAR1, "
						+ "CP_VAR2, "
						+ "CP_CHAR2, "
						+ "DOCUMENTO_VENTA, "
						+ "FARMACIA, "
						+ "CONTRATO_ABF, "
						+ "CP_CHAR3, "
						+ "CP_NUM3, "
						+ "CP_DATE3, "
						+ "CP_DATE2, "
						+ "CP_NUM4, "
						+ "CP_DATE1, "
						+ "CP_VAR3, "
						+ "CP_CHAR4, "
						+ "CP_DATE4, "
						+ "CP_VAR4)");


		sqlString.append(" Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");		
		try {
			ps = conn.prepareStatement(sqlString.toString());


			
			ps.setString(1, datos.getCpVar1());
			ps.setString(2,datos.getPersonaABF());
			ps.setString(3,datos.getAutorizacionABF());
			ps.setLong(4,datos.getCp_num2());
			ps.setLong(5, datos.getCp_num1());
			ps.setString(6,datos.getCpChar1());
			ps.setString(7, datos.getCpVar2());
			ps.setString(8, datos.getCpChar2());
			ps.setLong(9, datos.getDocumentoVenta());
			ps.setLong(10,datos.getFarmacia());
			ps.setString(11, datos.getContratoABF());
			ps.setString(12,datos.getCpChar3());
			ps.setLong(13, datos.getCp_num3());
			ps.setDate(14, datos.getCpDate3());
			ps.setDate(15, datos.getCpDate2());
			ps.setLong(16, datos.getCp_num4());
			ps.setDate(17, datos.getCpDate1());
			ps.setString(18, datos.getCpVar3());
			ps.setString(19, datos.getCpChar4());
			ps.setDate(20, datos.getCpDate4());
			ps.setString(21, datos.getCpVar4());
			
			
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// METODOS POR REVISAR//
	
	
	
	
	
	

}
