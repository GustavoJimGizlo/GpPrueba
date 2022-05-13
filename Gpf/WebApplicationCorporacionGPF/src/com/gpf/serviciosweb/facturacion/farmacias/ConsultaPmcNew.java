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
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mftellor
 */
public class ConsultaPmcNew extends ObtenerNuevaConexion implements Serializable{
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    List<DataPmcBean> listadoDataPmcBean=new ArrayList<DataPmcBean>();
    Logger log=Logger.getLogger("ConsultaPmcNew");
    public ConsultaPmcNew(String p_persona, String p_item, String p_tipo_id, String p_cod_club, Integer empresa,String promocion){
        super(ConsultaPmcNew.class);
        log.info("Inicio ConsultaPmcNew p_persona:"+p_persona+"  p_item: "+p_item+"  p_tipo_id: "+p_tipo_id
                +" p_cod_club:"+p_cod_club+" empresa:"+empresa+" promocion:  "+promocion);
        XStream xstream = new XStream(new StaxDriver());
        DataPmcBean dato=null;        
        //SE OBTIENE LA CONEXION DE LA EMPRESA DEL MASTER
        ConexionVarianteSid conexionVarianteSid=null;
        conexionVarianteSid=obtenerNuevaConexionVarianteSidEspecialVictoria(empresa.toString());
        
        if(conexionVarianteSid.getConn()==null){
          resultadoXml ="ERROR AL CONECTAR A LA BDD "+empresa;
          return;
        }
        String filtroPromocion="";
        if(promocion!=null)
        	filtroPromocion=" and f.promocion="+promocion;
        
        String queryBusqueda =  "";
        //if(empresa==1)
           queryBusqueda =  "SELECT TO_CHAR(f.fecha,'yyyy-mm-dd HH24:MI:SS') as fecha, f.documento_venta, f.farmacia, f.cancelada, f.tipo_movimiento," +
		         "  f.clasificacion_movimiento, a.empresa, " +
		         "  DECODE (f.tipo_movimiento,'01', DECODE (f.cancelada, 'S', 1, -1),'02', -1) tipo," +
		         "  f.cajas FROM farmacias.fa_movimientos_acumula f, ad_farmacias a" +
				 "  WHERE f.farmacia = a.codigo " +
				 "  AND f.persona = " + p_persona + " AND f.item = " + p_item + filtroPromocion +" and f.fecha>=sysdate-400 "+
				 "  union select to_char(f.fecha, 'yyyy-mm-dd HH24:MI:SS') as fecha, "+
                 "  NULL, NULL, NULL, NULL, NULL, NULL, NULL tipo,  f.cajas "+
                 "  from     farmacias.fa_movimientos_encera f where f.persona ="+p_persona+
                

                 
                 "  ORDER BY fecha";
           /*
        else
            queryBusqueda="SELECT TO_CHAR(f.fecha,'yyyy-mm-dd HH24:MI:SS') as fecha, f.documento_venta, f.farmacia, f.cancelada, f.tipo_movimiento," +
                " f.clasificacion_movimiento, a.empresa, " +
                " DECODE (f.tipo_movimiento,'01', DECODE (f.cancelada, 'S', 1, -1),'02', -1) tipo," +
                " f.cajas FROM farmacias.fa_movimientos_acumula f, ad_farmacias a" +
                " WHERE f.farmacia = a.codigo " +
                " AND f.persona = " + p_persona + " AND f.item = " + p_item +" ORDER BY f.fecha";
        */
        ResultSet resultado1 = conexionVarianteSid.consulta(queryBusqueda);
        while(conexionVarianteSid.siguiente(resultado1)){
	                dato = new DataPmcBean();
	                //datasana.setGrupo((String) Convert.getDato(rs,"GRUPO"));
	                //dato.setFecha((Calendar) Convert.getDato(rs,"FECHA"));
	                dato.setFecha(conexionVarianteSid.getString(resultado1, "FECHA"));
	                dato.setDocumentoVenta(conexionVarianteSid.getString(resultado1,"DOCUMENTO_VENTA"));
	                dato.setEmpresa(conexionVarianteSid.getString(resultado1,"EMPRESA"));
	                dato.setFarmacia(conexionVarianteSid.getString(resultado1,"FARMACIA"));
	                dato.setCancelada(conexionVarianteSid.getString(resultado1,"CANCELADA"));
	                dato.setTipoMvto(conexionVarianteSid.getString(resultado1,"TIPO_MOVIMIENTO"));
	                dato.setClasificacionMvto(conexionVarianteSid.getString(resultado1,"CLASIFICACION_MOVIMIENTO"));
	                dato.setTipo(conexionVarianteSid.getString(resultado1,"TIPO"));
	                dato.setCajas(conexionVarianteSid.getString(resultado1,"CAJAS"));
                    if(dato.getTipoMvto().trim().length()>0 && dato.getFarmacia().trim().length()>0){
		                queryBusqueda = "select REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (nombre))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') nombre" +
	            	                " from   ad_farmacias  where  codigo =" + dato.getFarmacia();
	                        ResultSet resultado2=null;
	                        ConexionVarianteSid conexionVarianteSid2=obtenerNuevaConexionVarianteSidMaster(empresa.toString());
	                        try{
	                           resultado2 = conexionVarianteSid2.consulta(queryBusqueda);
	                        }catch (Exception ex) {
	                          Logger.getLogger(ConsultaPmcNew.class.getName()).log(Level.SEVERE, null, ex);
	                        }
	                        while(conexionVarianteSid2.siguiente(resultado2)){
	                                dato.setNombreFarmacia(conexionVarianteSid2.getString(resultado2,"NOMBRE"));
	                                queryBusqueda =  "select descripcion from   ad_clasificacion_movimientos" +
	                                                 " where  codigo = " + dato.getClasificacionMvto() + " and    tipo_movimiento = " + dato.getTipoMvto();
	                                validarConexionVarianteSid(empresa.toString(),conexionVarianteSid2);
	                                ResultSet resultado3 = conexionVarianteSid2.consulta(queryBusqueda);
	                                while(conexionVarianteSid2.siguiente(resultado3)){
	                                       dato.setDescripcionMvto(conexionVarianteSid2.getString(resultado3,"DESCRIPCION"));
	
	                                       queryBusqueda = "select a.cantidad cantidad, a.unidades unidades  from   fa_detalles_factura a" + " where  a.documento_venta = " + dato.getDocumentoVenta() + " and    a.farmacia = " + dato.getFarmacia() + " and    a.item = " + p_item + " and    a.venta > 0";
	                                       getUnidadesVentasPmc(dato,empresa.toString(),false,queryBusqueda,conexionVarianteSid2);
	                                       //if (Integer.parseInt(dato.getEmpresa())!=empresa.intValue()){
	                                        queryBusqueda="select nvl(SUM(b.cantidad/nvl(b.unidades,1)),0) caja_bonificada" +
						    " from   fa_ofertas_promociones a, fa_detalles_factura b" +
						    " where  a.documento_venta = " + dato.getDocumentoVenta() + " and    a.farmacia = " + dato.getFarmacia() +
						    " and    a.item = " + p_item +" and    a.identificador = " + p_cod_club +
						    " and    a.tipo_identificador = '" + p_tipo_id +"' and    b.documento_venta = a.documento_venta" +
						    " and    b.farmacia = a.farmacia and    b.item = a.item" +
						    " and    b.codigo = a.codigo and    b.promocion is not null";
	                                        getUnidadesVentasPmc(dato,empresa.toString(),true,queryBusqueda,conexionVarianteSid2);
	                                    //}
	                                }
	                        }
	                        
	                        conexionVarianteSid2.cerrarConexion();
                    }
            listadoDataPmcBean.add(dato);
           }
      conexionVarianteSid.cerrarConexion();
      if(listadoDataPmcBean.size()>0){
          xstream.alias("DataPmcBean", DataPmcBean.class);
          resultadoXml=xstream.toXML(listadoDataPmcBean);
          resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
          resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
          resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
          resultadoXml=resultadoXml.replace("<list>","");
          resultadoXml=resultadoXml.replace("</list>","");
          resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.DataPmcBean","DataPmcBean");
      }else
          resultadoXml= "NO EXISTEN DATOS";
      
      log.info("FIN ConsultaPmcNew p_persona:"+p_persona+"  p_item: "+p_item+"  p_tipo_id: "+p_tipo_id
                +" p_cod_club:"+p_cod_club+" empresa:"+empresa);

    }

