package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta;

import java.io.Serializable;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;

public class RecetaDiagnosticoGenericoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7482241349871283210L;

	private String diag_codigo;
	private String diag_nombre_diagnostico;

	public String getDiag_codigo() {
		return TextoUtil.fromUTF8(diag_codigo);
	}

	public void setDiag_codigo(String diag_codigo) {
		this.diag_codigo = TextoUtil.fromUTF8(diag_codigo);
	}

	public String getDiag_nombre_diagnostico() {
		return TextoUtil.fromUTF8(diag_nombre_diagnostico);
	}

	public void setDiag_nombre_diagnostico(String diag_nombre_diagnostico) {
		this.diag_nombre_diagnostico = TextoUtil.fromUTF8(diag_nombre_diagnostico);
	}
}
