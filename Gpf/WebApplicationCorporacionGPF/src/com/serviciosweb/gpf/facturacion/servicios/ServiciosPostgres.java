/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionPostgres;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 *
 * @author mftellor
 */
public class ServiciosPostgres  extends ObtenerNuevaConexion{
  ConexionPostgres conexionPostgres=new ConexionPostgres();
  ConexionVarianteSid conexionOracle;
  
  Logger log=Logger.getLogger("ServiciosPostgres");

  public ServiciosPostgres(){
      super(ServiciosPostgres.class);
  }
  public String AlertasLocal(String codigoAlerta,String codigoLocal,String codigoTipoAlerta,
          String codigoItem,String cantidad, String tipoAbc, String fechaAlerta, String cpVar3){	  
      conexionPostgres.getConexionPostgres();
      HashMap<String,String> respuestaAdicional=getInformacionAdicional(codigoItem,codigoLocal);
      
      String tipoAbcNull=(tipoAbc.toUpperCase().equals("NULL"))?null:"'"+tipoAbc+"'";
      String cpVar3Null=(cpVar3.toUpperCase().equals("NULL"))?null:"'"+cpVar3+"'";
      String habitualNull=respuestaAdicional.get("habitual")==null?null:"'"+respuestaAdicional.get("habitual")+"'";
      String categoriaNull=respuestaAdicional.get("categoria")==null?null:"'"+respuestaAdicional.get("categoria")+"'";
      String descripcionNull=respuestaAdicional.get("descripcion")==null?null:"'"+respuestaAdicional.get("descripcion")+"'";
      String tipoNegocioNull=respuestaAdicional.get("tipoNegocio")==null?null:"'"+respuestaAdicional.get("tipoNegocio")+"'";
      
      String respuestaEjecucion="ERROR";
      
      String queeryIsert="insert into farmacias.pw_alertas_local (codigo,local,fecha,tipo_alerta,item,cantidad,tipo_abc,autorizada,habitual,descripcion,categoria,tipo_negocio,CP_VAR3) values" +
              "("+codigoAlerta+","+codigoLocal+",'"+fechaAlerta+"',"+codigoTipoAlerta+","
              +codigoItem+","+cantidad+","+tipoAbcNull+",'N',"+habitualNull+","+descripcionNull
              +","+categoriaNull+","+tipoNegocioNull+","+cpVar3Null+")";      
      
      String queryUpdate="UPDATE farmacias.pw_alertas_local SET fecha='"+fechaAlerta+"',tipo_alerta="+codigoTipoAlerta+",item="+codigoItem+",cantidad="+cantidad
    		  +",tipo_abc="+tipoAbcNull+",habitual="+habitualNull+",descripcion="+descripcionNull+",categoria="+categoriaNull+",tipo_negocio="+tipoNegocioNull+",CP_VAR3="+cpVar3Null                            
              +" WHERE item="+codigoItem+" AND local="+codigoLocal+" AND autorizada='N' AND tipo_alerta="+codigoTipoAlerta;           
      if(!codigoTipoAlerta.equals("37")){
    	  if(conexionPostgres.updateQuery(queryUpdate)<=0)    	  
    		  respuestaEjecucion=conexionPostgres.ejecutarQuery(queeryIsert);
    	  else
    		  respuestaEjecucion="OK";
      }else{
    	  respuestaEjecucion=conexionPostgres.ejecutarQuery(queeryIsert);
      }      
      String respuesta=Constantes.respuestaXmlUnica(respuestaEjecucion);
      conexionPostgres.cerrarConexion();
      return respuesta;
  }
  private HashMap<String,String> getInformacionAdicional(String codigoItem, String codigoLocal){
        HashMap<String,String> respuesta=new HashMap<String, String>();
        String query="SELECT a.habitual lc_habitual, e.nombre lc_categoria, c.nombre || ' ' || presentacion lc_descripcion, c.tipo_negocio lc_tipoNegocio"+
                   " FROM productos.pr_items_autorizados a,productos.pr_items b,productos.pr_productos c,productos.pr_casas d,"+
                   " productos.pr_secciones e WHERE a.item = b.codigo AND b.producto = c.codigo AND c.casa = d.codigo "+
                   " AND d.seccion = e.codigo AND item="+codigoItem+" AND farmacia="+codigoLocal;
        //log.info("INFO ADICIONAL "+query);
        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
        if(conexionVarianteSid.getConn()==null){
        	log.info("ERROR EN LA CONEXION AL PROD");
        }
        ResultSet rs = conexionVarianteSid.consulta(query);
        while(conexionVarianteSid.siguiente(rs)){
            respuesta.put("habitual",Constantes.escaparSql(conexionVarianteSid.getString(rs, "lc_habitual")));
            respuesta.put("categoria",Constantes.escaparSql(conexionVarianteSid.getString(rs, "lc_categoria")));
            respuesta.put("descripcion",Constantes.escaparSql(conexionVarianteSid.getString(rs, "lc_descripcion")));
            respuesta.put("tipoNegocio",Constantes.escaparSql(conexionVarianteSid.getString(rs, "lc_tipoNegocio")));
        }
        return respuesta;
  }
  public String PedidoManual(String local,String codigo){
	  log.info("INICIA local "+local+" codigo "+codigo);
	  ResultSet rs;
	  Boolean cabeceraInsertada=false;
	  Collection<HashMap<String,Object>> resultado=new ArrayList<HashMap<String,Object>>();
	  //conexionOracle=obtenerNuevaConexionVarianteSid("2");
	  conexionOracle=obtenerNuevaConexionVarianteSid("1");
	  conexionPostgres.getConexionPostgres();
	  String querySql="select * from farmacias.pw_pedidos_manual a, farmacias.pw_detalles_pedido_man b " +
	  		          " where a.codigo=b.codigo and a.local=b.local and a.codigo="+codigo+" and a.local="+local;
	  //log.info("querySql "+querySql);
	  rs=conexionOracle.consulta(querySql);
	  while(conexionOracle.siguiente(rs)){
		  HashMap<String,Object> pwPedidosManual=new HashMap<String, Object>();
		  pwPedidosManual.put("codigo", conexionOracle.getInt(rs, "CODIGO"));
		  pwPedidosManual.put("local", conexionOracle.getInt(rs, "LOCAL"));
		  pwPedidosManual.put("fecha", conexionOracle.getString(rs, "FECHA"));
		  pwPedidosManual.put("estado", conexionOracle.getString(rs, "ESTADO"));
		  pwPedidosManual.put("fechaProceso", conexionOracle.getString(rs, "FECHA_PROCESO"));
		  pwPedidosManual.put("usuarioCrea", conexionOracle.getString(rs, "USUARIO_CREA"));
		  //DETALLE
		  pwPedidosManual.put("item", conexionOracle.getInt(rs, "ITEM"));
		  pwPedidosManual.put("cantidad", conexionOracle.getInt(rs, "CANTIDAD"));
		  pwPedidosManual.put("unidades", conexionOracle.getInt(rs, "UNIDADES"));
		  pwPedidosManual.put("tipoAbc", conexionOracle.getString(rs, "TIPO_ABC"));
		  resultado.add(pwPedidosManual);
	  }	 
	    try {
		    rs.close();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	  conexionOracle.cerrarConexion();
	  conexionPostgres.getConexionPostgres();
	  for(HashMap<String,Object> pwPedidosManualIterador:resultado){
		  log.info("pwPedidosManualIterador "+pwPedidosManualIterador.get("fecha"));
		  if(!cabeceraInsertada){
			  querySql="UPDATE farmacias.pw_pedidos_manual SET FECHA=to_date('"+pwPedidosManualIterador.get("fecha")+"','yyyy-mm-dd HH24:MI:SS'),ESTADO='" +pwPedidosManualIterador.get("estado")+"'"
					    +",USUARIO_CREA='"+pwPedidosManualIterador.get("usuarioCrea")+"' WHERE CODIGO="+pwPedidosManualIterador.get("codigo")+" AND LOCAL="+pwPedidosManualIterador.get("local");
			  //conexionPostgres.ejecutar(querySql);
			  insertarPedidoManual(querySql,pwPedidosManualIterador,1);
			  cabeceraInsertada=true;
		  }
		  querySql="UPDATE farmacias.pw_detalles_pedido_man set CANTIDAD="+pwPedidosManualIterador.get("cantidad")+"" +
		  		",UNIDADES="+pwPedidosManualIterador.get("unidades")+",TIPO_ABC='"+pwPedidosManualIterador.get("tipoAbc")+"'" +
		  				" where CODIGO="+pwPedidosManualIterador.get("codigo")+ " and LOCAL="+pwPedidosManualIterador.get("local")+" and ITEM="+pwPedidosManualIterador.get("item");
		  //conexionPostgres.ejecutar(querySql);
		  insertarPedidoManual(querySql,pwPedidosManualIterador,2);
	  }
	  conexionPostgres.cerrarConexion();
	  log.info("FIN local "+local+" codigo "+codigo);
	 return Constantes.respuestaXmlUnica("OK"); 	  
  }
  private void insertarPedidoManual(String querySql,HashMap<String,Object> pwPedidosManualIterador, int tipo){
	  if(conexionPostgres.updateQuery(querySql)==0 && pwPedidosManualIterador!=null){
		  if(tipo==1)
			  querySql="INSERT INTO farmacias.pw_pedidos_manual (CODIGO,LOCAL,FECHA,ESTADO,USUARIO_CREA) VALUES ("+pwPedidosManualIterador.get("codigo")+","+pwPedidosManualIterador.get("local")
					    +",to_date('"+pwPedidosManualIterador.get("fecha")+"','yyyy-mm-dd HH24:MI:SS'),'" +pwPedidosManualIterador.get("estado")+"'"
					    +",'"+pwPedidosManualIterador.get("usuarioCrea")+"')";
		  else if(tipo==2)
			  querySql="INSERT INTO farmacias.pw_detalles_pedido_man (CANTIDAD,UNIDADES,TIPO_ABC,CODIGO,LOCAL,ITEM) " +
			  		    " VALUES("+pwPedidosManualIterador.get("cantidad")+","+pwPedidosManualIterador.get("unidades")+
			  		    ",'"+pwPedidosManualIterador.get("tipoAbc")+"',"+pwPedidosManualIterador.get("codigo")+ 
			  		    ","+pwPedidosManualIterador.get("local")+","+pwPedidosManualIterador.get("item")+")";

		  insertarPedidoManual(querySql,null,tipo);
	  }	  
		  
  }
}
