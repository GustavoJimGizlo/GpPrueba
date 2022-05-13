package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

@XmlRootElement(name = "recetagen")
public class RecetaGenericaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219935338266944043L;

	private String receta;
	private List<RecetaItemsGenericoBean> rec_items;
	private List<RecetaDiagnosticoGenericoBean> rec_diagnostico;
	private RecetaFacturacionGenericoBean rec_facturacion;
	private String rec_titular;
	private RecetaCreditoGenericoBean rec_credito;
	private String result;
	private String tipo_facturacion;

	public String getReceta() {
		return TextoUtil.fromUTF8(receta);
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public List<RecetaItemsGenericoBean> getRec_items() {
		return rec_items;
	}

	public void setRec_items(List<RecetaItemsGenericoBean> rec_items) {
		this.rec_items = rec_items;
	}

	public List<RecetaDiagnosticoGenericoBean> getRec_diagnostico() {
		return rec_diagnostico;
	}

	public void setRec_diagnostico(List<RecetaDiagnosticoGenericoBean> rec_diagnostico) {
		this.rec_diagnostico = rec_diagnostico;
	}

	public RecetaFacturacionGenericoBean getRec_facturacion() {
		return rec_facturacion;
	}

	public void setRec_facturacion(RecetaFacturacionGenericoBean rec_facturacion) {
		this.rec_facturacion = rec_facturacion;
	}

	public String getRec_titular() {
		return TextoUtil.fromUTF8(rec_titular);
	}

	public void setRec_titular(String rec_titular) {
		this.rec_titular = rec_titular;
	}

	public RecetaCreditoGenericoBean getRec_credito() {
		return rec_credito;
	}

	public void setRec_credito(RecetaCreditoGenericoBean rec_credito) {
		this.rec_credito = rec_credito;
	}

	public String getResult() {
		return TextoUtil.fromUTF8(result);
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTipo_facturacion() {
		return TextoUtil.fromUTF8(tipo_facturacion);
	}

	public void setTipo_facturacion(String tipo_facturacion) {
		this.tipo_facturacion = tipo_facturacion;
	}

}
