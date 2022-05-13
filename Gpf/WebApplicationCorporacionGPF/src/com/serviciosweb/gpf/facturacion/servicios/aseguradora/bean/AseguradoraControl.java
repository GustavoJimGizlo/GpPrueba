/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean;

import java.io.Serializable;

/**
 *
 * @author mtorres
 */
public class AseguradoraControl implements Serializable {

	private Integer codigo;
	private Integer codigoAseguradora;
	private String consulta;
	private String paqueteClase;
	
	private String paqueteContructor;
	private String metodoRetorno;
	private String url;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoAseguradora() {
		return codigoAseguradora;
	}

	public void setCodigoAseguradora(Integer codigoAseguradora) {
		this.codigoAseguradora = codigoAseguradora;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getPaqueteClase() {
		return paqueteClase;
	}

	public void setPaqueteClase(String paqueteClase) {
		this.paqueteClase = paqueteClase;
	}

	public String getPaqueteContructor() {
		return paqueteContructor;
	}

	public void setPaqueteContructor(String paqueteContructor) {
		this.paqueteContructor = paqueteContructor;
	}
	
	public String getMetodoRetorno() {
		return metodoRetorno;
	}

	public void setMetodoRetorno(String metodoRetorno) {
		this.metodoRetorno = metodoRetorno;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
