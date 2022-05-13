package com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.plan;

import java.util.List;

public class ConsultarPlanesBean {

	private List<PlanesBean> planes;
	private Result result;

	public List<PlanesBean> getPlanes() {
		return planes;
	}

	public void setPlanes(List<PlanesBean> planes) {
		this.planes = planes;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
