package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DatosBasicosEmpleadoBean;

public class DatosBasicosEmpleado extends ObtenerNuevaConexion{
	private Boolean isValidConexion=true;
	private DatosBasicosEmpleadoBean datosBasicosEmpleadoBean;
	private String resultado;
	public DatosBasicosEmpleado(String cedula){
		super(DatosBasicosEmpleado.class);
		ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
		if(!conexionVarianteSid.isValid()){
			isValidConexion=false;
			return;
		}		
		String queryBusqueda=" select  primer_apellido  || ' ' || segundo_apellido   || ' ' || primer_nombre || ' ' || SEGUNDO_NOMBRE NOMBRES, "+
				 " (SELECT B.CAMPO4   FROM RH_CONTRATOS A, AD_EMPRESAS B WHERE A.EMPRESA=B.CODIGO AND A.EMPLEADO=ABP.CODIGO and	rownum = 1) EMPRESA, "+
				 " (select descripcion dato from AB_NACIONALIDADES where codigo =ABP.CODIGO) NACIONALIDAD, "+
				 " ABP.FECHA_NACIMIENTO FECHA_NACIMIENTO, "+
				 " (select TRIM(PRINCIPAL) || ' ' || TRIM(NUMERO) || ' ' || TRIM(INTERSECCION)   from ab_direcciones where persona=ABP.CODIGO AND TIPO=1  AND ROWNUM = 1) DIRECCION, "+
				 " (select VALOR from AB_MEDIOS_CONTACTO where persona=ABP.CODIGO AND TIPO=1 AND ROWNUM=1 ) TELEFONO_CASA, "+
				 " (select VALOR from AB_MEDIOS_CONTACTO where persona=ABP.CODIGO AND TIPO=6 AND ROWNUM=1 ) TELEFONO_CELULAR "+
				 " from ab_personas ABP  where identificacion='"+cedula+"'";
		ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
		while(conexionVarianteSid.siguiente(rs)){		
			this.datosBasicosEmpleadoBean=new DatosBasicosEmpleadoBean();
			datosBasicosEmpleadoBean.setCedula(cedula);
			datosBasicosEmpleadoBean.setDireccionDomiciliaria(conexionVarianteSid.getString(rs, "DIRECCION"));
			datosBasicosEmpleadoBean.setEmpresa(conexionVarianteSid.getString(rs, "EMPRESA"));
			datosBasicosEmpleadoBean.setFechaNacimiento(conexionVarianteSid.getString(rs, "FECHA_NACIMIENTO"));
			datosBasicosEmpleadoBean.setNacionalidad(conexionVarianteSid.getString(rs, "NACIONALIDAD"));
			datosBasicosEmpleadoBean.setNombresApellidos(conexionVarianteSid.getString(rs, "NOMBRES"));
			datosBasicosEmpleadoBean.setTelefonoCelular(conexionVarianteSid.getString(rs, "TELEFONO_CELULAR"));
			datosBasicosEmpleadoBean.setTelefonoDomicilio(conexionVarianteSid.getString(rs, "TELEFONO_CELULAR"));
		}		
	}
	public String getResultado() {
		if(this.datosBasicosEmpleadoBean!=null)
		   return Constantes.serializar(this.datosBasicosEmpleadoBean, "datosBasicosEmpleadoBean", "DATOS EMPLEADO", "104");
		else
			return Constantes.serializar("SIN DATOS", "datosBasicosEmpleadoBean", "NO EXISTE DATOS EMPLEADO", "504");
	}
	
	

}
