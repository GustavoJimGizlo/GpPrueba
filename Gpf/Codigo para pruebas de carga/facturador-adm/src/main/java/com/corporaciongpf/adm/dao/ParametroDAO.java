package com.corporaciongpf.adm.dao;

import java.sql.Connection;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Parametro;



@Local
public interface ParametroDAO  {


	public String obtenerParametro(Connection conn, String clave);	
	public String obtenerDatoSecciones(Connection conn, Long codigoItem, String campo)throws GizloException;
	public String obtenerFarmacia(Connection conn,Long codigo);
	public String obtenerCodigoMetodoPagoFarmacia(Connection conn, String codigoPago, String codigoMetodoPago)throws GizloException	;

	public Long obtenerSecuenciaNotaCredito(Connection conn);


}
