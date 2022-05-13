/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 *
 * @author mftellor
 */
public class ImagenPromociones extends ObtenerNuevaConexion{


    public byte[] resultadoByte; 

    public ImagenPromociones(){
    	 super(ImagenPromociones.class);
       
    }
    public ImagenPromociones(String item){
        super(ImagenPromociones.class);
        try {
        	 String  queryBusqueda=" select * from  PR_IMAGENES_ITEMS where item = "+item;
            ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
            ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
            while (conexionVarianteSid.siguiente(rs)) {
            	resultadoByte = conexionVarianteSid.getByte(rs, "FOTO");
             
            }
            conexionVarianteSid.cerrarConexion();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ImagenPromociones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  }

