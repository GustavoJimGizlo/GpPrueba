package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBean;




@XmlRootElement(name = "rec_titular")

@XmlType(propOrder = { 
		
						"ti_tipoident", 
					   "ti_cedula", 
					   "ti_mail", 
					   "ti_telefono",
					   "ti_estado", 
					   "ti_autnumcont", 
					   "ti_codplan", 
					   "ti_autcodigo",
					   "ti_nomplan", 
					   "ti_fechinicontr", 
					   "ti_codgenero", 
					   "ti_contrato",
					   "ti_nombreContratante", 
					   "ti_nombreNegocio", 
					   "ti_versionPlan",
					   "ti_infoAdic",
					   "ti_mensaje",
					   "ti_producto",
					   "rec_dependiente", 
					   "ti_nombre", 
					   "ti_fecnaci", 
					   "ti_planm",
					   "estado", 
					   "mensaje",
					   "estaMoroso"
					   
})
public class DatosTitularesGenericoBean implements Serializable {
	private static final long serialVersionUID = -7325589119127486349L;

	private String ti_tipoident;
	private String ti_cedula;
	private String ti_mail;
	private String ti_telefono;
	private String ti_estado;
	private String ti_mensaje;
	private String ti_autnumcont;
	private String ti_codplan;
	private String ti_autcodigo;
	private String ti_nomplan;
	private String ti_fechinicontr;
	private String ti_codgenero;
	private String ti_contrato;
	private String ti_nombreContratante;
	private String ti_nombreNegocio;
	private String ti_versionPlan;
	// aunentamos info adic para coberturas
	private List<infoAdicBean> ti_infoAdic;
	private List<DatosDependientesGenericoBean> rec_dependiente;	
	private String ti_nombre;	
	private String ti_fecnaci;
	private String ti_planm;
	private String estado;
	private String mensaje;
	private String estaMoroso;
    private String ti_producto;

    

	public String getTi_producto() {
		return ti_producto;
	}

	public void setTi_producto(String ti_producto) {
		this.ti_producto = ti_producto;
	}

	public String getTi_mensaje() {
		return ti_mensaje;
	}

	public void setTi_mensaje(String ti_mensaje) {
		this.ti_mensaje = ti_mensaje;
	}

	public String getTi_tipoident() {
		return ti_tipoident;
	}

	public void setTi_tipoident(String ti_tipoident) {
		this.ti_tipoident = ti_tipoident;
	}

	public String getTi_cedula() {
		return ti_cedula;
	}

	public void setTi_cedula(String ti_cedula) {
		this.ti_cedula = ti_cedula;
	}

	public String getTi_mail() {
		return ti_mail;
	}

	public void setTi_mail(String ti_mail) {
		this.ti_mail = ti_mail;
	}

	public String getTi_telefono() {
		return ti_telefono;
	}

	public void setTi_telefono(String ti_telefono) {
		this.ti_telefono = ti_telefono;
	}

	public String getTi_estado() {
		return ti_estado;
	}

	public void setTi_estado(String ti_estado) {
		this.ti_estado = ti_estado;
	}

	public String getTi_autnumcont() {
		return ti_autnumcont;
	}

	public void setTi_autnumcont(String ti_autnumcont) {
		this.ti_autnumcont = ti_autnumcont;
	}

	public String getTi_codplan() {
		return ti_codplan;
	}

	public void setTi_codplan(String ti_codplan) {
		this.ti_codplan = ti_codplan;
	}

	public String getTi_autcodigo() {
		return ti_autcodigo;
	}

	public void setTi_autcodigo(String ti_autcodigo) {
		this.ti_autcodigo = ti_autcodigo;
	}

	public String getTi_nomplan() {
		return ti_nomplan;
	}

	public void setTi_nomplan(String ti_nomplan) {
		this.ti_nomplan = ti_nomplan;
	}

	public String getTi_fechinicontr() {
		return ti_fechinicontr;
	}

	public void setTi_fechinicontr(String ti_fechinicontr) {
		this.ti_fechinicontr = ti_fechinicontr;
	}

	public String getTi_codgenero() {
		return ti_codgenero;
	}

	public void setTi_codgenero(String ti_codgenero) {
		this.ti_codgenero = ti_codgenero;
	}

	public String getTi_contrato() {
		return ti_contrato;
	}

	public void setTi_contrato(String ti_contrato) {
		this.ti_contrato = ti_contrato;
	}

	public String getTi_nombreContratante() {
		return ti_nombreContratante;
	}

	public void setTi_nombreContratante(String ti_nombreContratante) {
		this.ti_nombreContratante = ti_nombreContratante;
	}

	public String getTi_nombreNegocio() {
		return ti_nombreNegocio;
	}

	public void setTi_nombreNegocio(String ti_nombreNegocio) {
		this.ti_nombreNegocio = ti_nombreNegocio;
	}

	public String getTi_versionPlan() {
		return ti_versionPlan;
	}

	public void setTi_versionPlan(String ti_versionPlan) {
		this.ti_versionPlan = ti_versionPlan;
	}

	public List<infoAdicBean> getTi_infoAdic() {
		return ti_infoAdic;
	}

	public void setTi_infoAdic(List<infoAdicBean> ti_infoAdic) {
		this.ti_infoAdic = ti_infoAdic;
	}

	public List<DatosDependientesGenericoBean> getRec_dependiente() {
		return rec_dependiente;
	}

	public void setRec_dependiente(List<DatosDependientesGenericoBean> rec_dependiente) {
		this.rec_dependiente = rec_dependiente;
	}

	public String getTi_nombre() {
		return ti_nombre;
	}

	public void setTi_nombre(String ti_nombre) {
		this.ti_nombre = ti_nombre;
	}

	public String getTi_fecnaci() {
		return ti_fecnaci;
	}

	public void setTi_fecnaci(String ti_fecnaci) {
		this.ti_fecnaci = ti_fecnaci;
	}

	public String getTi_planm() {
		return ti_planm;
	}

	public void setTi_planm(String ti_planm) {
		this.ti_planm = ti_planm;
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

	public String getEstaMoroso() {
		return estaMoroso;
	}

	public void setEstaMoroso(String estaMoroso) {
		this.estaMoroso = estaMoroso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
		
	
	
	
	


}
