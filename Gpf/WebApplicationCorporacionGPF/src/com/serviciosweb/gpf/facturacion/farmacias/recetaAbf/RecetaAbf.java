package com.serviciosweb.gpf.facturacion.farmacias.recetaAbf;

import java.sql.ResultSet;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.ItemRecetaAbfBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.ItemRelacionadoRecetaAbfBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.RecetaAbfBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;



public class RecetaAbf extends ObtenerNuevaConexion {
	public static void main(String[] args) {
		RecetaAbf recetaAbf=new RecetaAbf("0913396578");
		System.out.println(recetaAbf.getResultadoXml());
	}
	
	private String  resultadoXml=Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS");
	private final static String PARAMETRO_BDD="51";
	private XStream xstream = new XStream(new StaxDriver());
	public RecetaAbf(String codigoReceta){
		super(RecetaAbf.class);
				
		//ConexionVarianteSid con=obtenerNuevaConexionVarianteSidSidDataBase(44,"RECETAABF","vo7Qc457");
		 ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		//CABECERA RECETA

		ResultSetMapper<RecetaAbfBean> resultSetMapper=new ResultSetMapper<RecetaAbfBean>();
		String queryBusqueda="SELECT * FROM RE_RECETA WHERE CODIGO_RECETA='"+codigoReceta+"' AND ESTADO='ING'";		
		ResultSet rs = con.consulta(queryBusqueda);
		List<RecetaAbfBean> listadoRecetaAbfBean=resultSetMapper.mapRersultSetToObject(rs, RecetaAbfBean.class);
		if(listadoRecetaAbfBean==null)
			return;
		if(listadoRecetaAbfBean.isEmpty())
			return;
		for(RecetaAbfBean recetaAbfBean:listadoRecetaAbfBean){
				//RecetaAbfBean recetaAbfBean=listadoRecetaAbfBean.get(0);		
				//ITEMS RECETA
				ResultSetMapper<ItemRecetaAbfBean> resultSetMapperItemRecetaAbfBean=new ResultSetMapper<ItemRecetaAbfBean>();
				queryBusqueda="SELECT * FROM RE_RECETA_DETALLE WHERE RECETA_ID="+recetaAbfBean.getRecetaId();
				rs = con.consulta(queryBusqueda);
				List<ItemRecetaAbfBean> listadoItemRecetaAbfBean=resultSetMapperItemRecetaAbfBean.mapRersultSetToObject(rs, ItemRecetaAbfBean.class);
				recetaAbfBean.getItemsRecetaAbf().addAll(listadoItemRecetaAbfBean);
				
				//ITEMS ALTERNATIVOS ITEM
				ResultSetMapper<ItemRelacionadoRecetaAbfBean> resultSetMapperItemRelacionadoRecetaAbfBean=new ResultSetMapper<ItemRelacionadoRecetaAbfBean>();
				for(ItemRecetaAbfBean itemRecetaAbfBean:recetaAbfBean.getItemsRecetaAbf()){			
					queryBusqueda="SELECT * FROM RE_RECETA_ITEM_RELACIONADO WHERE RECETA_ID="+recetaAbfBean.getRecetaId()+" AND CODIGO_ITEM="+itemRecetaAbfBean.getItem();
					rs = con.consulta(queryBusqueda);
					List<ItemRelacionadoRecetaAbfBean> listadoItemRelacionadoRecetaAbfBean=resultSetMapperItemRelacionadoRecetaAbfBean.mapRersultSetToObject(rs, ItemRelacionadoRecetaAbfBean.class);
					if(listadoItemRelacionadoRecetaAbfBean!=null)
						itemRecetaAbfBean.getItemsRelacionadoRecetaAbf().addAll(listadoItemRelacionadoRecetaAbfBean);
				}
		}
		con.cerrarConexion(rs);
		xstream.alias("receta", RecetaAbfBean.class);
		xstream.alias("recetaDetalle", ItemRecetaAbfBean.class);
		xstream.alias("recetaItemRelacionado", ItemRelacionadoRecetaAbfBean.class);
		this.resultadoXml=xstream.toXML(listadoRecetaAbfBean);
		this.resultadoXml=Constantes.respuestaXmlObjeto("OK",this.resultadoXml);
	}
	
	
	public RecetaAbf(Integer recetaId){
		super(RecetaAbf.class);
		//ConexionVarianteSid con=obtenerNuevaConexionVarianteSidSidDataBase(44, "recetaabf", "vo7Qc457");
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		int registrosActualizados=con.updateQuery("UPDATE RE_RECETA SET ESTADO='CER' WHERE RECETA_ID="+recetaId);
		con.cerrarConexion();
		if(registrosActualizados==1)
			this.resultadoXml=Constantes.respuestaXmlObjeto("OK","REGISTROS ACTUALIZADOS "+registrosActualizados);
		else
			this.resultadoXml=Constantes.respuestaXmlObjeto("ERROR","REGISTROS ACTUALIZADOS "+registrosActualizados);
	}
	
	public RecetaAbf(Integer recetaId,String documentoVenta,String codigoLocal){
		super(RecetaAbf.class);
		//ConexionVarianteSid con=obtenerNuevaConexionVarianteSidSidDataBase(44, "recetaabf", "vo7Qc457");
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
		int registrosActualizados=con.updateQuery("UPDATE RE_RECETA SET ESTADO='CER', DOCUMENTO_VENTA="+documentoVenta+", FARMACIA="+codigoLocal+" WHERE RECETA_ID="+recetaId);
		con.cerrarConexion();
		if(registrosActualizados==1)
			this.resultadoXml=Constantes.respuestaXmlObjeto("OK","REGISTROS ACTUALIZADOS "+registrosActualizados);
		else
			this.resultadoXml=Constantes.respuestaXmlObjeto("ERROR","REGISTROS ACTUALIZADOS "+registrosActualizados);
	}
			
	public String getResultadoXml() {
		return resultadoXml;
	}

}
