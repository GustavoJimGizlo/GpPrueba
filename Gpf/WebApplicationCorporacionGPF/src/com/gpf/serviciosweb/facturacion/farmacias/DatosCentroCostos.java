package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DatosCentroCostosBean;



public class DatosCentroCostos extends ObtenerNuevaConexion{
	String resultadoJson="NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS";
	public DatosCentroCostos(){
		super(DatosCentroCostos.class);		
		//new Temporizador(this).start();
	}
	public DatosCentroCostos(String codigoCentroCosto){
		super(DatosCentroCostos.class);
		//new Temporizador(this).start();
		ResultSetMapper<DatosCentroCostosBean> resultSetMapper=new ResultSetMapper<DatosCentroCostosBean>();
		/*String queryBusqueda=" Select (SELECT RAZON_SOCIAL FROM AB_PERSONAS Z WHERE Z.CODIGO=A.codigo2) empresa, "+
				" (SELECT C.NOMBRE FROM MAPEO_CLASES_NOMINA B,AD_SUCURSALES C WHERE RTRIM(B.INSTALACION)=C.INSTALA_RRHH "+
				" AND B.EMPRESA=A.CODIGO2 AND B.EMPRESA=C.EMPRESA AND RTRIM(CODIGO1)=CLASE_NOMINA) Sucursal, "+
				" rtrim(descri_cod) DESCRIPCIONCCT from qscodigos A where grupo='62' and RTRIM(codigo)='"+codigoCentroCosto+"' AND CODIGO3='U' "+
				" UNION SELECT  (SELECT RAZON_SOCIAL FROM AB_PERSONAS Z WHERE Z.CODIGO =A.CODIGO1) EMPRESA, (SELECT C.NOMBRE "+
				" FROM MAPEO_CLASES_NOMINA B,AD_SUCURSALES C WHERE RTRIM(B.INSTALACION)=C.INSTALA_RRHH AND B.EMPRESA=A.CODIGO1 "+
				" AND B.EMPRESA=C.EMPRESA AND RTRIM(a.CODIGO)=CLASE_NOMINA) Sucursal,  (SELECT DESCRI_COD FROM QSCODIGOS X "+
				" WHERE X.CODIGO3='T' AND X.CODIGO2=A.CODIGO1 and RTRIM(X.codigo)='"+codigoCentroCosto+"') Desc_cct  FROM QSCODIGOS A "+
				" WHERE GRUPO='63' and codigo  not in ('HP','HRL') AND A.CODIGO1=(SELECT X.CODIGO2 FROM QSCODIGOS X "+
				" WHERE X.CODIGO3='T' AND X.CODIGO2=A.CODIGO1 and RTRIM(X.codigo)='"+codigoCentroCosto+"') ";
		*/
		/*String queryBusqueda="SELECT (SELECT RAZON_SOCIAL FROM AB_PERSONAS Z  WHERE Z.CODIGO = A.codigo2)  empresa, " +
			   " (SELECT C.NOMBRE  FROM MAPEO_CLASES_NOMINA B, AD_SUCURSALES C " +
			   " WHERE     RTRIM (B.INSTALACION) = C.INSTALA_RRHH AND B.EMPRESA = A.CODIGO2  AND B.EMPRESA = C.EMPRESA" +
			   " AND RTRIM (CODIGO1) = CLASE_NOMINA AND RTRIM(B.INSTALACION) = DECODE(RTRIM(LOCALIDAD), NULL, B.INSTALACION,RTRIM(LOCALIDAD)) " +
			   " and rownum=1 )          Sucursal,       RTRIM (descri_cod) DESCRIPCIONCCT  FROM qscodigos A WHERE     grupo = '62'" +
			   " AND RTRIM (codigo) = '"+codigoCentroCosto+"'  AND CODIGO3 = 'U' UNION SELECT (SELECT RAZON_SOCIAL   FROM AB_PERSONAS Z" +
    		   " WHERE Z.CODIGO = A.CODIGO1)   EMPRESA, (SELECT C.NOMBRE  FROM MAPEO_CLASES_NOMINA B, AD_SUCURSALES C" +
        	   " WHERE     RTRIM (B.INSTALACION) = C.INSTALA_RRHH  AND B.EMPRESA = A.CODIGO1 AND B.EMPRESA = C.EMPRESA" +
               " AND RTRIM (a.CODIGO) = CLASE_NOMINA AND RTRIM(B.INSTALACION) = DECODE(RTRIM(LOCALIDAD), NULL,B.INSTALACION,RTRIM(LOCALIDAD)) and rownum=1 ) " +
               "  Sucursal,        (SELECT DESCRI_COD          FROM QSCODIGOS X         WHERE     X.CODIGO3 = 'T'               AND X.CODIGO2 = A.CODIGO1 " +
               " and grupo = '62'  AND RTRIM (X.codigo) = '"+codigoCentroCosto+"')          Desc_cct  FROM QSCODIGOS A WHERE     GRUPO = '63'       AND codigo NOT IN ('HP', 'HRL') " + 
               " AND A.CODIGO1 =(SELECT X.CODIGO2  FROM QSCODIGOS X  WHERE     X.CODIGO3 = 'T' AND X.CODIGO2 = A.CODIGO1 " +
               " and grupo = '62' AND RTRIM (X.codigo) = '"+codigoCentroCosto+"')";
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSidMaster("1");*/
		
		
		String queryBusqueda="SELECT (SELECT RAZON_SOCIAL"
				+ " FROM AB_PERSONAS Z"
				+ " WHERE Z.CODIGO = A.codigo2)"
				+ " empresa,"
				+ " (SELECT C.NOMBRE"
				+ " FROM MAPEO_CLASES_NOMINA B, AD_SUCURSALES C"
				+ " WHERE     RTRIM (B.INSTALACION) = C.INSTALA_RRHH"
				+ " AND B.EMPRESA = A.CODIGO2"
				+ " AND B.EMPRESA = C.EMPRESA"
				+ " AND RTRIM (CODIGO1) = CLASE_NOMINA AND B.INSTALACION = DECODE(RTRIM(LOCALIDAD), NULL, B.INSTALACION,RTRIM(LOCALIDAD)) and rownum=1 )"
				+ " Sucursal,"
				+ " RTRIM (descri_cod) DESCRIPCIONCCT"
				+ " FROM qscodigos A"
				+ " WHERE     grupo = '62'"
				+ " AND RTRIM (codigo) = '"+codigoCentroCosto+"'"
				+ " AND CODIGO3 = 'U'"
				+ " UNION"
				+ " SELECT (SELECT RAZON_SOCIAL"
				+ " FROM AB_PERSONAS Z"
				+ " WHERE Z.CODIGO = A.CODIGO1)"
				+ " EMPRESA,"
				+ " (SELECT C.NOMBRE"
				+ " FROM MAPEO_CLASES_NOMINA B, AD_SUCURSALES C"
				+ " WHERE     RTRIM (B.INSTALACION) = C.INSTALA_RRHH"
				+ " AND B.EMPRESA = A.CODIGO1"
				+ " AND B.EMPRESA = C.EMPRESA"
				+ " AND RTRIM (a.CODIGO) = CLASE_NOMINA AND B.INSTALACION = DECODE(RTRIM(LOCALIDAD), NULL,B.INSTALACION,RTRIM(LOCALIDAD)) and rownum=1 )"
				+ " Sucursal,"
				+ " (SELECT DESCRI_COD"
				+ " FROM QSCODIGOS X"
				+ " WHERE     X.CODIGO3 = 'T'"
				+ " AND X.CODIGO2 = A.CODIGO1"
				+ " and grupo = '62'"
				+ " AND RTRIM (X.codigo) = '"+codigoCentroCosto+"')"
				+ " Desc_cct"
				+ " FROM QSCODIGOS A"
				+ " WHERE     GRUPO = '63'"
				+ " AND codigo NOT IN ('HP', 'HRL')"
				+ " AND A.CODIGO1 ="
				+ " (SELECT X.CODIGO2"
				+ " FROM QSCODIGOS X"
				+ " WHERE     X.CODIGO3 = 'T'"
				+ "  AND X.CODIGO2 = A.CODIGO1"
				+ "   and grupo = '62'"
				+ "   AND RTRIM (X.codigo) = '"+codigoCentroCosto+"')";
		
			ConexionVarianteSid con=obtenerNuevaConexionVarianteSidMaster("1");
			
		
		List<DatosCentroCostosBean> listDatosCentroCostos=resultSetMapper.mapRersultSetToObject(con.consulta(queryBusqueda), DatosCentroCostosBean.class);
		
		if(listDatosCentroCostos!=null)
		  resultadoJson=Constantes.serializar(listDatosCentroCostos, "datosCentroCostos", "Centro costo "+codigoCentroCosto,"104");
		else
			resultadoJson=Constantes.serializar("No existe infotrmacion del centro de costo "+codigoCentroCosto, "datosCentroCostos", "Centro costo "+codigoCentroCosto,"504");
		
		resultadoJson=resultadoJson.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DatosCentroCostosBean", "DatosCentroCostosBean");
		con.cerrarConexion();
	}
	
