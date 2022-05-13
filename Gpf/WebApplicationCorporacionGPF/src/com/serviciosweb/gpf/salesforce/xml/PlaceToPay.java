package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "numeroTarjeta","numeroAutorizacion","tarjetaHabiente","planCredito","numeroAutBoletin"
	})
@XmlRootElement
public class PlaceToPay {
	private String numeroTarjeta;
	private String numeroAutorizacion;
	private String tarjetaHabiente;
	private String planCredito;
	@XmlElement(name = "numeroAutBoletin", required = true, nillable = true)
	private String numeroAutBoletin ="" ;

	// Getter Methods

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public String getTarjetaHabiente() {
		return tarjetaHabiente;
	}

	public String getPlanCredito() {
		return planCredito;
	}

	// Setter Methods

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public void setTarjetaHabiente(String tarjetaHabiente) {
		this.tarjetaHabiente = tarjetaHabiente;
	}

	public void setPlanCredito(String planCredito) {
		this.planCredito = planCredito;
	}

	public String getNumeroAutBoletin() {
		return numeroAutBoletin;
	}

	public void setNumeroAutBoletin(String numeroAutBoletin) {
		if(numeroAutBoletin== null)
			this.numeroAutBoletin ="";
		else
			this.numeroAutBoletin = numeroAutBoletin;
	}

}