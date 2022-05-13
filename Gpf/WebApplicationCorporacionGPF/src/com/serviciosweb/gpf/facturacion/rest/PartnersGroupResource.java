package com.serviciosweb.gpf.facturacion.rest;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.gpf.partnersGroupGPF.bean.AbDirecciones;
import com.gpf.partnersGroupGPF.bean.AbMediosContacto;
import com.gpf.partnersGroupGPF.bean.AbPersonas;
import com.gpf.partnersGroupGPF.servicios.PartnersGroupGpf;
import com.gpf.postg.pedidos.util.ConversionsTasks;
import com.gpf.postg.pedidos.util.FacadeException;


@Path("PartnersGroupGPF")
public class PartnersGroupResource {
	
	 @Context
	 private UriInfo context;
	 private static final Logger log = Logger.getLogger(PartnersGroupResource.class);
	 
	 
	   public PartnersGroupResource() {
	    }
	   
	   
	   @GET
	   @Path("/consulta/{identificacion}/{telefono}/{claveAcceso}")
	   @Produces("application/json")
	   public String getPartnerGroupGpf(@PathParam("identificacion")  String identificacion, @PathParam("telefono")  String telefono,@PathParam("claveAcceso")  String claveAcceso){
		   return new PartnersGroupGpf(identificacion,telefono, claveAcceso).getResultadoJson();
	   }
	   
	   
	   
