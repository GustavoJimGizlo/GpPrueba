/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author mftellor
 */
@Entity
public class ItemRecetaAbfBean {
	@Column(name = "CODIGO_ITEM")
    private Integer item;
	@Column(name = "CANTIDAD")
    private Integer cantidad;
	@Column(name = "DIAS_TRATAMIENTO")
    private Integer diasTratamiento;	
	private Integer itemPrincipal=0;
	
    private List<ItemRelacionadoRecetaAbfBean> itemsRelacionadoRecetaAbf;
    
    public ItemRecetaAbfBean(){
        
    }
    public ItemRecetaAbfBean(Integer item,Integer cantidad,Integer diasTratamiento){
        this.item=item;
        this.cantidad=cantidad;
        this.diasTratamiento=diasTratamiento;
    }
    /**
     *@descricion Código del producto en base al catálogo de productos proporcionado por ABF.
     */
    public Integer getItem() {
        return item;
    }
    /**
     *@descricion Código del producto en base al catálogo de productos proporcionado por ABF.
     */
    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getDiasTratamiento() {
        return diasTratamiento;
    }

    public void setDiasTratamiento(Integer diasTratamiento) {
        this.diasTratamiento = diasTratamiento;
    }

    public List<ItemRelacionadoRecetaAbfBean> getItemsRelacionadoRecetaAbf() {
    	if(itemsRelacionadoRecetaAbf==null)
    		itemsRelacionadoRecetaAbf=new ArrayList<ItemRelacionadoRecetaAbfBean>();
        return itemsRelacionadoRecetaAbf;
    }

    public void setItemsRelacionadoRecetaAbf(List<ItemRelacionadoRecetaAbfBean> itemsRelacionadoRecetaAbf) {
        this.itemsRelacionadoRecetaAbf = itemsRelacionadoRecetaAbf;
    }
	public Integer getItemPrincipal() {
		return itemPrincipal;
	}
	public void setItemPrincipal(Integer itemPrincipal) {
		this.itemPrincipal = itemPrincipal;
	}

    
}
