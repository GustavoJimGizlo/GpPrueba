package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Domicilio {
 private String ventaNeta;
 private String iva;
 private String tax;
 private String venta;


 // Getter Methods 

 public String getVentaNeta() {
  return ventaNeta;
 }

 public String getIva() {
  return iva;
 }

 public String getTax() {
  return tax;
 }

 public String getVenta() {
  return venta;
 }

 // Setter Methods 

 public void setVentaNeta(String ventaNeta) {
  this.ventaNeta = ventaNeta;
 }

 public void setIva(String iva) {
  this.iva = iva;
 }

 public void setTax(String tax) {
  this.tax = tax;
 }

 public void setVenta(String venta) {
  this.venta = venta;
 }
}