	   @GET
	   @Path("/login/{usario}/{clave}/{claveAcceso}")
	   @Produces("application/json")
	   public String getLogin(@PathParam("usario")  String usario, @PathParam("clave")  String clave,@PathParam("claveAcceso")  String claveAcceso){
		   return new PartnersGroupGpf(usario.toLowerCase(),clave, claveAcceso,"openSSO").getResultadoJson();
	   }
	   
	   
	    @POST
	    @Path("/grabar/")
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    public String  actulizarAddressBook(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException{
	    	
	    	
	    	AbPersonas persona = new AbPersonas();
	    	AbDirecciones direcciones = new AbDirecciones();
	    	AbMediosContacto mediosContacto = new AbMediosContacto() ;
	    	String claveAcceso = "";
	    	String paso="";
	    	try {
				for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
					String value = ConversionsTasks.obtenerValorItemRequest(item);
					
					if (item.getFieldName().equals("claveAcceso")){
						log.info("claveAcceso:"+value);
						if(!"".equals(value) && value != null)
						claveAcceso =value;
					}
					
					if (item.getFieldName().equals("PASO")){
						if(!"".equals(value) && value != null)
						log.info("PASO:"+value);
						paso =value;
					}
					
					//abPersonas
					
					if (item.getFieldName().equals("CODIGOPERSONA")){
						log.info("CODIGOPERSONA:"+value);
						if(!"".equals(value) && value != null)
						persona.setCodigo(Long.valueOf(value));
					}
					if (item.getFieldName().equals("TIPOPERSONA")){
						log.info("TIPOPERSONA:"+value);
						if(!"".equals(value) && value != null)
						persona.setTipoPersona(value);
					}
					if (item.getFieldName().equals("IDENTIFICACION")){
						log.info("IDENTIFICACION:"+value);
						if(!"".equals(value) && value != null)
						persona.setIdentificacion(value);
					}
					if (item.getFieldName().equals("TIPOIDENTIFICACION")){
						log.info("TIPOIDENTIFICACION:"+value);
						if(!"".equals(value) && value != null)
						persona.setTipoIdentificacion(value);
					}
					if (item.getFieldName().equals("RAZONSOCIAL")){
						log.info("RAZONSOCIAL:"+value);
						if(!"".equals(value) && value != null)
						persona.setRazonSocial(value);
					}
					if (item.getFieldName().equals("PRIMERNOMBRE")){
						log.info("PRIMERNOMBRE:"+value);
						if(!"".equals(value) && value != null)
						persona.setPrimerNombre(value);
					}

					if (item.getFieldName().equals("SEGUNDONOMBRE")){
						log.info("SEGUNDONOMBRE:"+value);
						if(!"".equals(value) && value != null)
						persona.setSegundoNombre(value);
					}
					
					if (item.getFieldName().equals("PRIMERAPELLIDO")){
						log.info("PRIMERAPELLIDO:"+value);
						if(!"".equals(value) && value != null)
						persona.setPrimerApellido(value);
					}
					if (item.getFieldName().equals("SEGUNDOAPELLIDO")){
						log.info("SEGUNDOAPELLIDO:"+value);
						if(!"".equals(value) && value != null)
						persona.setSegundoApellido(value);
					}
					
					if (item.getFieldName().equals("REPRESENTANTELEGAL")){
						log.info("REPRESENTANTELEGAL:"+value);
						if(!"".equals(value) && value != null)
						persona.setRepresentanteLegal(value);
					}

					
					if (item.getFieldName().equals("IDENTIFICACIONREPLEGAL")){
						log.info("IDENTIFICACIONREPLEGAL:"+value);
						if(!"".equals(value) && value != null)
						persona.setIdentificacionRepLegal(value);
					}
		
					
					if (item.getFieldName().equals("TIPOIDENTIFICACIONREPLEGAL")){
						log.info("TIPOIDENTIFICACIONREPLEGAL:"+value);
						if(!"".equals(value) && value != null)
						persona.setTipoIdentificacionRepLegal(value);
					}
		
					
					if (item.getFieldName().equals("FECHANACIMIENTO")){
						log.info("FECHANACIMIENTO:"+value);
						if(!"".equals(value) && value != null){
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							try {
								persona.setFechaNacimiento(formatter.parse(value));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					
					
					//abDierecciones 
					
					if (item.getFieldName().equals("CODIGODIRECCION")){
						log.info("CODIGODIRECCION:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setCodigo(Long.valueOf(value));
					}
					
					if (item.getFieldName().equals("PRINCIPAL")){
						log.info("PRINCIPAL:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setPrincipal(value);
					}
					
					if (item.getFieldName().equals("REFERENCIA")){
						log.info("REFERENCIA:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setReferencia(value);
					}

					
					if (item.getFieldName().equals("NUMERO")){
						log.info("NUMERO:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setNumero(value);
					}
					if (item.getFieldName().equals("INTERSECCION")){
						log.info("INTERSECCION:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setInterseccion(value);
					}
					if (item.getFieldName().equals("BARRIO")){
						log.info("BARRIO:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setBarrio(Long.valueOf(value));
					}
					if (item.getFieldName().equals("CIUDAD")){
						log.info("CIUDAD:"+value);
						if(!"".equals(value) && value != null)
						direcciones.setCiudad(Long.valueOf(value));
					}
					if (item.getFieldName().equals("TIPO")){
						log.info("TIPO:"+value);
						if(!"".equals(value) && value != null)
							direcciones.setTipo(Long.valueOf(value));
					}
					
					
					//abMediosContacto
					
					if (item.getFieldName().equals("CODIGOMEDIOSCONTACTO")){
						log.info("CODIGOMEDIOSCONTACTO:"+value);
						if(!"".equals(value) && value != null)
						mediosContacto.setCodigo(Long.valueOf(value));
					}
					
					if (item.getFieldName().equals("VALOR")){
						log.info("VALOR:"+value);
						if(!"".equals(value) && value != null)
						mediosContacto.setValor(value);
					}
					if (item.getFieldName().equals("TIPO")){
						log.info("TIPO:"+value);
						if(!"".equals(value) && value != null)
						mediosContacto.setTipo(Long.valueOf(value));
					}
					
					
					
										
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       return new PartnersGroupGpf(persona,direcciones, mediosContacto ,paso,claveAcceso).getResultadoJson();
	    }

	    
	    
	    
   
	   

}
