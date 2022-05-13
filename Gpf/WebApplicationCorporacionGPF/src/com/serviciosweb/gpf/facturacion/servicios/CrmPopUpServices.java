package com.serviciosweb.gpf.facturacion.servicios;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FaItemMensajeMecanica;

public class CrmPopUpServices extends ConexionVarianteSid implements Serializable {
	private static final long serialVersionUID = 3897483315608937898L;
	private static Logger log = Logger.getLogger(CrmPopUpServices.class.getName());
	private Integer empresa =0;
	


	public CrmPopUpServices(Integer empresa) {
		this.empresa = empresa;
	}
	
	
	public String copiaPdv(){
		
		StringBuilder respuestaXml= new StringBuilder();
		respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		respuestaXml.append( "<Transaccion><Respuesta>Los datos enviados son correctos</Respuesta></Transaccion>");
		
		CredencialDS credencialDS = new CredencialDS();
		credencialDS.setDatabaseId("oficina");
		credencialDS.setUsuario("WEBLINK");
		credencialDS.setClave("weblink_2013");
		

		
		List<FaItemMensajeMecanica> listaFaItemMensajeMecanica = new  ArrayList<FaItemMensajeMecanica>();
		Connection connProd = null;
		
		try {
			connProd = ConexionServices.obtenerConexionFybeca(credencialDS);
			listaFaItemMensajeMecanica = listaFaItemMensajeMecanica(connProd);
			log.error("OK:Items a copiar"+listaFaItemMensajeMecanica.size());
		} catch (Exception e) {
			log.error(e.getMessage() + " " + e.getLocalizedMessage());
		} finally {
			try {
				if (connProd != null)
					connProd.close();
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			}
		}
		
	
		List<String> respuestaAll = new ArrayList<String>();
		
		Connection conn = null;
		List<String> respuesta = null;
		if (empresa == 1) {
			try {
				conn = ConexionServices.obtenerConexionFybeca(credencialDS);
				respuesta = listaSidOficina(conn);
				respuestaAll.addAll(respuesta);
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					log.error(e.getMessage() + " " + e.getLocalizedMessage());
				}
			}
		}
		Connection connSana = null;
		List<String> respuestaSana = null;
		
