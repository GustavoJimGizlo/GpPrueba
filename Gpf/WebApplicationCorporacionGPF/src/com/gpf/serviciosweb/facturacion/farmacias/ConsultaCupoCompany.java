package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasCupoCorpBean;

public class ConsultaCupoCompany extends ObtenerNuevaConexion implements Serializable{

	
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(this.getClass().getName());
	String resultadoXml=Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA ESTA CONSULTA");
	private String idBdd="44"; // 1 PRODUCCION 44 PORD_DESA
	
	public ConsultaCupoCompany(String codigocc) {
		super(ConsultaCupoCompany.class);  
		this.getConsultaCupoCompany(codigocc);
	}

	 public List<AdFarmaciasCupoCorpBean> getConsultaCupoCompany(String codigocc) 
	 {
		 ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid(idBdd);
		 String queryBusqueda= "select convenio_corporativo convenio_Corporativo ,saldo_cupo saldo_Cupo, fecha_actualizacion fecha_Actualizacion from vitalcard.vc_cupo_corporativo where convenio_corporativo="+codigocc+"";
         ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
         ResultSetMapper<AdFarmaciasCupoCorpBean> resultSetMapper = new ResultSetMapper<AdFarmaciasCupoCorpBean>();
         List<AdFarmaciasCupoCorpBean> listadoCupo =resultSetMapper.mapRersultSetToObject(rs, AdFarmaciasCupoCorpBean.class);
         conexionVarianteSid.cerrarConexion(rs);
         
         if(listadoCupo == null || listadoCupo.isEmpty())
	         {
        	 resultadoXml=null;
	         }else{
	        	 xstream.alias("list", AdFarmaciasCupoCorpBean.class);
	             resultadoXml=xstream.toXML(listadoCupo);
	         }
        
        
         return listadoCupo;
        
	}
	 /* public String getResultadoXml() {
			return Constantes.toUTF8(resultadoXml);
		}
	*/
	 public String getResultadoXml() {

		 if(resultadoXml!=null)
				return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
				//return Constantes.toUTF8(resultadoXml);
			
			else
				return  Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA") ;
		}	
	 
	 
	 
	 
	 
	 
	/* public static void main(String[] args) {
	 new ConsultaCupoCompany("1344");
	 }*/
	 
	
}
