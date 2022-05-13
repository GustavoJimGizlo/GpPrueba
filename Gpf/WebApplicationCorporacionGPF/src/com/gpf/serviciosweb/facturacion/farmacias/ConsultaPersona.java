
package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;


import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataGrupoEnvioNewBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DataPersona;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @date 06/06/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
public class ConsultaPersona extends ObtenerNuevaConexion implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<DataPersona> wfArchivos;
	
	String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA"; 
	public ConsultaPersona(String cedula, String empresa) {
		super(ConsultaPersona.class);
		ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");//empresa);
		if(!conexionVarianteSid.isValid()){
			resultadoXml="ERROR AL CONECTAR A LA BDD "+conexionVarianteSid.getError();
			return;
		}
		Integer codigoPersona=null;
		ResultSetMapper<DataPersona> resultSetMapper = new ResultSetMapper<DataPersona>();
		cedula=cedula.replace("'", "");
		String  queryBusqueda = "SELECT codigo FROM  ab_personas  WHERE identificacion = '" + cedula + "' AND ROWNUM = 1";
		System.out.println(queryBusqueda);
		ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);		
		while(conexionVarianteSid.siguiente(rs))
			codigoPersona=conexionVarianteSid.getInt(rs, "codigo");
		
		if(codigoPersona==null){
			resultadoXml="NO EXISTEN DATOS PARA LA PERSONA CON CEDULA No "+cedula;
			return;
		}
		queryBusqueda = "SELECT p.codigo, p.identificacion, p.tipo_identificacion," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (p.primer_nombre))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') primer_nombre, " +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (p.segundo_nombre))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') segundo_nombre," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (p.primer_apellido))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') primer_apellido," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (p.segundo_apellido))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') segundo_apellido," +
                " p.fecha_nacimiento, p.sexo," +
                " teldom.dom telef_dom, telcelular.celular, email.valor_email email," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (direcc.principal))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') principal, " +
                " direcc.numero," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (direcc.interseccion))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') interseccion," +
                " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(TRIM(TO_CHAR (direcc.referencia))),'Ã�','A'),'Ã‰','E'),'Ã�','I'),'Ã“','O'),'Ãš','U'),'Ã‘','N') referencia," +
                " direcc.ciudad, direcc.barrio, direcc.tipo" +
                "  FROM ab_personas p," +
                " (SELECT   t1.persona persona_fono, t1.valor celular, t1.codigo codigo_cel" +
                "   FROM     ab_medios_contacto t1" +
                "  WHERE    t1.persona = " + codigoPersona +
                "  AND      t1.tipo = 6" +
                "  AND      t1.direccion IS NULL" +
                "  AND      ROWNUM = 1" +
                "  ORDER BY t1.ROWID) telcelular," +
                " (SELECT   t2.persona persona_fono, t2.valor dom, t2.codigo codigo_dom" +
                "  FROM     ab_medios_contacto t2" +
                "  WHERE    t2.persona = " + codigoPersona +
                "  AND      t2.tipo = 1" +
                "  AND      t2.direccion IS NOT NULL" +
                "  AND      ROWNUM = 1" +
                "  ORDER BY t2.ROWID) teldom," +
                " (SELECT   t3.persona persona_fono, t3.valor valor_email," +
                "           t3.codigo codigo_email" +
                "   FROM     ab_medios_contacto t3" +
                "  WHERE    t3.persona = " + codigoPersona +
                "  AND      t3.tipo = 3" +
                "  AND      t3.direccion IS NULL" +
                "  AND      ROWNUM = 1" +
                "  ORDER BY t3.ROWID) email," +
                " (SELECT   d1.persona, d1.principal, d1.codigo," +
                "           d1.numero, d1.interseccion, d1.referencia," +
                "           adc.nombre as CIUDAD, ab.nombre as BARRIO, 'Domicilio' tipo" +
                "   FROM     ab_direcciones d1, ad_barrios ab, AD_CIUDADES adc" +
                "  WHERE    d1.barrio=ab.codigo and d1.ciudad=adc.codigo and d1.persona = " + codigoPersona +
                "  AND      d1.tipo = 1" +
                "  AND      ROWNUM = 1" +
                "  ORDER BY d1.ROWID) direcc" +                 
                " WHERE  p.codigo = " + codigoPersona +
                " AND    telcelular.persona_fono(+) = p.codigo" +
                " AND    teldom.persona_fono(+) = p.codigo" +
                " AND    email.persona_fono(+) = p.codigo" +
                " AND    p.codigo = direcc.persona(+)";
			rs = conexionVarianteSid.consulta(queryBusqueda);	
			System.out.println("queryBusqueda  "+queryBusqueda);
			this.wfArchivos=resultSetMapper.mapRersultSetToObject(rs, DataPersona.class);
			conexionVarianteSid.cerrarConexion(rs);
	}
	
	public String getResultadoXml() {
		XStream xstream = new XStream(new StaxDriver());
		
		if(this.wfArchivos!=null){
	          xstream.alias("DataPersona", DataPersona.class);
	          resultadoXml=xstream.toXML(this.wfArchivos);
	          resultadoXml=resultadoXml.replace("com.serviciosweb.gpf.facturacion.farmacias.bean.DataPersona","DataPersonaBean");	      
		}		
		
		
		/*
		 * <informacion>
			<dato>


			


			<ciudad>null</ciudad>
			<barrio>null</barrio>
			<tipod>null</tipod>
			</dato>
			</informacion>
			
			
						
			<tipoIdentificacion>C</tipoIdentificacion>			
			<primerNombre>MILTON</primerNombre>
			<segundoNombre>FABRICIO</segundoNombre>
			<primerApellido>TELLO</primerApellido>
			<segundoApellido>REINA</segundoApellido>
			<sexo>M</sexo>
			<fechaNacimiento>1976-07-20 00:00:00.0</fechaNacimiento>
			<teleDomicilio>022010205</teleDomicilio>
			<celular>0996901744</celular>
			<email>FAVRYCIO@GMAIL.COM</email>
			<callePrincipal>PRUEBAS</callePrincipal>
			<numero>CS D10</numero>
			<interseccion>NO DESPACHAR</interseccion>
			<referencia>ANULAR ESTA ORDEN, PRUEBAS TECNOLOGIA GPF</referencia>
			<ciudad>17060</ciudad>
			<barrio>293</barrio>
			<tipoDireccion>Domicilio</tipoDireccion>
			</DataPersona>
		 * */
		String resultado=Constantes.respuestaXmlObjetoSinCabecera("OK",this.resultadoXml);
		resultado=resultado.replace("transaccion", "informacion");
		resultado=resultado.replace("DataPersona", "dato");
		resultado=resultado.replace("DataPersona", "dato");
		resultado=resultado.replace("primerNombre", "pnombre");
		resultado=resultado.replace("segundoNombre", "snombre");
		resultado=resultado.replace("primerApellido", "papellido");
		resultado=resultado.replace("segundoApellido", "sapellido");
		resultado=resultado.replace("sexo", "genero");
		resultado=resultado.replace("fechaNacimiento", "fechan");
		resultado=resultado.replace("teleDomicilio", "telefd");
		resultado=resultado.replace("callePrincipal", "callep");
		resultado=resultado.replace("interseccion", "intersecc");
		resultado=resultado.replace("tipoDireccion", "tipod");
		return   resultado;
	}


	
}
