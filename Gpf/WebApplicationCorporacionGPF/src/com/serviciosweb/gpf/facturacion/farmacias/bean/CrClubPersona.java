package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
@Entity
public class CrClubPersona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  @Column(name = "CP_CHAR1")
	  private String cpChar1;
	  @Column(name = "ACTIVA")
	  private String activa;
	  @Column(name = "CP_VAR1")
	  private String cpVar1;
	  @Column(name = "CP_NUM4")
	  private Integer cpNum4;
	  @Column(name = "CP_CHAR5")
	  private String cpChar5;
	  @Column(name = "USUARIO_AFILIA")
	  private String usuarioAfilia;
	  @Column(name = "CP_VAR6")
	  private String cpVar6;
	  @Column(name = "CP_VAR2")
	  private String cpVar2;
	  @Column(name = "USUARIO_MODIFICA")
	  private String usuarioModifica;
	  @Column(name = "USUARIO_DESAFILIA")
	  private String usuarioDesafilia;
	  @Column(name = "CP_VAR3")
	  private String cpVar3;
	  @Column(name = "CP_CHAR2")
	  private String cpChar2;
	  @Column(name = "CP_VAR5")
	  private String cpVar5;
	  @Column(name = "CP_DATE5")
	  private Date cpDate5;
	  @Column(name = "CP_DATE1")
	  private Date cpDate1;
	  @Column(name = "OBSERVACIONES")
	  private String observaciones;
	  @Column(name = "CP_VAR4")
	  private String cpVar4;
	  @Column(name = "CP_NUM3")
	  private Integer cpNum3;
	  @Column(name = "CP_NUM2")
	  private Integer cpNum2;
	  @Column(name = "CP_DATE4")
	  private Date cpDate4;
	  @Column(name = "FECHA_MODIFICA")
	  private Date fechaModifica;
	  @Column(name = "CP_CHAR4")
	  private String cpChar4;
	  @Column(name = "CP_DATE2")
	  private Date cpDate2;
	  @Column(name = "CP_NUM5")
	  private Integer cpNum5;
	  @Column(name = "CP_DATE3")
	  private Date cpDate3;
	  @Column(name = "PERSONA_DEPENDIENTE")
	  private Integer personaDependiente;
	  @Column(name = "FECHA_AFILIACION")
	  private Date fechaAfiliacion;
	  @Column(name = "CP_NUM1")
	  private Integer cpNum1;
	  @Column(name = "FECHA_DESAFILIA")
	  private Date fechaDesafilia;
	  @Column(name = "CP_CHAR3")
	  private String cpChar3;
	  @Column(name = "PERSONA_AFILIADA")
	  private Integer personaAfiliada;
	  @Column(name = "CLUB")
	  private String club;
	  @Column(name = "FARMACIA")
	  private Integer farmacia;
	  @Column(name = "TUTOR")
	  private String tutor;
	  @Column(name = "TELEFONO")
	  private String telefono;
	  @Column(name = "CELULAR")
	  private String celular;
	  @Column(name = "IDENTIFICACION")
	  private String identificacion;
	  @Column(name = "BARRIO")
	  private Integer barrio;
	  @Column(name = "PROVINCIA")
	  private Integer provincia;
	  @Column(name = "NUMERO")
	  private String numero;
	  @Column(name = "HORA_CONTACTO1")
	  private Integer horaContacto1;
	  @Column(name = "PRINCIPAL")
	  private String principal;
	  @Column(name = "INTERSECCION")
	  private String interseccion;
	  @Column(name = "HORA_CONTACTO2")
	  private Integer horaContacto2;
	  @Column(name = "TIPO_TELEFONO")
	  private String tipo_telefono;
	  @Column(name = "CIUDAD")
	  private Integer ciudad;
	public String getCpChar1() {
		return cpChar1;
	}
	public void setCpChar1(String cpChar1) {
		this.cpChar1 = cpChar1;
	}
	public String getActiva() {
		return activa;
	}
	public void setActiva(String activa) {
		this.activa = activa;
	}
	public String getCpVar1() {
		return cpVar1;
	}
	public void setCpVar1(String cpVar1) {
		this.cpVar1 = cpVar1;
	}
	public Integer getCpNum4() {
		return cpNum4;
	}
	public void setCpNum4(Integer cpNum4) {
		this.cpNum4 = cpNum4;
	}
	public String getCpChar5() {
		return cpChar5;
	}
	public void setCpChar5(String cpChar5) {
		this.cpChar5 = cpChar5;
	}
	public String getUsuarioAfilia() {
		return usuarioAfilia;
	}
	public void setUsuarioAfilia(String usuarioAfilia) {
		this.usuarioAfilia = usuarioAfilia;
	}
	public String getCpVar6() {
		return cpVar6;
	}
	public void setCpVar6(String cpVar6) {
		this.cpVar6 = cpVar6;
	}
	public String getCpVar2() {
		return cpVar2;
	}
	public void setCpVar2(String cpVar2) {
		this.cpVar2 = cpVar2;
	}
	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public String getUsuarioDesafilia() {
		return usuarioDesafilia;
	}
	public void setUsuarioDesafilia(String usuarioDesafilia) {
		this.usuarioDesafilia = usuarioDesafilia;
	}
	public String getCpVar3() {
		return cpVar3;
	}
	public void setCpVar3(String cpVar3) {
		this.cpVar3 = cpVar3;
	}
	public String getCpChar2() {
		return cpChar2;
	}
	public void setCpChar2(String cpChar2) {
		this.cpChar2 = cpChar2;
	}
	public String getCpVar5() {
		return cpVar5;
	}
	public void setCpVar5(String cpVar5) {
		this.cpVar5 = cpVar5;
	}
	public Date getCpDate5() {
		return cpDate5;
	}
	public void setCpDate5(Date cpDate5) {
		this.cpDate5 = cpDate5;
	}
	public Date getCpDate1() {
		return cpDate1;
	}
	public void setCpDate1(Date cpDate1) {
		this.cpDate1 = cpDate1;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCpVar4() {
		return cpVar4;
	}
	public void setCpVar4(String cpVar4) {
		this.cpVar4 = cpVar4;
	}
	public Integer getCpNum3() {
		return cpNum3;
	}
	public void setCpNum3(Integer cpNum3) {
		this.cpNum3 = cpNum3;
	}
	public Integer getCpNum2() {
		return cpNum2;
	}
	public void setCpNum2(Integer cpNum2) {
		this.cpNum2 = cpNum2;
	}
	public Date getCpDate4() {
		return cpDate4;
	}
	public void setCpDate4(Date cpDate4) {
		this.cpDate4 = cpDate4;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	public String getCpChar4() {
		return cpChar4;
	}
	public void setCpChar4(String cpChar4) {
		this.cpChar4 = cpChar4;
	}
	public Date getCpDate2() {
		return cpDate2;
	}
	public void setCpDate2(Date cpDate2) {
		this.cpDate2 = cpDate2;
	}
	public Integer getCpNum5() {
		return cpNum5;
	}
	public void setCpNum5(Integer cpNum5) {
		this.cpNum5 = cpNum5;
	}
	public Date getCpDate3() {
		return cpDate3;
	}
	public void setCpDate3(Date cpDate3) {
		this.cpDate3 = cpDate3;
	}
	public Integer getPersonaDependiente() {
		return personaDependiente;
	}
	public void setPersonaDependiente(Integer personaDependiente) {
		this.personaDependiente = personaDependiente;
	}
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}
	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}
	public Integer getCpNum1() {
		return cpNum1;
	}
	public void setCpNum1(Integer cpNum1) {
		this.cpNum1 = cpNum1;
	}
	public Date getFechaDesafilia() {
		return fechaDesafilia;
	}
	public void setFechaDesafilia(Date fechaDesafilia) {
		this.fechaDesafilia = fechaDesafilia;
	}
	public String getCpChar3() {
		return cpChar3;
	}
	public void setCpChar3(String cpChar3) {
		this.cpChar3 = cpChar3;
	}
	public Integer getPersonaAfiliada() {
		return personaAfiliada;
	}
	public void setPersonaAfiliada(Integer personaAfiliada) {
		this.personaAfiliada = personaAfiliada;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	public Integer getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Integer farmacia) {
		this.farmacia = farmacia;
	}
	public String getTutor() {
		return tutor;
	}
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public Integer getBarrio() {
		return barrio;
	}
	public void setBarrio(Integer barrio) {
		this.barrio = barrio;
	}
	public Integer getProvincia() {
		return provincia;
	}
	public void setProvincia(Integer provincia) {
		this.provincia = provincia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Integer getHoraContacto1() {
		return horaContacto1;
	}
	public void setHoraContacto1(Integer horaContacto1) {
		this.horaContacto1 = horaContacto1;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterseccion() {
		return interseccion;
	}
	public void setInterseccion(String interseccion) {
		this.interseccion = interseccion;
	}
	public Integer getHoraContacto2() {
		return horaContacto2;
	}
	public void setHoraContacto2(Integer horaContacto2) {
		this.horaContacto2 = horaContacto2;
	}
	public String getTipo_telefono() {
		return tipo_telefono;
	}
	public void setTipo_telefono(String tipo_telefono) {
		this.tipo_telefono = tipo_telefono;
	}
	public Integer getCiudad() {
		return ciudad;
	}
	public void setCiudad(Integer ciudad) {
		this.ciudad = ciudad;
	}
	
}
