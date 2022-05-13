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
public class DatosCedulaBean implements Serializable{
 private String nombres;
 private String fechaNacimiento;
 private String ciudadania;
 private String datosConyugue;
 private String estadoCivil;
 private String digitoVerificador;
/*
    public DatosCedulaBean(){
        this.nombres="CEDULA INCORRECTA";
        this.fechaNacimiento="CEDULA INCORRECTA";
        this.ciudadania="CEDULA INCORRECTA";
        this.datosConyugue="CEDULA INCORRECTA";
        this.estadoCivil="CEDULA INCORRECTA";
    }
 * */
    public String getCiudadania() {
        return ciudadania;
    }

    public void setCiudadania(String ciudadania) {
        this.ciudadania = ciudadania;
    }

    public String getDatosConyugue() {
        return datosConyugue;
    }

    public void setDatosConyugue(String datosConyugue) {
        this.datosConyugue = datosConyugue;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

}
