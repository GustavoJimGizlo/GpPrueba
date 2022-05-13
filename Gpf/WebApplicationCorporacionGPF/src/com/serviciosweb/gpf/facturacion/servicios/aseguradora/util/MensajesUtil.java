package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

/**
 * 
 * @author Juan Pablo Rojas H.
 * Interlancompu Cía. Ltda.
 * 
 * Clase utilitaria para definir los mensajes por defecto y formatearlos dependiendo la aseguradora
 * 
 * Última modificación
 * Fecha: 06/04/2020 
 * Detalle: Creación de la clase
 * 
 */
public class MensajesUtil {
	
	public static final String ASEGURADORA_HUMANA = "Humana";
	public static final String ASEGURADORA_CONFIAMED = "Confiamed";
	public static final String ASEGURADORA_SALUD = "Saludsa";
	
	public static final String MSJ_CLIENTE_EN_MORA = "Estimado cliente, el contrato registra valores pendiente de pago, por favor comuníquese con la aseguradora XXXXX para mayor información.";
	public static final String MSJ_CLIENTE_EN_CARENCIA = "Estimado cliente, aún no se cumple su período de carencias, por favor comuníquese con la aseguradora XXXXX para mayor información.";
	public static final String MSJ_CLIENTE_NO_CUBRE = "Estimado cliente, su contrato no tiene esta cobertura, por favor comuníquese con la aseguradora XXXXX para mayor información.";
	public static final String MSJ_ERROR_GENERICO = "Servicio de XXXXX no esta disponible";
			
	/***
	 * Método para formatear un mensaje dependiendo la seguradora
	 * 
	 * @param mensajeInicial Mensaje sin incluir el nombre de la aseguradora
	 * @param parametro Texto a reemplazar a XXXXX
	 * @return Mensaje incluido el nombre de la aseguradora
	 */
	public static String parametrizarMensaje(String mensajeInicial, String parametro) {
		String mensajeFinal = mensajeInicial.replace("XXXXX", parametro);
		return mensajeFinal;
	}
}
