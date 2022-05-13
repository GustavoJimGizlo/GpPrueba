package com.corporaciongpf.web.bean.local;

import javax.ejb.Local;

import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VORequestCancelacion;
import com.corporaciongpf.adm.vo.VOResponse;

@Local
public interface FacturaBeanLocal {

	
	public String testEjb();
	public VOResponse crearFactura(VOFactura factura) ;	
	public VOResponse registrarCancelarFactura(VOCancelarFactura factura) ;	
	public VOResponse ejecutarActualizarRecaudo() ;	
	public String ejecutarConsumoWS(VORequestCancelacion request);	
	public String crearArchivo();
}