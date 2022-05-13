package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.confiamed;

import java.io.Serializable;
import java.util.List;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.receta.RecetaGenericaBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;



public class RecetaConfiamed implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre_benficio;
	private List<RecetaGenBean> recetagen;
	
	public String getNombre_benficio() {
		return nombre_benficio;
	}
	
	public void setNombre_benficio(String nombre_benficio) {
		this.nombre_benficio = nombre_benficio;
	}
	
	public List<RecetaGenBean> getRecetagen() {
		return recetagen;
	}
	
	public void setRecetagen(List<RecetaGenBean> recetagen) {
		this.recetagen = recetagen;
	}
}
