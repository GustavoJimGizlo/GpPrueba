package com.serviciosweb.gpf.salesforce.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DetalleCompra {
	List<Articulo> articulo = new ArrayList<Articulo>();

	public List<Articulo> getArticulo() {
		return articulo;
	}

	public void setArticulo(List<Articulo> articulo) {
		this.articulo = articulo;
	}

}