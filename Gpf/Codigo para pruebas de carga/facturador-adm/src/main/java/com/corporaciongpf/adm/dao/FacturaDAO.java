package com.corporaciongpf.adm.dao;


import java.math.BigDecimal;

import javax.ejb.Local;

import com.corporaciongpf.adm.modelo.Factura;



@Local
public interface FacturaDAO extends GenericDAO<Factura, BigDecimal> {

	Boolean existeFactura(String numeroAutorizacion);

}
