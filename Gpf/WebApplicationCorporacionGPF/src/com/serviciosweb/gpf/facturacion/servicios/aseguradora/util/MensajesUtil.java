package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

/**
 * 
 * @author Juan Pablo Rojas H.
 * Interlancompu C�a. Ltda.
 * 
 * Clase utilitaria para definir los mensajes por defecto y formatearlos dependiendo la aseguradora
 * 
 * �ltima modificaci�n
 * Fecha: 06/04/2020 
 * Detalle: Creaci�n de la clase
 * 
 */
public class MensajesUtil {
	
	public static final String ASEGURADORA_HUMANA = "Humana";
	public static final String ASEGURADORA_CONFIAMED = "Confiamed";
	public static final String ASEGURADORA_SALUD = "Saludsa";
	
	public static final String MSJ_CLIENTE_EN_MORA = "Estimado cliente, el contrato registra valores pendiente de pago, por favor comun�quese con la aseguradora XXXXX para mayor informaci�n.";
	public static final String MSJ_CLIENTE_EN_CARENCIA = "Estimado cliente, a�n no se cumple su per�odo de carencias, por favor comun�quese con la aseguradora XXXXX para mayor informaci�n.";
	public static final String MSJ_CLIENTE_NO_CUBRE = "Estimado cliente, su contrato no tiene esta cobertura, por favor comun�quese con la aseguradora XXXXX para mayor informaci�n.";
	public static final String MSJ_ERROR_GENERICO = "Servicio de XXXXX no esta disponible";
			
	/***
	 * M�todo para formatear un mensaje dependiendo la seguradora
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
