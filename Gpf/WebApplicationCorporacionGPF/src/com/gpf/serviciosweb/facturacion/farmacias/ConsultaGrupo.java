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
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataGrupoEnvioBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataGrupoEnvioNewBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;


public class ConsultaGrupo extends ObtenerNuevaConexion implements Serializable {
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    private Vector xmlCliente = new Vector();
    private String sSQL = "";
    DataGrupoEnvioBean datasana = null;
    ResultSet rs = null ;
    Logger log=Logger.getLogger("ConsultaGrupo");

  public ConsultaGrupo(String pbodega, String pdocumento ) {
        super(ConsultaGrupo.class);
        //log.info("Inicio ConsultaGrupo");
        XStream xstream = new XStream(new StaxDriver());
  	ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }

        try{
        	sSQL = "SELECT TO_CHAR(c.grupo) grupo" +
            "  FROM distribucion.ds_detalle_envios_farmacias a," +
            "       distribucion.ds_localizaciones c" +
            " WHERE a.documento_bodega = " + pdocumento  +
            "   AND a.bodega = " + pbodega +
            "   AND a.item = c.item" +
            "   AND a.bodega=c.bodega" +
            "   GROUP BY C.GRUPO" ;            
            rs = conexionVarianteSid.consulta(sSQL);
            while(rs.next()) {
                datasana = new DataGrupoEnvioBean();
                //datasana.setGrupo((String) Convert.getDato(rs,"GRUPO"));
                datasana.setGrupo(rs.getString("GRUPO"));
                xmlCliente.addElement(datasana);
                }
                rs.close();
                conexionVarianteSid.cerrarConexion();
            }catch(Exception ex){
            	log.info(ex.getMessage());
            }
        if(xmlCliente.size()>0){
            xstream.alias("DataGrupoEnvioBean", DataGrupoEnvioBean.class);
            resultadoXml=xstream.toXML(xmlCliente);
            resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataGrupoEnvioBean","DataGrupoEnvioBean");
        }
            //log.info("Fin ConsultaGrupo");
        }

  @SuppressWarnings("unchecked")
public ConsultaGrupo(String idFarmacia, String fechaInicio, String fechaFin ) {
      super(ConsultaGrupo.class);
      
      List<DataGrupoEnvioNewBean> listadoDataGrupoEnvioNewBean=new ArrayList<DataGrupoEnvioNewBean>();
      XStream xstream = new XStream(new StaxDriver());
	  ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
      if(conexionVarianteSid.getConn()==null){
          resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
          return;
      }

      try{     
      	sSQL = "select DISTINCT a.bodega AS BODEGA, a.documento_bodega AS DOCUMENTO_BODEGA,c.grupo AS GRUPO from distribucion.ds_envios_farmacia a, " +
      			" distribucion.ds_detalle_envios_farmacias b," +
      			" distribucion.ds_localizaciones c where a.bodega=b.bodega   and a.documento_bodega=b.documento_bodega   and b.item=c.item " +
      			"  and b.bodega=c.bodega   and a.fecha >= to_date('"+fechaInicio+"','dd-mm-yyyy') and a.fecha < to_date('"+fechaFin+"','dd-mm-yyyy')  " +
      			" and a.farmacia="+ idFarmacia+
                  " and (a.bodega in (7,8) or a.bodega in (45,46)) order by a.bodega";
      	
          rs = conexionVarianteSid.consulta(sSQL);
          while(rs.next()) {
        	  DataGrupoEnvioNewBean dataGrupoEnvioNewBean = new DataGrupoEnvioNewBean();
        	  dataGrupoEnvioNewBean.setBodega(conexionVarianteSid.getString(rs, "BODEGA"));
        	  dataGrupoEnvioNewBean.setDocumentoBodega(conexionVarianteSid.getString(rs, "DOCUMENTO_BODEGA"));
        	  dataGrupoEnvioNewBean.setGrupo(conexionVarianteSid.getString(rs,"GRUPO"));
        	  listadoDataGrupoEnvioNewBean.add(dataGrupoEnvioNewBean);
        	  xmlCliente.add(dataGrupoEnvioNewBean);
           }
              rs.close();
              conexionVarianteSid.cerrarConexion();
          }catch(Exception ex){
          	log.info(ex.getMessage());
          }
      if(listadoDataGrupoEnvioNewBean.size()>0){
          xstream.alias("DataGrupoEnvioNewBean", DataGrupoEnvioNewBean.class);
          resultadoXml=xstream.toXML(listadoDataGrupoEnvioNewBean);
          resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataGrupoEnvioNewBean","DataGrupoEnvioBean");
      }         
      }
     public ConsultaGrupo() {
        super(ConsultaGrupo.class);
     }
     public String getResultadoXml() {
       if(xmlCliente.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
}
