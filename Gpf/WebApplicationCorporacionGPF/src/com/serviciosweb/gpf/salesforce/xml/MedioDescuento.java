package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "codigo","tarjetaDescuento"
	})
@XmlRootElement
public class MedioDescuento {
	@XmlElement(name = "codigo", required = true, nillable = true)
 private String codigo ="";
	@XmlElement(name = "tarjetaDescuento", required = true, nillable = true)
 private String tarjetaDescuento="";


 // Getter Methods 

 public String getCodigo() {
  return codigo;
 }

 public String getTarjetaDescuento() {
  return tarjetaDescuento;
 }

 // Setter Methods 

 public void setCodigo(String codigo) {
	 if(codigo == null)
		 this.codigo ="";
	 else
		 this.codigo = codigo;
 }

 public void setTarjetaDescuento(String tarjetaDescuento) {
	 if(tarjetaDescuento== null)
		 this.tarjetaDescuento ="";
	 else
		 this.tarjetaDescuento = tarjetaDescuento;
 }
}
