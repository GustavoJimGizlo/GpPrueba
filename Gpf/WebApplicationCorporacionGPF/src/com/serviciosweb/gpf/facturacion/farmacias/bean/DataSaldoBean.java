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
public class DataSaldoBean implements Serializable{
    private String saldo;

   public DataSaldoBean(){
   }

   public DataSaldoBean(String saldo ){
      this.saldo = saldo;
   }

  /**
   * @return Returns the saldo.
   */
   public String getSaldo() {
	return saldo;
   }
   /**
   * @param saldo The saldo to set.
   */
   public void setSaldo(String saldo) {
	this.saldo = saldo;
   }
}
