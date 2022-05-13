package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatosGeneralesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4113237577149836633L;

	private List<DatosPersonalesBean> rec_titular;

	private ResultadosBean result;

	private String tipo_facturacion;

	
	
	public List<DatosPersonalesBean> getRec_titular() {
		return rec_titular;
	}

	public void setRec_titular(List<DatosPersonalesBean> rec_titular) {
		this.rec_titular = rec_titular;
	}

	public ResultadosBean getResult() {
		return result;
	}

	public void setResult(ResultadosBean result) {
		this.result = result;
	}

	public String getTipo_facturacion() {
		return tipo_facturacion;
	}

	public void setTipo_facturacion(String tipo_facturacion) {
		this.tipo_facturacion = tipo_facturacion;
	}

}
