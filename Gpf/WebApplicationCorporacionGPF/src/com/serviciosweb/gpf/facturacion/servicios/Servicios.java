/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataPmcBean;
import com.serviciosweb.gpf.facturacion.servicios.bean.Farmacia;
import com.serviciosweb.gpf.facturacion.servicios.bean.ItemStockFarmacia;
import com.serviciosweb.gpf.facturacion.servicios.bean.FacturasVentasProd;
import com.serviciosweb.gpf.facturacion.servicios.bean.EstadisticasVentas;
import com.serviciosweb.gpf.facturacion.servicios.bean.VentasTopBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.naming.NamingException;
import javax.swing.ImageIcon;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import java.io.File;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.HashMap;
/**
 *
 * @author mftellor
 */
public class Servicios {

  
	Logger log=Logger.getLogger(this.getClass().getName());
	
    @SuppressWarnings("static-access")
    public static String  facturasProd(String idItem,String idProducto,String codigoPersona, String identificacion,
            String fechaDesde,String fechaHasta,String codigoFarmacia){
      identificacion=identificacion.toUpperCase();
      if(idProducto.equals("NULL"))
          idProducto=" ";
      else
          idProducto=" and p.CODIGO="+idProducto;

      if(idItem.equals("NULL"))
          idItem=" ";
      else
          idItem=" and d.ITEM="+idItem;

      if(codigoPersona.equals("NULL"))
          codigoPersona=" ";
      else
          codigoPersona=" and f.PERSONA ="+codigoPersona;

      if(identificacion.equals("NULL"))
          identificacion=" ";
      else
          identificacion="and f.IDENTIFICACION = '"+identificacion+"'";


      XStream xstream = new XStream(new StaxDriver());
      fechaDesde=fechaDesde.replace("-", "/");
      fechaHasta=fechaHasta.replace("-", "/");
      Logger.getLogger(Servicios.class.getName()).info("Inicio Facturas Prod ");

      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");


      if(!conexionVarianteSid.isValid())
          return Constantes.respuestaXmlUnica("Error al conectar a la BDD");

      //Collection <HashMap<String,String>> listadoResultado=new ArrayList<HashMap<String,String>>();
      Collection <FacturasVentasProd> listadoResultado=new ArrayList<FacturasVentasProd>();

      String queryEjecutar="select f.farmacia, f.documento_venta,f.VENTA_TOTAL_FACTURA,  f.primer_apellido ||' '|| f.segundo_apellido|| ' ' ||f.nombres cliente,"+
                          " f.identificacion, TO_CHAR(f.fecha,'dd-mm-yyyy HH24:MI:SS') as fecha,d.ITEM, p.NOMBRE, i.presentacion, d.CANTIDAD,round(d.venta+(d.venta*d.porcentaje_iva),2) as VENTA "+//d.VENTA"+
                          " from FA_FACTURAS f, FA_DETALLES_FACTURA d,pr_items i, pr_productos p"+
                          " where f.farmacia<>"+codigoFarmacia+
                          " and fecha between to_date('"+fechaDesde+"','dd/mm/yyyy') and to_date('"+fechaHasta+"','dd/mm/yyyy')"+
                          " and f.CANCELADA = 'S'"+
                          " and  f.TIPO_MOVIMIENTO = '01'"+
                          " and f.CLASIFICACION_MOVIMIENTO = '01'"+
                          codigoPersona+//"and f.PERSONA = nvl(3002533,f.PERSONA)"+
                          identificacion+//"and f.IDENTIFICACION = nvl(null,f.IDENTIFICACION)"+
                          idProducto+//"and p.CODIGO = nvl(3364,p.CODIGO)"+
                          idItem+//"and d.ITEM = nvl(21330,d.ITEM)"+
                          " and f.FARMACIA = d.FARMACIA"+
                          " and f.DOCUMENTO_VENTA = d.DOCUMENTO_VENTA"+
                          " and d.ITEM = i.CODIGO"+
                          " and i.PRODUCTO = p.CODIGO order by f.fecha";
      ResultSet resultado = conexionVarianteSid.consulta(queryEjecutar);
      while(conexionVarianteSid.siguiente(resultado)){
          FacturasVentasProd facturasVentasProd=new FacturasVentasProd();
          facturasVentasProd.setCantidad(conexionVarianteSid.getString(resultado, "CANTIDAD"));
          facturasVentasProd.setCliente(conexionVarianteSid.getString(resultado, "CLIENTE"));
          facturasVentasProd.setDocumentoVenta(conexionVarianteSid.getString(resultado, "DOCUMENTO_VENTA"));
          facturasVentasProd.setFarmacia(conexionVarianteSid.getString(resultado, "FARMACIA"));
          facturasVentasProd.setFecha(conexionVarianteSid.getString(resultado, "FECHA"));
          facturasVentasProd.setIdentificacion(conexionVarianteSid.getString(resultado, "IDENTIFICACION"));
          facturasVentasProd.setItem(conexionVarianteSid.getString(resultado, "ITEM"));
          facturasVentasProd.setNombre(conexionVarianteSid.getString(resultado, "NOMBRE"));
          facturasVentasProd.setPresentacion(conexionVarianteSid.getString(resultado, "PRESENTACION"));
          facturasVentasProd.setVenta(conexionVarianteSid.getString(resultado, "VENTA"));
          facturasVentasProd.setVentaTotalFactura(conexionVarianteSid.getString(resultado, "VENTA_TOTAL_FACTURA"));
          listadoResultado.add(facturasVentasProd);
      }
      conexionVarianteSid.cerrarConexion();
      Logger.getLogger(Servicios.class.getName()).info("Fin Facturas Prod");
      xstream.alias("FacturasVentasProd", FacturasVentasProd.class);
      String resultadoXml=xstream.toXML(listadoResultado);
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
      resultadoXml=resultadoXml.replace("<list>","");
      resultadoXml=resultadoXml.replace("</list>","");
      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.FacturasVentasProd","FacturasVentasProd");
      return Constantes.respuestaXmlObjeto("OK", resultadoXml);//xstream.toXML(listadoResultado);
    }








