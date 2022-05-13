package com.corporaciongpf.adm.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.corporaciongpf.adm.dao.PersonaDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Persona;

@Stateless
public class PersonaDAOImpl extends GenericJpaDAO<Persona, String> implements PersonaDAO{


	@Override
	@SuppressWarnings("unchecked")
	public Boolean obtenerPorParametros(String identificacion) throws GizloException{
		StringBuilder sql = new StringBuilder();
		HashMap<String, Object> mapa = new HashMap<String, Object>();


		sql.append("select c from Persona c where c.identificacion = :identificacion ");
		

		
		Query q = em3.createQuery(sql.toString());

		q.setParameter("identificacion", identificacion);
		
		List<Persona> result = q.getResultList();
		
		if (result.size()>0) {
			return true;
		}
		else {
			return false;
		}		
		
		
		
		
	}

}
