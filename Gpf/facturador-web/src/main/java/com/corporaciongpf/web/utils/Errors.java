package com.corporaciongpf.web.utils;

public enum Errors {
	
	S200(200) {
		@Override
		public String getMensaje() {
			return "OK";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E101(101) {
		@Override
		public String getMensaje() {
			return "Login incorrecto";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E102(102) {
		@Override
		public String getMensaje() {
			return "La propiedad tranKey es incorrecta";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E103(103) {
		@Override
		public String getMensaje() {
			return "La propiedad seed es incorrecta";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E104(104) {
		@Override
		public String getMensaje() {
			return "Sitio desactivado";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E105(105) {
		@Override
		public String getMensaje() {
			return "Las credenciales han expirado";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E106(106) {
		@Override
		public String getMensaje() {
			return "Estructura de consumo no cumple con la definición";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E107(107) {
		@Override
		public String getMensaje() {
			return "Tiempo de respuesta tardía";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	,E108(108) {
		@Override
		public String getMensaje() {
			return "No concuerda la identificacion con el tipo";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty"};
		}
	}
	,E109(109) {
		@Override
		public String getMensaje() {
			return "Identificacion no cumple con los criterios de aceptación";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty"};
		}
	}
	,E110(110) {
		@Override
		public String getMensaje() {
			return "Cliente consultado no existe";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty", "Compra"};
		}
	}
	,E111(111) {
		@Override
		public String getMensaje() {
			return "Acción de Inserción o Actualización no realizada";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty", "Compra"};
		}
	}
	,E112(112) {
		@Override
		public String getMensaje() {
			return "El cliente se encuentra afiliado al club con anterioridad";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty"};
		}
	}
	,E113(113) {
		@Override
		public String getMensaje() {
			return "Los datos enviados no cumplen las reglas";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General", "Loyalty"};
		}
	}
	,E114(114) {
		@Override
		public String getMensaje() {
			return "No se encontró datos de convenio relacionados a los datos enviados";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"Compra"};
		}
	}
	,E115(115) {
		@Override
		public String getMensaje() {
			return "El convenio identificado no es de credito";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"Compra"};
		}
	}
	,E116(116) {
		@Override
		public String getMensaje() {
			return "Exepción presente, error generico";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"Compra"};
		}
	}
	,E117(117) {
		@Override
		public String getMensaje() {
			return "Cupo insuficiente para realizar compra";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"Compra"};
		}
	}
	,E118(118) {
		@Override
		public String getMensaje() {
			return "Error al generar el número de autorización";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"Compra"};
		}
	}
	,E1000(1000) {
		@Override
		public String getMensaje() {
			return "Contacte a un administrador";
		}
		@Override
		public String[] getMethodUse() {
			return new String[] {"General"};
		}
	}
	;

	private int status;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	public abstract String getMensaje();
	public abstract String[] getMethodUse();
	
	Errors(int status) {
		this.status = status;
	}
	
}