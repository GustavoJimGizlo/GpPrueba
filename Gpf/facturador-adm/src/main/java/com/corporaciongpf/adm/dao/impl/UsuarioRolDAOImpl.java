package com.corporaciongpf.adm.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.corporaciongpf.adm.dao.UsuarioRolDAO;
import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Usuario;
import com.corporaciongpf.adm.modelo.UsuarioRol;

/**
 * @author Jose Vinueza
 * 
 */

@Stateless
public class UsuarioRolDAOImpl extends GenericJpaDAO<UsuarioRol, Long>
		implements UsuarioRolDAO {
	

	
	@Override
	public List<UsuarioRol> obtenerUsuarioRolPorUsuario(Usuario usuario)
			throws GizloException {
		try {
			String hql = "select r from UsuarioRol r where r.usuario=:usuario";
			Query q = em3.createQuery(hql);
			q.setParameter("usuario", usuario);

			@SuppressWarnings("unchecked")
			List<UsuarioRol> result = q.getResultList();
			return result;

		} catch (Exception e) {
			throw new GizloException(
					"Se produjo un error al buscar el rol por nombre.", e);
		}
	}
}
