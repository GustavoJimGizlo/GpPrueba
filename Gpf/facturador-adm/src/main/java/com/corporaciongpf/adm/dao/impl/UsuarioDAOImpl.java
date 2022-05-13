package com.corporaciongpf.adm.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.corporaciongpf.adm.dao.UsuarioDAO;
import com.corporaciongpf.adm.modelo.Persona;
import com.corporaciongpf.adm.modelo.Usuario;

/**
 * @author Jose Vinueza
 * 
 */

@Stateless
public class UsuarioDAOImpl extends GenericJpaDAO<Usuario, Long> implements
		UsuarioDAO {
	

	
	@SuppressWarnings("unchecked")
	public List<Usuario> obtenerBasico(String username, Persona persona) {
		StringBuilder sql = new StringBuilder();
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		sql.append("select c from Usuario c where 1 = 1 ");

		if (username != null && !username.isEmpty()) {
			sql.append("and c.username = :username ");
			mapa.put("username", username);
		}

		if (persona != null) {
			sql.append("and c.persona = :persona ");
			mapa.put("persona", persona);
		}

		Query q = em3.createQuery(sql.toString());
		for (String key : mapa.keySet()) {
			q.setParameter(key, mapa.get(key));
		}

		List<Usuario> result = q.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> obtenerParametros(String nombres, String apellidos,
			String identificacion) {
		StringBuilder sql = new StringBuilder();
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		sql.append("select c from Usuario c where 1=1 ");
		if (identificacion != null && !identificacion.isEmpty()) {
			sql.append("and c.persona.identificacion = :identificacion ");
			mapa.put("identificacion", identificacion);
		}

		if (nombres != null && !nombres.isEmpty()) {
			sql.append("and upper(c.persona.nombres) like :nombres ");
			mapa.put("nombres", "%" + nombres.toUpperCase() + "%");
		}

		if (apellidos != null && !apellidos.isEmpty()) {
			sql.append("and upper(c.persona.apellidos) like :apellidos ");
			mapa.put("apellidos", "%" + apellidos.toUpperCase() + "%");
		}

		Query q = em3.createQuery(sql.toString());
		for (String key : mapa.keySet()) {
			q.setParameter(key, mapa.get(key));
		}

		List<Usuario> result = q.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario obtenerUsuario(String username) {
		Query q = em
				.createQuery("select c from Usuario c where c.username = :username ");
		q.setParameter("username", username);

		List<Usuario> result = q.getResultList();
		return result != null && !result.isEmpty() ? result.get(0) : null;
	}

}
