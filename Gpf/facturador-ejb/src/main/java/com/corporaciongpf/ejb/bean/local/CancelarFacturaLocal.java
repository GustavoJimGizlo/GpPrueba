package com.corporaciongpf.ejb.bean.local;

import java.io.IOException;

import javax.ejb.Local;

import com.corporaciongpf.adm.vo.VOCancelarFactura;
import com.corporaciongpf.adm.vo.VOResponse;



@Local
public interface CancelarFacturaLocal {
	public VOResponse registrarCancelarFactura( VOCancelarFactura factura  )  throws IOException;
}
