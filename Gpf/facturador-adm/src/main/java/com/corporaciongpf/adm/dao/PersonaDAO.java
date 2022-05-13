package com.corporaciongpf.adm.dao;

import java.util.List;

import javax.ejb.Local;

import com.corporaciongpf.adm.excepcion.GizloException;
import com.corporaciongpf.adm.modelo.Persona;


@Local
public interface PersonaDAO extends GenericDAO<Persona, String>{
	
	Boolean obtenerPorParametros(String identificacion) throws GizloException;

}
