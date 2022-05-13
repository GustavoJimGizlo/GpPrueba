package com.gpf.serviciosweb.facturacion.farmacias;


import java.sql.ResultSet;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.pagoagil.farcomed.ServicioFarcomed;

public class PagoAgilAutenticacion extends ObtenerNuevaConexion {
    private String url="http://uiopagoa01.gfybeca.int:8000/InicioSesion.aspx?Ticket=";
    Logger log=Logger.getLogger(this.getClass().getName());
    public static void main(String[] args) {
		PagoAgilAutenticacion pa = new PagoAgilAutenticacion("pasinchiguanov");
		System.out.println(pa.getUrl());
	}
	public PagoAgilAutenticacion(String usuario){
		super(PagoAgilAutenticacion.class);
		int intentos=0;
		while(intentos<=2){
		try{				
				ServicioFarcomed sf=new ServicioFarcomed();			
				String ticket = sf.getBasicHttp().ticketGenerar(usuario ,"010101");				
				url+=ticket+ "&Usuario=" + usuario + "&Fagencia=" + obtenerCentroCostos(usuario);
				intentos=11;
				
			}catch(Exception e){
				e.printStackTrace();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {				
					e1.printStackTrace();
				}
				log.info("PagoAgilAutenticacion ERROR DE AUTENTICACION USUARIO "+usuario+" INTENTO "+intentos);
				url="http://172.20.200.192/WebApplicationCorporacionGPF/pages/errorPagoAgil.html?="+usuario;
				intentos++;
			}
		}
	}
	
	private String obtenerCentroCostos(String usuario){	
		String centroCosto="0";
		String codigoEmpleado="select CODIGO_PERSONA from WF_USUARIOS where upper(nombre_usuario)='"+usuario.trim().toUpperCase()+"'";
		ConexionVarianteSid conexion=obtenerNuevaConexionVarianteSid("1");
		ResultSet rs = conexion.consulta(codigoEmpleado);
		while(conexion.siguiente(rs))
			codigoEmpleado=conexion.getString(rs, "CODIGO_PERSONA");
		
		String centroCostoFuncion="(select TO_CHAR(nvl(rh_obt_centro_costo("+codigoEmpleado+"),0)) from dual) as CENTRO_COSTO_FUNCION";
		String queryBusqueda="select CENTRO_COSTO_NOMINA,"+centroCostoFuncion+" from RH_CONTRATOS where empleado=("+codigoEmpleado+")";
		rs = conexion.consulta(queryBusqueda);
		while(conexion.siguiente(rs)){
			if(conexion.getInt(rs, "CENTRO_COSTO_FUNCION")>0)
				centroCosto=conexion.getString(rs, "CENTRO_COSTO_FUNCION");
			else
				centroCosto=conexion.getString(rs, "CENTRO_COSTO_NOMINA");
		}
		conexion.cerrarConexion(rs);
		return centroCosto;
	}

	public String getUrl() {
		return url;
	}
	
}