    @SuppressWarnings("static-access")
    public  static String bodegaItem(String idCadena,String idItem){
       Logger.getLogger(Servicios.class.getName()).info("Inicio Bodega Item");
       XStream xstream = new XStream(new StaxDriver());
      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid(idCadena);

      if(!conexionVarianteSid.isValid())
          return Constantes.respuestaXmlUnica("Error al conectar a la BDD");

       String queryEjecutar="select  grupo from	 ad_bodegas b, ad_bodega_adicional ba, "+
		            " pr_items_empresa ie, ds_localizaciones lo  where  b.empresa = ie.empresa_despacha and "+
                            " ie.empresa_recibe="+idCadena+" and ie.item="+idItem+" and ba.bodega = b.codigo and "+
		            " ba.tipo_bodega = 'D' and b.tipo_negocio in ('N', 'M') and lo.bodega = b.codigo and "+
		            " lo.item = ie.item";
       ResultSet resultado = conexionVarianteSid.consulta(queryEjecutar);
       Logger.getLogger(Servicios.class.getName()).info("Fin Bodega Item");
       while(conexionVarianteSid.siguiente(resultado)){
           return Constantes.respuestaXmlObjeto("OK", conexionVarianteSid.getString(resultado, "GRUPO"));
       }
       conexionVarianteSid.cerrarConexion();
       return Constantes.respuestaXmlObjeto("LA CONSULTA NO GENERO RESULTADO", "LA CONSULTA NO GENERO RESULTADO");
    }

