package com.corporaciongpf.adm.dao;

import java.util.List;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Usuario;
import com.corporaciongpf.adm.modelo.UsuarioRol;

@Local
public interface UsuarioRolDAO extends GenericDAO<UsuarioRol, Long> {

	List<UsuarioRol> obtenerUsuarioRolPorUsuario(Usuario usuario)
			throws GizloException;

}
