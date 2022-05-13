package com.serviciosweb.gpf.salesforce.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormasPago {
 private String codigo;
 private String valor;


 // Getter Methods 

 public String getCodigo() {
  return codigo;
 }

 public String getValor() {
  return valor;
 }

 // Setter Methods 

 public void setCodigo(String codigo) {
  this.codigo = codigo;
 }

 public void setValor(String valor) {
  this.valor = valor;
 }
}