   @SuppressWarnings("static-access")
   public static String pasoEstadisticasLocales(String idLocalOrigen,String idLocalDestino,String tipoEstadisticas){
              Logger.getLogger(Servicios.class.getName()).info("Inicio Paso Estadisticas Locales");


      //obtenerConexion("1");
      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");

      Farmacia farmacia=new Farmacia(Long.decode(idLocalOrigen),null,conexionVarianteSid);
      farmacia.setDatabaseSid(farmacia.getDataBaseIdReal(farmacia.getDatabaseSid(), conexionVarianteSid));
      //System.out.println("farmacia  "+farmacia.toString());
      conexionVarianteSid=obtenerConexion(farmacia.getIpFarmacia(),farmacia.getDatabaseSid());


      if(!conexionVarianteSid.isValid())
          return Constantes.respuestaXmlUnica("Error al conectar a la BDD del Local Origen");


       String tipoEstadisticaFiltro="";
       if(tipoEstadisticas.equals("M"))
           tipoEstadisticaFiltro=" AND exists ( select 1 from pr_items where codigo = o.item and tipo_negocio = 'M' ) ";
       else if(tipoEstadisticas.equals("N"))
           tipoEstadisticaFiltro=" AND exists ( select 1 from pr_items where codigo = o.item and tipo_negocio = 'N' ) ";
       else if(tipoEstadisticas.equals("T"))
           tipoEstadisticaFiltro=" ";

       String queryBusqueda="select FARMACIA, ITEM, NUMERO_PERIODO, VENTA "+
                            " from farmacias.pe_estadisticas_venta o "+
                            " where  farmacia = "+idLocalOrigen+
                            tipoEstadisticaFiltro+
                            " AND (((   (    calcula_periodo (SYSDATE, 'S') - 7 <= 0 "+
                            "           AND NOT (    o.numero_periodo > calcula_periodo (SYSDATE, 'S') "+
                            "           AND o.numero_periodo < calcula_periodo (SYSDATE, 'S') - 7 + 48  ) "+
                            "          )"+
                            "       OR (    calcula_periodo (SYSDATE, 'S') - 7 > 0 "+
                            "           AND o.numero_periodo >= calcula_periodo (SYSDATE, 'S') - 7 "+
                            "           AND o.numero_periodo <= calcula_periodo (SYSDATE, 'S')  ) "+
                            "      ) "+
                            "     ) "+
                            "    )";
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda);
       String insertarPedidoNuevoLocal = insertarPedidoNuevoLocal(resultado,idLocalDestino);
       Logger.getLogger(Servicios.class.getName()).info("Fin Paso Estadisticas Locales");
       conexionVarianteSid=obtenerConexion(farmacia.getIpFarmacia(),farmacia.getDatabaseSid());
       conexionVarianteSid.cerrarConexion();
       return insertarPedidoNuevoLocal;
   }
   @SuppressWarnings("static-access")
   private static String insertarPedidoNuevoLocal(ResultSet resultado,String idLocalDestino){
      //obtenerConexion("1");
      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");

      XStream xstream = new XStream(new StaxDriver());
      Collection<EstadisticasVentas> listadoResultado=new ArrayList<EstadisticasVentas>();

      Farmacia farmacia=new Farmacia(Long.decode(idLocalDestino),null,conexionVarianteSid);
      farmacia.setDatabaseSid(farmacia.getDataBaseIdReal(farmacia.getDatabaseSid(), conexionVarianteSid));

      //System.out.println("farmacia  "+farmacia.toString());
      conexionVarianteSid=obtenerConexion(farmacia.getIpFarmacia(),farmacia.getDatabaseSid());

      if(!conexionVarianteSid.isValid())
          return Constantes.respuestaXmlUnica("Error al conectar a la BDD del Local de Destino");

       //conexionVarianteSid.ejecutar("DELETE FROM pe_estadisticas_venta WHERE FARMACIA="+idLocalDestino);
       String queryInsert="";
       Integer totalEstadisticasInsertados=0;
       Integer totalEstadisticasOrigen=0;
       Integer totalEstadisticasActualizados=0;
       Integer totalEstadisticasFallan=0;
       Integer numeroActualizado=0;
       while(conexionVarianteSid.siguiente(resultado)){
           totalEstadisticasOrigen++;
           EstadisticasVentas estadisticasVentas=new EstadisticasVentas(idLocalDestino,//conexionVarianteSid.getString(resultado, "FARMACIA"),
                               conexionVarianteSid.getString(resultado, "ITEM"),conexionVarianteSid.getString(resultado, "NUMERO_PERIODO"),
                               conexionVarianteSid.getString(resultado, "VENTA"),"N");
               //VERIFICAR LA CONEXION POR SI ACASO ALGUN ERROR
               if(!conexionVarianteSid.isValid())
                  conexionVarianteSid=obtenerConexion(farmacia.getIpFarmacia(),farmacia.getDatabaseSid());

               if(updateEstadisticaVenta(estadisticasVentas,conexionVarianteSid)>0)
                  totalEstadisticasActualizados++;
               else{
                 if(!conexionVarianteSid.isValid())
                    conexionVarianteSid=obtenerConexion(farmacia.getIpFarmacia(),farmacia.getDatabaseSid());

                   estadisticasVentas.setMensaje(insertarEstadisticaVenta(estadisticasVentas,conexionVarianteSid));
                    if(estadisticasVentas.getMensaje().equals("OK")){
                      estadisticasVentas.setInsertado("S");
                      totalEstadisticasInsertados++;
                    }else
                       totalEstadisticasFallan++;
               }

               listadoResultado.add(estadisticasVentas);
       }

      xstream.alias("EstadisticasVentas", EstadisticasVentas.class);
      String resultadoXml=xstream.toXML(listadoResultado);
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
      resultadoXml=resultadoXml.replace("<list>","");
      resultadoXml=resultadoXml.replace("</list>","");
      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.EstadisticasVentas","EstadisticasVentas");
      //return Constantes.respuestaXmlObjeto("OK", resultadoXml);
      return Constantes.respuestaXml("<TotalLocalOrigen>"+totalEstadisticasOrigen+"</TotalLocalOrigen>" +
                                          "<TotalInsertadoDestino>"+totalEstadisticasInsertados+"</TotalInsertadoDestino>" +
                                          "<TotalActualizados>"+totalEstadisticasActualizados+"</TotalActualizados>" +
                                          "<TotalFallan>"+totalEstadisticasFallan+"</TotalFallan>");
   }

   private static Integer updateEstadisticaVenta(EstadisticasVentas estadisticasVentas,ConexionVarianteSid conexion){
       String queryUpdate="UPDATE pe_estadisticas_venta SET VENTA="+estadisticasVentas.getVenta()+" WHERE FARMACIA="+estadisticasVentas.getCodigoFarmacia()+" AND ITEM="+estadisticasVentas.getCodigoItem()+" AND numero_periodo="+estadisticasVentas.getNumeroPeridodo();
       return conexion.updateQuery(queryUpdate);

   }
   private static String insertarEstadisticaVenta(EstadisticasVentas estadisticasVentas,ConexionVarianteSid conexion){
       String queryInsert="INSERT INTO pe_estadisticas_venta "+
                        "   (farmacia, item, venta, numero_periodo)"+
                        " VALUES ("+estadisticasVentas.getCodigoFarmacia()+"" +
                        ", "+estadisticasVentas.getCodigoItem()+"" +
                        ", "+estadisticasVentas.getVenta()+"" +
                        ", "+estadisticasVentas.getNumeroPeridodo()+" )";
       return conexion.ejecutarQuery(queryInsert);
   }

    @SuppressWarnings("static-access")
   public static String stockItemsFarmacias(String item, String empresas,String farmacia){
       Logger.getLogger(Servicios.class.getName()).info("Inicio Stock Items Farmacias");
       XStream xstream = new XStream(new StaxDriver());
       Collection<ItemStockFarmacia> listadoItemStockFarmacia=new ArrayList<ItemStockFarmacia>();
       String listadoEmpresa[] = new String[1];
       String queryBusqueda="";
       ResultSet resultado;
       String filtroAdicional="";
       if(!farmacia.equals("0"))
           filtroAdicional=" and farmacia in ("+farmacia.replace("-", ",")+")";

       if(empresas.contains("-"))
           listadoEmpresa=empresas.split("-");
       else
           listadoEmpresa[0]=empresas;
       for(String codigoEmpresa:listadoEmpresa){
           ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid(codigoEmpresa.trim());
           if(!conexionVarianteSid.isValid()){
               Logger.getLogger(Servicios.class.getName()).info("Error al conectar "+codigoEmpresa);
               break;
           }
           queryBusqueda="select a.farmacia, b.nombre, a.cantidad_stock, a.unidad_venta,a.pvp_con_iva,a.pvp_sin_iva from pr_items_autorizados a, ad_farmacias b " +
                   " where a.farmacia=b.codigo and a.item="+item +filtroAdicional;
           resultado = conexionVarianteSid.consulta(queryBusqueda);
           while(conexionVarianteSid.siguiente(resultado)){
               ItemStockFarmacia itemStockFarmacia=new ItemStockFarmacia(conexionVarianteSid.getString(resultado, "FARMACIA"),
                       conexionVarianteSid.getString(resultado, "CANTIDAD_STOCK"),
                       conexionVarianteSid.getString(resultado, "UNIDAD_VENTA"),
                       conexionVarianteSid.getString(resultado, "pvp_con_iva"),
                       conexionVarianteSid.getString(resultado, "pvp_sin_iva"),
                       conexionVarianteSid.getString(resultado, "nombre"));
               listadoItemStockFarmacia.add(itemStockFarmacia);
           }
           conexionVarianteSid.cerrarConexion();
       }
      xstream.alias("ItemStockFarmacia", ItemStockFarmacia.class);
      String resultadoXml=xstream.toXML(listadoItemStockFarmacia);
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
      resultadoXml=resultadoXml.replace("<list>","");
      resultadoXml=resultadoXml.replace("</list>","");
      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.ItemStockFarmacia","ItemStockFarmacia");

      Logger.getLogger(Servicios.class.getName()).info("Fin Stock Items Farmacias");
      if(listadoItemStockFarmacia.size()>0)
        return Constantes.respuestaXmlObjeto("OK", resultadoXml);
      else
        return Constantes.respuestaXmlObjeto("OK", "");
   }

