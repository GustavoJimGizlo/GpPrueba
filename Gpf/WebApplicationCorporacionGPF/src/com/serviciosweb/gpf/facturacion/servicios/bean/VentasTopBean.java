/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

import java.io.Serializable;

/**
 *
 * @author mftellor
 */
public class VentasTopBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer total;
    public String nombre;
    public String presentacion;
    public String codigoItem;

    public VentasTopBean(String codigoItem,Integer total,String nombre,String presentacion){
        this.total=total;
        this.nombre=nombre;
        this.presentacion=presentacion;
        this.codigoItem=codigoItem;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

	public String getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(String codigoItem) {
		this.codigoItem = codigoItem;
	}
    

}
