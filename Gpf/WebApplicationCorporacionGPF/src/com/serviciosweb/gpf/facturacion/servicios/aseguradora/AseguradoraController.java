package com.serviciosweb.gpf.facturacion.servicios.aseguradora;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.codehaus.jackson.map.ObjectMapper;

import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;
import com.serviciosweb.gpf.facturacion.servicios.ConexionServices;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.AseguradoraControl;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DatosGeneralesBean;

import oracle.jdbc.driver.OracleDriver;

public class AseguradoraController {
	public String AseguradoraResponse = "";
	// final String SIDBASE="jdbc:oracle:thin:@PRODUAT_CLOUD_M2C.UIO:1521:prs6";
	// final String SIDBASE="jdbc:oracle:thin:@PRODUAT_CLOUD_M2C.UIO:1521:prs6";
	// //test
	// final String SIDBASE="jdbc:oracle:thin:@PROD.UIO:1521:prs6";//prd
	// final String SIDBASE="jdbc:oracle:thin:@prs6.uio:1521:prs6";//prd
	// final String SIDBASE="jdbc:oracle:thin:@10.100.3.20:1521:prs6";

	// final String USUARIOBASE="Farmacias";
	// final String CLAVEBASE="farmacias";
	// final String USUARIOBASE="weblink";
	// final String CLAVEBASE="weblink_2013";

