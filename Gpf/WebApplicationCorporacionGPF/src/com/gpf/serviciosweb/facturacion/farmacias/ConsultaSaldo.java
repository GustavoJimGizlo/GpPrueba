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
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataSaldoBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;


public class ConsultaSaldo extends ObtenerNuevaConexion implements Serializable {
    Logger log=Logger.getLogger("ConsultaSaldo");
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    private Vector xmlDato = new Vector();
    private String sSQL = "";
    private String sSQL1 = "";
    DataSaldoBean dato = null;
    ResultSet rs = null ;
    ResultSet rs1 = null ;
 @SuppressWarnings("static-access")
  public ConsultaSaldo(String vnCuenta) {
       super(ConsultaSaldo.class);
       //log.info("Inicio ConsultaSaldo");
        XStream xstream = new XStream(new StaxDriver());
  	    ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1"); //PROD
        //ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("44"); //TEST
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }
        try{
            sSQL = "SELECT saldo" +
            	   " FROM   cr_cuenta_ahorros" +
		   " WHERE  numero_cuenta = to_number( " + vnCuenta + ")";
            rs = conexionVarianteSid.consulta(sSQL);
            if (conexionVarianteSid.siguiente(rs)) {
                dato = new DataSaldoBean();
                dato.setSaldo(conexionVarianteSid.getString(rs,"SALDO"));
                xmlDato.addElement(dato);

                sSQL1 = "INSERT INTO fa_saldos_vital_log" +
         	   	" VALUES(" + conexionVarianteSid.getString(rs,"SALDO") + "," + "sysdate" +
			" ," + vnCuenta + ",user )" ;
                conexionVarianteSid.ejecutar(sSQL1);
            }
            rs.close();
            conexionVarianteSid.cerrarConexion();
            }catch(Exception ex){
                ex.printStackTrace();
                log.info(ex.getMessage());
            }
            if(xmlDato.size()>0){
                xstream.alias("DataSaldoBean", DataSaldoBean.class);
                resultadoXml=xstream.toXML(dato);
                resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataSaldoBean","DataSaldoBean");
           }
            //log.info("Fin ConsultaSaldo");
        }

    public Iterator getXmlDato() {
      return xmlDato.iterator();
    }

    public String getResultadoXml() {
       if(xmlDato.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }   
}

