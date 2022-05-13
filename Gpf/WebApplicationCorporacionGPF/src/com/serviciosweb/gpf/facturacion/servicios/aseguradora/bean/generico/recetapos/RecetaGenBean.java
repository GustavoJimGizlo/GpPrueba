package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recetagen")
public class RecetaGenBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219935338266944043L;

	private String receta;
	private List<RecItemsBean> rec_items;
	private List<RecDiagnosticoBean> rec_diagnostico;
	private RecFacturacionBean rec_facturacion;
	private String rec_titular;
	private RecCreditoBean rec_credito;
	private String result;
	private String tipo_facturacion;

	public String getReceta() {
		return fromUTF8(receta);
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public List<RecItemsBean> getRec_items() {
		return rec_items;
	}

	public void setRec_items(List<RecItemsBean> rec_items) {
		this.rec_items = rec_items;
	}

	public List<RecDiagnosticoBean> getRec_diagnostico() {
		return rec_diagnostico;
	}

	public void setRec_diagnostico(List<RecDiagnosticoBean> rec_diagnostico) {
		this.rec_diagnostico = rec_diagnostico;
	}

	public RecFacturacionBean getRec_facturacion() {
		return rec_facturacion;
	}

	public void setRec_facturacion(RecFacturacionBean rec_facturacion) {
		this.rec_facturacion = rec_facturacion;
	}

	public String getRec_titular() {
		return fromUTF8(rec_titular);
	}

	public void setRec_titular(String rec_titular) {
		this.rec_titular = rec_titular;
	}

	public RecCreditoBean getRec_credito() {
		return rec_credito;
	}

	public void setRec_credito(RecCreditoBean rec_credito) {
		this.rec_credito = rec_credito;
	}

	public String getResult() {
		return fromUTF8(result);
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTipo_facturacion() {
		return fromUTF8(tipo_facturacion);
	}

	public void setTipo_facturacion(String tipo_facturacion) {
		this.tipo_facturacion = tipo_facturacion;
	}
	
	
	public static String fromUTF8(String cadenaRevisar) {
		// return cadenaRevisar;
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã¡");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã©");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã­");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã³");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãº");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã±");

		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ¡", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ©", "Ã‰");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ­", "Ã�");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ³", "Ã“");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂº", "Ãš");
		cadenaRevisar = cadenaRevisar.replace("ÃƒÂ±", "Ã‘");
		return cadenaRevisar;
	}

}
