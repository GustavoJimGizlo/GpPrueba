package com.corporaciongpf.adm.dao;

import javax.ejb.Local;

import com.corporaciongpf.adm.modelo.NotaCredito;



@Local
public interface NotaCreditoDAO extends GenericDAO<NotaCredito, Long> {

	Boolean existeNotaCredito(String numeroDocumento);

}