     public String getResultadoXml() {
       if(listadoDataPmcBean.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
    @SuppressWarnings("static-access")
   private void getUnidadesVentasPmc(DataPmcBean dato,String idEmpresa,Boolean cajaBonificada,String queryBusqueda,
           ConexionVarianteSid conexionVarianteSidMaster){
           ConexionVarianteSid conexionVarianteSid = conexionVarianteSidMaster;
        validarConexionVarianteSid(dato.getEmpresa(),conexionVarianteSid);
        try {
            if(!idEmpresa.equals(dato.getEmpresa()))
            	conexionVarianteSid=obtenerNuevaConexionVarianteSidMaster(dato.getEmpresa());	
               //conexionVarianteSid=obtenerNuevaConexionVarianteSid(idEmpresa);            	
            //if(!cajaBonificada)
            //	 conexionVarianteSid=obtenerNuevaConexionVarianteSidEspecialVictoria(idEmpresa);
            if (conexionVarianteSid.getConn() == null) {
                if(!cajaBonificada){
                    dato.setCantidad("ERROR DE BDD");
                    dato.setUnidades("ERROR DE BDD");
                }else
                    dato.setCajaBonifica("ERROR DE BDD");
                return;
            }
            ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda);
            while (conexionVarianteSid.siguiente(resultado)) {
                if(!cajaBonificada){
                    dato.setCantidad(conexionVarianteSid.getString(resultado,"CANTIDAD"));
                    dato.setUnidades(conexionVarianteSid.getString(resultado,"UNIDADES"));
                }else
                    dato.setCajaBonifica(conexionVarianteSid.getString(resultado,"CAJA_BONIFICADA"));
            }
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaPmcNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!idEmpresa.equals(dato.getEmpresa()))
            conexionVarianteSid.cerrarConexion();
   }
}
