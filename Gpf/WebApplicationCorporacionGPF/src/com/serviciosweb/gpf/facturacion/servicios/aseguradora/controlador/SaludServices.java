package com.serviciosweb.gpf.facturacion.servicios.aseguradora.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.serviciosweb.gpf.facturacion.config.WebApplicationConfig;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosDependientesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosGeneralesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.infoAdicBean;

import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosTitularesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.ResultGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.humana.DatosGeneralesBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.salud.TokenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.MensajesUtil;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TextoUtil;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.util.TokenUtil;

/**
 * 
 * @author Juan Pablo Rojas H.
 * Interlancompu Cía. Ltda.
 * 
 * Clase para interactuar con el web service de Saludsa
 * 
 * Última modificación
 * Fecha: 01/04/2020 
 * Detalle: Creación de la clase
 * 
 */
public class SaludServices {

	private WebApplicationConfig waConfig;
	
	public String saludServicesResponse = "";
	private String errorGenerico = MensajesUtil.parametrizarMensaje(MensajesUtil.MSJ_ERROR_GENERICO, MensajesUtil.ASEGURADORA_SALUD);
	
	// Constructores
	//public SaludServices() {
	//	waConfig = new WebApplicationConfig();
	//}
	
	public SaludServices(String identificacion, String durl,String tipoid) {
		waConfig = new WebApplicationConfig();
		saludServicesResponse = consultaDatos(identificacion, durl,tipoid);
	}
	
	public SaludServices(RecetaGenBean recetaGenBean, String url) {
		waConfig = new WebApplicationConfig();
		saludServicesResponse = confirmacionDatos (recetaGenBean, url);
	}
	
	
	// Metodos de consulta con ws
	
