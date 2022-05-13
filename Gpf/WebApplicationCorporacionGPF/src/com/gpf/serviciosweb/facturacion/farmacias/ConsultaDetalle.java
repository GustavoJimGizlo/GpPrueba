/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;

/**
 *
 * @author mftellor
 */

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DetalleEnvioBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Logger;



public class ConsultaDetalle extends ObtenerNuevaConexion implements Serializable  {
    private Vector xmlDatos = new Vector();
    private String sSQL = "";
    DetalleEnvioBean datos = null;
    ResultSet rs = null ;
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    Logger log=Logger.getLogger("ConsultaDetalle");

  public ConsultaDetalle(String pbodega, String pdocumento ) {
        super(ConsultaDetalle.class);
        //log.info("Inicia ConsultaDetalle");
        XStream xstream = new XStream(new StaxDriver());
  	ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }

        try{

            sSQL = "SELECT TO_CHAR(item) item, TO_CHAR(bodega) bodega, TO_CHAR(documento_bodega)documento_bodega, TO_CHAR(cantidad) cantidad" +
            " FROM  distribucion.ds_detalle_envios_farmacias" +
            " WHERE documento_bodega = " + pdocumento  +
            " AND   bodega = " + pbodega;
            rs = conexionVarianteSid.consulta(sSQL);
            while(rs.next()) {
                datos = new DetalleEnvioBean();
                datos.setItem(conexionVarianteSid.getString(rs,"ITEM"));
                datos.setBodega(conexionVarianteSid.getString(rs,"BODEGA"));
                datos.setDocumentoBodega(conexionVarianteSid.getString(rs,"DOCUMENTO_BODEGA"));
                datos.setCantidad(conexionVarianteSid.getString(rs,"CANTIDAD"));
                xmlDatos.addElement(datos);
            }
                rs.close();
                conexionVarianteSid.cerrarConexion();
            }catch(Exception ex){
            	log.info(ex.getMessage());
            }
            if(xmlDatos.size()>0){
                xstream.alias("DetalleEnvioBean", DetalleEnvioBean.class);
                resultadoXml=xstream.toXML(xmlDatos);
                resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DetalleEnvioBean","DetalleEnvioBean");
            }
            //log.info("Fin ConsultaDetalle");
        }
    public String getResultadoXml() {
       if(xmlDatos.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
}