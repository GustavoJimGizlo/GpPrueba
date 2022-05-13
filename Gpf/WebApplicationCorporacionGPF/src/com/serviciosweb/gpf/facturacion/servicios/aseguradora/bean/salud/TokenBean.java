package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.salud;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenBean implements Serializable {

	private static final long serialVersionUID = 2312697102142019221L;
	
	private String access_token;
	private String token_type;
	private String expires_in;
	private String refresh_token;
	private String audience;
	private String client_id;
	private String user_data;
	private String issued;
	private String expires;
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public String getAudience() {
		return audience;
	}
	
	public void setAudience(String audience) {
		this.audience = audience;
	}
	
	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
	public String getExpires() {
		return expires;
	}
	
	public void setExpires(String expires) {
		this.expires = expires;
	}
	
	public String getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	public String getIssued() {
		return issued;
	}
	
	public void setIssued(String issued) {
		this.issued = issued;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	public String getToken_type() {
		return token_type;
	}
	
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	
	public String getUser_data() {
		return user_data;
	}
	
	public void setUser_data(String user_data) {
		this.user_data = user_data;
	}
	
}
