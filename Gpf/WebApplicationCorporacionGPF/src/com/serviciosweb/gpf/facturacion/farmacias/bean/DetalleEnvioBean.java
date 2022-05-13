/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.farmacias.bean;

/**
 *
 * @author mftellor
 */
public class DetalleEnvioBean implements java.io.Serializable {
	 private  java.lang.String item;
	 private  java.lang.String bodega;
	 private  java.lang.String documentoBodega;
	 private  java.lang.String cantidad;

    public DetalleEnvioBean()
    {
    }

    public DetalleEnvioBean(
            java.lang.String item,
		    java.lang.String bodega,
		    java.lang.String documentoBodega,
		    java.lang.String cantidad ) {
	        this.item = item;
	        this.bodega = bodega;
	        this.documentoBodega = documentoBodega;
	        this.cantidad = cantidad;
	        }


	/**
	 * @return Returns the bodega.
	 */
	public java.lang.String getBodega() {
		return bodega;
	}
	/**
	 * @param bodega The bodega to set.
	 */
	public void setBodega(java.lang.String bodega) {
		this.bodega = bodega;
	}
	/**
	 * @return Returns the cantidad.
	 */
	public java.lang.String getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad The cantidad to set.
	 */
	public void setCantidad(java.lang.String cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return Returns the documentoBodega.
	 */
	public java.lang.String getDocumentoBodega() {
		return documentoBodega;
	}
	/**
	 * @param documentoBodega The documentoBodega to set.
	 */
	public void setDocumentoBodega(java.lang.String documentoBodega) {
		this.documentoBodega = documentoBodega;
	}
	/**
	 * @return Returns the item.
	 */
	public java.lang.String getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(java.lang.String item) {
		this.item = item;
	}
}