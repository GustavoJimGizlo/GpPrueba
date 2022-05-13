package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana;

import java.io.Serializable;

public class RecDiagnosticoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7482241349871283210L;

	private String diag_codigo;
	private String diag_nombre_diagnostico;

	public String getDiag_codigo() {
		return fromUTF8(diag_codigo);
	}

	public void setDiag_codigo(String diag_codigo) {
		this.diag_codigo = fromUTF8(diag_codigo);
	}

	public String getDiag_nombre_diagnostico() {
		return fromUTF8(diag_nombre_diagnostico);
	}

	public void setDiag_nombre_diagnostico(String diag_nombre_diagnostico) {
		this.diag_nombre_diagnostico = fromUTF8(diag_nombre_diagnostico);
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
		
		cadenaRevisar = cadenaRevisar.replaceAll("[^\\dA-Za-z | ]", "");
		return cadenaRevisar;
	}

}
