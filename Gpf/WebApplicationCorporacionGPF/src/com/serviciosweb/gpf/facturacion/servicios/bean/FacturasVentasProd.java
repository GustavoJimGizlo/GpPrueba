/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

/**
 *
 * @author mftellor
 */
public class FacturasVentasProd {
 private String farmacia;
 private String documentoVenta;
 private String ventaTotalFactura;
 private String cliente;
 private String identificacion;
 private String fecha;
 private String item;
 private String nombre;
 private String presentacion;
 private String cantidad;
 private String venta;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDocumentoVenta() {
        return documentoVenta;
    }

    public void setDocumentoVenta(String documento_venta) {
        this.documentoVenta = documento_venta;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getVentaTotalFactura() {
        return ventaTotalFactura;
    }

    public void setVentaTotalFactura(String venta_total_factura) {
        this.ventaTotalFactura = venta_total_factura;
    }
  
}
