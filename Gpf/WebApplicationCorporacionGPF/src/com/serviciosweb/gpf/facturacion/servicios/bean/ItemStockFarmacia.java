/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios.bean;

/**
 *
 * @author mftellor
 */
public class ItemStockFarmacia {
   private String farmacia;
   private String nombreFarmacia;
   private String cantidadStock;
   private String unidadVenta;
   private String pvpConIva;
   private String pvpSinIva;


    public ItemStockFarmacia(String farmacia,String cantidadStock,String unidadVenta,String pvpConIva,String pvpSinIva,String nombreFarmacia){
        this.farmacia=farmacia;
        this.cantidadStock=cantidadStock;
        this.unidadVenta=unidadVenta;
        this.pvpConIva=pvpConIva;
        this.pvpSinIva=pvpSinIva;
        this.nombreFarmacia=nombreFarmacia;
    }
    public ItemStockFarmacia(){

    }
    public String getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(String cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getUnidadVenta() {
        return unidadVenta;
    }

    public void setUnidadVenta(String unidadVenta) {
        this.unidadVenta = unidadVenta;
    }

    public String getPvpConIva() {
        return pvpConIva;
    }

    public void setPvpConIva(String pvpConIva) {
        this.pvpConIva = pvpConIva;
    }

    public String getPvpSinIva() {
        return pvpSinIva;
    }

    public void setPvpSinIva(String pvpSinIva) {
        this.pvpSinIva = pvpSinIva;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }
    
   
}
