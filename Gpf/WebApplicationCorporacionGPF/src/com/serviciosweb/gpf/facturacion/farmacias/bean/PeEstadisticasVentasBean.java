
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @date 11/07/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
@Entity
public class PeEstadisticasVentasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Column(name="FARMACIA")
	 private Integer farmacia;
	 @Column(name="ITEM")
	 private Integer item;
	 @Column(name="NUMERO_PERIODO")
	 private Integer numeroPeriodo;
	 @Column(name="VENTA")
	 private Integer venta;
	public Integer getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Integer farmacia) {
		this.farmacia = farmacia;
	}
	public Integer getItem() {
		return item;
	}
	public void setItem(Integer item) {
		this.item = item;
	}
	public Integer getNumeroPeriodo() {
		return numeroPeriodo;
	}
	public void setNumeroPeriodo(Integer numeroPeriodo) {
		this.numeroPeriodo = numeroPeriodo;
	}
	public Integer getVenta() {
		return venta;
	}
	public void setVenta(Integer venta) {
		this.venta = venta;
	}
	 
}
