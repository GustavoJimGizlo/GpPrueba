
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @date 13/01/2014
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
@Entity
public class InformacionPadronBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="wcedula")
	private String wcedula="NULL";
	@Column(name="wdigito")
	private String wdigito="NULL";
	@Column(name="wnombres")
	private String wnombres="NULL";
	@Column(name="wnompro")
	private String wnompro="NULL";
	@Column(name="wnomcan")
	private String wnomcan="NULL";
	@Column(name="wnompar")
	private String wnompar="NULL";
	@Column(name="wnomzon")
	private String wnomzon="NULL";
	@Column(name="wnomrec")
	private String wnomrec="NULL";
	@Column(name="wdirrec")
	private String wdirrec="NULL";
	@Column(name="wjunta")
	private String wjunta="NULL";
	@Column(name="wsexo")
	private String wsexo="NULL";
	@Column(name="wdesjrv")
	private String wdesjrv="NULL";
	@Column(name="wjunjrv")
	private String wjunjrv="NULL";
	@Column(name="wsexjrv")
	private String wsexjrv="NULL";
	@Column(name="wnrejrv")
	private String wnrejrv="NULL";
	@Column(name="wdrejrv")
	private String wdrejrv="NULL";
	@Column(name="identificacion")
	private String identificacion="NULL";
	
	@Column(name="WNOMCIR")
	private String wnomcir="NULL";
	@Column(name="WCODREC") 
	private String wcodrec="NULL";
	
	public String getWcedula() {
		return wcedula;
	}
	public void setWcedula(String wcedula) {
		this.wcedula = wcedula;
	}
	public String getWdigito() {
		return wdigito;
	}
	public void setWdigito(String wdigito) {
		this.wdigito = wdigito;
	}
	public String getWnombres() {
		return wnombres;
	}
	public void setWnombres(String wnombres) {
		this.wnombres = wnombres;
	}
	public String getWnompro() {
		return wnompro;
	}
	public void setWnompro(String wnompro) {
		this.wnompro = wnompro;
	}
	public String getWnomcan() {
		return wnomcan;
	}
	public void setWnomcan(String wnomcan) {
		this.wnomcan = wnomcan;
	}
	public String getWnompar() {
		return wnompar;
	}
	public void setWnompar(String wnompar) {
		this.wnompar = wnompar;
	}
	public String getWnomzon() {
		return wnomzon;
	}
	public void setWnomzon(String wnomzon) {
		this.wnomzon = wnomzon;
	}
	public String getWnomrec() {
		return wnomrec;
	}
	public void setWnomrec(String wnomrec) {
		this.wnomrec = wnomrec;
	}
	public String getWdirrec() {
		return wdirrec;
	}
	public void setWdirrec(String wdirrec) {
		this.wdirrec = wdirrec;
	}
	public String getWjunta() {
		return wjunta;
	}
	public void setWjunta(String wjunta) {
		this.wjunta = wjunta;
	}
	public String getWsexo() {
		return wsexo;
	}
	public void setWsexo(String wsexo) {
		this.wsexo = wsexo;
	}
	public String getWdesjrv() {
		return wdesjrv;
	}
	public void setWdesjrv(String wdesjrv) {
		this.wdesjrv = wdesjrv;
	}
	public String getWjunjrv() {
		return wjunjrv;
	}
	public void setWjunjrv(String wjunjrv) {
		this.wjunjrv = wjunjrv;
	}
	public String getWsexjrv() {
		return wsexjrv;
	}
	public void setWsexjrv(String wsexjrv) {
		this.wsexjrv = wsexjrv;
	}
	public String getWnrejrv() {
		return wnrejrv;
	}
	public void setWnrejrv(String wnrejrv) {
		this.wnrejrv = wnrejrv;
	}
	public String getWdrejrv() {
		return wdrejrv;
	}
	public void setWdrejrv(String wdrejrv) {
		this.wdrejrv = wdrejrv;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getWnomcir() {
		return wnomcir;
	}
	public void setWnomcir(String wnomcir) {
		this.wnomcir = wnomcir;
	}
	public String getWcodrec() {
		return wcodrec;
	}
	public void setWcodrec(String wcodrec) {
		this.wcodrec = wcodrec;
	}
	
	

}
