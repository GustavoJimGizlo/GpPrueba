/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

import java.util.Date;

/**
 *
 * @author japerezc
 */
public class ResultadoTransaccionesBean {
    private Long codigoFamacia;
    //private Long codigoDocumento;
    private String cedula;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Long codigoProducto;
    private String descripcionProducto;
    private Long cantidadCompras;
    private String especificacionTipoTrx;
    private String especificacionCantidad;
    private String fechaCompra;
    private String tipoDocumento;
    private String numeroFactura;
    private Boolean estadoTrx;
    private String mensaje;
    private Long unidadBonificacion;
    private Long unidadVenta;



    public Long getCantidadCompras() {
        return cantidadCompras;
    }

    public void setCantidadCompras(Long cantidadCompras) {
        this.cantidadCompras = cantidadCompras;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /*public Long getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }*/

    public Long getCodigoFamacia() {
        return codigoFamacia;
    }

    public void setCodigoFamacia(Long codigoFamacia) {
        this.codigoFamacia = codigoFamacia;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getEspecificacionCantidad() {
        return especificacionCantidad;
    }

    public void setEspecificacionCantidad(String especificacionCantidad) {
        this.especificacionCantidad = especificacionCantidad;
    }

    

    public String getEspecificacionTipoTrx() {
        return especificacionTipoTrx;
    }

    public void setEspecificacionTipoTrx(String especificacionTipoTrx) {
        this.especificacionTipoTrx = especificacionTipoTrx;
    }

    public Boolean getEstadoTrx() {
        return estadoTrx;
    }

    public void setEstadoTrx(Boolean estadoTrx) {
        this.estadoTrx = estadoTrx;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getUnidadBonificacion() {
        return unidadBonificacion;
    }

    public void setUnidadBonificacion(Long unidadBonificacion) {
        this.unidadBonificacion = unidadBonificacion;
    }

    public Long getUnidadVenta() {
        return unidadVenta;
    }

    public void setUnidadVenta(Long unidadVenta) {
        this.unidadVenta = unidadVenta;
    }

   
}
