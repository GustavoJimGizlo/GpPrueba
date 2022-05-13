/**
 * @date 18/02/2014
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.GetDatosGenerica;
import com.gpf.postg.pedidos.util.Messages;
import com.serviciosweb.gpf.facturacion.farmacias.bean.PropuestaNivelBean;
import com.serviciosweb.gpf.facturacion.servicios.Farmacia;

/**
 * @author mftellor
 *
 */
public class PropuestaNivel extends GetDatosGenerica<PropuestaNivelBean>{

	//private String codigoLocal;
	private String resultado="S";
	//private Boolean desarrollo=true;// desarollo
	private Boolean desarrollo=false;//produccion
	
	private String codigoMaster=Constantes.CODIGO_BDD;
	
	public static void main(String[] args) {
		PropuestaNivel p= new PropuestaNivel("1");
		p= new PropuestaNivel("17");
		p= new PropuestaNivel("96");
		p= new PropuestaNivel("1710");
		p= new PropuestaNivel("17");
	}
	
	public static String  getPropuestaNivel(String codigoLocal){
		PropuestaNivel p= new PropuestaNivel(codigoLocal);
		return p.resultado;
	}
	
	public PropuestaNivel(String codigoLocal) {
		super(PropuestaNivelBean.class);
		if(!Boolean.parseBoolean(Messages.getString("EJBCommonsGPF.SO99.PROPUESTA")))
				return;
		
		log.info("Ambiente desarrollo: "+desarrollo);
		if(this.desarrollo)
			this.codigoMaster="6";
		
		/*
		if(!verificarInformacionCompleta())
			return;
		*/
		//this.codigoLocal=codigoLocal;
		
		String queryInsert;
		Integer totalRegistros=0;
		Integer totalRegistrosUpdate=0;
		
		Farmacia farmacia=new Farmacia(codigoLocal);
		if(this.desarrollo){
	       if(codigoLocal.equals("1"))
	       	farmacia.setDatabaseSid("F1_desa.UIO");
	       if(codigoLocal.equals("17"))
	   		farmacia.setDatabaseSid("F17_desa.UIO");
	       if(codigoLocal.equals("96"))
	   		farmacia.setDatabaseSid("S96_desa.UIO");
	       if(codigoLocal.equals("1710"))
	   		farmacia.setDatabaseSid("O1710_desa.UIO");
	       if(codigoLocal.equals("1818"))
	   		farmacia.setDatabaseSid("Q1818_desa.UIO");
	   		
		}
		
		List<PropuestaNivelBean> listadoPropuestaNivelBean=getDatosOracle(this.codigoMaster, "select *  from FARMACIAS.PE_PROPUESTA_SERVICEX_PDV WHERE COD_PUNTO_VENTA="+codigoLocal);
		if(listadoPropuestaNivelBean==null){
			log.info("NO EXISTE PropuestaNivel PARA "+codigoLocal);
			return;
		}
		try{
		truncateOracle(farmacia.getDatabaseSid(),"FARMACIAS.PE_PROPUESTA_SERVICEX_PDV WHERE COD_PUNTO_VENTA="+codigoLocal);
		for(PropuestaNivelBean pn:listadoPropuestaNivelBean){
			
			//DESARROLLO			
			queryInsert="INSERT INTO FARMACIAS.PE_PROPUESTA_SERVICEX_PDV ( ITEM, COD_PUNTO_VENTA,PROVEEDOR,IND_PREP,FECHA,CANTIDAD_PEDIDA,CONSULTADO_CENTRAL,TSF_NO,FROM_LOC,PLANIFICABLE,cod_ped) "
					+ "  VALUES("+pn.getItem()+","+pn.getDestLoc()+","+pn.getSupplier()+","+pn.getIndProp()+",to_date('"+Constantes.parseFecha(pn.getDueDatePrd(), "yyyy-MM-dd") +"','yyyy-MM-dd')"
							+ ","+pn.getqProp()+",'"+pn.getConsultadoCentral()+"','"+pn.getTsfNo()+"','"+pn.getFromLoc()+"','"+pn.getPlanificable()+"',"+pn.getCodPed()+")";
			
			
			
			//PRODUCCION
			/*
			queryInsert="INSERT INTO FARMACIAS.PE_PROPUESTA_SERVICEX_PDV ( ITEM, COD_PUNTO_VENTA,PROVEEDOR,IND_PREP,FECHA,CANTIDAD_PEDIDA,CONSULTADO_CENTRAL) "
					+ "  VALUES("+pn.getItem()+","+pn.getDestLoc()+","+pn.getSupplier()+","+pn.getIndProp()+",to_date('"+Constantes.parseFecha(pn.getDueDatePrd(), "yyyy-MM-dd") +"','yyyy-MM-dd')"
							+ ","+pn.getqProp()+",'N')";		
							
							*/
			
			totalRegistros=setDatosOracle(farmacia.getDatabaseSid(), queryInsert, totalRegistros);
			totalRegistrosUpdate++;	
		}
		this.conmmidOracle();
		if(Constantes.DEBUG)
			this.log.info("FINALIZA COPIA PropuestaNivel para: "+codigoLocal+"   totalRegistros:   "+totalRegistrosUpdate);
		
		}catch(Exception e){
			this.log.info("ERROR EN COPIA AL: "+codigoLocal);
		}
	}

	private boolean verificarInformacionCompleta(){
		this.log.info("VERIFICA EXISTENCIA PROPUESTA");
		if(this.desarrollo)
			return true;
		String fechaMaxima="";		
		this.resultado="N";
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSidMaster(this.codigoMaster);
		String queryBusqueda="SELECT MAX(fecha_carga) as fechaMaxima FROM PE_LOG_CARGA_SERVICES";
		ResultSet rs = con.consulta(queryBusqueda);
		while(con.siguiente(rs))
			fechaMaxima=con.getString(rs, "fechaMaxima");
		queryBusqueda="select TERMINA_COPIA  from PE_LOG_CARGA_SERVICES where fecha_carga=(select max(fecha_carga) from PE_LOG_CARGA_SERVICES) " +
				" AND trunc(sysdate)=trunc(to_date('"+fechaMaxima+"','yyyy-MM-dd HH24:MI:SS'))";
		rs = con.consulta(queryBusqueda);
		while(con.siguiente(rs))
			resultado=con.getString(rs, "TERMINA_COPIA");
		con.cerrarConexion(rs);
		return resultado.equals("S")?true:false;		
	}
}
