package com.corporaciongpf.web.bean.local;

import java.io.IOException;
import java.sql.SQLException;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.excepcion.GizloPersistException;
import com.corporaciongpf.adm.excepcion.GizloUpdateException;
import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VORequestCancelacion;
import com.corporaciongpf.adm.vo.VOResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@Local
public interface FacturaBeanLocal {

	
	public String testEjb();
	public VOResponse crearFactura(VOFactura factura)  ;	
	public VOResponse registrarCancelarFactura(VOCancelarFactura factura) ;	
	public VOResponse ejecutarActualizarRecaudo() ;	
	public String ejecutarConsumoWSCancelacionMDT(VORequestCancelacion request);	
}