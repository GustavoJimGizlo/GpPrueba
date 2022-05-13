package com.corporaciongpf.adm.dao;


import java.sql.Connection;

import javax.ejb.Local;




@Local
public interface TipoIdentificacionClienteDAO {
	
	Boolean obtenerIdentificacion(Connection conn,String codigo);

}