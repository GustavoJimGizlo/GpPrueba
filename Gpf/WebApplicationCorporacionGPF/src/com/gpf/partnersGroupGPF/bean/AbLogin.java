package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;
import java.util.List;


public class AbLogin implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;
	private List<AbRoles> roles;
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public List<AbRoles> getRoles() {
		return roles;
	}
	public void setRoles(List<AbRoles> roles) {
		this.roles = roles;
	}
	
	
	 

}
