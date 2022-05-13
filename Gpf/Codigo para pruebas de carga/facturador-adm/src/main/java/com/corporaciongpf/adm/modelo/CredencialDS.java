package com.corporaciongpf.adm.modelo;


import java.io.Serializable;

/**
 * The persistent class for the Factura database table.
 * 
 */
// @Entity
public class CredencialDS implements Serializable {
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private String clave;
	
	private String databaseId;
	
	private String directorio;
	private String region;

	
	
	
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public CredencialDS() {
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	
}