package com.corporaciongpf.ejb.bean.local;


import java.sql.SQLException;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.excepcion.GizloPersistException;
import com.corporaciongpf.adm.excepcion.GizloUpdateException;
import com.corporaciongpf.adm.vo.VOFactura;
import com.corporaciongpf.adm.vo.VOResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

 
@Local
public interface FacturaLocal {
	public VOResponse crearFactura( VOFactura facturaRequest  ) throws GizloPersistException,GizloException,GizloUpdateException,SQLException,JsonProcessingException;
	public String test(String name);
	
}