	/**
	 * 
	 * @param cedula
	 * @param url
	 * @return
	 */
	public String consultaDatos (String cedula, String url,String tipoid) {
		
		String guiaElectronicaEspsResponse = "";
		try {
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("convenio", waConfig.epSaludConfig.paramConvenio);
			params.put("numeroDocumento", cedula);
			params.put("tipoDocumento", tipoid);
			
			
			
			
			
			String urlFinal = url + "?".concat(TextoUtil.hashMapAQueryParams(params));
			URL obj = new URL(urlFinal);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			
			String token = TokenUtil.leerToken(waConfig.epSaludConfig.rutaArchivoToken);
			
			
			con.setRequestProperty("Authorization", token);
			con.setRequestProperty("CodigoAplicacion", waConfig.epSaludConfig.headerCodAplicacion);
			con.setRequestProperty("CodigoPlataforma", waConfig.epSaludConfig.headerCodPlataforma);
			con.setRequestProperty("SistemaOperativo", waConfig.epSaludConfig.headerSO);
			con.setRequestProperty("DispositivoNavegador", waConfig.epSaludConfig.headerNavegador);
			con.setRequestProperty("DireccionIP", waConfig.epSaludConfig.headerDireccionIP);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			
			OutputStream os = con.getOutputStream();
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
	        writer.write(TextoUtil.hashMapAQueryParams(params));
	        writer.flush();
	        writer.close();
	        os.close();
	        
			int responseCode = 0;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				responseCode = 401;
			}
			
			if (responseCode == 401) {
				generarToken();
				guiaElectronicaEspsResponse = consultaDatos(cedula, url,tipoid);
			} else if (responseCode == 200) {

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				String stringJson = "";
				String contrato ="";
				
				while ((inputLine = in.readLine()) != null) {
					//System.out.println(inputLine);
					stringJson = inputLine;
					
					
					  // quitamos letras con tildes y ñ 03 04 2020 sfbarrionuevoa
                 	stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
					stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			
					
			    	//stringJson = Normalizer.normalize(stringJson, Normalizer.Form.NFD);
						//		stringJson = stringJson.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
						
				}
				in.close();
				
				//stringJson = "";
				//stringJson = "{\"Estado\":\"OK\",\"Datos\":[{\"Region\":\"Sierra\",\"Producto\":\"IND\",\"Numero\":743032,\"CodigoPlan\":\"N5-S\",\"Codigo\":920313,\"Estado\":\"Activo\",\"CodigoEstado\":1,\"NombrePlan\":\"Elite 5 Sierra\",\"Version\":24,\"CoberturaMaxima\":100000.0,\"Nivel\":5,\"Titular\":{\"Numero\":370551,\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1713324042\",\"Nombres\":\"Sandra Elizabeth\",\"Apellidos\":\"Zurita Granda\",\"FechaNacimiento\":\"1975-11-03T00:00:00\",\"Genero\":\"F\"},\"DeducibleTotal\":0.0,\"TieneImpedimento\":false,\"MotivoImpedimento\":\"\",\"EsMoroso\":false,\"NombreComercialPlan\":\"Elite\",\"FechaInicio\":\"2016-05-16T00:00:00\",\"FechaVigencia\":\"2022-05-16T00:00:00\",\"CodigoPmf\":\"IND00056\",\"Empresa\":\"SALUD INDIVIDUALES S.A.\",\"NumeroEmpresa\":1,\"Sucursal\":\"Quito\",\"CodigoSucursal\":1,\"NombreLista\":\"Salud Individuales S.a.\",\"NumeroLista\":1,\"EsDeducibleAnual\":false,\"Ruc\":\"9999999999999\",\"RazonSocial\":\"SALUD INDIVIDUALES S.A.\",\"EsSmartPlan\":false,\"Observaciones\":\"mensaje de observacion\",\"TipoVademecum\":\"NORMAL\",\"Deducibles\":[],\"Beneficiarios\":[{\"NumeroPersona\":1122186,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Diego Martin\",\"Apellidos\":\"Vallejo Zurita\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726973595\",\"FechaNacimiento\":\"2003-01-09T00:00:00\",\"Edad\":18,\"FechaInclusion\":\"2016-05-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":true,\"Observaciones\":\"\",\"Maternidad\":false,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"},{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"}]},{\"NumeroPersona\":1122187,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Maria Emilia\",\"Apellidos\":\"Vallejo Zurita\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726973587\",\"FechaNacimiento\":\"2006-03-16T00:00:00\",\"Edad\":15,\"FechaInclusion\":\"2016-05-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":true,\"Observaciones\":\"\",\"Maternidad\":true,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"},{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"}]}]},{\"Region\":\"Sierra\",\"Producto\":\"IND\",\"Numero\":4021736,\"CodigoPlan\":\"N4-D-S\",\"Codigo\":1508731,\"Estado\":\"Activo\",\"CodigoEstado\":1,\"NombrePlan\":\"Ideal 4d Sierra\",\"Version\":23,\"CoberturaMaxima\":45000.0,\"Nivel\":4,\"Titular\":{\"Numero\":370551,\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1713324042\",\"Nombres\":\"Sandra Elizabeth\",\"Apellidos\":\"Zurita Granda\",\"FechaNacimiento\":\"1975-11-03T00:00:00\",\"Genero\":\"F\"},\"DeducibleTotal\":100.0,\"TieneImpedimento\":false,\"MotivoImpedimento\":\"\",\"EsMoroso\":false,\"NombreComercialPlan\":\"Ideal\",\"FechaInicio\":\"2020-09-25T00:00:00\",\"FechaVigencia\":\"2021-09-25T00:00:00\",\"CodigoPmf\":\"IND00054\",\"Empresa\":\"SALUD INDIVIDUALES S.A.\",\"NumeroEmpresa\":1,\"Sucursal\":\"Quito\",\"CodigoSucursal\":1,\"NombreLista\":\"Salud Individuales S.a.\",\"NumeroLista\":1,\"EsDeducibleAnual\":false,\"Ruc\":\"9999999999999\",\"RazonSocial\":\"SALUD INDIVIDUALES S.A.\",\"EsSmartPlan\":false,\"Observaciones\":\"\",\"TipoVademecum\":\"NORMAL\",\"Deducibles\":[{\"Codigo\":\"PERSONAL\",\"Region\":\"Sierra\",\"CodigoProducto\":\"IND\",\"CodigoPlan\":\"N4-D-S\",\"VersionPlan\":23,\"TipoCobertura\":\"Ambos\",\"Monto\":100.0}],\"Beneficiarios\":[{\"NumeroPersona\":370551,\"RelacionDependiente\":\"Titular\",\"Nombres\":\"Sandra Elizabeth\",\"Apellidos\":\"Zurita Granda\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1713324042\",\"FechaNacimiento\":\"1975-11-03T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2020-09-25T00:00:00\",\"DeducibleCubierto\":100.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":true,\"Observaciones\":\"\",\"Maternidad\":true,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"},{\"IdBeneficioConvenio\":0,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80.0,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0,\"Descripcion\":\"\"}]}]},{\"Region\":\"Sierra\",\"Producto\":\"IND\",\"Numero\":4031770,\"CodigoPlan\":\"PRGVLT+F\",\"Codigo\":1564969,\"Estado\":\"Activo\",\"CodigoEstado\":1,\"NombrePlan\":\"Privilegio Titular + Familia Venta Libre (dental)\",\"Version\":24,\"CoberturaMaxima\":0.0,\"Nivel\":1,\"Titular\":{\"Numero\":370551,\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1713324042\",\"Nombres\":\"Sandra Elizabeth\",\"Apellidos\":\"Zurita Granda\",\"FechaNacimiento\":\"1975-11-03T00:00:00\",\"Genero\":\"F\"},\"DeducibleTotal\":0.0,\"TieneImpedimento\":false,\"MotivoImpedimento\":\"\",\"EsMoroso\":false,\"NombreComercialPlan\":null,\"FechaInicio\":\"2021-04-16T00:00:00\",\"FechaVigencia\":\"2022-04-16T00:00:00\",\"CodigoPmf\":null,\"Empresa\":\"SALUD INDIVIDUALES S.A.\",\"NumeroEmpresa\":1,\"Sucursal\":\"Quito\",\"CodigoSucursal\":1,\"NombreLista\":\"Salud Individuales S.a.\",\"NumeroLista\":1,\"EsDeducibleAnual\":false,\"Ruc\":\"9999999999999\",\"RazonSocial\":\"SALUD INDIVIDUALES S.A.\",\"EsSmartPlan\":false,\"Observaciones\":\"\",\"TipoVademecum\":\"NORMAL\",\"Deducibles\":[],\"Beneficiarios\":[{\"NumeroPersona\":72748,\"RelacionDependiente\":\"Primos / As\",\"Nombres\":\"Diego Fernando\",\"Apellidos\":\"Vallejo Espinosa\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1710255850\",\"FechaNacimiento\":\"1975-06-25T00:00:00\",\"Edad\":46,\"FechaInclusion\":\"2021-04-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":370551,\"RelacionDependiente\":\"Titular\",\"Nombres\":\"Sandra Elizabeth\",\"Apellidos\":\"Zurita Granda\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1713324042\",\"FechaNacimiento\":\"1975-11-03T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2021-04-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":556151,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Maria Emilia\",\"Apellidos\":\"Vallejo Zurita\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726973587\",\"FechaNacimiento\":\"2006-03-16T00:00:00\",\"Edad\":15,\"FechaInclusion\":\"2021-04-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":556150,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Diego Martin\",\"Apellidos\":\"Vallejo Zurita\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726973595\",\"FechaNacimiento\":\"2003-01-09T00:00:00\",\"Edad\":18,\"FechaInclusion\":\"2021-04-16T00:00:00\",\"DeducibleCubierto\":0.0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficioRedSinDeducible\":false,\"BeneficiosPlan\":[]}]}],\"Mensajes\":[]}";
				
				//DatosGeneralesGenericoBean objeto = new ObjectMapper().readValue(stringJson,
					//	DatosGeneralesGenericoBean.class);
				
				//DatosGeneralesBean objeto = new ObjectMapper().readValue(stringJson, DatosGeneralesBean.class);
				
				// objeto = validaMensaje(objeto);

				/*for(int i=0;i<objeto.getRec_titular().size();i++){
					
					
					
					objeto.getRec_titular().get(i).setTi_mail("1");
					objeto.getRec_titular().get(i).setTi_telefono("1");
					contrato = objeto.getRec_titular().get(i).getTi_autnumcont() + objeto.getRec_titular().get(i).getTi_codplan();
					objeto.getRec_titular().get(i).setTi_contrato(contrato);
					
					
					
				}
				*/
				
				//stringJson = "{\"Estado\":\"OK\",\"Datos\":[{\"Region\":\"Sierra\",\"Producto\":\"IND\",\"Numero\":803359,\"CodigoPlan\":\"N5-D-S\",\"Codigo\":1296247,\"Estado\":\"Activo\",\"CodigoEstado\":1,\"NombrePlan\":\"Elite 5d Sierra\",\"Version\":21,\"CoberturaMaxima\":100000,\"Nivel\":5,\"Titular\":{\"Numero\":1241946,\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602117509\",\"Nombres\":\"Diego Patricio\",\"Apellidos\":\"Balseca Chavez\",\"FechaNacimiento\":\"1974-09-22T00:00:00\",\"Genero\":\"M\"},\"DeducibleTotal\":120,\"TieneImpedimento\":false,\"MotivoImpedimento\":\"\",\"EsMoroso\":true,\"NombreComercialPlan\":\"Élite\",\"FechaInicio\":\"2019-08-27T00:00:00\",\"FechaVigencia\":\"2020-08-27T00:00:00\",\"CodigoPmf\":\"IND00058\",\"Empresa\":\"SALUD INDIVIDUALES S.A.\",\"NumeroEmpresa\":1,\"Sucursal\":\"Quito\",\"CodigoSucursal\":1,\"NombreLista\":\"Salud Individuales S.a.\",\"NumeroLista\":1,\"EsDeducibleAnual\":false,\"Ruc\":\"9999999999999\",\"RazonSocial\":\"SALUD INDIVIDUALES S.A.\",\"EsSmartPlan\":false,\"Observaciones\":\"\",\"Deducibles\":[{\"Codigo\":\"PERSONAL\",\"Region\":\"Sierra\",\"CodigoProducto\":\"IND\",\"CodigoPlan\":\"N5-D-S\",\"VersionPlan\":21,\"TipoCobertura\":\"Ambos\",\"Monto\":120}],\"Beneficiarios\":[{\"NumeroPersona\":1241946,\"RelacionDependiente\":\"Titular\",\"Nombres\":\"Diego Patricio\",\"Apellidos\":\"Balseca Chavez\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602117509\",\"FechaNacimiento\":\"1974-09-22T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2018-08-27T00:00:00\",\"DeducibleCubierto\":25,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[{\"CodigoDiagnostico\":\"J30\",\"Descripcion\":\"Rinitis Alergica y Vasomotora\",\"FechaInicio\":\"2018-08-27T00:00:00\",\"DiasFaltanCobertura\":-232}],\"BeneficioOda\":false,\"Observaciones\":\"No se ha superado al deducible, favor comuníquese con Saludsa al 6020920 o escribe al vive@saludsa.com.ec\",\"Maternidad\":false,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":6234449,\"CodigoBeneficio\":\"A002\",\"Nombre\":\"Consulta Medica\",\"Valor\":4.5,\"EsPorcentaje\":false,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234450,\"CodigoBeneficio\":\"A003\",\"Nombre\":\"Laboratorio Clinico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234451,\"CodigoBeneficio\":\"A004\",\"Nombre\":\"Laboratorio Imagen\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234452,\"CodigoBeneficio\":\"A005\",\"Nombre\":\"Procedimientos Diagnostico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234453,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234454,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234455,\"CodigoBeneficio\":\"AH01\",\"Nombre\":\"Terapia Respiratoria\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234456,\"CodigoBeneficio\":\"AH02\",\"Nombre\":\"Terapia de Lenguaje\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234457,\"CodigoBeneficio\":\"AH03\",\"Nombre\":\"Terapia Fisica\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20}]},{\"NumeroPersona\":1254711,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Alia Isabella\",\"Apellidos\":\"Balseca Orozco\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1757532922\",\"FechaNacimiento\":\"2016-07-04T00:00:00\",\"Edad\":3,\"FechaInclusion\":\"2018-09-27T00:00:00\",\"DeducibleCubierto\":120,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":true,\"Observaciones\":\"\",\"Maternidad\":true,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":6234449,\"CodigoBeneficio\":\"A002\",\"Nombre\":\"Consulta Medica\",\"Valor\":4.5,\"EsPorcentaje\":false,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234450,\"CodigoBeneficio\":\"A003\",\"Nombre\":\"Laboratorio Clinico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234451,\"CodigoBeneficio\":\"A004\",\"Nombre\":\"Laboratorio Imagen\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234452,\"CodigoBeneficio\":\"A005\",\"Nombre\":\"Procedimientos Diagnostico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234453,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234454,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234455,\"CodigoBeneficio\":\"AH01\",\"Nombre\":\"Terapia Respiratoria\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234456,\"CodigoBeneficio\":\"AH02\",\"Nombre\":\"Terapia de Lenguaje\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234457,\"CodigoBeneficio\":\"AH03\",\"Nombre\":\"Terapia Fisica\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20}]},{\"NumeroPersona\":1254712,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Irai Estefania\",\"Apellidos\":\"Balseca Orozco\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726184706\",\"FechaNacimiento\":\"2008-07-18T00:00:00\",\"Edad\":11,\"FechaInclusion\":\"2018-09-27T00:00:00\",\"DeducibleCubierto\":120,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":true,\"Observaciones\":\"\",\"Maternidad\":true,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":6234449,\"CodigoBeneficio\":\"A002\",\"Nombre\":\"Consulta Medica\",\"Valor\":4.5,\"EsPorcentaje\":false,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234450,\"CodigoBeneficio\":\"A003\",\"Nombre\":\"Laboratorio Clinico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234451,\"CodigoBeneficio\":\"A004\",\"Nombre\":\"Laboratorio Imagen\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234452,\"CodigoBeneficio\":\"A005\",\"Nombre\":\"Procedimientos Diagnostico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234453,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234454,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234455,\"CodigoBeneficio\":\"AH01\",\"Nombre\":\"Terapia Respiratoria\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234456,\"CodigoBeneficio\":\"AH02\",\"Nombre\":\"Terapia de Lenguaje\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234457,\"CodigoBeneficio\":\"AH03\",\"Nombre\":\"Terapia Fisica\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":true,\"Total\":20,\"Disponibles\":20}]},{\"NumeroPersona\":58284120,\"RelacionDependiente\":\"Conyuge\",\"Nombres\":\"Alba Mercedes\",\"Apellidos\":\"Orozco Peñafiel\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602912354\",\"FechaNacimiento\":\"1975-01-21T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2020-01-27T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":true,\"DiasFinCarenciaHospitalaria\":11,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"No se ha superado al deducible, favor comuníquese con Saludsa al 6020920 o escribe al vive@saludsa.com.ec\",\"Maternidad\":true,\"BeneficiosPlan\":[{\"IdBeneficioConvenio\":6234449,\"CodigoBeneficio\":\"A002\",\"Nombre\":\"Consulta Medica\",\"Valor\":4.5,\"EsPorcentaje\":false,\"EstadoActivo\":true,\"Credito\":true,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234450,\"CodigoBeneficio\":\"A003\",\"Nombre\":\"Laboratorio Clinico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234451,\"CodigoBeneficio\":\"A004\",\"Nombre\":\"Laboratorio Imagen\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234452,\"CodigoBeneficio\":\"A005\",\"Nombre\":\"Procedimientos Diagnostico\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234453,\"CodigoBeneficio\":\"A010\",\"Nombre\":\"Medicinas Marca\",\"Valor\":60,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234454,\"CodigoBeneficio\":\"A011\",\"Nombre\":\"Medicinas Genericas\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":0,\"Disponibles\":0},{\"IdBeneficioConvenio\":6234455,\"CodigoBeneficio\":\"AH01\",\"Nombre\":\"Terapia Respiratoria\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234456,\"CodigoBeneficio\":\"AH02\",\"Nombre\":\"Terapia de Lenguaje\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20},{\"IdBeneficioConvenio\":6234457,\"CodigoBeneficio\":\"AH03\",\"Nombre\":\"Terapia Fisica\",\"Valor\":80,\"EsPorcentaje\":true,\"EstadoActivo\":true,\"Credito\":false,\"Total\":20,\"Disponibles\":20}]}]},{\"Region\":\"Sierra\",\"Producto\":\"IND\",\"Numero\":274195,\"CodigoPlan\":\"PRGVLT+F\",\"Codigo\":1452905,\"Estado\":\"Activo\",\"CodigoEstado\":1,\"NombrePlan\":\"Privilegio Titular + Familia Venta Libre (dental)\",\"Version\":22,\"CoberturaMaxima\":0,\"Nivel\":1,\"Titular\":{\"Numero\":1241946,\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602117509\",\"Nombres\":\"Diego Patricio\",\"Apellidos\":\"Balseca Chavez\",\"FechaNacimiento\":\"1974-09-22T00:00:00\",\"Genero\":\"M\"},\"DeducibleTotal\":0,\"TieneImpedimento\":false,\"MotivoImpedimento\":\"\",\"EsMoroso\":false,\"NombreComercialPlan\":null,\"FechaInicio\":\"2020-01-02T00:00:00\",\"FechaVigencia\":\"2021-01-02T00:00:00\",\"CodigoPmf\":null,\"Empresa\":\"SALUD INDIVIDUALES S.A.\",\"NumeroEmpresa\":1,\"Sucursal\":\"Quito\",\"CodigoSucursal\":1,\"NombreLista\":\"Salud Individuales S.a.\",\"NumeroLista\":1,\"EsDeducibleAnual\":false,\"Ruc\":\"9999999999999\",\"RazonSocial\":\"SALUD INDIVIDUALES S.A.\",\"EsSmartPlan\":false,\"Observaciones\":\"\",\"Deducibles\":[],\"Beneficiarios\":[{\"NumeroPersona\":1241946,\"RelacionDependiente\":\"Titular\",\"Nombres\":\"Diego Patricio\",\"Apellidos\":\"Balseca Chavez\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602117509\",\"FechaNacimiento\":\"1974-09-22T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":true,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":58284120,\"RelacionDependiente\":\"Conyuge\",\"Nombres\":\"Alba Mercedes\",\"Apellidos\":\"Orozco Peñafiel\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0602912354\",\"FechaNacimiento\":\"1975-01-21T00:00:00\",\"Edad\":45,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":1254712,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Irai Estefania\",\"Apellidos\":\"Balseca Orozco\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1726184706\",\"FechaNacimiento\":\"2008-07-18T00:00:00\",\"Edad\":11,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":1254711,\"RelacionDependiente\":\"Hijo\",\"Nombres\":\"Alia Isabella\",\"Apellidos\":\"Balseca Orozco\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"1757532922\",\"FechaNacimiento\":\"2016-07-04T00:00:00\",\"Edad\":3,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":1324727,\"RelacionDependiente\":\"Padres\",\"Nombres\":\"Ernesto\",\"Apellidos\":\"Orozco Hernandez\",\"Genero\":\"M\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0600557805\",\"FechaNacimiento\":\"1945-06-10T00:00:00\",\"Edad\":74,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]},{\"NumeroPersona\":1324732,\"RelacionDependiente\":\"Padres\",\"Nombres\":\"Margod\",\"Apellidos\":\"Peñafiel Haro\",\"Genero\":\"F\",\"TipoDocumento\":\"C\",\"NumeroDocumento\":\"0600742571\",\"FechaNacimiento\":\"1978-08-26T00:00:00\",\"Edad\":41,\"FechaInclusion\":\"2020-01-02T00:00:00\",\"DeducibleCubierto\":0,\"EnCarencia\":false,\"DiasFinCarencia\":0,\"EnCarenciaHospitalaria\":false,\"DiasFinCarenciaHospitalaria\":0,\"Preexistencias\":[],\"BeneficioOda\":false,\"Observaciones\":\"El plan PRGVLT+F no posee cobertura para Odas.\",\"Maternidad\":false,\"BeneficiosPlan\":[]}]}],\"Mensajes\":[]}"; 
				guiaElectronicaEspsResponse = this.mapearResultadoConsultarDatos(stringJson);
				
			} else {
				guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			}

		} catch (Exception e) {
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			e.printStackTrace();
		}/*catch (MalformedURLException e) {
			saludServicesResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			e.printStackTrace();
		} catch (IOException e) {
			saludServicesResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			e.printStackTrace();
		}*/

