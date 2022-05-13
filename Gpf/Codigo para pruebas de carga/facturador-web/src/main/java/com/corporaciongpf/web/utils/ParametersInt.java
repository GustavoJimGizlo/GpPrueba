package com.corporaciongpf.web.utils;

public enum ParametersInt {
	TIPO_CELULAR(6),
	//TIPO_CONVENCIONAL(1), 
	TIPO_CORREO(3),
	MAX_MINUTES_AUTH(5);
	
	private final int value;

	private ParametersInt(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
