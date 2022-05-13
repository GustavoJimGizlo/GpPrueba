
package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasCercanas;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasInterCompany;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @date 20/06/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class ConsultaPdvCercanosInterCompany extends ObtenerNuevaConexion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(this.getClass().getName());
	String resultadoXml=Constantes.respuestaXmlObjeto("ERROR", "NO EXISTEN DATOS PARA ESTA CONSULTA");
	private String idBdd="44"; // 1 PRODUCCION 44 PORD_DESA
	private Boolean desarrollo=false;
	{
		if(desarrollo)
			idBdd="6";
	}
	public ConsultaPdvCercanosInterCompany(String idLocal,String empresa, String ambiente) {
		super(ConsultaPdvCercanosInterCompany.class);
		this.getCodigosFarmaciasCercanas(Integer.parseInt(idLocal),Integer.parseInt(empresa), ambiente);
	}

	
	 public List<AdFarmaciasInterCompany> getCodigosFarmaciasCercanas(int codigoFarmacia,int empresa, String ambiente) 
     {   		 
		   
		 	String nombreConexion = "PRODDEV_CLOUD_M2C.UIO";
		 	if(ambiente.equals("T")){
		 		nombreConexion = "PRODUAT_CLOUD_M2C.UIO";
		 	}else if(ambiente.equals("D")){
		 		nombreConexion = "PRODDEV_CLOUD_M2C.UIO";
		 	}else if(ambiente.equals("P")){
		 		nombreConexion = "PROD.UIO";
		 	}
		     XStream xstream = new XStream(new StaxDriver());
             String farmaciasCercanas = "";
             CallableStatement cs = null;
             ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSidSidDataBase(nombreConexion);
             String queryBusqueda= "SELECT f.codigo AS codigo,f.nombre AS nombre,f.empresa AS empresa,f.calle || ' ' || f.numero || ' ' || f.interseccion AS direccion,g.longitud AS longitudOrigen,g.latitud AS latitudOrigen,round(ad_f_distanciakilometros(g1.latitud, g1.longitud, g.latitud, g.longitud), 2) distanciaKilometros " 
                             + " FROM ad_farmacias f,ad_farmacias_geolocalizacion g,ad_farmacias_geolocalizacion g1 "
                             + " WHERE f.fma_autorizacion_farmaceutica = 'FS' "
                             + " AND f.codigo = g.codigo_farmacia "
                             + " AND g1.codigo_farmacia ="+codigoFarmacia+" "
                             + " AND f.codigo IN(SELECT regexp_substr((SELECT cp_var3 FROM fa_empresas_tipo_farmacias WHERE parametro = 'TRANS_INTERCOMPANY'AND activo = 'S' AND empresa = "+empresa+"), '[^,]+', 1, LEVEL) categorias"
                             + " FROM dual CONNECT BY regexp_substr((SELECT cp_var3 FROM fa_empresas_tipo_farmacias WHERE parametro = 'TRANS_INTERCOMPANY' AND activo = 'S' AND empresa = "+empresa+"), '[^,]+', 1, LEVEL) IS NOT NULL)"
                             + " AND round(ad_f_distanciakilometros(g1.latitud, g1.longitud, g.latitud, g.longitud), 2) IS NOT NULL"
                             + " ORDER BY distanciaKilometros ";
             
           
         ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
         ResultSetMapper<AdFarmaciasInterCompany> resultSetMapper = new ResultSetMapper<AdFarmaciasInterCompany>();
         List<AdFarmaciasInterCompany> listadoAdFarmaciasCercanas=resultSetMapper.mapRersultSetToObject(rs, AdFarmaciasInterCompany.class);
         conexionVarianteSid.cerrarConexion(rs);
       
             
         xstream.alias("AdFarmaciasInterCompany", AdFarmaciasInterCompany.class);
         resultadoXml=xstream.toXML(listadoAdFarmaciasCercanas);
         return listadoAdFarmaciasCercanas;
         
       
         
      }

	
	 private AdFarmaciasInterCompany getLocal(List<AdFarmaciasInterCompany> listadoAdFarmaciasCercanas, int idLocal){
		 
		    for(AdFarmaciasInterCompany adFarmaciasCercanasIterador:listadoAdFarmaciasCercanas)
	        	// if(adFarmaciasCercanasIterador.getCodigoLocal().compareTo(idLocal)==0)
	        		 return adFarmaciasCercanasIterador;
		    
		    
			return listadoAdFarmaciasCercanas.get(0);
	         
	 }



	public String getResultadoXml() {
		return Constantes.toUTF8(resultadoXml);
	}
	 
}
