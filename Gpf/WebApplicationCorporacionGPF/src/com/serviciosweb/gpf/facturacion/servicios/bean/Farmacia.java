/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.serviciosweb.gpf.facturacion.servicios.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mftellor
 */
public class Farmacia {
  private Long codigo;
  private String nombre;
  private String databaseSid;
  private String ipFarmacia;


  

  public Farmacia(){

  }
  
  public Farmacia(Long idFarmacia,String databaseSid,ConexionVarianteSid conexion){
      String filtroQuery="";
      if(idFarmacia!=null)
         filtroQuery=" and f.CODIGO="+idFarmacia;
      else
         filtroQuery=" and f.DATABASE_SID='"+databaseSid+"' ";
      
      ResultSet rs=conexion.consulta("SELECT f.CODIGO,f.NOMBRE,f.database_sid, b.directorio_envio_mercaderia   FROM ad_farmacias f, pr_farmacias_bodega b " +
              " WHERE f.codigo = b.fma_codigo "+filtroQuery);
      while(conexion.siguiente(rs)){

             this.codigo=conexion.getLong(rs, "CODIGO");
             this.nombre=conexion.getString(rs, "NOMBRE");
             if(conexion.getString(rs, "DATABASE_SID").contains("S"))
                this.databaseSid=conexion.getString(rs, "DATABASE_SID");
             else if(conexion.getString(rs, "DATABASE_SID").contains("F"))
                this.databaseSid=conexion.getString(rs, "DATABASE_SID")+"G";
             else
                this.databaseSid=conexion.getString(rs, "DATABASE_SID");
             this.ipFarmacia=conexion.getString(rs, "DIRECTORIO_ENVIO_MERCADERIA");
      }
    }


   public static String getDataBaseIdReal(String sidFarmacia,ConexionVarianteSid conexion){
       String resultado = "";
        String[] resultadoSplit;

        try {            
            String queryBusqueda = "select * from GLOBAL_NAME@" + sidFarmacia;
            ResultSet rs = conexion.consulta(queryBusqueda);
            while (conexion.siguiente(rs)) {
                resultado = conexion.getString(rs, "GLOBAL_NAME");
                resultadoSplit=resultado.split("\\.");
                //System.out.println(" resultadoSplit  "+resultadoSplit.length+"  resultado  "+resultado);
                resultado=resultadoSplit[0];
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Farmacia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
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

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setDatabaseSid(String databaseSid) {
        this.databaseSid = databaseSid;
    }

    public void setIpFarmacia(String ipFarmacia) {
        this.ipFarmacia = ipFarmacia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String toString(){
        return " this.getDatabaseSid()  "+  this.getDatabaseSid() +"  this.getIpFarmacia()  "+this.getIpFarmacia() +"  this.getNombre()  "+this.getNombre() +"  this.getClass()  "+this.getClass();
    }
}
