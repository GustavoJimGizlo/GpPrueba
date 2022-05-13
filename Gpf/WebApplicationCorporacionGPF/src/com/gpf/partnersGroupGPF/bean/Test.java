package com.gpf.partnersGroupGPF.bean;

public class Test {

	public static void main(String[] args) {
	String resultado =""
			+ " <cabecera>"
			+ " <baseGravable>0</baseGravable>"
			+ " <baseGravableDevoluciones>0</baseGravableDevoluciones>"
			+ " <codigoError>55W002</codigoError>"
			+ " <mensajeError>No se encuentra factura en JDE</mensajeError>"
			+ " <numeroFactura>001006000000900</numeroFactura>"
			+ " <totalImpuestos>0</totalImpuestos>"
			+ " <totalImpuestosDevoluciones>0</totalImpuestosDevoluciones>"
			+ " </cabecera>";
	
	   System.out.println(resultado.split("<codigoError>")[0]);
	
	

	}

}
