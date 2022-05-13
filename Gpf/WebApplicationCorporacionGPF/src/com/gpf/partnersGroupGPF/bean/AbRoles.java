package com.gpf.partnersGroupGPF.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AbRoles {
	
	@Column(name = "codigo_rol")
	private long codRol;

	public long getCodRol() {
		return codRol;
	}

	public void setCodRol(long codRol) {
		this.codRol = codRol;
	}


	   
	
	
	

}
