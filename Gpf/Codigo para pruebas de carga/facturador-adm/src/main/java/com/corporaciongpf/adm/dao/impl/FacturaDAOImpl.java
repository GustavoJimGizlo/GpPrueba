package com.corporaciongpf.adm.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.corporaciongpf.adm.dao.FacturaDAO;
import com.corporaciongpf.adm.modelo.Factura;



@Stateless
public class FacturaDAOImpl extends GenericJpaDAO<Factura, BigDecimal>
		implements FacturaDAO {
	private static Logger log = Logger.getLogger(FacturaDAOImpl.class
			.getName());
	

	
	@Override
	@SuppressWarnings("unchecked")
	public Boolean existeFactura(String numeroAutorizacion) {
		
	
			StringBuilder sql = new StringBuilder();
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			sql.append("select f from Factura f where  ");

			if (numeroAutorizacion != null && !numeroAutorizacion.isEmpty()) {
				sql.append("  f.claveAcceso = :claveAcceso ");
				mapa.put("claveAcceso", numeroAutorizacion);
			}
			Query q = em.createQuery(sql.toString());
			for (String key : mapa.keySet()) {
				q.setParameter(key, mapa.get(key));
			}
			
			if (q.getResultList().size()>0) {
				return true;
			}
			else {
				return false;
			}		
	}

	
	
	
	
}