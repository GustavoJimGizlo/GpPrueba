package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos;
import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(propOrder={"codigo", "respuesta", "mensaje"})


public class RecPerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7169240849483782976L;
	
	private String codigo;
	private String respuesta;
	private String mensaje;
	
	
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}




	public String getRespuesta() {
		return respuesta;
	}




	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}









	public String getMensaje() {
		return mensaje;
	}









	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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
