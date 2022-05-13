/**
 * @date 17/12/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author mftellor
 *
 */
@Entity
public class VentasConsolidadasMDVBean {
@Column(name="NOMBRE")	
public String farmacia;
@Column(name="FECHA_ULTIMO_MOVIMIENTO")
public String fechaUltimoMvimiento;
@Column(name="VALOR_VITALCARD")
public Double valorVitalCard;
@Column(name="VALOR_PERFUMERIA")
public Double valorPerfumeria;
@Column(name="VALOR_ESPECIALIDAD")
public Double valorEspecialidad;
@Column(name="AYUDA_COMUNITARIA")
public Double valorAyudaComunitaria;
@Column(name="VALOR_SERVICIOS")

public Double valorServicios;
public Double total;
@Column(name="totalTransacciones")
public Integer totalTransacciones;

public String getFarmacia() {
	return farmacia;
}
public void setFarmacia(String farmacia) {
	this.farmacia = farmacia;
}
public String getFechaUltimoMvimiento() {
	return fechaUltimoMvimiento;
}
public void setFechaUltimoMvimiento(String fechaUltimoMvimiento) {
	this.fechaUltimoMvimiento = fechaUltimoMvimiento;
}
public Double getValorVitalCard() {
	return valorVitalCard!=null?valorVitalCard:0;
}
public void setValorVitalCard(Double valorVitalCard) {
	this.valorVitalCard = valorVitalCard;
}
public Double getValorPerfumeria() {
	return valorPerfumeria!=null?valorPerfumeria:0;
}
public void setValorPerfumeria(Double valorPerfumeria) {
	this.valorPerfumeria = valorPerfumeria;
}
public Double getValorEspecialidad() {
	return valorEspecialidad!=null?valorEspecialidad:0;
}
public void setValorEspecialidad(Double valorEspecialidad) {
	this.valorEspecialidad = valorEspecialidad;
}
public Double getValorAyudaComunitaria() {
	return valorAyudaComunitaria!=null?valorAyudaComunitaria:0;
}
public void setValorAyudaComunitaria(Double valorAyudaComunitaria) {
	this.valorAyudaComunitaria = valorAyudaComunitaria;
}
public Double getValorServicios() {
	return valorServicios!=null?valorServicios:0;
}
public void setValorServicios(Double valorServicios) {
	this.valorServicios = valorServicios;
}
public Double getTotal() {
	
	total= this.getValorEspecialidad()+this.getValorPerfumeria()+this.getValorServicios();	
	total=Math.floor(total * 100) / 100;
	
	return total;
}
public void setTotal(Double total) {
	this.total = total;
}
public Integer getTotalTransacciones() {
	return totalTransacciones;
}
public void setTotalTransacciones(Integer totalTransacciones) {
	this.totalTransacciones = totalTransacciones;
}



}
