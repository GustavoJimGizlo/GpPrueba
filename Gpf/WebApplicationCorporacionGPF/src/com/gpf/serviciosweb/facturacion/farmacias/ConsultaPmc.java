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
import java.util.Vector;
import java.util.logging.Logger;

/**
 *
 * @author mftellor
 */
public class ConsultaPmc extends ObtenerNuevaConexion implements Serializable{
    private Vector xmlDato = new Vector();
    private String sSQL = "";
    private String sSQL1 = "";
    private String sSQL2 = "";
    private String sSQL3 = "";
    private String sSQL4 = "";
    DataPmcBean dato = null;
    ResultSet rs = null ;
    ResultSet rs1 = null;
    ResultSet rs2 = null ;
    ResultSet rs3 = null ;
    ResultSet rs4 = null ;
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    Logger log=Logger.getLogger("ConsultaPmc");
  public ConsultaPmc(String p_persona, String p_item, String p_tipo_id, String p_cod_club) {
        super(ConsultaPmc.class);
        //log.info("Inicio ConsultaPmc");
        XStream xstream = new XStream(new StaxDriver());
  	ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
        if(conexionVarianteSid.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }

        try{
            sSQL =  "SELECT fecha, documento_venta, farmacia, cancelada, tipo_movimiento, clasificacion_movimiento," +
			         " DECODE (tipo_movimiento,'01', DECODE (cancelada, 'S', 1, -1),'02', -1) tipo," +
			         " cajas  FROM fa_movimientos_acumula WHERE persona = " + p_persona + " AND item = " + p_item  +
				 " ORDER BY fecha";

	            rs = conexionVarianteSid.consulta(sSQL);

	            while(rs.next()) {
	                dato = new DataPmcBean();
	                dato.setFecha(conexionVarianteSid.getString(rs,"FECHA"));
	                dato.setDocumentoVenta(conexionVarianteSid.getString(rs,"DOCUMENTO_VENTA"));
	                dato.setFarmacia(conexionVarianteSid.getString(rs,"FARMACIA"));
	                dato.setCancelada(conexionVarianteSid.getString(rs,"CANCELADA"));
	                dato.setTipoMvto(conexionVarianteSid.getString(rs,"TIPO_MOVIMIENTO"));
	                dato.setClasificacionMvto(conexionVarianteSid.getString(rs,"CLASIFICACION_MOVIMIENTO"));
	                dato.setTipo(conexionVarianteSid.getString(rs,"TIPO"));
	                dato.setCajas(conexionVarianteSid.getString(rs,"CAJAS"));
	                sSQL1 = "select nombre from   ad_farmacias where  codigo =" + dato.getFarmacia();

	                rs1 = conexionVarianteSid.consulta(sSQL1);
	                while(rs1.next()) {
	                	dato.setNombre(conexionVarianteSid.getString(rs1,"NOMBRE"));
	                }
	                sSQL2 =  "select descripcion from   ad_clasificacion_movimientos" +
				 " where  codigo = " + dato.getClasificacionMvto()+ " and    tipo_movimiento = " + dato.getTipoMvto();

	                rs2 = conexionVarianteSid.consulta(sSQL2);
	                while(rs2.next()) {
	                	dato.setDescripcion(conexionVarianteSid.getString(rs2,"DESCRIPCION"));
	               	}
	                sSQL3 = "select a.cantidad cantidad, a.unidades unidades " +
                	  " from   fa_detalles_factura a" +
                	  " where  a.documento_venta = " + dato.getDocumentoVenta() +
                	  " and    a.farmacia = " + dato.getFarmacia() +
                	  " and    a.item = " + p_item +
                	  " and    a.venta > 0";
	                	rs3 = conexionVarianteSid.consulta(sSQL3);
	                	while(rs3.next()) {
	                		dato.setCantidad(conexionVarianteSid.getString(rs3,"CANTIDAD"));
	                		dato.setUnidades(conexionVarianteSid.getString(rs3,"UNIDADES"));
	                	}
	                  sSQL4 = "select SUM(b.cantidad/nvl(b.unidades,1)) caja_bonificada" +
						    " from   fa_ofertas_promociones a, fa_detalles_factura b" +
						    " where  a.documento_venta = " + dato.getDocumentoVenta() +
						    " and    a.farmacia = " + dato.getFarmacia() +
						    " and    a.item = " + p_item +
						    " and    a.identificador = " + p_cod_club +
						    " and    a.tipo_identificador = " + p_tipo_id +
						    " and    b.documento_venta = a.documento_venta" +
						    " and    b.farmacia = a.farmacia" +
						    " and    b.item = a.item" +
						    " and    b.codigo = a.codigo" +
						    " and    b.promocion is not null";
	                  rs4 = conexionVarianteSid.consulta(sSQL4);
	                  while(rs4.next()) {
	                  	dato.setCajaBonifica(conexionVarianteSid.getString(rs4,"CAJA_BONIFICADA"));
	                  }
                     xmlDato.addElement(dato);
	       }
                rs.close();
                rs1.close();
                rs2.close();
                rs3.close();
                conexionVarianteSid.cerrarConexion();
         }catch(Exception ex){
        	 log.info(ex.getMessage());
         }
        if(xmlDato.size()>0){
            xstream.alias("DataPmcBean", DataPmcBean.class);
            resultadoXml=xstream.toXML(xmlDato);
            resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataPmcBean","DataPmcBean");
        }
            //log.info("Fin ConsultaPmc");
  }
      public String getResultadoXml() {
       if(xmlDato.size()>0)
          return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
       else
         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
    }
}
