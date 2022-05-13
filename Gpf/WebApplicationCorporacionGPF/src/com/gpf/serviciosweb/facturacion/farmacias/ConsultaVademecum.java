/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.Information;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 *
 * @author spramirezv
 */
public class ConsultaVademecum extends ObtenerNuevaConexion {
    private Vector xmlDato = new Vector();
    private String sSQL = "";
    ResultSet rs = null ;
    Information dato = null;
    //private int EMPRESA_CONECCION =44;// PROD_DESA
    private int EMPRESA_CONECCION =1;//1 PROD 
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    Logger log=Logger.getLogger("ConsultaAcuPmc");
    
    public ConsultaVademecum(String itemID, String itemDescription ) {
        super(ConsultaVademecum.class);
        XStream xstream = new XStream(new StaxDriver());
        ConexionVarianteSid conexionVarianteSid= obtenerNuevaConexionVarianteSidSidDataBase(EMPRESA_CONECCION, "portal", "p0rt4lbusd4t0s");
        
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }
        try{
            sSQL = "select fnc_vademecum("+(!"0".equals(itemID)?itemID:null)+", "+(!"0".equals(itemDescription)?itemDescription:null)+") as idProducto from dual";    

                    /* " SELECT POR.id_producto   idProducto,"
            		+  " POR.ID_PADRE idPadre"
            		+  " FROM por_producto por"
            		+  " WHERE (POR.CODIGO_INTERNO_GPF = '"+itemID+"' or '"+itemID+"' = '0')"
            		+  " and (POR.descripcion_completa LIKE upper('%"+itemDescription+"%') or '"+itemDescription+"' = '0')";
             		*/
            rs=conexionVarianteSid.consulta(sSQL);
            while(rs.next()) {
            	/*String codigoPadre =rs.getString("idPadre");
            	if(codigoPadre == null)*/
            		xmlDato.addAll(consultaVademecum(rs.getString("idProducto")));
            	/*else 
            		xmlDato.addAll(consultaVademecum(codigoPadre));*/
            }
            conexionVarianteSid.cerrarConexion();
            rs.close();         
       }catch(Exception ex){
           ex.printStackTrace();
           log.info(ex.getMessage());
       }

        if(xmlDato.size()>0){
            xstream.alias("Information", Information.class);
            resultadoXml=xstream.toXML(xmlDato);
            resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.Information","Information");
        }
    }

    public Vector consultaVademecum(String  codigoProd){
    	Vector xmlDato = new Vector();
    	dato = null;
        try {
            
            XStream xstream = new XStream(new StaxDriver());
            ConexionVarianteSid conexionVarianteSid =  obtenerNuevaConexionVarianteSidSidDataBase(EMPRESA_CONECCION, "portal", "p0rt4lbusd4t0s");
            if (conexionVarianteSid.getConn() == null) {
                resultadoXml = "FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
                return null;
            }
            sSQL = " select *  from por_vademecum va "
            	 + " where  va.id_producto = "+codigoProd;
         
            		
            rs = conexionVarianteSid.consulta(sSQL);
            while (rs.next()) {
            	dato = new Information();
            	dato.setAntidoto(rs.getString("antidoto"));
            	dato.setContraindicaciones(rs.getString("contraindicaciones"));
            	dato.setDosificacion(rs.getString("dosificacion"));
            	dato.setEfectosSecundarios(rs.getString("efectos_secundarios"));
            	dato.setEquivalente(rs.getString("equivalente"));
            	dato.setIndicaciones(rs.getString("indicaciones"));
            	dato.setInteracciones(rs.getString("interacciones"));
            	dato.setPatologiaAplica(rs.getString("patologia_aplica"));
            	dato.setPrecaucionesUso(rs.getString("precauciones_uso"));
            	dato.setPresentacion(rs.getString("presentacion"));
            	dato.setPresentaciones(rs.getString("presentaciones"));
            	dato.setPrincipioActivo(rs.getString("principio_activo"));
				xmlDato.addElement(dato);

            }
          
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaVademecum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDato;
    }
    
    
    
    
    
    public ConsultaVademecum(String  principioActivo){
        super(ConsultaVademecum.class);
       try {
           log.info("Inicio ConsultaAcuPmc Cambio");
           XStream xstream = new XStream(new StaxDriver());
           ConexionVarianteSid  conexionVarianteSid =  obtenerNuevaConexionVarianteSidSidDataBase(EMPRESA_CONECCION, "portal", "p0rt4lbusd4t0s");
           if (conexionVarianteSid.getConn() == null) {
               resultadoXml = "FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
               return;
           }
           sSQL =  " select por.id_producto " +
                   " from por_vademecum va , por_producto por " +
                   " where  va.id_producto = por.id_producto " +
                   " and UPPER(va.PRINCIPIO_ACTIVO) like UPPER('%"+principioActivo+"%')";
        
           		
           rs = conexionVarianteSid.consulta(sSQL);
           while (rs.next()) {
           	String dato = new String();
				dato = rs.getString("id_producto");
				xmlDato.addElement(dato);
           }


           if(xmlDato.size()>0){
               xstream.alias("Information", Information.class);
               resultadoXml=xstream.toXML(xmlDato);
               resultadoXml=resultadoXml.replace("string","idProducto");
           }
       } catch (SQLException ex) {
           Logger.getLogger(ConsultaVademecum.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
    
    private Information setInformation(){
    	Information dato = new Information();
    	dato.setAntidoto(new String("0"));
    	dato.setContraindicaciones(new String("0"));
    	dato.setDosificacion(new String("0"));
    	dato.setEfectosSecundarios(new String("0"));
    	dato.setEquivalente(new String("0"));
    	dato.setIndicaciones(new String("0"));
    	dato.setInteracciones(new String("0"));
    	dato.setPatologiaAplica(new String("0"));
    	dato.setPrecaucionesUso(new String("0"));
    	dato.setPresentacion(new String("0"));
    	dato.setPresentaciones(new String("0"));
    	dato.setPrincipioActivo(new String("0"));
    	
        return dato;
    }
    public String getResultadoXml() {
       if(xmlDato.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
}
