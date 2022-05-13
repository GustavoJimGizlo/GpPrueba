package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DatosCompletoEmpleadoBean;

public class DatosCompletosEmpleado extends ObtenerNuevaConexion {

	String resultadoJson=Constantes.serializar("No existe infotrmacion para el empleado ", "datosCentroCostos", "Cedula empleado","504");
	public DatosCompletosEmpleado(String numeroCedulaEmpleado){
		super(DatosCompletosEmpleado.class);
		ResultSetMapper<DatosCompletoEmpleadoBean> resultSetMapper=new ResultSetMapper<DatosCompletoEmpleadoBean>();
		String queryBusqueda="SELECT (SELECT NOM_EMP FROM QSEMPRESA WHERE COD_EMP =a.cod_emp) EMPRESA,"+
               " rtrim(b.cedide_mf) cedula,(rtrim(pri_ape_mf)||' '||rtrim(seg_ape_mf)||' '||rtrim(pri_nom_mf)||' '||rtrim(seg_nom_mf)) nombres, "+
               " fecha2 fec_ing, fecha1 fec_desv, ' ' sucursal, ' 'CCT, '' nom_cct, null sueldo, '' tipo_contrato, ' ' puesto , null meses "+
               " FROM legajo@dbnomina.uio a, maefunc b WHERE     a.cod_mot_lg = 'desvi' AND a.cod_mf = b.cod_mf "+
               " AND RTRIM (b.cedide_mf)='"+numeroCedulaEmpleado+"' AND b.fecha_efectiva = (SELECT MAX (fecha_efectiva)  FROM maefunc x "+
               "       WHERE x.cod_mf = b.cod_mf AND x.cod_emp = b.cod_emp)"+           
               " UNION "+
               " SELECT (SELECT NOM_EMP FROM QSEMPRESA WHERE COD_EMP =a.cod_emp)EMPRESA, rtrim(a.cedide_mf) cedula, "+ 
               " (rtrim(pri_ape_mf)||' '||rtrim(seg_ape_mf)||' '||rtrim(pri_nom_mf)||' '||rtrim(seg_nom_mf)) nombres, "+
               " fec_ing_suc_mf fec_ing, fec_desv_mf, (select nombre from MAPEO_CLASES_NOMINA A, AD_SUCURSALES B "+
               " WHERE A.CLASE_NOMINA=RTRIM(A.sucursal) and a.empresa=b.empresa and a.instalacion =b.instala_rrhh ) sucursal, A.CENCOS_MF,"+
               " (SELECT descri_cod FROM qscodigos where grupo='62' and codigo=a.cencos_mf) nom_cct, a.sjh_mf sueldo, "+ 
               " (select descri_cod from qscodigos where grupo='60' and codigo = a.tipo_contrato) tipo_contrato, (select rtrim(desc_pue) "+ 
               " from bxhr_vpuestos@dbnomina where cod_pue= a.posicion) puesto, round (months_between (sysdate, fec_ing_mf), 2) meses "+
               " FROM maefunc a WHERE     RTRIM (a.cedide_mf)='"+numeroCedulaEmpleado+"'  AND a.fecha_efectiva = "+
               "     (SELECT MAX (fecha_efectiva) FROM maefunc x WHERE x.cod_mf = a.cod_mf AND x.cod_emp = a.cod_emp) "+
               " AND a.fec_ing_suc_mf NOT IN (SELECT fecha2 FROM legajo@dbnomina.uio x WHERE     x.cod_mf = a.cod_mf "+
               " AND x.cod_mot_lg = 'desvi'  AND x.cod_emp = a.cod_emp)"; 
		ConexionVarianteSid con=obtenerNuevaConexionVarianteSidMaster("1");		
		ResultSet rs = con.consulta(queryBusqueda);
		List<DatosCompletoEmpleadoBean> listDatosEmpleado=resultSetMapper.mapRersultSetToObject(rs, DatosCompletoEmpleadoBean.class);
		con.cerrarConexion(rs);

		if(listDatosEmpleado!=null)
			  resultadoJson=Constantes.serializar(listDatosEmpleado, "datosCentroCostos", "Cedula empleado: "+numeroCedulaEmpleado,"104");
			else
				resultadoJson=Constantes.serializar("No existe infotrmacion para el empleado "+numeroCedulaEmpleado, "datosCentroCostos", "Cedula empleado: "+numeroCedulaEmpleado,"504");
			
			resultadoJson=resultadoJson.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DatosCompletoEmpleadoBean", "DatosCompletoEmpleadoBean");
	}
	public String getResultadoJson() {
		return resultadoJson;
	}	
}
