package com.corporaciongpf.integration.soap.empleado.consultas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.corporaciongpf.integration.soap.empleado.v1.Empleados;
import com.corporaciongpf.integration.soap.empleado.v1.Empleados.Empleado;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

public class Consultas  {


	//private EmpleadoResponse empleadoResponse = new EmpleadoResponse();
	private ConexionVarianteSid conexionVarianteSid;


	public Empleados getEmpleadosActivos() {
		Empleado auxEmpleado;
		Empleados emps = new Empleados();
		String query = queryEmpladosActivos();
		try {
			
			//conexionVarianteSid = (new ObtenerNuevaConexion(Consultas.class)).obtenerNuevaConexionVarianteSidSidDataBase(47,  "HCMS", "fybeca"); // TEST
			conexionVarianteSid = (new ObtenerNuevaConexion(Consultas.class)).obtenerNuevaConexionVarianteSidSidDataBase(46,  "HCMS", "fybeca");
			PreparedStatement stat = conexionVarianteSid.getConn().prepareStatement(query);
			ResultSet consulta = stat.executeQuery();
			while (conexionVarianteSid.siguiente(consulta)) {
				auxEmpleado = new Empleado();
				auxEmpleado.setCodCct(consulta.getString("CENCOS_MF"));
				auxEmpleado.setCodEmp(consulta.getInt("COD_EMP"));
				auxEmpleado.setNombreCct(consulta.getString("DESC_CCT"));
				auxEmpleado.setNumEmpleados(consulta.getInt("TOTAL"));
				auxEmpleado.setPosicion(consulta.getString("POSICION"));
				auxEmpleado.setPresupuesto(consulta.getString("PRESUPUESTO"));
				emps.getEmpleado().add(auxEmpleado);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emps;
	}

	public Empleados getEmpleadosRetirados() {
		Empleado auxEmpleado;
		Empleados emps = new Empleados();
		String query = queryEmpleadosRetirados();
		try {
			//conexionVarianteSid = (new ObtenerNuevaConexion(Consultas.class)).obtenerNuevaConexionVarianteSidSidDataBase(47,  "HCMS", "fybeca"); // TEST
			conexionVarianteSid = (new ObtenerNuevaConexion(Consultas.class)).obtenerNuevaConexionVarianteSidSidDataBase(46,  "HCMS", "fybeca");
			PreparedStatement stat = conexionVarianteSid.getConn().prepareStatement(query);
			ResultSet consulta = stat.executeQuery();
			while (conexionVarianteSid.siguiente(consulta)) {
				auxEmpleado = new Empleado();
				auxEmpleado.setCodCct(consulta.getString("CENCOS_MF"));
				auxEmpleado.setCodEmp(consulta.getInt("COD_EMP"));
				auxEmpleado.setNombreCct(consulta.getString("DESC_CCT"));
				auxEmpleado.setNumEmpleados(consulta.getInt("TOTAL"));
				auxEmpleado.setPosicion(consulta.getString("POSICION"));
				auxEmpleado.setPresupuesto(consulta.getString("PRESUPUESTO"));
				emps.getEmpleado().add(auxEmpleado);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emps;
	}

	private String queryEmpladosActivos() {
		StringBuilder queryBusqueda = new StringBuilder();
		queryBusqueda.append(
				" SELECT A.COD_EMP, A.CENCOS_MF, RTRIM(C.DESCRI_COD) DESC_CCT, RTRIM(B.DESC_PUE) POSICION, F_OBTIENE_NOM_GRUPCOD(69502,AREA_CLASIF) PRESUPUESTO, COUNT(1) TOTAL ");
		queryBusqueda.append(" FROM MAEFUNC A, BXHR_VPUESTOS B, QSCODIGOS C ");
		queryBusqueda.append(" WHERE FEC_DESV_MF IS NULL ");
		queryBusqueda.append(" AND FEC_ING_SUC_MF <= SYSDATE ");
		queryBusqueda.append(" AND A.POSICION = B.COD_PUE ");
		queryBusqueda.append(" AND A.CENCOS_MF = C.CODIGO ");
		queryBusqueda.append(" AND C.GRUPO = '62' ");
		queryBusqueda.append(" GROUP BY A.COD_EMP, A.CENCOS_MF, C.DESCRI_COD, B.DESC_PUE,AREA_CLASIF ");
		return queryBusqueda.toString();
	}

	private String queryEmpleadosRetirados() {
		StringBuilder queryBusqueda = new StringBuilder();
		queryBusqueda.append(
				" SELECT A.COD_EMP, A.CENCOS_MF, RTRIM(C.DESCRI_COD) DESC_CCT, RTRIM(B.DESC_PUE) POSICION, F_OBTIENE_NOM_GRUPCOD(69502,AREA_CLASIF) PRESUPUESTO, COUNT(1) TOTAL ");
		queryBusqueda.append(" FROM MAEFUNC A, BXHR_VPUESTOS B, QSCODIGOS C ");
		queryBusqueda.append(" WHERE FEC_DESV_MF IS NOT NULL ");
		queryBusqueda.append(" AND FEC_ING_SUC_MF <= SYSDATE ");
		queryBusqueda.append(" AND A.POSICION = B.COD_PUE ");
		queryBusqueda.append(" AND A.CENCOS_MF = C.CODIGO ");
		queryBusqueda.append(" AND C.GRUPO = '62' ");
		queryBusqueda.append(" GROUP BY A.COD_EMP, A.CENCOS_MF, C.DESCRI_COD, B.DESC_PUE,AREA_CLASIF ");
		return queryBusqueda.toString();
	}



}
