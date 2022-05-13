/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

import java.io.Serializable;

/**
 *
 * @author paviterip
 */
public class DataBrowseItems implements Serializable{

   private String  codigo;
   private String  nombre;
   private String  presentacion;
   private String  casa;
   private String  subgrupo;
   private String  recetario;
   private String  gravamen;
   private String  discontinuado;
   private String  agotado;
   private String  psicotropico;

   public DataBrowseItems
    (String  codigo,
    String  nombre,
    String  presentacion,
    String  casa,
    String  subgrupo,
    String  recetario,
    String  gravamen,
    String  discontinuado,
    String  agotado,
    String  psicotropico){
       this.codigo=codigo;
       this.nombre=nombre;
       this.presentacion=presentacion;
       this.casa=casa;
       this.subgrupo=subgrupo;
       this.recetario=recetario;
       this.discontinuado=discontinuado;
       this.agotado=agotado;
       this.psicotropico=psicotropico;

   };

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }

    public String getRecetario() {
        return recetario;
    }

    public void setRecetario(String recetario) {
        this.recetario = recetario;
    }

    public String getGravamen() {
        return gravamen;
    }

    public void setGravamen(String gravamen) {
        this.gravamen = gravamen;
    }

    public String getDiscontinuado() {
        return discontinuado;
    }

    public void setDiscontinuado(String discontinuado) {
        this.discontinuado = discontinuado;
    }

    public String getAgotado() {
        return agotado;
    }

    public void setAgotado(String agotado) {
        this.agotado = agotado;
    }

    public String getPsicotropico() {
        return psicotropico;
    }

    public void setPsicotropico(String psicotropico) {
        this.psicotropico = psicotropico;
    }

    
}
