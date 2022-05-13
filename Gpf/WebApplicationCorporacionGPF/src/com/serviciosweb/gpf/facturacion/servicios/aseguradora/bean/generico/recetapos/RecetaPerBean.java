package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recetaper")
public class RecetaPerBean implements Serializable {

	//private static final long serialVersionUID = -5219935338266944043L;
	
	
	
	private String codaseguradora;
	private List<RecPerBean> rec_items;
	private List<DiaPerBean> diagnosticos;
	
		


	public String getCodaseguradora() {
		return codaseguradora;
	}

	public void setCodaseguradora(String codaseguradora) {
		this.codaseguradora = codaseguradora;
	}

	public List<RecPerBean> getRec_items() {
		return rec_items;
	}
















	public void setRec_items(List<RecPerBean> rec_items) {
		this.rec_items = rec_items;
	}
















	public List<DiaPerBean> getDiagnosticos() {
		return diagnosticos;
	}
















	public void setDiagnosticos(List<DiaPerBean> diagnosticos) {
		this.diagnosticos = diagnosticos;
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
