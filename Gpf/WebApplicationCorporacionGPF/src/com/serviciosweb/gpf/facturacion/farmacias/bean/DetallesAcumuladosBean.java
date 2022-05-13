
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @date 27/06/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
@Entity
public class DetallesAcumuladosBean {
	@Column(name="UBICACION")
    private Integer ubicacion;
	@Column(name="CAJAS_RESIDUO")
    private Integer cajasResiduo;
	@Column(name="CAJAS_ACUMULADAS")
    private Integer cajasAcumuladas;
	@Column(name="NUMERO_VECES_BONIFICA")
    private Double numeroVecesBonifica;
	@Column(name="FECHA_ACUMULA_MENSUAL")
    private String fechaAcumulaMensual;
	
	public Integer getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Integer ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Integer getCajasResiduo() {
		return cajasResiduo;
	}
	public void setCajasResiduo(Integer cajasResiduo) {
		this.cajasResiduo = cajasResiduo;
	}
	public Integer getCajasAcumuladas() {
		return cajasAcumuladas;
	}
	public void setCajasAcumuladas(Integer cajasAcumuladas) {
		this.cajasAcumuladas = cajasAcumuladas;
	}
	public Double getNumeroVecesBonifica() {
		return numeroVecesBonifica;
	}
	public void setNumeroVecesBonifica(Double numeroVecesBonifica) {
		this.numeroVecesBonifica = numeroVecesBonifica;
	}
	public String getFechaAcumulaMensual() {
		return fechaAcumulaMensual;
	}
	public void setFechaAcumulaMensual(String fechaAcumulaMensual) {
		this.fechaAcumulaMensual = fechaAcumulaMensual;
	}
    

}
