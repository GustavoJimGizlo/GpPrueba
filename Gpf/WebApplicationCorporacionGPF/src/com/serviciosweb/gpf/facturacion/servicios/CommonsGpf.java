/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;

/**
 *
 * @author mftellor
 */
public class CommonsGpf {

    public List<HashMap<String,String>> getCategoria(String tipoNegocio){
        String filtroBusqueda="";
        if(tipoNegocio!=null)
            filtroBusqueda=" where TIPO_NEGOCIO='"+tipoNegocio+"'";
        String querySql="select * from pr_secciones  "+filtroBusqueda+" order by nombre";//codigo not in (37,38,65,90,36) order by nombre";
        return getResultadoGenerico(querySql);
    }
    public List<HashMap<String,String>> getCasa(String codigoCategoria){
        String querySql="select * from pr_casas where seccion="+codigoCategoria+" order by nombre";
        return getResultadoGenerico(querySql);
    }
    public List<HashMap<String,String>> getProducto(String codigoCasa){
        String querySql="select * from pr_productos where casa="+codigoCasa+" order by nombre";
        return getResultadoGenerico(querySql);
    }
   public List<HashMap<String,String>> getGrupoLocalizacion(String tipoNegocioBodega){
        String querySql="select distinct grupo as codigo, grupo as NOMBRE from   ds_localizaciones where bodega="+tipoNegocioBodega+"  order by grupo";
        return getResultadoGenerico(querySql);
    }

   public List<HashMap<String,String>> getGrupoModulo(String grupo){
        String querySql="select distinct  L.ZONA||L.PISO||L.FILA_RACK||L.MODULO CODIGO,   L.ZONA||L.PISO||L.FILA_RACK||L.MODULO NOMBRE "+
                        " from   ds_localizaciones l where grupo="+grupo+" order by L.ZONA||L.PISO||L.FILA_RACK||L.MODULO";
        return getResultadoGenerico(querySql);
    }


    public List<HashMap<String,String>> getResultadoGenerico(String querySql){
        List<HashMap<String,String>> listado=new ArrayList<HashMap<String, String>>();
        ResultSet rs=null;
        ConexionVarianteSid conexionVarianteSid=null;
        try{
            conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
            rs = conexionVarianteSid.consulta(querySql.toUpperCase());
            while(conexionVarianteSid.siguiente(rs)){
                HashMap<String,String> dato=new HashMap<String,String>();
                dato.put("CODIGO", conexionVarianteSid.getString(rs, "CODIGO"));
                dato.put("DESCRIPCION", conexionVarianteSid.getString(rs, "NOMBRE"));
                listado.add(dato);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //conexionVarianteSid.cerrarConexion();
        return listado;
    }
   @SuppressWarnings("static-access")
   public static ConexionVarianteSid obtenerConexion(String ipFarmacia,String sid){
        try {
            ConexionVarianteSid conexionVarianteSid = new ConexionVarianteSid(ipFarmacia, sid);
            return conexionVarianteSid;
        } catch (NamingException ex) {
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }


   @SuppressWarnings("static-access")
   public static ConexionVarianteSid obtenerNuevaConexionVarianteSid(String parametro){
        try {
            return new ConexionVarianteSid(Integer.decode(parametro));
        } catch (NamingException ex) {
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }
   @SuppressWarnings("static-access")
   public static void validarConexionVarianteSid(String empresa,ConexionVarianteSid conexionVarianteSid){
        try {
            if (conexionVarianteSid.getConn()==null)
                 conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
            else if (conexionVarianteSid.getConn().isClosed())
                conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
        } catch (SQLException ex) {
            Logger.getLogger(CommonsGpf.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
