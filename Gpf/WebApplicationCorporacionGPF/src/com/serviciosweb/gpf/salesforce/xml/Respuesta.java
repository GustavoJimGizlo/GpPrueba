package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "detalleCompra", "detalleFactura", "domicilio",
		"formasPago", "medioDescuento", "placeToPay", "sumario","direccionEnvio","estado" })
public class Respuesta {
	DetalleFactura detalleFactura;
	MedioDescuento medioDescuento;
	FormasPago formasPago;
	@XmlElement(name = "placeToPay", required = true, nillable = true)
	PlaceToPay placeToPay;
	Domicilio domicilio;
	DetalleCompra detalleCompra;
	Sumario sumario;
	DireccionEnvio direccionEnvio;
	
	String estado;
	public DetalleFactura getDetalleFactura() {
		return detalleFactura;
	}
	public void setDetalleFactura(DetalleFactura detalleFactura) {
		this.detalleFactura = detalleFactura;
	}
	public MedioDescuento getMedioDescuento() {
		return medioDescuento;
	}
	public void setMedioDescuento(MedioDescuento medioDescuento) {
		this.medioDescuento = medioDescuento;
	}
	public FormasPago getFormasPago() {
		return formasPago;
	}
	public void setFormasPago(FormasPago formasPago) {
		this.formasPago = formasPago;
	}
	public PlaceToPay getPlaceToPay() {
		return placeToPay;
	}
	public void setPlaceToPay(PlaceToPay placeToPay) {
		this.placeToPay = placeToPay;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public DetalleCompra getDetalleCompra() {
		return detalleCompra;
	}
	public void setDetalleCompra(DetalleCompra detalleCompra) {
		this.detalleCompra = detalleCompra;
	}
	public Sumario getSumario() {
		return sumario;
	}
	public void setSumario(Sumario sumario) {
		this.sumario = sumario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public DireccionEnvio getDireccionEnvio() {
		return direccionEnvio;
	}
	public void setDireccionEnvio(DireccionEnvio direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}
	
	
	 
	
	

}
