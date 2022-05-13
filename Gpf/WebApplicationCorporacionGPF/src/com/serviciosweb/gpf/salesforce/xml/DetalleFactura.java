package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DetalleFactura {
	Persona PersonaObject;
	private String pdv;
	Direccion DireccionObject;
	Contactos contactos;

	// Getter Methods

	public Persona getPersona() {
		return PersonaObject;
	}

	public String getPdv() {
		return pdv;
	}

	public Direccion getDireccion() {
		return DireccionObject;
	}

	// Setter Methods

	public void setPersona(Persona personaObject) {
		this.PersonaObject = personaObject;
	}

	public void setPdv(String pdv) {
		this.pdv = pdv;
	}

	public void setDireccion(Direccion direccionObject) {
		this.DireccionObject = direccionObject;
	}

	public Contactos getContactos() {
		return contactos;
	}

	public void setContactos(Contactos contactos) {
		this.contactos = contactos;
	}

}