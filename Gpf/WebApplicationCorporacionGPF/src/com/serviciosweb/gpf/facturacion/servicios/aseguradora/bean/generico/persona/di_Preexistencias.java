package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

//        com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBean
import java.io.Serializable;
//info adic ASEGURADORA SALUD SA. y CONFIAMED POR SFBA 31 ENERO 2021


public class di_Preexistencias  implements Serializable{

//public class di_Preexistencias {
	
	/**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	private String estado;
	
	private String pre_estado;
	
	
	private String mensaje;
	private String msj;
	
	private String codigoCIE10;
	private String nombreDiagnostico;
	
	
	
	
	
	
	public String getPre_estado() {
		return pre_estado;
	}
	public void setPre_estado(String pre_estado) {
		this.pre_estado = pre_estado;
	}
	public String getMsj() {
		return msj;
	}
	public void setMsj(String msj) {
		this.msj = msj;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getCodigoCIE10() {
		return codigoCIE10;
	}
	public void setCodigoCIE10(String codigoCIE10) {
		this.codigoCIE10 = codigoCIE10;
	}
	public String getNombreDiagnostico() {
		return nombreDiagnostico;
	}
	public void setNombreDiagnostico(String nombreDiagnostico) {
		this.nombreDiagnostico = nombreDiagnostico;
	}
	
	
	
	
	
    
		
}
