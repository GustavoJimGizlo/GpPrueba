package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.util.List;

public class GridCeldasRespuestaBean {
	private Long id;
	private List<String> cell;
	public GridCeldasRespuestaBean(Long id, List<String> cell) {
		super();
		this.id = id;
		this.cell = cell;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getCell() {
		return cell;
	}
	public void setCell(List<String> cell) {
		this.cell = cell;
	} 
}
