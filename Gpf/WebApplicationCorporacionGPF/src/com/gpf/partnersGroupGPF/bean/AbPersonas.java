package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class AbPersonas implements Serializable {
	
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Column(name = "CODIGO")
	private long codigo; 

	@Column(name = "TIPO_PERSONA")
	private String tipoPersona; 
	
	@Column(name = "IDENTIFICACION")
	private String identificacion;
	
	@Column(name = "TIPO_IDENTIFICACION")
	private String tipoIdentificacion;
	
	@Column(name = "PRIMER_NOMBRE")
	private String primerNombre; 
	
	@Column(name = "SEGUNDO_NOMBRE")
	private String segundoNombre;
	
	@Column(name = "PRIMER_APELLIDO")
	private String primerApellido;
	
	@Column(name = "SEGUNDO_APELLIDO")
	private String segundoApellido;
	
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name = "RAZON_SOCIAL")
	private String razonSocial; 
	
	@Column(name = "REPRESENTANTE_LEGAL")
	private String representanteLegal;
	
	@Column(name = "IDENTIFICACION_REP_LEGAL")
	private String identificacionRepLegal;
	
	@Column(name = "TIPO_IDENTIFICACION_REP_LEGAL")
	private String tipoIdentificacionRepLegal;
	
	@Column(name = "NOMBRE_COMERCIAL")
	private String nombreComercial;
	
	private List<AbMediosContacto> mediosContacto;
	
	@Transient
	private List<AbDirecciones> direcciones;
	
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	
	   
	
	
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRepresentanteLegal() {
		return representanteLegal;
	}
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	public String getIdentificacionRepLegal() {
		return identificacionRepLegal;
	}
	public void setIdentificacionRepLegal(String identificacionRepLegal) {
		this.identificacionRepLegal = identificacionRepLegal;
	}
	
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public List<AbMediosContacto> getMediosContacto() {
		return mediosContacto;
	}
	public void setMediosContacto(List<AbMediosContacto> mediosContacto) {
		this.mediosContacto = mediosContacto;
	}
	public List<AbDirecciones> getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(List<AbDirecciones> direcciones) {
		this.direcciones = direcciones;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getTipoIdentificacionRepLegal() {
		return tipoIdentificacionRepLegal;
	}
	public void setTipoIdentificacionRepLegal(String tipoIdentificacionRepLegal) {
		this.tipoIdentificacionRepLegal = tipoIdentificacionRepLegal;
	}
	
	
	   
	   
	
	
	
	
}
