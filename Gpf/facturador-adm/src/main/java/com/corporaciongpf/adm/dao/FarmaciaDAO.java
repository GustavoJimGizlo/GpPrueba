package com.corporaciongpf.adm.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.vo.VOCreditoDevolucion;
import com.corporaciongpf.adm.vo.VOFacturasMas;
import com.corporaciongpf.adm.vo.VOFarmaciaDetalleFormaPago;
import com.corporaciongpf.adm.vo.VOFarmaciaFactura;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaAdicional;
import com.corporaciongpf.adm.vo.VOFarmaciaFacturaDetalle;
import com.corporaciongpf.adm.vo.VOFarmaciaNotaCredito;
import com.corporaciongpf.adm.vo.VOSecuenciaElectronica;
@Local
public interface FarmaciaDAO {
	public String obtenerNumeroSRIDeDocumentoVenta(Connection conn, String documentoVenta )throws GizloException;

	public String obtenerDocumentoVentaPorCorrelative(Connection conn, String correlative, String columna)throws GizloException;	
	public String obtenerClasificacionMovimiento(Connection conn,String tipoMovimiento) throws GizloException;	
	
	public String listaSidGeneral(Connection conn, Long codigo)throws GizloException;	
	
	public Long obtenerCaja(Connection conn,Long farmacia)throws GizloException;	
	public Long obtenerPersona(Connection conn,String identificacion) throws GizloException;	
	public String obtenerSecuenciaDocumentoVenta(Connection conn) throws GizloException;	

	public String obtenerTarjetaDescuento(Connection conn,String identificacion) throws GizloException;	
	

	public BigDecimal obtenerPrecioPrItemAutorizado(Connection conn, Long item, Long farmacia, String campo)throws GizloException;
	

	public Long ejecutarProcesoSRI(Connection conn, Date fecha, Long farmacia)throws GizloException;

	
	
	public void actualizarCupoTarjetaConvenioEmpresarial(Connection conn, String identificacion ,BigDecimal monto)throws GizloException;
	
	
	
	public String obtenerPersonaRRHH(Connection conn, String user)throws GizloException;
	
	
	public void  actualizarSecuenciaDocumentoVenta(Connection conn,String nueva_secuencia) throws GizloException;	;	
	
	public void insertarFacturaFarmacia(Connection conn, VOFarmaciaFactura factura)throws GizloException;	
	public void insertarFacturaFarmaciaAdicional(Connection conn, VOFarmaciaFacturaAdicional factura)	throws GizloException ;
	public void insertarFacturaFarmaciaFormaPago(Connection conn, VOFarmaciaDetalleFormaPago formaPago)	throws GizloException ;

	public void insertarFarmaciaNotaCredito(Connection conn, VOFarmaciaNotaCredito notaCredito)	throws GizloException ;
	
	
	public void insertarFarmaciaCreditoDevolucion(Connection conn, VOCreditoDevolucion notaCredito)	throws GizloException ;
	
	public void insertarFarmaciaSecuenciaElectronica(Connection conn, VOSecuenciaElectronica factura)	throws GizloException;
	
	
	public void insertarFacturaDetalleFarmacia(Connection conn, VOFarmaciaFacturaDetalle detalleFactura) throws GizloException;
	

	//METODOS POR REVISAR// 
	
	public Long obtenerDocumentoVentaFM(Connection conn) throws GizloException;
	
	public String obtenerDocumentoVentaYFarm(Connection conn, String documentoVentayFarmacia) throws GizloException;
	
	public String obtenerObservacion(Connection conn, String NombreObs) throws GizloException;
	
	public String obtenerCodigocpVar(Connection conn, String Codigo )throws GizloException;
	
	public void insertarDocumentosVenta(Connection conn, VOFacturasMas datos)	throws GizloException;
	
	

}