/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

/**
 *
 * @author mftellor
 */
public class EstadisticasVentas {
   private String codigoFarmacia;
   private String codigoItem;
   private String numeroPeridodo;
   private String venta;
   private String insertado;
   private String mensaje;


   public EstadisticasVentas(){

   }
   public EstadisticasVentas(String codigoFarmacia, String codigoItem,
                            String numeroPeridodo,String venta,String insertado){
       this.codigoFarmacia=codigoFarmacia;
       this.codigoItem=codigoItem;
       this.numeroPeridodo=numeroPeridodo;
       this.venta=venta;
       this.insertado=insertado;
   }
    public String getCodigoFarmacia() {
        return codigoFarmacia;
    }

    public void setCodigoFarmacia(String codigoFarmacia) {
        this.codigoFarmacia = codigoFarmacia;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getInsertado() {
        return insertado;
    }

    public void setInsertado(String insertado) {
        this.insertado = insertado;
    }

    public String getNumeroPeridodo() {
        return numeroPeridodo;
    }

    public void setNumeroPeridodo(String numeroPeridodo) {
        this.numeroPeridodo = numeroPeridodo;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
