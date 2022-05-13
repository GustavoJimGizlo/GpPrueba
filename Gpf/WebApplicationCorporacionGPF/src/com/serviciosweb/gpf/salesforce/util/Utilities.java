package com.serviciosweb.gpf.salesforce.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.NamingException;

public class Utilities {
	public static final String nameProject = "SalesForceServices.";
	public static Logger log = Logger.getLogger("SalesForceServices."
			+ Utilities.class.getName());
	static Properties prop;
	final String pathFileConfig = "/data/";
	final String nameFileConfig = "SalesForceServices.properties";
	boolean showLogs = false;

	public String errorRequest() {
		return "La consulta ingreso a un proceso de auditor?a, se procede a solicitar informacion al ISP.";
	}

	public String formatErrorSQL(SQLException e) {
		return "Codigo de Error ORA: " + e.getErrorCode() + "\n" + "SLQState: "
				+ e.getSQLState() + "\n" + "Mensaje: " + e.getMessage() + "\n";
	}

	public ConexionVarianteSid conexionFileExtern(String database) {
		ConexionVarianteSid con = null;
		try {
			con = new ConexionVarianteSid(getProperty("sid.database."
					+ database + ".prod.bd"), getProperty("sid.database."
					+ database + ".prod.usr"), getProperty("sid.database."
					+ database + ".prod.pwd"));
		} catch (NamingException e) {
			log.warning(e.getStackTrace().toString());
		} catch (Exception e) {
			log.warning(e.getStackTrace().toString());
		}
		return con;
	}

	public ConexionVarianteSid conexionFileExtern(String database,
			String service) {
		ConexionVarianteSid con = null;
		try {
			con = new ConexionVarianteSid(
					getProperty(service + ".database." + database + ".prod.bd"),
					getProperty(service + ".database." + database + ".prod.usr"),
					getProperty(service + ".database." + database + ".prod.pwd"));
		} catch (NamingException e) {
			log.warning(e.getStackTrace().toString());
		} catch (Exception e) {
			log.warning(e.getStackTrace().toString());
		}
		return con;
	}

	public String getProperty(String key) {
		try {
			if (prop != null) {
				String pkey = getAmbienteKey(prop, key);
				String keyVal = prop.getProperty(pkey);
				if (keyVal != null) {
					this.showLogs = Boolean.parseBoolean(prop
							.getProperty("show.logs"));
					if (this.showLogs) {
						log.info("Se consuto el key: " + pkey
								+ " ; se obtuvo el valor: " + keyVal);
					}
					return keyVal;
				}
				throw new NoSuchFieldError("Error, no existe la propiedad: "
						+ pkey);
			}
			InputStream input = new FileInputStream(
					"/data/SalesForceServices.properties");
			prop = new Properties();
			prop.load(input);
			String pkey = getAmbienteKey(prop, key);
			String keyVal = prop.getProperty(pkey);
			input.close();
			if (keyVal != null) {
				this.showLogs = Boolean.parseBoolean(prop
						.getProperty("show.logs"));
				if (this.showLogs) {
					log.info("Se consuto el key: " + pkey
							+ " ; se obtuvo el valor: " + keyVal);
				}
				return keyVal;
			}
			throw new NoSuchFieldError("Error, no existe la propiedad: " + pkey);
		} catch (FileNotFoundException e) {
			log.warning(e.getStackTrace().toString());
		} catch (NoSuchFieldError e) {
			log.warning(e.getStackTrace().toString());
		} catch (IOException e) {
			log.warning(e.getStackTrace().toString());
		}
		return null;
	}

	private String getAmbienteKey(Properties p, String key) {
		if (prop.getProperty("ambiente.server") != null) {
			if (prop.getProperty("ambiente.server").equals("PRUE")) {
				return key.replace(".prod.", ".prue.");
			}
			if (prop.getProperty("ambiente.server").equals("DESA")) {
				return key.replace(".prod.", ".desa.");
			}
			if (prop.getProperty("ambiente.server").equals("PROD")) {
				return key;
			}
			throw new NoSuchFieldError(
					"Error, la propiedad: ambiente.server, tiene definido un valor distinto a PROD, PRUE, DESA");
		}
		throw new NoSuchFieldError(
				"Error, no existe la propiedad: ambiente.server");
	}

	public String printParamValues(Object... parameters) {
		String r = "";
		int num = 0;
		Object[] arrayOfObject;
		int j = (arrayOfObject = parameters).length;
		for (int i = 0; i < j; i++) {
			Object o = arrayOfObject[i];
			if ((o instanceof String)) {
				r = r + "param(" + num + "):" + (String) o + "\n";
			} else if ((o instanceof Integer)) {
				r = r + "param(" + num + "):" + (Integer) o + "\n";
			}
			num++;
		}
		return r;
	}

	public static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}

	public static String getContenidoFileUTF8(String nombreArchivo,String folder) {

		StringBuilder resultado = new StringBuilder();
		try {
			String directorio = File.separator +"data"+File.separator+folder+File.separator+nombreArchivo;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(directorio), "utf-8"));
			String sCadena = "";
			while ((sCadena = in.readLine()) != null) {
				resultado.append(sCadena);
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultado.toString();
	}
}
