package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "datosGeneralesBean")
public class DatosGeneralesGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4113237577149836633L;

	private List<DatosTitularesGenericoBean> rec_titular;

	private ResultGenericoBean result;
	
	private String tipo_facturacion;

	public List<DatosTitularesGenericoBean> getRec_titular() {
		return rec_titular;
	}

	public void setRec_titular(List<DatosTitularesGenericoBean> rec_titular) {
		this.rec_titular = rec_titular;
	}

	public ResultGenericoBean getResult() {
		return result;
	}

	public void setResult(ResultGenericoBean result) {
		this.result = result;
	}

	public String getTipo_facturacion() {
		return tipo_facturacion;
	}

	public void setTipo_facturacion(String tipo_facturacion) {
		this.tipo_facturacion = tipo_facturacion;
	}

	
	 

}