@SuppressWarnings("static-access")
public static String ConsultaPmcNew(String p_persona, String p_item, String p_tipo_id, String p_cod_club, Integer empresa){
        Logger.getLogger(Servicios.class.getName()).info("Inicio Consultas PMC p_persona:"+p_persona+"  p_item: "+p_item+"  p_tipo_id: "+p_tipo_id
                +" p_cod_club:"+p_cod_club+" empresa:"+empresa);
        XStream xstream = new XStream(new StaxDriver());
        DataPmcBean dato=null;
        List<DataPmcBean> listadoDataPmcBean=new ArrayList<DataPmcBean>();
        //SE OBTIENE LA CONEXION DE LA EMPRESA DEL MASTER
        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSidEspecialVictoria(empresa.toString());

        if(conexionVarianteSid.getConn()==null)
          return Constantes.respuestaXmlObjeto("ERROR", "ERROR AL CONECTAR A LA BDD "+empresa);

        String queryBusqueda =  "SELECT TO_CHAR(f.fecha,'yyyy-mm-dd HH24:MI:SS') as fecha, f.documento_venta, f.farmacia, f.cancelada, f.tipo_movimiento," +
			         " f.clasificacion_movimiento, a.empresa, " +
			         " DECODE (f.tipo_movimiento,'01', DECODE (f.cancelada, 'S', 1, -1),'02', -1) tipo," +
			         " f.cajas FROM farmacias.fa_movimientos_acumula f, ad_farmacias a" +
				 " WHERE f.farmacia = a.codigo " +
				 " AND f.persona = " + p_persona + " AND f.item = " + p_item +" ORDER BY f.fecha";
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

	                queryBusqueda = "select REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (nombre))),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') nombre" +
            	                " from   ad_farmacias  where  codigo =" + dato.getFarmacia();
                        ResultSet resultado2=null;
                        ConexionVarianteSid conexionVarianteSid2=obtenerNuevaConexionVarianteSidCommonsGPF(empresa.toString());
                        try{
                           resultado2 = conexionVarianteSid2.consulta(queryBusqueda);
                        }catch (Exception ex) {
                          Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        while(conexionVarianteSid2.siguiente(resultado2)){
                                dato.setNombreFarmacia(conexionVarianteSid2.getString(resultado2,"NOMBRE"));
                                queryBusqueda =  "select descripcion from   ad_clasificacion_movimientos" +
                                                 " where  codigo = " + dato.getClasificacionMvto() + " and    tipo_movimiento = " + dato.getTipoMvto();
                                validarConexionVarianteSidEspecialVictoria(empresa.toString(),conexionVarianteSid2);
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
            listadoDataPmcBean.add(dato);
           }
      conexionVarianteSid.cerrarConexion();

      xstream.alias("DataPmcBean", DataPmcBean.class);
      String resultadoXml=xstream.toXML(listadoDataPmcBean);
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
      resultadoXml=resultadoXml.replace("<list>","");
      resultadoXml=resultadoXml.replace("</list>","");
      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.DataPmcBean","DataPmcBean");
      Logger.getLogger(Servicios.class.getName()).info("FIN Consultas PMC p_persona:"+p_persona+"  p_item: "+p_item+"  p_tipo_id: "+p_tipo_id
                +" p_cod_club:"+p_cod_club+" empresa:"+empresa);

      if(listadoDataPmcBean.size()>0)
         return Constantes.respuestaXmlObjeto("OK", resultadoXml);
      else
         return Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS");
    }

   @SuppressWarnings("static-access")
   private static void getUnidadesVentasPmc(DataPmcBean dato,String idEmpresa,Boolean cajaBonificada,String queryBusqueda,
           ConexionVarianteSid conexionVarianteSidMaster){
        ConexionVarianteSid conexionVarianteSid = conexionVarianteSidMaster;
        validarConexionVarianteSidEspecialVictoria(dato.getEmpresa(),conexionVarianteSid);
        try {
            if(!idEmpresa.equals(dato.getEmpresa()))
               conexionVarianteSid=obtenerNuevaConexionVarianteSidEspecialVictoria(idEmpresa);
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
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!idEmpresa.equals(dato.getEmpresa()))
            conexionVarianteSid.cerrarConexion();
   }
/*
  @SuppressWarnings("static-access")
  public static String VentasTop( String fechaInicio, String fechaFinal , String  codigoPersona){
      Logger log=Logger.getLogger("VentasTop");
      log.info("Inicio Stock VentasTop");
      fechaInicio=fechaInicio.replace("-", "/");
      fechaFinal=fechaFinal.replace("-", "/");

      ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
      XStream xstream = new XStream(new StaxDriver());
      if(!conexionVarianteSid.isValid())
          return Constantes.respuestaXmlObjeto("ERROR", "ERROR AL CONECTAR AL PROD ");

      List<VentasTopBean> resultadoList=new ArrayList<VentasTopBean>();

      String queryBusqueda="select *   from (select count(d.ITEM) total, p.NOMBRE, i.presentacion " +
                        "  from FA_FACTURAS f, FA_DETALLES_FACTURA d,pr_items i, pr_productos p "+
                        "  where f.CANCELADA = 'S'   and  f.TIPO_MOVIMIENTO = '01' "+
                        "  and f.CLASIFICACION_MOVIMIENTO = '01'   and f.PERSONA = "+codigoPersona+"   and d.venta > 0 "+
                        "  and fecha between to_date('"+fechaInicio+"','dd/mm/yyyy') and to_date('"+fechaFinal+"','dd/mm/yyyy') "+
                        "  and f.FARMACIA = d.FARMACIA  and f.DOCUMENTO_VENTA = d.DOCUMENTO_VENTA "+
                        "  and d.ITEM = i.CODIGO  and i.PRODUCTO = p.CODIGO "+
                        "  group by  d.ITEM, p.NOMBRE, i.presentacion   order by total desc) where rownum <= 15";
      ResultSet resultado=conexionVarianteSid.consulta(queryBusqueda);
      while (conexionVarianteSid.siguiente(resultado)) {
             VentasTopBean ventasTopBean=new VentasTopBean(conexionVarianteSid.getInt(resultado,"TOTAL"),conexionVarianteSid.getString(resultado,"NOMBRE"),conexionVarianteSid.getString(resultado,"PRESENTACION"));
             resultadoList.add(ventasTopBean);
      }
      conexionVarianteSid.cerrarConexion();
      xstream.alias("VentasTopBean", VentasTopBean.class);
      String resultadoXml=xstream.toXML(resultadoList);
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" ?>","");
      resultadoXml=resultadoXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ","");
      resultadoXml=resultadoXml.replace("<?xml version='1.0' encoding='UTF-8'?>","");
      resultadoXml=resultadoXml.replace("<list>","");
      resultadoXml=resultadoXml.replace("</list>","");
      resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.servicios.VentasTopBean","VentasTopBean");
      log.info("Fin Stock VentasTop");
      if(resultadoList.size()>0)
        return Constantes.respuestaXmlObjeto("OK", resultadoXml);
      else
        return Constantes.respuestaXmlObjeto("OK", "");

  }
*/
   public static void generarImagenesZip(String filtroQuery){
        /*
        HSSFWorkbook libro = new HSSFWorkbook();
        HSSFSheet hoja = libro.createSheet();
        HSSFPatriarch patriarch = hoja.createDrawingPatriarch();
        //HSSFPicture picture =  patriarch.createPicture(anchor, loadPicture(files, wb ));
         */
        ConexionVarianteSid conexionVarianteSid=null;
        ZipOutputStream zipOut = null;
        try {
            String pathImagenes="/data/imagenes/";
            zipOut = new ZipOutputStream(new FileOutputStream(pathImagenes+"imagenes.zip"));
            String nombreArchivo = "";

            conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
            if (!conexionVarianteSid.isValid()) {
                return;
            }
            String queryBusqueda = "select PR.NOMBRE,PS.PRESENTACION,PI.ITEM,PI.FOTO " +
                    "from PR_PRODUCTOS PR, PR_ITEMS PS,PR_IMAGENES_ITEMS PI ,ds_localizaciones dsl,pr_casas prc " +
                    " WHERE PR.CODIGO=PS.PRODUCTO AND PS.CODIGO=PI.item and  PS.CODIGO=dsl.item  " +
                    "  AND ( dsl.bodega=7 or dsl.bodega=8) and PR.CASA=PRC.CODIGO " + filtroQuery;
            ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
            while (conexionVarianteSid.siguiente(resultado)) {
                OutputStream out = null;
                try {
                 if(conexionVarianteSid.getByte(resultado, "FOTO")!=null){
                    nombreArchivo =  Constantes.getValidString(conexionVarianteSid.getString(resultado, "NOMBRE")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "PRESENTACION")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "ITEM")) + ".jpg";
                    out = new FileOutputStream(pathImagenes+nombreArchivo);
                    out.write(conexionVarianteSid.getByte(resultado, "FOTO"));
                    out.close();
                    FileInputStream fis = new FileInputStream(pathImagenes+nombreArchivo);
                    zipOut.putNextEntry(new ZipEntry(pathImagenes+nombreArchivo));

                    byte [] buffer = new byte[1024];
                    int leido=0;
                    while (0 < (leido=fis.read(buffer))){
                       zipOut.write(buffer,0,leido);
                    }
                    fis.close();
                    zipOut.closeEntry();
                    new File (pathImagenes+nombreArchivo).delete();
                 }
                } catch (IOException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if(out!=null)
                          out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //return conexionVarianteSid.getByte(resultado,"FOTO");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                zipOut.close();
                conexionVarianteSid.cerrarConexion();
            } catch (IOException ex) {
                Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }

      public static String getImagenesJson(String filtroQuery){
       List<HashMap<String,Object>> listadoItems=new ArrayList<HashMap<String, Object>>();
       ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if (!conexionVarianteSid.isValid()) {
                return Constantes.serializar("Falla la conexion al Prod", "imagenesItems", "Error Imagenes Items", "504");
       }
       String queryBusqueda = "select PR.NOMBRE,PS.PRESENTACION,PI.ITEM,PI.FOTO " +
                    " from PR_PRODUCTOS PR, PR_ITEMS PS,PR_IMAGENES_ITEMS PI ,ds_localizaciones dsl,pr_casas prc " +
                    " WHERE PR.CODIGO=PS.PRODUCTO AND PS.CODIGO=PI.item and  PS.CODIGO=dsl.item  " +
                    " AND ( dsl.bodega=7 or dsl.bodega=8) and PR.CASA=PRC.CODIGO " + filtroQuery;
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
            while (conexionVarianteSid.siguiente(resultado)) {
              OutputStream out = null;
              if(conexionVarianteSid.getByte(resultado, "FOTO")!=null){
                  HashMap<String,Object> item=new HashMap<String, Object>();
                  item.put("CODIGO",conexionVarianteSid.getInt(resultado, "ITEM"));
                  item.put("NOMBRE",conexionVarianteSid.getString(resultado, "NOMBRE"));
                  item.put("PRESENTACION",conexionVarianteSid.getString(resultado, "PRESENTACION"));
                  //item.put("IMAGEN","/WebApplicationCorporacionGPF/resources/GPFResource/getImagenItemJPG/"+conexionVarianteSid.getInt(resultado, "ITEM")+".jpg");
                  item.put("DESCARGAR",Constantes.urlPath+"/WebApplicationCorporacionGPF/resources/GPFResource/getImagenItem/"+conexionVarianteSid.getInt(resultado, "ITEM")+".jpg");
                  listadoItems.add(item);
               }
       }
       return Constantes.serializar(listadoItems, "imagenesItems", "Imagenes Items", "104");
   }

   public static File getImagenItem(String idItem){
       String pathImagenes="/data/imagenes/";
       ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if (!conexionVarianteSid.isValid()) {
                return null;
       }
       String queryBusqueda = "select PR.NOMBRE,PS.PRESENTACION,PI.ITEM,PI.FOTO " +
                    " from PR_PRODUCTOS PR, PR_ITEMS PS,PR_IMAGENES_ITEMS PI ,ds_localizaciones dsl,pr_casas prc " +
                    " WHERE PR.CODIGO=PS.PRODUCTO AND PS.CODIGO=PI.item and  PS.CODIGO=dsl.item  " +
                    " AND ( dsl.bodega=7 or dsl.bodega=8) and PR.CASA=PRC.CODIGO AND PS.CODIGO=" + idItem;
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
            while (conexionVarianteSid.siguiente(resultado)) {
              OutputStream out = null;
              if(conexionVarianteSid.getByte(resultado, "FOTO")!=null){
                try {
                    String nombreArchivo = Constantes.getValidString(conexionVarianteSid.getString(resultado, "NOMBRE")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "PRESENTACION")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "ITEM")) + ".jpg";
                    out = new FileOutputStream(pathImagenes + nombreArchivo);
                    out.write(conexionVarianteSid.getByte(resultado, "FOTO"));
                    out.close();
                    return new File (pathImagenes+nombreArchivo);
                } catch (IOException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
       }
       return null;
   }


   public static String getImagenWfArchivos(String idWfArchivos,String codigoSolicitud, String codigoBarras){	  
	   int codeigoBarrasInt=new Random(new Date().getTime()).nextInt();
	   int cantidadLetras=2;
	   if(codeigoBarrasInt<0)
		   codeigoBarrasInt=codeigoBarrasInt*-1;
	   if(codigoSolicitud==null)
		   cantidadLetras=3;
	   String archivoWfArchivos=codeigoBarrasInt+nomeAleatorio(cantidadLetras);
	   if(codigoBarras!=null)
		   archivoWfArchivos=codigoBarras;
	   
       String pathImagenes="/data/imagenes/gastos/";
       ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if (!conexionVarianteSid.isValid()) {
                return null;
       }
       String queryBusqueda = "SELECT CONTENIDO FROM  WORKFLOW.WF_ARCHIVOS WHERE FULL_FILENAME='1' AND FILENAME='1' AND CODIGO="+idWfArchivos;
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
       while (conexionVarianteSid.siguiente(resultado)) {
         OutputStream out = null;
         if(conexionVarianteSid.getByte(resultado, "CONTENIDO")!=null){
           try {
               String nombreArchivo = archivoWfArchivos.toString() + "F.jpg";
               out = new FileOutputStream(pathImagenes + nombreArchivo);
               out.write(conexionVarianteSid.getByte(resultado, "CONTENIDO"));
               out.close();               
           } catch (IOException ex) {
               Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
           }
          }
     }
       if(codigoSolicitud!=null){
          String queryUpdate="UPDATE WORKFLOW.WF_GASTOS_PERSONALES_DETALLES SET CODIGO_BARRAS='"+archivoWfArchivos+"' WHERE CODIGO_SOLICITUD="+codigoSolicitud;
          conexionVarianteSid.ejecutar(queryUpdate);
       }
       conexionVarianteSid.cerrarConexion();
       return archivoWfArchivos;
   }

   private static String  verificarSolicitud(String codigoSolicitud){
	   Logger.getLogger("verificarSolicitud").info("codigoSolicitud llega "+codigoSolicitud);
       ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if (!conexionVarianteSid.isValid()) {
                return null;
       }
       String queryBusqueda = "SELECT CODIGO_BARRAS FROM WORKFLOW.WF_GASTOS_PERSONALES_DETALLES WHERE CODIGO_SOLICITUD="+codigoSolicitud;
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
       while (conexionVarianteSid.siguiente(resultado)){
    	   if(!conexionVarianteSid.getString(resultado, "CODIGO_BARRAS").equals("")){
    		   Logger.getLogger("verificarSolicitud").info("EXISTE CODIGO_BARRAS "+conexionVarianteSid.getString(resultado, "CODIGO_BARRAS"));
              return conexionVarianteSid.getString(resultado, "CODIGO_BARRAS");
    	   }else
    		   Logger.getLogger("verificarSolicitud").info("NO HAY CODIGO_BARRAS "+conexionVarianteSid.getString(resultado, "CODIGO_BARRAS"));
       }
       conexionVarianteSid.cerrarConexion();
       return null;
   }
   public static File outputtingBarcodeAsPNG(String codigoSolicitud) {
	   Logger.getLogger("outputtingBarcodeAsPNG").info("codigoSolicitud llega "+codigoSolicitud);
	   String codigoBarras=null;
	   codigoBarras=verificarSolicitud(codigoSolicitud);
	   codigoBarras=getImagenWfArchivos("1992",codigoSolicitud,codigoBarras);
	   System.out.println("codigoBarras   "+codigoBarras);
	   generarCodigoBarras(codigoBarras.toString(),"/data/imagenes/gastos/"+codigoBarras.toString()+"B.jpg");
	   ImageConcatenator(codigoBarras.toString()+"F.jpg",codigoBarras.toString()+"B.jpg");
	   
	   try {
			getImagenMiniatura("/data/imagenes/gastos/"+codigoBarras+"F.jpg",200,200,codigoBarras+"F.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	   return new File("/data/imagenes/gastos/"+codigoBarras+"F.jpg"); 
   }
   
   
   public static File outputtingBarcodeAsPNG(String codigoEmpleado, String codigoEmpresa, String anyo) {
	   String codigoBarras=null;
	   
	   ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if (!conexionVarianteSid.isValid()) {
                return null;
       }
       String queryBusqueda = "SELECT CODIGO_BARRAS FROM WF_CODIGOS_BARRAS_CIENTO_SIETE WHERE COD_EMPLEADO="+codigoEmpleado
    		         		  +" AND COD_EMPRESA="+codigoEmpresa+" AND  ANIO="+anyo;
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
       while (conexionVarianteSid.siguiente(resultado)){
    	   if(!conexionVarianteSid.getString(resultado, "CODIGO_BARRAS").equals(""))
    		   codigoBarras=conexionVarianteSid.getString(resultado, "CODIGO_BARRAS");
       }
       
	   
	   
	   
	   codigoBarras=getImagenWfArchivos("1992",null,codigoBarras);
	   System.out.println("codigoBarras  107  "+codigoBarras);
	   generarCodigoBarras(codigoBarras.toString(),"/data/imagenes/gastos/"+codigoBarras.toString()+"B.jpg");
	   ImageConcatenator(codigoBarras.toString()+"F.jpg",codigoBarras.toString()+"B.jpg");
	   
	   try {
			getImagenMiniatura("/data/imagenes/gastos/"+codigoBarras+"F.jpg",200,200,codigoBarras+"F.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
	   conexionVarianteSid.ejecutar("UPDATE WF_CODIGOS_BARRAS_CIENTO_SIETE SET CODIGO_BARRAS='"+codigoBarras+"'  WHERE COD_EMPLEADO="+codigoEmpleado+" AND COD_EMPRESA="+codigoEmpresa+" AND  ANIO="+anyo);
	   conexionVarianteSid.cerrarConexion();
	   return new File("/data/imagenes/gastos/"+codigoBarras+"F.jpg"); 
   }
   
   public static void generarCodigoBarras(String textoCodificar, String fileName){	 
	        try {	            
	            Code128Bean bean=new Code128Bean();
	            bean.setHeight(8);	            	            
	            bean.setFontSize(3);
	            final int dpi =100;	            
	            //Configure the barcode generator
	            bean.setModuleWidth(UnitConv.in2mm(1f / dpi)); //makes the narrow bar //width exactly one pixel
	            //bean.setModuleWidth(new Double("1.5"));
	            //bean.setWideFactor(3);
	            bean.doQuietZone(false);	            
	            //Open output file
	            File outputFile = new File(fileName);
	            OutputStream out = new FileOutputStream(outputFile);
	            try {
	                //Set up the canvas provider for monochrome JPEG output 
	                BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);	            
	                //Generate the barcode
	                bean.generateBarcode(canvas, textoCodificar);	            
	                //Signal end of generation
	                canvas.finish();
	            } finally {
	                out.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }	    
   }
   
   public static void ImageConcatenator(String firma, String codigoBarras) {
	   try {
		        String path = "/data/imagenes/gastos/";
	
				// load source images
				BufferedImage image = ImageIO.read(new File(path, firma));
				BufferedImage overlay = ImageIO.read(new File(path, codigoBarras));
	
				// create the new image, canvas size is the max. of both image sizes
				int w = Math.max(image.getWidth()-30, overlay.getWidth());
				int h = Math.max(image.getHeight(), overlay.getHeight());
				BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	
				// paint both images, preserving the alpha channels
				Graphics g = combined.getGraphics();
				g.drawImage(image, 0, 0, null);
				g.drawImage(overlay, 0, 0, null);
	
			
				// Save as new image
				
				ImageIO.write(combined, "JPG", new File(path, firma));
		} catch (IOException e) {			
			e.printStackTrace();
		}	
   }
   
  
   
   @SuppressWarnings({ "rawtypes"})
  	public static  void getImagenMiniatura(String imagen, int ancho,int alto,String fileName) throws IOException  {
  	  OutputStream out = null;
  	  Image img=null;
  	  File fileImagen=null;
          ByteArrayInputStream in=new ByteArrayInputStream(getBytesFromFile(new File(imagen)));
          Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
          ImageReader reader = (ImageReader)readers.next();
          
          try {
              ImageInputStream iis = ImageIO.createImageInputStream(in);              
              reader.setInput(iis, true);
              ImageReadParam param = reader.getDefaultReadParam();
              double anchoOriginal = reader.getWidth(0);
              double altoOriginal = reader.getHeight(0);
              if ((ancho != 0)&&(alto!=0)&&(ancho<=anchoOriginal)&&(alto<=altoOriginal)){
                  double proporcion = (altoOriginal / anchoOriginal) - (alto / ancho);
                  int radio;
                  if(proporcion < 0){
                      radio = (int)(anchoOriginal / ancho);
                  }else{
                      radio = (int)(altoOriginal / alto);
                  }
                  param.setSourceSubsampling(radio, radio, 0, 0);
              }
              img = reader.read(0,param);            
              fileImagen=new File("/data/imagenes/gastos/"+fileName);            
              ImageIO.write(reader.read(0,param), "jpg", fileImagen);
              fileImagen=null;
              reader.dispose();
              in.close();
              iis.close();
          }catch (Exception e) {
               e.printStackTrace();		
          } finally {
              in.close();
          }                                
  }
   private static Random rand = new Random();  
   private static char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();//ÁÉÍÓÚÃÕÂÊÎÔÛÀÈÌÒÙÇ  
   public static String nomeAleatorio (int nCaracteres) {  
	    StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < nCaracteres; i++) {  
	        int ch = rand.nextInt (letras.length);  
	        sb.append (letras [ch]);  
	    }      
	    return sb.toString();      
	}  
   
   private static final int MAXLENGTH = 100000000; // se for > 100 M então vamos evitar carregar tudo!  
   
   private static byte[] getBytesFromFile(File file) throws IOException {  
       InputStream is = null;
       byte[] ret;
       try {  
           long length = file.length();  
           if (length > MAXLENGTH) throw new IllegalArgumentException ("File is too big");  
           ret = new byte [(int) length];  
           is = new FileInputStream (file);  
           is.read (ret);  
       } finally {  
           if (is != null) try { is.close(); } catch (IOException ex) {}  
       }  
       return ret;  
   } 
  
   /*
   public static void ImageConcatenator(String firma, String codigoBarras) {
	   File fileFirma = new File("/data/imagenes/gastos/"+firma);
	   File filecodigoBarras = new File("/data/imagenes/gastos/"+codigoBarras);
	
	   BufferedImage img1=null;
	   BufferedImage img2=null;
		try {
			img1 = ImageIO.read(fileFirma);
			img2 = ImageIO.read(filecodigoBarras);
		} catch (IOException e) {			
			e.printStackTrace();
		}	   
	
	   int widthImg1 = img1.getWidth();
	   int heightImg1 = img1.getHeight();
	
	   int widthImg2 = img2.getWidth();
	   int heightImg2 = img2.getHeight();
	
	   BufferedImage img = new BufferedImage(
	   widthImg1+widthImg2, // Final image will have width and height as
	   heightImg1+heightImg2, // addition of widths and heights of the images we already have
	   BufferedImage.TYPE_INT_RGB);
	
	   boolean image1Drawn = img.createGraphics().drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions
	   if(!image1Drawn) System.out.println("Problems drawing first image"); //where we are placing image1 in final image
	
	   boolean image2Drawn = img.createGraphics().drawImage(img2, widthImg1, 0, null); // here width is mentioned as width of
	   if(!image2Drawn) System.out.println("Problems drawing second image"); // image1 so both images will come in same level
	   // horizontally
	   File final_image = new File("/data/imagenes/gastos/"+firma); // “png can also be used here”
	   boolean final_Image_drawing=false;
	try {
		final_Image_drawing = ImageIO.write(img, "jpeg", final_image);
	} catch (IOException e) {

		e.printStackTrace();
	} 
	   if(!final_Image_drawing)
		   System.out.println("Problems drawing final image");
	   
   }
   

*/

   public static String generalExcelImagenes(){
       String tablaHtml="<table><tr><th>";
       ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       if(!conexionVarianteSid.isValid())
          return "";

        String queryBusqueda="select * from PR_IMAGENES_ITEMS  WHERE item IN(175,1880,1123,93151,151768)";
        ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda);
        while (conexionVarianteSid.siguiente(resultado)){

        }


       return "";
   }

   public static String copiarImagenes(String destino){
       String pathImagenes="/data/imagenes/";
       ConexionVarianteSid conexionVarianteSid=null;
       conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
       Farmacia farmacia=new Farmacia(Long.decode(destino),null,conexionVarianteSid);
       if (!conexionVarianteSid.isValid()) {
                return null;
       }



       String queryBusqueda = "select PR.NOMBRE,PS.PRESENTACION,PI.ITEM ITEM,PI.FOTO " +
                    " from PR_PRODUCTOS PR, PR_ITEMS PS,PR_IMAGENES_ITEMS PI ,ds_localizaciones dsl,pr_casas prc " +
                    " WHERE PR.CODIGO=PS.PRODUCTO AND PS.CODIGO=PI.item and  PS.CODIGO=dsl.item  " +
                    " AND ( dsl.bodega=7 or dsl.bodega=8) and PR.CASA=PRC.CODIGO";
       ResultSet resultado = conexionVarianteSid.consulta(queryBusqueda.toUpperCase());
            while (conexionVarianteSid.siguiente(resultado)) {
              OutputStream out = null;
              if(conexionVarianteSid.getByte(resultado, "FOTO")!=null){
                try {
                    String nombreArchivo = Constantes.getValidString(conexionVarianteSid.getString(resultado, "NOMBRE")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "PRESENTACION")) + "-" + Constantes.getValidString(conexionVarianteSid.getString(resultado, "ITEM")) + ".jpg";
                    out = new FileOutputStream(pathImagenes + nombreArchivo);
                    out.write(conexionVarianteSid.getByte(resultado, "FOTO"));
                    out.close();
                    grabarFoto(conexionVarianteSid.getInt(resultado, "ITEM"),new File (pathImagenes+nombreArchivo),farmacia.getIpFarmacia().toString(),farmacia.getDatabaseSid());
                } catch (IOException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
       }
       return null;
   }

   public static byte[] getImagenWfArchivosContador(String idWfArchivos){
	   byte [] imagen=null;
	   ConexionVarianteSid conexion=obtenerNuevaConexionVarianteSid("1");
	   ResultSet rs = conexion.consulta("SELECT CONTENIDO FROM WF_ARCHIVOS WHERE CODIGO="+idWfArchivos);
	   while(conexion.siguiente(rs))
		   imagen=conexion.getBytes(rs, "CONTENIDO");
	   conexion.cerrarConexion();
	   return imagen;
	   
   }
   /*
   public static void main(String[] args) {
	  new Servicios().getImagenes();
   }
   */
   public static void getImagenes() {
	   ConexionVarianteSid conexion=obtenerNuevaConexionVarianteSid("1");
	   ResultSet rs = conexion.consulta("SELECT distinct(codigo_solicitud) AS CODIGO FROM WORKFLOW.WF_GASTOS_PERSONALES_DETALLES WHERE codigo_barras is null");
	   String idWfArchivos="";
	   while(conexion.siguiente(rs)){
		    idWfArchivos=conexion.getString(rs, "CODIGO");
		    outputtingBarcodeAsPNG(idWfArchivos);
		    /*
		    GetMethod getMethod = new GetMethod("http://localhost:8080/WebApplicationCorporacionGPF/resources/GPFResource/getImagenWfArchivos/"+imagen+".jpg");
	        HttpClient httpClient = new HttpClient();
	        httpClient.getHostConfiguration().setProxy("UIOPROXY01", 3128);
	        try {	        	
	            httpClient.executeMethod(getMethod);
	        } catch (IOException e) {	            
	            throw new RuntimeException("No se puede llamar a servicio autorizador");
	        }
	        try {
	            getMethod.getResponseBodyAsStream();
	        } catch (IOException e) {
	            throw new RuntimeException("No se puede leer respuesta.",e);
	        }
	        getMethod.releaseConnection();
	        */
		    /*
	        try {
				Thread.sleep(5000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
	        */
	   }
   }
    @SuppressWarnings("static-access")
   public static void grabarFoto(Integer item,File file, String ipFarmacia, String sidFarmacia){
        try {
             FileInputStream fis = new FileInputStream(file);

            ConexionVarianteSid conexionVarianteSid = null;
            conexionVarianteSid = obtenerConexion(ipFarmacia,sidFarmacia);
            if (conexionVarianteSid.getConn() == null) {
                return;
            }
            PreparedStatement ps = null;
            conexionVarianteSid.getConn().setAutoCommit(false);
            String queryInser = "INSERT INTO pr_imagenes_items_MAS(ITEM, TIPO, FOTO) VALUES(?,?,?)";
            ps=conexionVarianteSid.getConn().prepareStatement(queryInser);

            ps.setInt(1, 20);
            ps.setInt(2,1 );
            ps.setBinaryStream(3, fis, (int) file.length());
            ps.executeUpdate();
            conexionVarianteSid.getConn().commit();
            ps.close();
            fis.close();
            conexionVarianteSid.cerrarConexion();
        } catch (IOException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
   }


   @SuppressWarnings("static-access")
   public static ConexionVarianteSid obtenerConexion(String ipFarmacia,String sid){
        try {
            ConexionVarianteSid conexionVarianteSid = new ConexionVarianteSid(ipFarmacia, sid);
            return conexionVarianteSid;
        } catch (NamingException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }


   @SuppressWarnings("static-access")
   public static ConexionVarianteSid obtenerNuevaConexionVarianteSid(String parametro){
        try {
            return new ConexionVarianteSid(Integer.decode(parametro));
        } catch (NamingException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }
   @SuppressWarnings("static-access")
   public static void validarConexionVarianteSid(String empresa,ConexionVarianteSid conexionVarianteSid){      
            if (!conexionVarianteSid.isValid())
                 conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);            
      
   }



      @SuppressWarnings("static-access")
   public static ConexionVarianteSid obtenerNuevaConexionVarianteSidCommonsGPF(String parametro){
        try {
            return new ConexionVarianteSid(Integer.decode(parametro));
        } catch (NamingException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }
   @SuppressWarnings("static-access")
   public static void validarConexionVarianteSidCommonsGPF(String empresa,ConexionVarianteSid conexionVarianteSid){
        try {
            if (conexionVarianteSid.getConn()==null)
                 conexionVarianteSid=obtenerNuevaConexionVarianteSidCommonsGPF(empresa);
            else if (conexionVarianteSid.getConn().isClosed())
                conexionVarianteSid=obtenerNuevaConexionVarianteSidCommonsGPF(empresa);
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

      public static void validarConexionVarianteSidEspecialVictoria(String empresa, ConexionVarianteSid conexionVarianteSid) {
            if(!conexionVarianteSid.isValid())
                obtenerNuevaConexionVarianteSidEspecialVictoria(empresa);
   }
      
      public static ConexionVarianteSid obtenerNuevaConexionVarianteSidEspecialVictoria(String parametro) {
          try {
              if(parametro.equals("16") || parametro.equals("9"))
                  parametro="8";
              return new ConexionVarianteSid(Integer.decode(parametro));
          } catch (NamingException ex) {
              Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
              Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
          }
         return null;
      }
}