		return guiaElectronicaEspsResponse;
	}
	
	/**
	 * 
	 * @param recetaGenBean
	 * @param url
	 * @return
	 */
	public String confirmacionDatos (RecetaGenBean recetaGenBean, String url) {
		
		String guiaElectronicaEspsResponse = "";
		
		try {
			JAXBContext context = JAXBContext.newInstance(RecetaGenBean.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter out = new StringWriter();
			marshaller.marshal(recetaGenBean, out);
			String xml = out.toString();
			
			System.out.println(xml);
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjetoUTF8("OK", "000000");
		} catch (Exception e) {
			e.printStackTrace();
			guiaElectronicaEspsResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
		}
		
		return guiaElectronicaEspsResponse;
	}
	
	/**
	 * 
	 */
	private void generarToken() throws Exception {
		 
		URL url;
		HashMap<String, String> paramsToken = new HashMap<String, String>();
		paramsToken.put("username", waConfig.epSaludConfig.credUsuario);
		paramsToken.put("password", waConfig.epSaludConfig.credClave);
		paramsToken.put("grant_type", waConfig.epSaludConfig.credGrantType);
		paramsToken.put("client_id", waConfig.epSaludConfig.credIdCliente);

		String body = "";
		 //try {
			body = TextoUtil.hashMapAQueryParams(paramsToken);
		//} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		/*}
		try {*/
			byte[] postData = body.getBytes( "UTF-8" );
			url = new URL(waConfig.epSaludConfig.urlToken);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//Se añade el Body del request
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			OutputStream os = con.getOutputStream();
			os.write(postData);	
			os.flush();
			
			System.out.println(con.getResponseCode());
			System.out.println("MENSAJE: " + con.getResponseMessage());
			
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			String accessToken = "";
			
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				output = output.replace(".issued", "issued");
				output = output.replace(".expires", "expires");
				TokenBean token = new ObjectMapper().readValue(output,TokenBean.class);
				accessToken = token.getAccess_token();
			}
			TokenUtil.escribirToken(waConfig.epSaludConfig.rutaArchivoToken, "Bearer ".concat(accessToken));
			con.disconnect();
			
		/*} catch (MalformedURLException e) {
			e.printStackTrace();
			saludServicesResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
		} catch (IOException e) {
			e.printStackTrace();
			saludServicesResponse = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
		}*/			
	}
	
	/**
	 * Método para mapear el resultado del metodo ObtenerContratoCobertura de SALUD en la estructura
	 * requerida por GPF
	 * @param stringJson Cadena JSON con el resultado devuelto por ObtenerContratoCobertura
	 * @return respuesta a entregar al cliente
	 */
	private String mapearResultadoConsultarDatos(String stringJson) {
		
		String resultado = null;
		String observaciones = null;
		String obs = null;
		String mensaje = null;
		
		try {
			JSONObject json = new JSONObject(stringJson);
			
			String estado = json.getString("Estado");
			
			// Validacion del estado
			if(estado.equalsIgnoreCase("OK")) {
				List<DatosTitularesGenericoBean> listaTitulares = new ArrayList<DatosTitularesGenericoBean>();
				
				// Mapeo de los beneficiarios
				boolean esMoroso = false;
				boolean esCarencia = false;
				boolean esDeducible = false;
			
				JSONArray jsonArrayDatos = json.getJSONArray("Datos");
				
				
				for (int i = 0; i < jsonArrayDatos.length(); i++) {
					JSONObject datos = json.getJSONArray("Datos").getJSONObject(i);
					//System.out.println("i" + obs + i);
					
					//obs= datos.getString("Observaciones");
					//System.out.println("obs" + obs + i);
					
					// 1. Validacion de Mora
					//if (datos.getBoolean("EsMoroso")) {
					//	esMoroso = true;
					//	observaciones = datos.optString("Observaciones");
					//	continue;
					//}
					
					String producto = datos.getString("Producto");
					
					System.out.println("producto" + producto);
					
					
					Long deducibleTotal = datos.getLong("DeducibleTotal");
					// Mapeo del titular
					JSONObject jsonTitular = datos.getJSONObject("Titular");
					DatosTitularesGenericoBean titular = new DatosTitularesGenericoBean();
					
					String tipoDocumento = jsonTitular.getString("TipoDocumento");
					titular.setTi_tipoident(this.formatearTipoIdentificacion(tipoDocumento));
					
					//producto
					titular.setTi_producto(producto);
					titular.setTi_telefono("1");
					titular.setTi_mensaje("A");
					
					titular.setTi_cedula(jsonTitular.getString("NumeroDocumento"));
					titular.setTi_mail("1");
					titular.setTi_telefono("1");
					
					
					titular.setTi_estado(datos.getString("Estado").equalsIgnoreCase("Activo") ? "Si" : "No"); // TODO: Validar
					titular.setTi_autnumcont(String.valueOf(datos.getLong("Numero")));
					titular.setTi_codplan(datos.getString("CodigoPlan"));
					titular.setTi_autcodigo(String.valueOf(jsonTitular.getLong("Numero"))); // TODO: Validar
					titular.setTi_nomplan(datos.getString("NombrePlan").toUpperCase());
					titular.setTi_fechinicontr(this.formatearFecha(datos.getString("FechaInicio"))); // TODO: Validar
					titular.setTi_codgenero(jsonTitular.getString("Genero").equalsIgnoreCase("M") ? "1" : "2"); // TODO: Validar mapeo
					// aqui se debe agregar el cambio
					//titular.setTi_contrato(String.valueOf(jsonTitular.getLong("Numero")) + datos.getString("CodigoPlan"));
					titular.setTi_contrato( String.valueOf(datos.getLong("Numero")) + datos.getString("CodigoPlan"));
					titular.setTi_nombreContratante(this.formatearNombre(jsonTitular.getString("Apellidos"), jsonTitular.getString("Nombres")));
					titular.setTi_nombreNegocio("1");
						try {
						JSONArray jsonDeducibles = datos.getJSONArray("Deducibles");
						titular.setTi_versionPlan(String.valueOf(jsonDeducibles.getJSONObject(0).getLong("VersionPlan")));
					} catch (Exception e) {
						titular.setTi_versionPlan(String.valueOf(datos.getLong("Version")));
					}
					
					
					mensaje = "";
						
					// Mapeo de beneficiarios
					List<DatosDependientesGenericoBean> listaBeneficiarios = new ArrayList<DatosDependientesGenericoBean>();
					JSONArray jsonArrayBeneficiarios = datos.getJSONArray("Beneficiarios");
					//
				
					//.getString("Observaciones");
					
					
					
					for (int j = 0; j < jsonArrayBeneficiarios.length(); j++) {
						
						JSONObject jsonBeneficiario = jsonArrayBeneficiarios.getJSONObject(j);
						
						        obs=""; 
								obs= jsonBeneficiario.getString("Observaciones");
								System.out.println("mensajex" +j +  obs);
								//obs= datos.getString("Observaciones");
						
						//arreglo de beneficion plan
						JSONArray jsonArrayBeneficiosPlan = jsonBeneficiario.getJSONArray("BeneficiosPlan");
						//int numerob = jsonArrayBeneficiosPlan.length();
						//definimos lista
						List<infoAdicBean> ti_infoAdic = new ArrayList<infoAdicBean>();
						int n = jsonArrayBeneficiosPlan.length();
						
						if (n== 0)
					    mensaje ="";	
						mensaje =" Contrato no tiene beneficio Plan para el beneficiario";	
								
					
						
						
						
						for (int a = 0; a < jsonArrayBeneficiosPlan.length(); a++) 
						{
					    	
					    	JSONObject jsonBeneficiarioPlan = jsonArrayBeneficiosPlan.getJSONObject(a);
							
							//jsonBeneficiarioPlan.getBoolean("EsPorcentaje");
					    	
							if (jsonBeneficiarioPlan.getBoolean("EsPorcentaje")) {
							infoAdicBean infoAdic = new infoAdicBean();
						    infoAdic.setClave(jsonBeneficiarioPlan.getString("CodigoBeneficio"));
					    	infoAdic.setValor(String.valueOf(jsonBeneficiarioPlan.getLong("Valor")));
							ti_infoAdic.add(infoAdic);
							}
							//Boolean var_credito =jsonBeneficiarioPlan.getBoolean("Credito");
							
							mensaje = "";
							
							if (!jsonBeneficiarioPlan.getBoolean("Credito")) {
								mensaje = obs;
								System.out.println(mensaje);
								continue;
							}
						}
						
						titular.setTi_infoAdic(ti_infoAdic);
				    	
						// Validacion de carencia
						//if (jsonBeneficiario.getBoolean("EnCarencia")) {
						//	esCarencia = true;
						//	observaciones = jsonBeneficiario.optString("Observaciones");
						//	continue;
						//}
						
						// Validacion de deducibles
						//Long deducibleCubierto = jsonBeneficiario.getLong("DeducibleCubierto");
						//if(deducibleCubierto < deducibleTotal) {
						//	esDeducible = true;
						//	observaciones = jsonBeneficiario.optString("Observaciones");
						//	continue;
						//}
						
						DatosDependientesGenericoBean beneficiario = new DatosDependientesGenericoBean();
						beneficiario.setDe_autcodigo(String.valueOf(jsonBeneficiario.getLong("NumeroPersona")));
						beneficiario.setDe_cedula(jsonBeneficiario.getString("NumeroDocumento"));
						beneficiario.setDe_mail("1");
						beneficiario.setDe_nombre(this.formatearNombre(jsonBeneficiario.getString("Apellidos"), jsonBeneficiario.getString("Nombres")));
						beneficiario.setDe_telefono("1");
						beneficiario.setDe_tipo(jsonBeneficiario.getString("RelacionDependiente").toUpperCase()); // TODO: Validar
						beneficiario.setDe_tipoident(this.formatearTipoIdentificacion(jsonBeneficiario.getString("TipoDocumento"))); // TODO: Validar
						
						if (!mensaje.equalsIgnoreCase(""))
						beneficiario.setMensaje(mensaje);
						else
						beneficiario.setMensaje("A");
							

						
						
						listaBeneficiarios.add(beneficiario);
					}
					titular.setRec_dependiente(listaBeneficiarios);
					
					// Si existen beneficiarios -> Se agrega el titular
					if (!listaBeneficiarios.isEmpty())
						listaTitulares.add(titular);
				}
				
				// Si no existen titulares agregados -> Se valida mensaje de error
				if (listaTitulares.isEmpty()) {
					if (observaciones == null || observaciones.isEmpty()) {
						if (esMoroso)
							observaciones =  MensajesUtil.parametrizarMensaje(MensajesUtil.MSJ_CLIENTE_EN_MORA, MensajesUtil.ASEGURADORA_SALUD);
						else if (esCarencia)
							observaciones =  MensajesUtil.parametrizarMensaje(MensajesUtil.MSJ_CLIENTE_EN_CARENCIA, MensajesUtil.ASEGURADORA_SALUD);
						else if (esDeducible)
							observaciones = MensajesUtil.parametrizarMensaje(MensajesUtil.MSJ_CLIENTE_NO_CUBRE, MensajesUtil.ASEGURADORA_SALUD);
						else
							observaciones = MensajesUtil.parametrizarMensaje(MensajesUtil.MSJ_ERROR_GENERICO, MensajesUtil.ASEGURADORA_SALUD);
					}
					
					resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", observaciones);
					return resultado;
				}
				
				ResultGenericoBean resultGenerico = new ResultGenericoBean();
				resultGenerico.setStatus("true");
				resultGenerico.setMensaje("OK");
				
				DatosGeneralesGenericoBean objeto = new DatosGeneralesGenericoBean();
				objeto.setRec_titular(listaTitulares);
				objeto.setResult(resultGenerico);
				objeto.setTipo_facturacion("NA");
				
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(DatosGeneralesGenericoBean.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					StringWriter sw = new StringWriter();
					jaxbMarshaller.marshal(objeto, sw);
					String xmlContent = sw.toString();
					System.out.println(xmlContent);
					resultado = TextoUtil.respuestaXmlObjetoUTF8("OK",
							"<datosGeneralesBean>" + xmlContent.split("<datosGeneralesBean>")[1]);
				} catch (JAXBException e) {
					resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", e.getMessage());
					e.printStackTrace();
				}
			}
			else if(estado.equalsIgnoreCase("Error")) {
				if (json.getJSONArray("Mensajes").length() > 0)
					resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", json.getJSONArray("Mensajes").getString(0));
				else
					resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			}
			else {
				resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", errorGenerico);
			}
		} catch (JSONException e) {
			resultado = TextoUtil.respuestaXmlObjetoUTF8("ERROR", e.getMessage());
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/**
	 * Método para formatear la fecha enviada por SALUD al formato requerido por GPF
	 * @param stringFecha Fecha sin formato requerido
	 * @return Fecha con formato requerido
	 */
	public String formatearFecha(String stringFecha) {
		
		String resultado = stringFecha;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date fecha = simpleDateFormat.parse(stringFecha);
			
			SimpleDateFormat sdfDestino = new SimpleDateFormat("dd/M/yyyy");
			resultado = sdfDestino.format(fecha);
		}
		catch (Exception e) {}
		
		return resultado;
	}
	
	/**
	 * Método para formatear el tipo de identificacion enviado por SALUD al formato requerido por GPF
	 * @param tipoIdentificacion Tipo de identificacion sin formato requerido
	 * @return Tipo de identificacion con formato requerido
	 */
	public String formatearTipoIdentificacion(String tipoIdentificacion) {
		String resultado = tipoIdentificacion.equalsIgnoreCase("C") ? "CÉDULA" : "PASAPORTE";
		return resultado;
	}
	
	/**
	 * Método para formatear los apellidos y nombres enviados por SALUD al formato requerido por GPF
	 * @param apellidos Apellidos enviados por SALUD
	 * @param nombres Nombres enviados por SALUD
	 * @return Apellidos y nombres con formato requerido
	 */
	public String formatearNombre(String apellidos, String nombres) {
		
		apellidos = (apellidos == null || apellidos.isEmpty()) ? "" : apellidos;
		nombres = (nombres == null || nombres.isEmpty()) ? "" : nombres;
		String resultado = (apellidos + " " + nombres).toUpperCase().trim();
		return resultado;
	}
	
	// get and set
	public String getSaludServicesResponse() {
		return saludServicesResponse;
	}
	
	public void setSaludServicesResponse(String saludServicesResponse) {
		this.saludServicesResponse = saludServicesResponse;
	}
}
