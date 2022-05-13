package com.serviciosweb.gpf.salesforce.service;

import com.serviciosweb.gpf.salesforce.querys.RespuestaQuery;
import com.serviciosweb.gpf.salesforce.util.Utilities;
import com.serviciosweb.gpf.salesforce.xml.Transaccion;

public class OrdenCompraService {

	private Transaccion resultado = new Transaccion();

	static {
		new Utilities();
	}

	public void consultarOrdenCompra(String farmacia ,String ordenCompraString) {

		Integer ordenCompra = 0;

		try {
			ordenCompra = Integer.parseInt(ordenCompraString);
			setResultado(new RespuestaQuery(farmacia,ordenCompra).getResultado());

		} catch (NumberFormatException e) {
			this.resultado.setMensaje("Error: Numero de orden incorrecta");
			this.resultado.setHttpStatus("504");

		} catch (Exception e) {
			resultado.setMensaje("Error al procesar");
			resultado.setHttpStatus("502");
		}

	}

	public Transaccion getResultado() {
		return resultado;
	}

	public void setResultado(Transaccion resultado) {
		this.resultado = resultado;
	}

}
