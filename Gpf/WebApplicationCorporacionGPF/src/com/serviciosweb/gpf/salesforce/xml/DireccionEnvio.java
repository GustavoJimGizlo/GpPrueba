package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DireccionEnvio {
	private String address1;
	private String address2;
	private String dirCompleta;
	private String phone;
	private String latitude;
	private String longitude;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDirCompleta() {
		return dirCompleta;
	}

	public void setDirCompleta(String dirCompleta) {
		this.dirCompleta = dirCompleta;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
	

}