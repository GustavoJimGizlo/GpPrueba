package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.transaccion;

import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosGeneralesGenericoBean;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

@XmlRootElement(name = "transaccion")
public class TransaccionBean implements Serializable{

	private static final long serialVersionUID = 5527100684499783452L;
	
	private int httpStatus;
	private String mensaje;	
	private Object respuesta;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public Object getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Object respuesta) {
		this.respuesta = respuesta;
	}


	public Respuesta formatearRespuestaConsultarDatos() throws Exception{
		
		Respuesta respuestaConsultarDatos = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(Respuesta.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		respuestaConsultarDatos = (Respuesta) jaxbUnmarshaller
				.unmarshal((ElementNSImpl) respuesta);
		
		return respuestaConsultarDatos;
	}
	
	public String formatearRespuestaGenerica() throws Exception {
		
		String respuestaGenerica = null;
		ElementNSImpl a = (ElementNSImpl) respuesta;
		respuestaGenerica = a.getChildNodes().item(0).getNodeValue().trim();
		
		return respuestaGenerica;
	}

	@XmlRootElement(name = "respuesta")
	public static class Respuesta implements Serializable{

		private static final long serialVersionUID = -6820042763085775971L;
		private DatosGeneralesGenericoBean datosGeneralesBean;

		public DatosGeneralesGenericoBean getDatosGeneralesBean() {
			return datosGeneralesBean;
		}

		public void setDatosGeneralesBean(DatosGeneralesGenericoBean datosGeneralesBean) {
			this.datosGeneralesBean = datosGeneralesBean;
		}
	}

}
