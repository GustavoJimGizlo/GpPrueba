package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

//        com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBean
import java.io.Serializable;
//info adic ASEGURADORA SALUD SA. y CONFIAMED POR SFBA 31 ENERO 2021


public class infoAdicBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String Clave;
	private String Valor;
	
	
	public String getClave() {
		return Clave;
	}
	public void setClave(String clave) {
		Clave = clave;
	}
	public String getValor() {
		return Valor;
	}
	public void setValor(String valor) {
		Valor = valor;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
