package com.serviciosweb.gpf.salesforce.service;

import com.serviciosweb.gpf.salesforce.querys.NotificaEstadoQuery;
import com.serviciosweb.gpf.salesforce.xml.Transaccion;

public class NotificaEstadoService {

	private Transaccion resultado = new Transaccion();

	public NotificaEstadoService(String ordenCompra, 
								 String codPdv,
								 String estado, 
								 String docVenta) {
		resultado = new NotificaEstadoQuery(ordenCompra, 
											codPdv,
											estado,
											docVenta).getResultado();

	}

	public Transaccion getResultado() {
		return resultado;
	}

	public void setResultado(Transaccion resultado) {
		this.resultado = resultado;
	}

}
