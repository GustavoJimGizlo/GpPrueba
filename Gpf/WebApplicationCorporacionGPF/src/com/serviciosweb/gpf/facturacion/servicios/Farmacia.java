/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 *
 * @author mftellor
 */
public class Farmacia extends ObtenerNuevaConexion {
  private Long codigo;
  private String nombre;
  private String databaseSid;
  private String ipFarmacia;


  
	  public Farmacia(){
		  super(Farmacia.class);
	  }
	  
    public Farmacia(Boolean cargaInicial) {
        super(Farmacia.class);
        Logger.getLogger(Farmacia.class.getName()).log(Level.INFO, "Inicia carga Farmacia");
        ConexionVarianteSid conexion = obtenerNuevaConexionVarianteSid("1");
        List<Farmacia> listaFarmacias=new ArrayList<Farmacia>();
        ResultSet rs=conexion.consulta("SELECT  f.CODIGO,f.NOMBRE,f.database_sid, b.directorio_envio_mercaderia     " +
                " FROM ad_farmacias f, pr_farmacias_bodega b WHERE f.codigo = b.fma_codigo and F.FMA_AUTORIZACION_FARMACEUTICA='FS' and F.CAMPO3='S'"); 
        while(conexion.siguiente(rs)){
            Farmacia farmacia=new Farmacia();            
            farmacia.codigo=conexion.getLong(rs, "CODIGO");
            farmacia.nombre=conexion.getString(rs, "NOMBRE");
            farmacia.databaseSid=conexion.getString(rs, "DATABASE_SID")+"G";
            farmacia.ipFarmacia=conexion.getString(rs, "DIRECTORIO_ENVIO_MERCADERIA");
            listaFarmacias.add(farmacia);
        }
         try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Farmacia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(Farmacia.class.getName()).log(Level.INFO, "Finaliza carga Farmacia");
        conexion.cerrarConexion();;
    }
    public Farmacia(String idFarmacia) {
    	super(Farmacia.class);
    	ConexionVarianteSid conexion = obtenerNuevaConexionVarianteSid("1");
    	ResultSet rs=conexion.consulta("SELECT * FROM AD_FARMACIAS WHERE CODIGO="+idFarmacia);
    	 while(conexion.siguiente(rs)){                        
             this.codigo=conexion.getLong(rs, "CODIGO");
             this.nombre=conexion.getString(rs, "NOMBRE");
             this.databaseSid=conexion.getString(rs, "DATABASE_SID")+".UIO";                          
         }
    	conexion.cerrarConexion(rs);
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public String getDatabaseSid() {
        return databaseSid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIpFarmacia() {
        return ipFarmacia;
    }
	public void setDatabaseSid(String databaseSid) {
		this.databaseSid = databaseSid;
	}
    
}
