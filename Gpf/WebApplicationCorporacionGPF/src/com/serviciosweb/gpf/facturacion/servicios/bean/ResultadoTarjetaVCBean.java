/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

/**
 *
 * @author wrhernandezj
 */
public class ResultadoTarjetaVCBean implements java.io.Serializable {
    private java.lang.Boolean estadoTrx;
    private java.lang.String mensaje;
    private java.lang.String primerNombre;
    private java.lang.String segundoNombre;
    private java.lang.String primerApellido;
    private java.lang.String segundoApellido;
    private java.lang.String tienePlan;

    public ResultadoTarjetaVCBean() {
    }

    public ResultadoTarjetaVCBean(
           java.lang.Boolean estadoTrx,
           java.lang.String mensaje,
           java.lang.String primerNombre,
           java.lang.String segundoNombre,
           java.lang.String primerApellido,
           java.lang.String segundoApellido,
           java.lang.String tienePlan) {
           this.estadoTrx = estadoTrx;
           this.mensaje = mensaje;
           this.primerNombre = primerNombre;
           this.segundoNombre = segundoNombre;
           this.primerApellido = primerApellido;
           this.segundoApellido = segundoApellido;
           this.tienePlan = tienePlan;
    }

    public Boolean getEstadoTrx() {
        return estadoTrx;
    }

    public void setEstadoTrx(Boolean estadoTrx) {
        this.estadoTrx = estadoTrx;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTienePlan() {
        return tienePlan;
    }

    public void setTienePlan(String tienePlan) {
        this.tienePlan = tienePlan;
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



}

