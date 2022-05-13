/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;


import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataPmcBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mftellor
 */
public class ConsultaAcuPmc extends ObtenerNuevaConexion {
    private Vector xmlDato = new Vector();
    private String sSQL = "";
    ResultSet rs = null ;
    DataPmcBean dato = null;
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    Logger log=Logger.getLogger("ConsultaAcuPmc");
    public ConsultaAcuPmc(String p_persona, String p_promo, String p_item, String empresa ) {
        super(ConsultaAcuPmc.class);
        //log.info("Inicio ConsultaAcuPmc");
        XStream xstream = new XStream(new StaxDriver());
        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSidEspecialVictoria(empresa);
        
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }
        try{
            sSQL =  "SELECT cajas_acumuladas, cajas_residuo, ubicacion" +
            	    " FROM   fa_detalles_Acumulados" +
		    " WHERE  persona = " + p_persona +" AND    promocion = " + p_promo +
		    " AND item = " + p_item ;

            rs=conexionVarianteSid.consulta(sSQL);
            while(rs.next()) {
                dato = new DataPmcBean(null);
                dato.setCajasAcumuladas(rs.getString("CAJAS_ACUMULADAS"));
                dato.setCajasResiduo(rs.getString("CAJAS_RESIDUO"));
                dato.setUbicacion(rs.getString("UBICACION"));
                xmlDato.addElement(dato);
            }
         
           //if (dato == null)
           //xmlDato.addElement(setDataPmcBean());
            
          conexionVarianteSid.cerrarConexion();
          rs.close();         
       }catch(Exception ex){
           ex.printStackTrace();
           log.info(ex.getMessage());
       }

        if(xmlDato.size()>0){
            xstream.alias("DataPmcBean", DataPmcBean.class);
            resultadoXml=xstream.toXML(dato);
            resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataPmcBean","DataPmcBean");
        }
        //log.info("Fin ConsultaAcuPmc");
    }

    public ConsultaAcuPmc(String p_persona, String p_promo, String p_item, Integer empresa){
         super(ConsultaAcuPmc.class);
        try {
            log.info("Inicio ConsultaAcuPmc Cambio");
            XStream xstream = new XStream(new StaxDriver());
            ConexionVarianteSid conexionVarianteSid = conexionVarianteSid = obtenerNuevaConexionVarianteSidEspecialVictoria(empresa.toString());
            if (conexionVarianteSid.getConn() == null) {
                resultadoXml = "FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
                return;
            }
            sSQL = "SELECT cajas_acumuladas, cajas_residuo, ubicacion" + " FROM   fa_detalles_Acumulados WHERE  persona = " + p_persona + " AND    promocion = " + p_promo + " AND    item = " + p_item;
            rs = conexionVarianteSid.consulta(sSQL);
            while (rs.next()) {
                    dato = new DataPmcBean();
                    dato.setCajasAcumuladas(rs.getString("CAJAS_ACUMULADAS"));
                    dato.setCajasResiduo(rs.getString("CAJAS_RESIDUO"));
                    dato.setUbicacion(rs.getString("UBICACION"));
                    xmlDato.addElement(dato);
            }
            if (dato == null)
                xmlDato.addElement(setDataPmcBean());

            if(xmlDato.size()>0){
                xstream.alias("DataPmcBean", DataPmcBean.class);
                resultadoXml=xstream.toXML(dato);
                resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataPmcBean","DataPmcBean");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaAcuPmc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private DataPmcBean setDataPmcBean(){
         DataPmcBean dato = new DataPmcBean(null);
         dato.setCajasAcumuladas(new String("0"));
         dato.setCajasResiduo(new String("0"));
         dato.setUbicacion(new String("0"));
         return dato;
    }
    public String getResultadoXml() {
       if(xmlDato.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
}