		if (empresa == 8) {
			try {
				connSana = ConexionServices.obtenerConexionFybeca(credencialDS);
				respuestaSana = listaSidSanaSana(connSana);
				respuestaAll.addAll(respuestaSana);
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					log.error(e.getMessage() + " " + e.getLocalizedMessage());
				}
			}
		}

		Connection connOki = null;
		List<String> respuestaOki = null;
		
		if (empresa == 11) {
			try {
				connOki = ConexionServices.obtenerConexionFybeca(credencialDS);
				respuestaOki =listaOkiDoki(connOki);
				respuestaAll.addAll(respuestaOki);
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					log.error(e.getMessage() + " " + e.getLocalizedMessage());
				}
			}
		}
		
		Connection connFran = null;
		List<String> respuestaFran = null;
		
		
		if (empresa == 16) {
			try {
				connFran = ConexionServices.obtenerConexionFybeca(credencialDS);
				respuestaFran = listaFranquicia(connFran);
				respuestaAll.addAll(respuestaFran);
			} catch (Exception e) {
				log.error(e.getMessage() + " " + e.getLocalizedMessage());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					log.error(e.getMessage() + " " + e.getLocalizedMessage());
				}
			}
		}
		
		
		
		
		List<String> listaFarmaciasAll = new ArrayList<String>();
		if(respuestaAll != null && !respuestaAll.isEmpty()){
			for (String itera : respuestaAll) {
				if(!(listaFarmaciasAll.contains(itera))){
					listaFarmaciasAll.add(itera);
				}				
			}
		}
		
		
		if(listaFarmaciasAll != null && !listaFarmaciasAll.isEmpty()){
			for(String itera: listaFarmaciasAll){
				log.info(itera.toString());
				String[] parametros = itera.split("&");
				if (parametros.length != 2) {
					respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					respuestaXml.append( "<Transaccion><Respuesta>Los datos enviados son correctos</Respuesta></Transaccion>");
				}
				String sid = parametros[1];
				credencialDS.setDatabaseId(sid);
				credencialDS.setUsuario("WEBLINK");
				credencialDS.setClave("weblink_2013");

				conn = null;
				try {
					conn = ConexionServices.obtenerConexionFybeca(credencialDS);
					eliminaMecanica(conn ,empresa);
					
					guardarListMecanica(conn,listaFaItemMensajeMecanica);
					log.error("OK:"+credencialDS.getDatabaseId());
				} catch (Exception e) {
					log.error(e.getMessage());
				} finally {
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
			

		}
		
		return respuestaXml.toString();
	            
	}
	
	
	public List<FaItemMensajeMecanica> listaFaItemMensajeMecanica(Connection conn) {
		List<FaItemMensajeMecanica> respuesta = new ArrayList<FaItemMensajeMecanica>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		
		sqlString.append(" select CODIGO,                   ");
		sqlString.append("        EMPRESA,                  ");
		sqlString.append("        ITEM,                     ");
		sqlString.append("        CODIGO_OFERTA,            ");
		sqlString.append("        CODIGO_PROMOCION,         ");
		sqlString.append("        FECHA_INICIO,             ");
		sqlString.append("        FECHA_FIN,                ");
		sqlString.append("        DESCRIPCION_MECANICA,     ");
		sqlString.append("        ORDEN                     ");
		sqlString.append("   from FA_ITEM_MENSAJE_MECANICA  ");
		sqlString.append("   where empresa = ?	            ");
		 ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			ps.setInt(1, empresa);
			set = ps.executeQuery();
			while (set.next()) {
				FaItemMensajeMecanica faItemMensajeMecanica = new FaItemMensajeMecanica();
			
				faItemMensajeMecanica.setCodigo(set.getInt("CODIGO"));
				faItemMensajeMecanica.setCodigoOferta(set.getInt("CODIGO_OFERTA"));
				faItemMensajeMecanica.setCodigoPromocion(set.getInt("CODIGO_PROMOCION"));
				faItemMensajeMecanica.setDescripcionMecanica(set.getString("DESCRIPCION_MECANICA"));
				faItemMensajeMecanica.setEmpresa(set.getInt("EMPRESA"));
				faItemMensajeMecanica.setFechaFin(set.getDate("FECHA_INICIO"));
				faItemMensajeMecanica.setFechaInicio(set.getDate("FECHA_INICIO"));
				faItemMensajeMecanica.setItem(set.getInt("ITEM"));
				faItemMensajeMecanica.setOrden(set.getInt("ORDEN"));
				
				respuesta.add(faItemMensajeMecanica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(set != null)
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return respuesta;
	}

	
	public void guardarListMecanica(Connection connection ,List<FaItemMensajeMecanica> listaFaItemMensajeMecanica) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			
		
			StringBuffer sqlInsert = new StringBuffer(); 
			sqlInsert.append(" insert into FA_ITEM_MENSAJE_MECANICA (CODIGO,");
			sqlInsert.append(" EMPRESA, ");
			sqlInsert.append(" ITEM,");
			sqlInsert.append(" CODIGO_OFERTA ,");
			sqlInsert.append(" CODIGO_PROMOCION,");
			sqlInsert.append(" FECHA_INICIO,");
			sqlInsert.append(" FECHA_FIN, ");
			sqlInsert.append(" DESCRIPCION_MECANICA,"); 
			sqlInsert.append(" USUARIO,"); 
			sqlInsert.append(" ESTADO, ");
			sqlInsert.append(" ORDEN )");
			sqlInsert.append(" values (?,?,?,?,?,?,?,?,?,?,?)");

		    pstmt = connection.prepareStatement(sqlInsert.toString());
			for(FaItemMensajeMecanica itemMensajeMecanica:listaFaItemMensajeMecanica){
				pstmt.setLong(1, itemMensajeMecanica.getCodigo());
				pstmt.setLong(2, itemMensajeMecanica.getEmpresa());
				pstmt.setLong(3, itemMensajeMecanica.getItem());
				pstmt.setLong(4, itemMensajeMecanica.getCodigoOferta());
				pstmt.setLong(5, itemMensajeMecanica.getCodigoPromocion());
				pstmt.setDate(6,new Date(itemMensajeMecanica.getFechaInicio().getTime()));
				pstmt.setDate(7,new Date(itemMensajeMecanica.getFechaFin().getTime()));
				pstmt.setString(8,itemMensajeMecanica.getDescripcionMecanica());
				pstmt.setString(9,itemMensajeMecanica.getUsuario());
				pstmt.setString(10,itemMensajeMecanica.getEstado());
				pstmt.setLong(11, itemMensajeMecanica.getOrden());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt !=null) {
				pstmt.close();
			}
		}
	}
	
	
	
	public void eliminaMecanica(Connection connection ,Integer empresa) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			StringBuffer sqlDelete = new StringBuffer(); 
			sqlDelete.append("delete from  FA_ITEM_MENSAJE_MECANICA where empresa = ?");
			 pstmt = connection.prepareStatement(sqlDelete.toString());
			 pstmt.setInt(1, empresa);
			 pstmt.executeUpdate();
			 log.error("OK:Items Eliminados");
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt !=null) {
				pstmt.close();
			}
		}
	}
	
	
	public List<String> listaSidOficina(Connection conn) {
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS' and campo3 = 'S' ");
		sqlString.append("AND EMPRESA in (1) ORDER BY EMPRESA,CODIGO asc");
		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo")+"&"+set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(set != null)
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return respuesta;
	}


	public List<String> listaSidSanaSana(Connection conn){
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS' and campo3 = 'S' ");
		sqlString.append("AND EMPRESA in (8)  ORDER BY EMPRESA,CODIGO asc");
		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo")+"&"+set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(set != null)
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return respuesta;
	}



	public List<String> listaOkiDoki(Connection conn)  {
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS' and campo3 = 'S' ");
		sqlString.append("AND EMPRESA in (11)  ORDER BY EMPRESA,CODIGO asc");
		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo")+"&"+set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(set != null)
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return respuesta;
	}
	
	
	public List<String> listaFranquicia(Connection conn)  {
		List<String> respuesta = new ArrayList<String>();
		PreparedStatement ps = null;
		
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select codigo,database_sid,nombre from ad_farmacias   where fma_autorizacion_farmaceutica = 'FS' and campo3 = 'S' ");
		sqlString.append("AND EMPRESA in (16)  ORDER BY EMPRESA,CODIGO asc");
		ResultSet set = null;
		try {
			ps = conn.prepareStatement(sqlString.toString());
			set = ps.executeQuery();
			while (set.next()) {
				respuesta.add(set.getInt("codigo")+"&"+set.getString("database_sid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(set != null)
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return respuesta;
	}


}
