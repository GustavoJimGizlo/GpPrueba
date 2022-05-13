package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * 
 * @author Juan Pablo Rojas H.
 * Interlancompu Cía. Ltda.
 * 
 * Clase utilitaria para leer y escribir el token de acceso en un archivo plano
 * 
 * Última modificación
 * Fecha: 06/04/2020 
 * Detalle: Creación de la clase
 * 
 */
public class TokenUtil {
	
	/**
	 * Método para leer el token de acceso desde un archivo de texto plano
	 * @param rutaArchivo Ruta del archivo
	 * @param token Token de acceso a escribir en el archivo plano
	 */
	public static void escribirToken(String rutaArchivo, String token) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(rutaArchivo);
			pw = new PrintWriter(fichero);
			pw.println(token);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Método para leer el token de acceso desde un archivo de texto plano
	 * @param rutaArchivo Ruta del archivo
	 * @return Token de acceso
	 */
	public static String leerToken(String rutaArchivo) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String result = "";
		try {
			archivo = new File(rutaArchivo);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				result = linea;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
