package com.corporaciongpf.adm.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.corporaciongpf.adm.dao.NotaCreditoDAO;
import com.corporaciongpf.adm.modelo.NotaCredito;

@Stateless
public class NotaCreditoDAOImpl extends GenericJpaDAO<NotaCredito, Long>
		implements NotaCreditoDAO {
	private static Logger log = Logger.getLogger(NotaCreditoDAOImpl.class
			.getName());

    
	@SuppressWarnings("unchecked")
	public Boolean existeNotaCredito(String numeroDocumento) {
		

		StringBuilder sql = new StringBuilder();
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		sql.append("select c from NotaCredito c where c.claveAcceso = :numeroDocumento ");
		mapa.put("numeroDocumento", numeroDocumento);


		
		Query q = em.createQuery(sql.toString());
		for (String key : mapa.keySet()) {
			q.setParameter(key, mapa.get(key));
		}
		
		List<NotaCredito> result = q.getResultList();
		
		if (result.size()>0) {
			return true;
		}
		else {
			return false;
		}	}
		
	
}