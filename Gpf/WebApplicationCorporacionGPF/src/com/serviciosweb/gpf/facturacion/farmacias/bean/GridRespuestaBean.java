package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.util.List;

public class GridRespuestaBean {
	private Long page;
	private Long total;
	private Long records;
	private List<GridCeldasRespuestaBean> rows;
	
	public GridRespuestaBean(Long page, Long total, Long records,
			List<GridCeldasRespuestaBean> rows) {
		super();
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}
	
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public List<GridCeldasRespuestaBean> getRows() {
		return rows;
	}
	public void setRows(List<GridCeldasRespuestaBean> rows) {
		this.rows = rows;
	}
	
}
