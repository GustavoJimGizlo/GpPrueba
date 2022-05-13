package com.corporaciongpf.adm.dao;

import java.math.BigDecimal;
import java.sql.Connection;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.CancelarFactura;




@Local
public interface CancelarFacturaDAO extends GenericDAO<CancelarFactura, Long> {
	
	public BigDecimal obtenerNumeroEnvio(Connection conn, Long documentoVenta);
	public Long obtenerSecuencia(Connection conn);
	public void insertRegistroCancelarFactura(Connection conn, CancelarFactura cancelarFactura)	throws GizloException;
}
