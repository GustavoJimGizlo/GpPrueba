/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;



/**
 *
 * @author mftellor
 */
@Entity
public class ItemRelacionadoRecetaAbfBean {
	@Column(name = "ITEM_ALTERNATIVO")
    private Integer itemAlternativo;
	@Column(name = "CANTIDAD")
    private Integer cantidad;
	private Integer diasTratamiento=0;
	@Column(name = "CODIGO_ITEM")
    private Integer itemPrincipal;

    public Integer getItemAlternativo() {
        return itemAlternativo;
    }

    public void setItemAlternativo(Integer itemAlternativo) {
        this.itemAlternativo = itemAlternativo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getItemPrincipal() {
        return itemPrincipal;
    }

    public void setItemPrincipal(Integer itemPrincipal) {
        this.itemPrincipal = itemPrincipal;
    }

	public Integer getDiasTratamiento() {
		return diasTratamiento;
	}

	public void setDiasTratamiento(Integer diasTratamiento) {
		this.diasTratamiento = diasTratamiento;
	}

}
