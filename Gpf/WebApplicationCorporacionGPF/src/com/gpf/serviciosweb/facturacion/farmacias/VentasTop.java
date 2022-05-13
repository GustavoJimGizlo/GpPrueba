/**
 * @date 17/07/2012
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.servicios.bean.VentasTopBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author mftellor
 *
 */
public class VentasTop extends ObtenerNuevaConexion{

	  private String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
	  private List<VentasTopBean> resultadoList=new ArrayList<VentasTopBean>();
	  public  VentasTop( String fechaInicio, String fechaFinal , String  codigoPersona){
		  super(VentasTop.class);
	      Logger log=Logger.getLogger("VentasTop");
	      log.info("Inicio Stock VentasTop");
	      fechaInicio=fechaInicio.replace("-", "/");
	      fechaFinal=fechaFinal.replace("-", "/");

	      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
	      XStream xstream = new XStream(new StaxDriver());
	      if(!conexionVarianteSid.isValid()){
	    	  resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
              return;
	      }

	      /*String queryBusqueda="select *   from (select count(d.ITEM) total, p.NOMBRE, i.presentacion,i.codigo CODIGOITEM" +
	                        "  from FA_FACTURAS f, FA_DETALLES_FACTURA d,pr_items i, pr_productos p "+
	                        "  where f.CANCELADA = 'S'   and  f.TIPO_MOVIMIENTO = '01' "+
	                        "  and f.CLASIFICACION_MOVIMIENTO = '01'   and f.PERSONA = "+codigoPersona+"   and d.venta > 0 "+
	                        "  and fecha between to_date('"+fechaInicio+"','dd/mm/yyyy') and to_date('"+fechaFinal+"','dd/mm/yyyy') "+
	                        "  and f.FARMACIA = d.FARMACIA  and f.DOCUMENTO_VENTA = d.DOCUMENTO_VENTA "+
	                        "  and d.ITEM = i.CODIGO  and i.PRODUCTO = p.CODIGO "+
	                        "  group by  d.ITEM, p.NOMBRE, i.presentacion,i.codigo  order by total desc) where rownum <= 15";*/
	      /*
	      String queryBusqueda=" select *   from (select count(d.ITEM) total, p.NOMBRE, i.presentacion,i.codigo CODIGOITEM "+ 
				                  " from FA_FACTURAS f, FA_DETALLES_FACTURA d,pr_items i, pr_productos p "+
				                  " where f.CANCELADA = 'S'   and  f.TIPO_MOVIMIENTO = '01' "+
				                  " and f.CLASIFICACION_MOVIMIENTO = '01'   and f.PERSONA = "+codigoPersona+"   and d.venta > 0 "+ 
				                  " and fecha >= to_date('"+fechaInicio+"','dd/mm/yyyy') "+
				                  " and fecha <= to_date('"+fechaFinal+"','dd/mm/yyyy') "+
				                  " and f.FARMACIA = d.FARMACIA  and f.DOCUMENTO_VENTA = d.DOCUMENTO_VENTA "+ 
				                  " and d.ITEM = i.CODIGO  and i.PRODUCTO = p.CODIGO "+
				                  " group by  d.ITEM, p.NOMBRE, i.presentacion,i.codigo  order by total desc) where rownum <= 15";
	      */
	      String queryBusqueda="select *  from (select count(d.ITEM) total, p.NOMBRE,  i.presentacion, i.codigo CODIGOITEM "+
	    		               " from FA_FACTURAS f, FA_DETALLES_FACTURA d,pr_items i,pr_productos p "+
	    		               " where f.CANCELADA = 'S'  and f.TIPO_MOVIMIENTO = '01'   and f.CLASIFICACION_MOVIMIENTO = '01' "+
                               " and f.PERSONA = "+codigoPersona+"  and d.venta > 0 and TRUNC(f.fecha) >= to_date('"+fechaInicio+"', 'dd/mm/yyyy') "+
                               " and TRUNC(f.fecha) <= to_date('"+fechaFinal+"', 'dd/mm/yyyy')   and f.FARMACIA = d.FARMACIA "+
                               " and f.DOCUMENTO_VENTA = d.DOCUMENTO_VENTA  and d.ITEM = i.CODIGO   and i.PRODUCTO = p.CODIGO "+
                               " group by d.ITEM, p.NOMBRE, i.presentacion, i.codigo   order by total desc) "+
                               " where rownum <= 15";
	      ResultSet resultado=conexionVarianteSid.consulta(queryBusqueda);
	      while (conexionVarianteSid.siguiente(resultado)) {
	             VentasTopBean ventasTopBean=new VentasTopBean(conexionVarianteSid.getString(resultado,"CODIGOITEM"),conexionVarianteSid.getInt(resultado,"TOTAL"),
	            		 conexionVarianteSid.getString(resultado,"NOMBRE"),conexionVarianteSid.getString(resultado,"PRESENTACION"));
	             resultadoList.add(ventasTopBean);
	      }
	      conexionVarianteSid.cerrarConexion();
	      xstream.alias("VentasTopBean", VentasTopBean.class);
	      resultadoXml=xstream.toXML(resultadoList);
	      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
	      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
	      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
	      resultadoXml=resultadoXml.replace("<list>","");
	      resultadoXml=resultadoXml.replace("</list>","");
	      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.VentasTopBean","VentasTopBean");
	      log.info("Fin Stock VentasTop");	      
	  }
	public String getResultadoXml() {
		if(resultadoList.size()>0)
	        return Constantes.respuestaXmlObjeto("OK", resultadoXml);
	      else
	        return Constantes.respuestaXmlObjeto("EEROR", "LA BUSQUEDA NO PRODUJO RESULTADOS");
	}
	  
}
