package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan;

import java.io.Serializable;

public class PlanesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6907967486369784742L;

	private String contrato;
	private String numrenov;
	private String codsuc;
	private String codneg;
	private String tope;
	private String codpro;
	private String planm;
	private String codgrupo;
	private String autnumcont;
	private String codplan;
	private String vad_A;
	private String vad_B;

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getNumrenov() {
		return numrenov;
	}

	public void setNumrenov(String numrenov) {
		this.numrenov = numrenov;
	}

	public String getCodsuc() {
		return codsuc;
	}

	public void setCodsuc(String codsuc) {
		this.codsuc = codsuc;
	}

	public String getCodneg() {
		return codneg;
	}

	public void setCodneg(String codneg) {
		this.codneg = codneg;
	}

	public String getTope() {
		return tope;
	}

	public void setTope(String tope) {
		this.tope = tope;
	}

	public String getCodpro() {
		return codpro;
	}

	public void setCodpro(String codpro) {
		this.codpro = codpro;
	}

	public String getPlanm() {
		return planm;
	}

	public void setPlanm(String planm) {
		this.planm = planm;
	}

	public String getCodgrupo() {
		return codgrupo;
	}

	public void setCodgrupo(String codgrupo) {
		this.codgrupo = codgrupo;
	}

	public String getAutnumcont() {
		return autnumcont;
	}

	public void setAutnumcont(String autnumcont) {
		this.autnumcont = autnumcont;
	}

	public String getCodplan() {
		return codplan;
	}

	public void setCodplan(String codplan) {
		this.codplan = codplan;
	}

	public String getVad_A() {
		return vad_A;
	}

	public void setVad_A(String vad_A) {
		this.vad_A = vad_A;
	}

	public String getVad_B() {
		return vad_B;
	}

	public void setVad_B(String vad_B) {
		this.vad_B = vad_B;
	}

}