	public AseguradoraController(Integer idAseguradora, String consulta, List<Object> objetos, String tipoid) {
		try {
			// TODO Se debe consultar los parametros de la BDD deacuerdo al idAseguradora
			AseguradoraControl aseguradoraControl = traerAseguradoraControl(idAseguradora, consulta);
			if (aseguradoraControl != null) {
				// TODO Se crea la clase deacuerdo al objeto
				Class<?> type = Class.forName(aseguradoraControl.getPaqueteClase());
				// verifica si tiene paquete

				// se añade la url a los parametros del constructor
				// se van añadiendo las clases del arreglo de objetos
				List<Class> classes = new ArrayList<Class>();
				List<Object> objets = new ArrayList<Object>();
				int i = 0;

				for (Object clase : objetos) {
					String nombreTipo = aseguradoraControl.getPaqueteContructor().split(",")[i];
					Class<?> type2 = Class.forName(nombreTipo);
					Object Object;
					classes.add(type2);

					if (consulta.equalsIgnoreCase("consultaDatos")) {
						String id = objetos.get(0).toString();
						objets.add(id);
					} else {
						try {
							Object = (Object) new ObjectMapper().readValue(clase.toString(), type2);
						} catch (Exception e) {
							String err = e.getMessage();
							JAXBContext jaxbContext = JAXBContext.newInstance(type2);
							Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
							XMLStreamReader reader = XMLInputFactory.newInstance()
									.createXMLStreamReader(new StringReader(clase.toString()));
							Object = (Object) jaxbUnmarshaller.unmarshal(reader);
						}
						objets.add(Object);
					}
					i = i + 1;
				} // fin de for

				// recorre cedula o pasaporte
				/*
				 * for(Object clase : objetos) { String nombreTipo =
				 * aseguradoraControl.getPaqueteContructor().split(",")[i]; Class<?> type2 =
				 * Class.forName(nombreTipo); Object Object; Object Object1 =null;
				 * 
				 * 
				 * try {//try
				 * 
				 * String claseNew; String claseNew1=null;
				 * 
				 * if (nombreTipo.equalsIgnoreCase("java.lang.String")) { // cuando es 0 la
				 * primera letra if (clase.toString().startsWith("0")) { claseNew =
				 * clase.toString().substring(1); Object = (Object) new
				 * ObjectMapper().readValue(claseNew.toString(), type2); Object = "0" + Object;
				 * } else { // cuando no es cero //Object = (Object) new
				 * ObjectMapper().readValue(clase.toString(), type2); //pasaportes int n =
				 * clase.toString().length(); int banderau = 0; int pos_letra=0;
				 * 
				 * String c=""; String cadenaf =""; int contadorc =0; for (i=0;i<n;i++) { //
				 * System.out.println(i);
				 * 
				 * c = clase.toString().substring(i,i+1); //System.out.println("c"+c);
				 * 
				 * 
				 * if (c.matches("[0-1-2-3-4-5-6-7-8-9]*")) { //System.out.println("numero");
				 * 
				 * } else { cadenaf = cadenaf + c; contadorc = contadorc +1;
				 * 
				 * int largo = n-1; if ( largo == i) { banderau= 1; pos_letra=i;
				 * 
				 * }
				 * 
				 * } }
				 * 
				 * if (banderau == 1) //claseNew = clase.toString().substring(0, contadorc+1);
				 * 
				 * claseNew = clase.toString().substring(0, pos_letra);
				 * 
				 * else claseNew = clase.toString().substring(contadorc);
				 * 
				 * 
				 * //String b=a.substring(0, 1);
				 * 
				 * 
				 * // cuando es 0 la primera letra if (claseNew.toString().startsWith("0")) { //
				 * String segundo = claseNew.toString().substring(1,2); if (segundo.equals("0"))
				 * { claseNew1 = claseNew.toString().substring(2); Object1 = (Object) new
				 * ObjectMapper().readValue(claseNew1.toString(), type2); Object1 = "00" +
				 * Object1; } else { claseNew1 = claseNew.toString().substring(1); Object1 =
				 * (Object) new ObjectMapper().readValue(claseNew1.toString(), type2); Object1 =
				 * "0" + Object1; } // }
				 * 
				 * 
				 * else { Object1 = (Object) new ObjectMapper().readValue(claseNew.toString(),
				 * type2); }
				 * 
				 * 
				 * //fin de cuando es 0 la primera
				 * 
				 * if (banderau == 0) Object = cadenaf + Object1; else Object = Object1 +
				 * cadenaf;
				 * 
				 * //fin de pasaportes }
				 * 
				 * 
				 * 
				 * }// else no es igual java lang else { Object = (Object) new
				 * ObjectMapper().readValue(clase.toString(), type2); }// fin de if
				 * 
				 * 
				 * //fin de try } catch (Exception e) { String err = e.getMessage(); JAXBContext
				 * jaxbContext = JAXBContext.newInstance(type2); Unmarshaller jaxbUnmarshaller =
				 * jaxbContext.createUnmarshaller(); XMLStreamReader reader =
				 * XMLInputFactory.newInstance().createXMLStreamReader(new
				 * StringReader(clase.toString())); Object = (Object)
				 * jaxbUnmarshaller.unmarshal(reader); }
				 * 
				 * classes.add(type2); objets.add(Object); i = i + 1; }//fin de for
				 * 
				 */
				// aqui terminar

				// se añade la url a los parametros del constructor
				classes.add(String.class);
				objets.add(aseguradoraControl.getUrl());

				if (aseguradoraControl.getConsulta().equalsIgnoreCase("consultaDatos")) {
					classes.add(String.class);
					objets.add(tipoid);
				}

				// Class[] arrayClass = classes.stream().toArray(Class[]::new);
				Class[] arrayClass = new Class[classes.size()];
				for (int ind = 0; ind < classes.size(); ind++) {
					arrayClass[ind] = classes.get(ind);
				}
				// Object[] arrayObject = objets.stream().toArray(Object[]::new);
				Object[] arrayObject = new Object[objets.size()];
				for (int ind = 0; ind < objets.size(); ind++) {
					arrayObject[ind] = objets.get(ind);
				}
				// se crea constructor con el arreglo de clases
				Constructor<?> constructor = type.getConstructor(arrayClass);
				// se instancia el constructor con el arreglo de objetos
				Object instance = constructor.newInstance(arrayObject);
				// se crea el metodo que debe retornar la informacion
				Method method = type.getMethod(aseguradoraControl.getMetodoRetorno());
				// se llama al metodo
				Object methodCallResult = method.invoke(instance);
				// se transforma en string la respuesta
				AseguradoraResponse = (String) methodCallResult;
			} else {
				StringBuilder respuestaXml = new StringBuilder();
				respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				respuestaXml.append(
						"<transaccion><respuesta>No exixten datos para esa aseguradora</respuesta></transaccion>");
				AseguradoraResponse = respuestaXml.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringBuilder respuestaXml = new StringBuilder();
			respuestaXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			respuestaXml.append("<transaccion><respuesta>Error no contemplado</respuesta></transaccion>");
			AseguradoraResponse = respuestaXml.toString();
		}

	}

	public AseguradoraControl traerAseguradoraControl(int idAseguradora, String consulta) {

		// CredencialDS credencialDS = new CredencialDS();
		Connection conn = null;
		Statement comando;
		ResultSet resultado = null;
		AseguradoraControl result = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
			System.setProperty("oracle.net.tns_admin", "/data/oracle");

			DriverManager.registerDriver(new OracleDriver());
			DriverManager.setLoginTimeout(100);

			// prd
			 String quienIngresa="WEBLINK";
			 String comoIngresa="weblink_2013";
			// test
			//String quienIngresa = "Farmacias";
			//String comoIngresa = "farmacias";
			// estas 2 lines descomentar para produciocion
			String url;
			// prd
		   url="jdbc:oracle:thin:"+quienIngresa+"/"+comoIngresa+"@PRS6.UIO";
			// test
			//url = "jdbc:oracle:thin:" + quienIngresa + "/" + comoIngresa + "@PRODUAT_CLOUD_M2C.UIO";
			// System.out.println("Conexion " + url);
			System.out.println("Conexion de Aseguradoradas Ambiente " + url);

			conn = DriverManager.getConnection(url);
			// conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@10.100.3.20:1521:prs6",USUARIOBASE,CLAVEBASE);
			// conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@172.20.200.146:1521:prs6",USUARIOBASE,CLAVEBASE);
			// conn = ConexionServices.obtenerConexionFarmacia(credencialDS);
			comando = conn.createStatement();
			resultado = comando.executeQuery("select * from farmacias.fa_aseguradora_control where codigoAseguradora="
					+ idAseguradora + " and consulta='" + consulta + "' ");

			while (resultado.next()) {
				result = new AseguradoraControl();

				result.setCodigo(resultado.getInt(1));
				result.setCodigoAseguradora(idAseguradora);
				result.setConsulta(consulta);
				result.setPaqueteClase(resultado.getString(4));
				result.setPaqueteContructor(resultado.getString(5));
				result.setMetodoRetorno(resultado.getString(6));
				result.setUrl(resultado.getString(7));
			}
			// result = deleteMapeoAseguradora(conn,codigoCarga );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