	public String getCargosCentroCostos(String codigoCargo){
		List<String> listadoCargos=new ArrayList<String>();
		String queryBusqueda="SELECT DESC_PUE FROM BXHR_VPUESTOS@DBNOMINA WHERE COD_PUE="+codigoCargo;
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSidMaster("1");
		ResultSet rs = con.consulta(queryBusqueda);
		while(con.siguiente(rs)){
			listadoCargos.add(con.getString(rs, "DESC_PUE"));
			
		}
		if(listadoCargos.size()>0)
			     resultadoJson=Constantes.serializar(listadoCargos, "datosCargosCentroCostos", "Centro costo "+codigoCargo,"104");
			else
				 resultadoJson=Constantes.serializar("No existe infotrmacion del centro de costo "+codigoCargo, "datosCargosCentroCostos", "Cargos Centro costo "+codigoCargo,"504");

		con.cerrarConexion(rs);
		return  resultadoJson;
	}
	
	
	public String getResultadoJson() {
		return resultadoJson;
	}
/*	
	public class Temporizador extends Thread{
		private DatosCentroCostos datosCentroCostos;
		public Temporizador(DatosCentroCostos datosCentroCostos){
			this.datosCentroCostos=datosCentroCostos;
		}
		@Override
		public void run() {
			Boolean validar=true;
			GregorianCalendar fechaInicio = (GregorianCalendar) GregorianCalendar.getInstance();
			GregorianCalendar fechaTimeOut = (GregorianCalendar) GregorianCalendar.getInstance();
			fechaTimeOut.setTime(fechaInicio.getTime());			
			fechaTimeOut.add(Calendar.SECOND, 15);
			GregorianCalendar fechaActual = (GregorianCalendar) GregorianCalendar.getInstance();
			while(validar){
				fechaActual = (GregorianCalendar) GregorianCalendar.getInstance();
				if(!fechaActual.before(fechaTimeOut)){
					System.out.println("se acabo el tiempo");
					this.datosCentroCostos.resultadoJson="xxxxx";
					validar=false;
				}
			}
			
		}
	}
*/
}
