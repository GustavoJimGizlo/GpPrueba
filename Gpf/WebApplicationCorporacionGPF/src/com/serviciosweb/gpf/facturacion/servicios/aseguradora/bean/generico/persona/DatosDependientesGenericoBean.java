package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBeanC;

//import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DependientesBean;

@XmlRootElement(name = "rec_dependiente")
@XmlType(propOrder = {"de_autcodigo", "de_cedula", "de_mail"
		, "de_nombre", "de_telefono","de_tipo","de_tipoident", "estado", "mensaje","de_descripcionCarencia","ti_infoAdic","di_preexistencias"
		})
public class DatosDependientesGenericoBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String de_tipoident;
	private String de_cedula;
	private String de_nombre;
	private String de_tipo;
	private String de_autcodigo;
	private String estado;
	private String mensaje;
	private String de_mail="";
	private String de_telefono="";
    private String de_descripcionCarencia;
	private infoAdicBeanC ti_infoAdic;
	private List<di_Preexistencias> di_preexistencias;
	
	
	
	
	
	public List<di_Preexistencias> getDi_preexistencias() {
		return di_preexistencias;
	}
	public void setDi_preexistencias(List<di_Preexistencias> di_preexistencias) {
		this.di_preexistencias = di_preexistencias;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDe_tipoident() {
		return de_tipoident;
	}
	public void setDe_tipoident(String de_tipoident) {
		this.de_tipoident = de_tipoident;
	}
	public String getDe_cedula() {
		return de_cedula;
	}
	public void setDe_cedula(String de_cedula) {
		this.de_cedula = de_cedula;
	}
	public String getDe_nombre() {
		return de_nombre;
	}
	public void setDe_nombre(String de_nombre) {
		this.de_nombre = de_nombre;
	}
	public String getDe_tipo() {
		return de_tipo;
	}
	public void setDe_tipo(String de_tipo) {
		this.de_tipo = de_tipo;
	}
	public String getDe_autcodigo() {
		return de_autcodigo;
	}
	public void setDe_autcodigo(String de_autcodigo) {
		this.de_autcodigo = de_autcodigo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	public String getDe_mail() {
		return de_mail;
	}
	public void setDe_mail(String de_mail) {
		this.de_mail = de_mail;
	}
	public String getDe_telefono() {
		return de_telefono;
	}
	public void setDe_telefono(String de_telefono) {
		this.de_telefono = de_telefono;
	}
	public String getDe_descripcionCarencia() {
		return de_descripcionCarencia;
	}
	public void setDe_descripcionCarencia(String de_descripcionCarencia) {
		this.de_descripcionCarencia = de_descripcionCarencia;
	}
	public infoAdicBeanC getTi_infoAdic() {
	
		//if (ti_infoAdic.toString().isEmpty())
		//{
		//	
		return ti_infoAdic;
	
	
	}
	public void setTi_infoAdic(infoAdicBeanC ti_infoAdic) {
		
		infoAdicBeanC infoAdicBeanc = new infoAdicBeanC();
		
		if (ti_infoAdic.toString().equalsIgnoreCase("null")) {}
		else
		infoAdicBeanc = ti_infoAdic;
		
		
		
		
	//}
	//else
		
		//ti_infoAdic
		
		//this.ti_infoAdic = ti_infoAdic;
		this.ti_infoAdic = infoAdicBeanc;
	}
	
	
    
    	
}
