package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta;

import java.io.Serializable;

public class RecetaCreditoGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311012859024356517L;

	private String de_celular;
	private String de_mail;
	private String cr_numtrans;
	private String cr_autnumcont;
	private String cr_autnumplan;
	private String de_autcodigo;
	private String ti_codigo;
	private String cr_autnumdiag;
	private String cr_obs;
	private String cr_fecaten;
	private String cr_fecexp;

	public String getDe_celular() {
		return de_celular;
	}

	public void setDe_celular(String de_celular) {
		this.de_celular = de_celular;
	}

	public String getDe_mail() {
		return de_mail;
	}

	public void setDe_mail(String de_mail) {
		this.de_mail = de_mail;
	}

	public String getCr_numtrans() {
		return cr_numtrans;
	}

	public void setCr_numtrans(String cr_numtrans) {
		this.cr_numtrans = cr_numtrans;
	}

	public String getCr_autnumcont() {
		return cr_autnumcont;
	}

	public void setCr_autnumcont(String cr_autnumcont) {
		this.cr_autnumcont = cr_autnumcont;
	}

	public String getCr_autnumplan() {
		return cr_autnumplan;
	}

	public void setCr_autnumplan(String cr_autnumplan) {
		this.cr_autnumplan = cr_autnumplan;
	}

	public String getDe_autcodigo() {
		return de_autcodigo;
	}

	public void setDe_autcodigo(String de_autcodigo) {
		this.de_autcodigo = de_autcodigo;
	}

	public String getTi_codigo() {
		return ti_codigo;
	}

	public void setTi_codigo(String ti_codigo) {
		this.ti_codigo = ti_codigo;
	}

	public String getCr_autnumdiag() {
		return cr_autnumdiag;
	}

	public void setCr_autnumdiag(String cr_autnumdiag) {
		this.cr_autnumdiag = cr_autnumdiag;
	}

	public String getCr_obs() {
		return cr_obs;
	}

	public void setCr_obs(String cr_obs) {
		this.cr_obs = cr_obs;
	}

	public String getCr_fecaten() {
		return cr_fecaten;
	}

	public void setCr_fecaten(String cr_fecaten) {
		this.cr_fecaten = cr_fecaten;
	}

	public String getCr_fecexp() {
		return cr_fecexp;
	}

	public void setCr_fecexp(String cr_fecexp) {
		this.cr_fecexp = cr_fecexp;
	}

}
