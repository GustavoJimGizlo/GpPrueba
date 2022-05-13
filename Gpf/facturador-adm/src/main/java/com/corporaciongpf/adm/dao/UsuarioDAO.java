package com.corporaciongpf.adm.dao;

import java.util.List;

import javax.ejb.Local;

import com.corporaciongpf.adm.modelo.Persona;
import com.corporaciongpf.adm.modelo.Usuario;

@Local
public interface UsuarioDAO extends GenericDAO<Usuario, Long> {

	List<Usuario> obtenerBasico(String username, Persona persona);

	List<Usuario> obtenerParametros(String nombres, String apellidos,
			String identificacion);

	Usuario obtenerUsuario(String username);

}
