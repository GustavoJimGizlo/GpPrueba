/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;

/**
 *
 * @author mftellor
 */
public class DataPmcBean implements Serializable {
    private String fecha;
    private String documentoVenta;
    private String nombre;
    private String descripcion;
    private String nombreFarmacia;
    private String cancelada;
    private String tipoMvto;
    private String descripcionMvto;
    private String clasificacionMvto;
    private String tipo;
    private String cajas;
    private String cajaBonifica="0";
    private String farmacia;
    private String empresa;
    private String cantidad="0";
    private String unidades="0";


    
    private String cajasAcumuladas="0";
    private String cajasResiduo="0";
    
    private String ubicacion="0";

    public DataPmcBean(){};
    public DataPmcBean(Object valor){
        this.cajaBonifica=null;
        this.cantidad=null;
        this.unidades=null;
    }

    public String getCajaBonifica() {
        return cajaBonifica;
    }

    public void setCajaBonifica(String cajaBonifica) {
        this.cajaBonifica = cajaBonifica;
    }

    public String getCajas() {
        return cajas;
    }

    public void setCajas(String cajas) {
        this.cajas = cajas;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getClasificacionMvto() {
        return clasificacionMvto;
    }

    public void setClasificacionMvto(String clasificacionMvto) {
        this.clasificacionMvto = clasificacionMvto;
    }

    public String getDescripcionMvto() {
        return descripcionMvto;
    }

    public void setDescripcionMvto(String descripcionMvto) {
        this.descripcionMvto = descripcionMvto;
    }

    public String getDocumentoVenta() {
        return documentoVenta;
    }

    public void setDocumentoVenta(String documentoVenta) {
        this.documentoVenta = documentoVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoMvto() {
        return tipoMvto;
    }

    public void setTipoMvto(String tipoMvto) {
        this.tipoMvto = tipoMvto;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getCajasAcumuladas() {
        return cajasAcumuladas;
    }

    public void setCajasAcumuladas(String cajasAcumuladas) {
        this.cajasAcumuladas = cajasAcumuladas;
    }

    public String getCajasResiduo() {
        return cajasResiduo;
    }

    public void setCajasResiduo(String cajasResiduo) {
        this.cajasResiduo = cajasResiduo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
