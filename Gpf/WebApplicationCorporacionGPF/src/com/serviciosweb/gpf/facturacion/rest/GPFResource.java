/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.rest;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ConversionsTasks;
import com.gpf.postg.pedidos.util.FacadeException;
import com.gpf.postg.pedidos.util.modelo.enums.SiNo;
import com.gpf.serviciosweb.facturacion.farmacias.DatosBasicosEmpleado;
import com.gpf.serviciosweb.facturacion.farmacias.DatosCentroCostos;
import com.gpf.serviciosweb.facturacion.farmacias.DatosCompletosEmpleado;
import com.gpf.serviciosweb.facturacion.farmacias.PeEstadisticasVentas;
import com.gpf.serviciosweb.facturacion.farmacias.VentasConsolidadasMDV;
import com.serviciosweb.gpf.facturacion.farmacias.bean.AdFarmaciasImagenBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FybecaAdminBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.WfArchivosBean;
import com.serviciosweb.gpf.facturacion.servicios.CommonsGpf;
import com.serviciosweb.gpf.facturacion.servicios.ParametrizacionKioskoServicios;
import com.serviciosweb.gpf.facturacion.servicios.Servicios;

/**
 * REST Web Service
 *
 * @author mftellor
 */

@Path("GPFResource")
public class GPFResource {
	private static final Logger log = Logger.getLogger(GPFResource.class);
    @Context
    private UriInfo context;

    /** Creates a new instance of GPFResource */
    public GPFResource() {
    }

    /**
     * Retrieves representation of an instance of com.serviciosweb.gpf.facturacion.rest.GPFResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GPFResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    @GET
    @Path("/categorias/{tipoNegocio}")
    @Produces("application/json")
    public String getCategorias(@PathParam("tipoNegocio") String tipoNegocio){
        return Constantes.serializar(new CommonsGpf().getCategoria(tipoNegocio), "categorias", "Categorias", "104");
    }
    @GET
    @Path("/casas/{idCategoria}")
    @Produces("application/json")
    public String getCasas(@PathParam("idCategoria") String idCategoria){
        return Constantes.serializar(new CommonsGpf().getCasa(idCategoria), "casas", "Casas", "104");
    }
    @GET
    @Path("/productos/{idCasa}")
    @Produces("application/json")
    public String getProductos(@PathParam("idCasa") String idCasa){
        return Constantes.serializar(new CommonsGpf().getProducto(idCasa), "productos", "Productos", "104");
    }

    @GET
    @Path("/grupoLocalizacion/{tipoNegocioBodega}")
    @Produces("application/json")
    public String getGrupoLocalizacion (@PathParam("tipoNegocioBodega") String tipoNegocioBodega){
        if(tipoNegocioBodega.equals("M"))
            tipoNegocioBodega="7";
        else if(tipoNegocioBodega.equals("N"))
            tipoNegocioBodega="8";        
        return Constantes.serializar(new CommonsGpf().getGrupoLocalizacion(tipoNegocioBodega), "grupoLocalizacion", "grupoLocalizacion", "104");
    }
    @GET
    @Path("/grupoModulo/{grupo}")
    @Produces("application/json")
    public String getGrupoModulo (@PathParam("grupo") String grupo){
        return Constantes.serializar(new CommonsGpf().getGrupoModulo(grupo), "grupoModulo", "grupoModulo", "104");
    }

    
    @POST
    @Path("/generarImagenes/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/html")
    //@Path("/imagenes.zip")
    //@Produces("application/download")
    public String  generarImagenes(@FormParam("cmbCategoria-hidden") String cmbCategoria,
            @FormParam("cmbCasa-hidden") String cmbCasa,@FormParam("cmbProducto-hidden") String cmbProducto,
            @FormParam("cmbGrupo-hidden") String cmbGrupo,@FormParam("cmbModulo-hidden") String cmbModulo){
        String filtroQuery="";
        if(cmbCategoria.trim().length()>0 && !cmbCategoria.equals("NULL"))
            filtroQuery+=" AND PRC.SECCION="+cmbCategoria;
        if(cmbCasa.trim().length()>0 && !cmbCasa.equals("NULL"))
            filtroQuery+=" AND PR.CASA="+cmbCasa;
        if(cmbProducto.trim().length()>0 && !cmbProducto.equals("NULL"))
            filtroQuery+=" AND PR.CODIGO="+cmbProducto;
        if(cmbGrupo.trim().length()>0 && !cmbGrupo.equals("NULL"))
            filtroQuery+=" AND DSL.GRUPO='"+cmbGrupo+"'";
        if(cmbModulo.trim().length()>0 && !cmbModulo.equals("NULL"))
            filtroQuery+="  AND DSL.ZONA||DSL.PISO||DSL.FILA_RACK||DSL.MODULO ='"+cmbModulo+"'";
       Servicios.generarImagenesZip(filtroQuery);
       return Constantes.serializar("generarImagenes", "generarImagenes", "LAS IMAGENES SE GENERARON CORRECTAMENTE", "104");
    }
    @GET
    @Path("/generarImagenesjSON/{cmbCategoria-hidden}/{cmbCasa-hidden}/{cmbProducto-hidden}/{cmbGrupo-hidden}/{cmbModulo-hidden}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public String  generarImagenesjSON(@PathParam("cmbCategoria-hidden") String cmbCategoria,
            @PathParam("cmbCasa-hidden") String cmbCasa,@PathParam("cmbProducto-hidden") String cmbProducto,
            @PathParam("cmbGrupo-hidden") String cmbGrupo,@PathParam("cmbModulo-hidden") String cmbModulo){
        String filtroQuery="";
                if(cmbCategoria.trim().length()>0 && !cmbCategoria.equals("NULL"))
            filtroQuery+=" AND PRC.SECCION="+cmbCategoria;
        if(cmbCasa.trim().length()>0 && !cmbCasa.equals("NULL"))
            filtroQuery+=" AND PR.CASA="+cmbCasa;
        if(cmbProducto.trim().length()>0 && !cmbProducto.equals("NULL"))
            filtroQuery+=" AND PR.CODIGO="+cmbProducto;
        if(cmbGrupo.trim().length()>0 && !cmbGrupo.equals("NULL"))
            filtroQuery+=" AND DSL.GRUPO='"+cmbGrupo+"'";
        if(cmbModulo.trim().length()>0 && !cmbModulo.equals("NULL"))
            filtroQuery+="  AND DSL.ZONA||DSL.PISO||DSL.FILA_RACK||DSL.MODULO ='"+cmbModulo+"'";
       return Servicios.getImagenesJson(filtroQuery);

    }
   @GET
   @Path("/imagenes.zip")
   @Produces("application/download")
   public File GenerarImagenes() {       
       return new File("/data/imagenes/imagenes.zip");
   }

   @GET
   @Path("/getImagenItem/{codigoItem}")
   @Produces("application/download")
   public File getImagenItem(@PathParam("codigoItem") String codigoItem) {
       codigoItem=codigoItem.split("\\.")[0];
       return Servicios.getImagenItem(codigoItem);
   }

   @GET
   @Path("/getImagenItemJPG/{codigoItem}")
   @Produces("image/jpeg")
   public File getImagenItemJPG(@PathParam("codigoItem") String codigoItem) {
       codigoItem=codigoItem.split("\\.")[0];
       return Servicios.getImagenItem(codigoItem);
   }

   @GET
   @Path("/getImagenWfArchivos/{idWfArchivos}")
   @Produces("image/jpeg")
   public File getImagenWfArchivos(@PathParam("idWfArchivos") String idWfArchivos) {	   
	   //System.out.println("idWfArchivos  "+idWfArchivos);
	   idWfArchivos=idWfArchivos.split("\\.")[0];
	   try{
		   Integer.parseInt(idWfArchivos.trim());
		   File imagen=Servicios.outputtingBarcodeAsPNG(idWfArchivos);
       return imagen;
	   }catch (Exception e){
		   e.printStackTrace();
		   return null;
	   }
       
   }
   
   
   @GET
   @Path("/getImagenWfArchivos107/{codigoEmpleadoEmpresaAnyo}")
   @Produces("image/jpeg")
   public File getImagenWfArchivos107(@PathParam("codigoEmpleadoEmpresaAnyo") String codigoEmpleadoEmpresaAnyo) {	   
	   //System.out.println("codigoEmpleadoEmpresaAnyo  "+codigoEmpleadoEmpresaAnyo);
	   codigoEmpleadoEmpresaAnyo=codigoEmpleadoEmpresaAnyo.split("\\.")[0];
	   try{
		   String[] codigos = codigoEmpleadoEmpresaAnyo.split("-");
		   return Servicios.outputtingBarcodeAsPNG(codigos[0].trim(),codigos[1].trim(),codigos[2].trim());       
	   }catch (Exception e){
		   e.printStackTrace();
		   return null;
	   }       
   }
   
   @GET
   @Path("/getImagenWfArchivosContador/{idWfArchivos}")
   @Produces("image/jpeg")
   public byte[] getImagenWfArchivosContador(@PathParam("idWfArchivos") String idWfArchivos) {	   
	   //System.out.println("idWfArchivos  "+idWfArchivos.trim());
	   idWfArchivos=idWfArchivos.split("\\.")[0];
       try{
		   Integer.parseInt(idWfArchivos);
		   return Servicios.getImagenWfArchivosContador(idWfArchivos);       
	   }catch (Exception e){
		   e.printStackTrace();
		   return null;
	   }
   }
   /*
   @GET
   @Path("/getImagenWfArchivos")
   @Produces("text/plain")
   public String getImagenes(){
	  System.out.println("empieza"); 
	  Servicios.getImagenes();
      return "EMPIEZA";
   }
   */
    @GET
    @Path("/copiarImagenesLocales")
    @Produces("application/json")

    public String copiarImagenesLocales(){
        Servicios.copiarImagenes("1271");
        return "";
    }
   
    /***************************   PARAMETRIZACION KIOSKO    ************************/
    
    @Path("/cargarFarmaciasHorarios/{codigoLocal}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarFarmaciasHorarios(@PathParam("codigoLocal") Long codigoLocal) throws FacadeException {
		/*
		AdTiposHorariosFacadeRemote servicio = ServiceLocatorProvider.lookupAdTiposHorariosBean();
		AdFarmaciasHorariosFacadeRemote servicioFarmaciasHorarios = ServiceLocatorProvider.lookupAdFarmaciasHorariosBean();
		List<AdTiposHorarios> lst = new ArrayList<AdTiposHorarios>();
		List<FarmaciasHorariosDTO> resultDto = new ArrayList<FarmaciasHorariosDTO>();
		lst=servicio.findAll();
		
		for (AdTiposHorarios th:lst){
			System.out.println(th.getNombre());
			FarmaciasHorariosDTO dtoH= new FarmaciasHorariosDTO();
			resultDto.addAll(servicioFarmaciasHorarios.buscartiposHorarios(codigoLocal, th.getCodigo()));
		}
		*/
    	log.info("...cargando Farmacias Horarios...");
    	return new ParametrizacionKioskoServicios().getFarmaciasHorarios(codigoLocal);
		//return ConversionsTasks.serializar(resultDto,"cargarFarmaciasHorarios","Carga Horarios","104");
		
	}
    @Path("/cargarAdFarmacias")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarAdFarmacias() throws FacadeException {
    	log.info("...cargando AdFarmacias...");
    	return new ParametrizacionKioskoServicios().getFarmacias();
	
		
	}
    
	@Path("/cargarGeoLocalizacion/{codigoLocal}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarGeoLocalizacion(@PathParam("codigoLocal") Long codigoLocal) throws FacadeException {
		log.info("...cargando Geolocalizacion...");
		return new ParametrizacionKioskoServicios().getGeoLocalizacion(codigoLocal);
		
	}
    
	@Path("/cargarServicios/{codigoLocal}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarServicios(@PathParam("codigoLocal") Long codigoLocal) throws FacadeException {
		log.info("...cargando Farmacias Servicios...");
		return new ParametrizacionKioskoServicios().getFarmaciasServicios(codigoLocal);
		
	}
	
	@Path("/cargarTiposHorarios")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarTiposHorarios() throws FacadeException {
		log.info("...cargando Tipos Horarios...");
		return new ParametrizacionKioskoServicios().getTiposHorarios();
		
	}
	
	
	
	@POST
	@Path("/guardarTiposHorarios")
	@Produces("text/html")
	@Consumes("multipart/form-data")

	public String guardarTiposHorarios(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException{
		
		log.info("...guardando Tipos Horarios...");
		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		String nuevo="";
		String codigoFarmaciasHorarios=null;
		String codigoTipoHorario = null;
		String codigoFarmacia=null;
		String desde=null;
		String hasta=null;
		
		try {
			for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
				String value = ConversionsTasks.obtenerValorItemRequest(item);
				
				if (item.getFieldName().equals("myHiddenFieldCodigoFarmaciasHorarios")){
					log.info("myHiddenFieldCodigoFarmaciasHorarios:"+value);
					codigoFarmaciasHorarios=value;
				}
				if (item.getFieldName().equals("myHiddenFieldCodigoTipoHorario")){
					log.info("myHiddenFieldCodigoTipoHorario:"+value);
					codigoTipoHorario=value;
				}
				if (item.getFieldName().equals("myHiddenFieldCodigoFarmacia")){
					log.info("myHiddenFieldCodigoFarmacia:"+value);
					codigoFarmacia=value;
				}
				if (item.getFieldName().equals("myTextFieldDesde")){
					log.info("myTextFieldDesde:"+value);
					desde=value;
				}
				if (item.getFieldName().equals("myTextFieldHasta")){
					log.info("myTextFieldHasta:"+value);
					hasta=value;
				}
				if (item.getFieldName().equals("myHiddenFieldIsNew")){
					log.info("myHiddenFieldIsNew:"+value);
					nuevo=value;
				}
				
			}

			if (nuevo.equals(SiNo.S.name())){
				//servicios.buscartiposHorarios(codigoFarmacia,codigoTipoHorario);
				if(servicios.buscartiposHorarios(codigoFarmacia,codigoTipoHorario)){
					
					codigoFarmaciasHorarios=(servicios.obtenerMaxAdFarmaciasHorarios()).toString();
					servicios.insertFarmaciasHorarios(codigoFarmaciasHorarios, desde, hasta, codigoTipoHorario, codigoFarmacia);
					
				}else{
					return ConversionsTasks.serializar("guardarTiposHorarios", "guardarTiposHorarios","Ya existe el Tipo Horario", "404");
				}
					

			}else{//editar
					servicios.updateFarmaciasHorarios(codigoFarmaciasHorarios, desde, hasta);

			}
			FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
			fybecaAdmin.setCom_codigo(String.valueOf(codigoFarmacia));
			fybecaAdmin.setCon_horario(servicios.obtenerHorarioAdFarmaciasHorarios(String.valueOf(codigoFarmacia)));
			if(actulizacionFybecaAdmin(fybecaAdmin))
				return ConversionsTasks.serializar("guardarTiposHorarios", "guardarTiposHorarios", "Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ", "404");
		} catch (FileUploadException ex) {
			
			return ConversionsTasks.serializar(ex, "guardarTiposHorarios", ex.toString(), "404");
		} catch (UnsupportedEncodingException e) {
			
			return ConversionsTasks.serializar(e, "guardarTiposHorarios", e.toString(), "404");
		}
		return ConversionsTasks.serializar("guardarTiposHorarios", "guardarTiposHorarios", "Horario grabado con exito", "104");
	}	
	
	

	@GET
	@Path("/borrarFarmaciasHorarios/{codigoTipoHorario}/{codigoFarmacia}")
	@Produces(MediaType.TEXT_PLAIN)

	public String borrarFarmaciasHorarios(@PathParam("codigoTipoHorario") String codigoTipoHorario,@PathParam("codigoFarmacia")String codigoFarmacia) throws SQLException  {
		
		log.info("...borrando Farmacias Horarios ...");

		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		servicios.eliminarFarmaciasHorarios(codigoTipoHorario, codigoFarmacia);
		
		FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
		fybecaAdmin.setCom_codigo(String.valueOf(codigoFarmacia));
		fybecaAdmin.setCon_horario(servicios.obtenerHorarioAdFarmaciasHorarios(String.valueOf(codigoFarmacia)));
		if(actulizacionFybecaAdmin(fybecaAdmin))
			return ConversionsTasks.serializar("borrarFarmaciasHorarios", "borrarFarmaciasHorarios", "Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ", "404");
		return ConversionsTasks.serializar("borrarFarmaciasHorarios", "borrarFarmaciasHorarios", "Servicio Borrado", "104");
	}	
	
	/***********************************************   GEOLOCALIZACION   ******************************/
    
	@POST
	@Path("/guardarGeoLocalizacion")
	@Produces("text/html")
	@Consumes("multipart/form-data")

	public String guardarGeoLocalizacion(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException{
		
		log.info("...guardando Geo Localizacion...");
		String nuevo="";
		String codigoGeolocalizacion = null;
		String codigoFarmacia = null;
		String longitud = null;
		String latitud=null;
		String url1=null;
		String distanciaKilometros=null;
		
		
		try {
			for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
				String value = ConversionsTasks.obtenerValorItemRequest(item);
				
				if (item.getFieldName().equals("myHiddenFieldCodigoGeoLocalizacion")){
					log.info("myHiddenFieldCodigoGeoLocalizacion:"+value);
					//adFarmaciasGeolocalizacion.setCodigo(value.equals("")?null:Long.parseLong(value));
					codigoGeolocalizacion=value;
				}
				if (item.getFieldName().equals("myHiddenFieldCodigoFarmacia")){
					log.info("myHiddenFieldCodigoFarmacia:"+value);
					//adFarmacias.setCodigo(value.equals("")?null:Long.parseLong(value));
					//adFarmaciasGeolocalizacion.setCodigoFarmacia(adFarmacias);
					codigoFarmacia=value;
				}
				if (item.getFieldName().equals("myTextFieldLongitud")){
					log.info("myTextFieldLongitud:"+value.replace(",", "."));
					//Double.parseDouble(value);
					//Double d = Double.parseDouble(value.replace(",", "."));
					//adFarmaciasGeolocalizacion.setLongitud(Double.parseDouble(value.replace(",", ".")));
					longitud=value.replace(",", ".");
				}
				if (item.getFieldName().equals("myTextFieldLatitud")){
					log.info("myTextFieldLatitud:"+value.replace(",", "."));
					//adFarmaciasGeolocalizacion.setLatitud(Double.parseDouble(value.replace(",", ".")));
					latitud=value.replace(",", ".");

				}
				if (item.getFieldName().equals("myTextFieldUrl")){
					log.info("myTextFieldUrl:"+value);
					//adFarmaciasGeolocalizacion.setUrl1(value);
					url1=value;
				}
				
				if (item.getFieldName().equals("myTextFieldDistanciaKm")){
					log.info("myTextFieldDistanciaKm:"+value);
					//adFarmaciasGeolocalizacion.setUrl1(value);
					distanciaKilometros=value;
				}
				if (item.getFieldName().equals("myHiddenFieldIsNew")){
					log.info("myHiddenFieldIsNew:"+value);
					nuevo=value;
				}
				
			}
			ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
			
			
			
			//if (nuevo.equals(SiNoA.SI.getVALOR())){
			if (nuevo.equals(SiNo.S.name())){
				if(servicios.buscarGeolocalizacion(codigoFarmacia)){
					codigoGeolocalizacion=servicios.obtenerMaxGeolocalizacion().toString();
					servicios.insertGeolocalizacion(codigoGeolocalizacion, longitud, latitud, codigoFarmacia, url1,distanciaKilometros);
					
				}else{
					return ConversionsTasks.serializar("guardarGeoLocalizacion", "guardarGeoLocalizacion","Ya existe la Geolocalizacion", "404");
				}
					

			}else{//editar
					servicios.updateGeolocalizacion(codigoGeolocalizacion, longitud, latitud, url1,distanciaKilometros);

			}
			
			FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
			fybecaAdmin.setCom_codigo(String.valueOf(codigoFarmacia));
			fybecaAdmin.setSit_latitud(latitud);
			fybecaAdmin.setSit_longitud(longitud);
			if(actulizacionFybecaAdmin(fybecaAdmin))
				return ConversionsTasks.serializar("guardarGeoLocalizacion", "guardarGeoLocalizacion", "Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ", "404");
			
			
		} catch (FileUploadException ex) {
			
			return ConversionsTasks.serializar(ex, "guardarGeoLocalizacion", ex.toString(), "404");
		} catch (UnsupportedEncodingException e) {
			
			return ConversionsTasks.serializar(e, "guardarGeoLocalizacion", e.toString(), "404");
		}
		return ConversionsTasks.serializar("guardarGeoLocalizacion", "guardarGeoLocalizacion", "Geolocalizacion Guardada", "104");
	}		
	
	/***********************************************  FARMACIA SERVICIOS   ******************************/
	
	@POST
	@Path("/guardarFarmaciasServicios")
	@Produces("text/html")
	@Consumes("multipart/form-data")

	public String guardarFarmaciasServicios(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException{
		
		log.info("...guardando Farmacias Servicios ...");

		String nuevo="";
		String codigoFarmacia=null;
		String codigoServiciosFarmacias=null;
		String codigoFarmaciasServicios=null;

		try {
			for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
				String value = ConversionsTasks.obtenerValorItemRequest(item);
				
				if (item.getFieldName().equals("myHiddenFieldCodigoFarmacia")){
					log.info("myHiddenFieldCodigoFarmacia:"+value);
					codigoFarmacia=value;
				}
				if (item.getFieldName().equals("myHiddenFieldCodigoServiciosFarmacias")){
					log.info("myHiddenFieldCodigoServiciosFarmacias:"+value);
					codigoServiciosFarmacias=value;
				}
				if (item.getFieldName().equals("myHiddenFieldIsNew")){
					log.info("myHiddenFieldIsNew:"+value);
					nuevo=value;
				}
				
			}		
			
			ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
			//if (nuevo.equals(SiNoA.SI.getVALOR())){
			if (nuevo.equals(SiNo.S.name())){
				if(servicios.buscarTiposServicios(codigoFarmacia, codigoServiciosFarmacias)){
					codigoFarmaciasServicios=servicios.obtenerMaxFarmaciasServicios().toString();
					servicios.insertFarmaciasServicios(codigoFarmaciasServicios, codigoServiciosFarmacias, codigoFarmacia);
					
					FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
					fybecaAdmin.setCom_codigo(String.valueOf(codigoFarmacia));
					fybecaAdmin.setSer_servicio(servicios.obtenerCodigoMapeoServicios(codigoServiciosFarmacias));
					fybecaAdmin.setEliminar(Boolean.FALSE);
					if(actulizacionFybecaAdmin(fybecaAdmin))
						return ConversionsTasks.serializar("guardarFarmaciasServicios", "guardarFarmaciasServicios", "Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ", "404");
				}else{
					return ConversionsTasks.serializar("guardarFarmaciasServicios", "guardarFarmaciasServicios","Ya existe el servicio", "404");
				}
					

			}//else{//editar
				//	servicio.edit(adFarmaciasServicios);

			//}
		} catch (FileUploadException ex) {
			
			return ConversionsTasks.serializar(ex, "guardarFarmaciasServicios", ex.toString(), "404");
		} catch (UnsupportedEncodingException e) {
			
			return ConversionsTasks.serializar(e, "guardarFarmaciasServicios", e.toString(), "404");
		}
		return ConversionsTasks.serializar("guardarFarmaciasServicios", "guardarFarmaciasServicios", "Servicio Guardado", "104");
	}		
	
/*********************************************** ELIMINAR  FARMACIA SERVICIOS    ******************************/

	@GET
	@Path("/borrarFarmaciasServicios/{codigoServiciosFarmacias}/{codigoFarmacia}")
	@Produces(MediaType.TEXT_PLAIN)

	public String borrarFarmaciasServicios(@PathParam("codigoServiciosFarmacias") String codigoServiciosFarmacias,@PathParam("codigoFarmacia")String codigoFarmacia) throws SQLException  {
		
		log.info("...borrando Farmacias Servicios ...");

		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		servicios.eliminarFarmaciasServicios(codigoServiciosFarmacias, codigoFarmacia);
		
		FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
		fybecaAdmin.setCom_codigo(String.valueOf(codigoFarmacia));
		fybecaAdmin.setSer_servicio(servicios.obtenerCodigoMapeoServicios(codigoServiciosFarmacias));
		fybecaAdmin.setEliminar(Boolean.TRUE);
		if(actulizacionFybecaAdmin(fybecaAdmin))
			return ConversionsTasks.serializar("borrarFarmaciasServicios", "borrarFarmaciasServicios", "Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ", "404");
		return ConversionsTasks.serializar("borrarFarmaciasServicios", "borrarFarmaciasServicios", "Servicio Borrado", "104");
	}	
	@Path("/cargarServiciosFarmaciasCombo")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarServiciosFarmaciasCombo() throws FacadeException {
		log.info("...cargando Servicios Farmacias Combo...");
		return new ParametrizacionKioskoServicios().getServiciosFarmaciasCombo();
		
	}
	
	/******************************************** FOTOS CENTROS COSTOS *********************************/
	
/***********************************************  FARMACIA GUARDAR FOTOS CENTROS COSTO   ******************************/
	
	@POST
	@Path("/guardarFotosCentrosCosto")
	@Produces("text/html")
	@Consumes("multipart/form-data")

	public String guardarFotosCentrosCosto(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException{
		
		log.info("...guardando Farmacias Servicios ...");

		String nuevo="";
	
		WfArchivosBean archivo = new WfArchivosBean();
		AdFarmaciasImagenBean farmaciaImagen = new AdFarmaciasImagenBean();
		long sizeInBytes = 0L;

		try {
			for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
				String value = ConversionsTasks.obtenerValorItemRequest(item);
				
				if (item.getFieldName().equals("myHiddenFieldCodigoFarmacia")){
					farmaciaImagen.setCodigoFarmacia(Long.parseLong(value));
                    continue;
				}
				if (item.getFieldName().equals("myHiddenFieldCodigoArchivo")){
					if(!"".equals(value))
						farmaciaImagen.setCodigoArchivo(Long.parseLong(value));
                    continue;
				}
				if (item.getFieldName().equals("myHiddenFieldMostar")){
					farmaciaImagen.setMostar(value);
                    continue;
				}
				if (item.getFieldName().equals("radMostrar")){
					farmaciaImagen.setMostar(value);
                    continue;
				}
				if (item.getFieldName().equals("myHiddenFieldIsNew")){
					nuevo=value;
                    continue;
				}
				if (item.getFieldName().equals("myTextFieldNombreFoto")){
					 archivo.setFileName(value);
                    continue;
				}
				
				 if (!item.isFormField()) {
					
					 
			             String fieldName = item.getFieldName();
			             String contentType = item.getContentType();
			             sizeInBytes = item.getSize();
			             
			             byte[] data = item.get();
			             if (fieldName.equals("imgFotoLocal")) {
			            	 archivo.setContenido(data);
			            	 archivo.setFechaCreacion(new Date(new java.util.Date().getTime() ));
			            	 archivo.setMinyType(contentType);
			            	 archivo.setUsuarioCreacion("rpriverae");
			            	 archivo.setFullFileName(fieldName);
			            	 archivo.setTamanio(sizeInBytes);
			            	 continue;
			             }
				 }
			}		
			
			if(sizeInBytes>= 102400){
				return ConversionsTasks.serializar("guardarFotosCentrosCosto", "guardarFotosCentrosCosto", "Imagen no guardada. El tama�o no debe superar los 100K", "404");
			}
			
			
			ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
			
			
			
			
			if("N".equals(farmaciaImagen.getMostar())){
				int codArchivo=servicios.buscarImgenActiva(farmaciaImagen.getCodigoFarmacia());
				if( codArchivo != 0){
					if( ( null != farmaciaImagen.getCodigoArchivo()  && codArchivo == farmaciaImagen.getCodigoArchivo() ) ){
						return ConversionsTasks.serializar("guardarFotosCentrosCosto", "guardarFotosCentrosCosto","Debe existe una imgen Activa", "404");
					}
				}
			}
			if (nuevo.equals(SiNo.S.name())) {
				archivo.setCodigo(servicios.obtenerMaxWfArchivos());
				if (servicios.insertWfArchivos(archivo)) {
					farmaciaImagen.setCodigoArchivo(archivo.getCodigo());
					servicios.insertFotosCentrosCosto(farmaciaImagen);
				}
			} else {
				archivo.setCodigo(farmaciaImagen.getCodigoArchivo());
				if (servicios.editarWfArchivos(archivo)) {
					farmaciaImagen.setCodigoArchivo(archivo.getCodigo());
					servicios.editarFotosCentrosCosto(farmaciaImagen);
				}
			}
			
			if("S".equals(farmaciaImagen.getMostar())){
				//int codArchivo=servicios.buscarImgenActiva(farmaciaImagen.getCodigoFarmacia());
				//if( codArchivo != 0){
				//	if( ( null != farmaciaImagen.getCodigoArchivo()  && codArchivo != farmaciaImagen.getCodigoArchivo() ) ){
						servicios.editarFotosCentrosCostoInactivas(farmaciaImagen);
				//	}
				//}
			}
			
				FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
				fybecaAdmin.setCom_codigo(String.valueOf(farmaciaImagen.getCodigoFarmacia()));
				try {
					fybecaAdmin.setCon_imagen(servicios.getImagenCodigoWfArchivos(servicios.buscarCodigoImgenActiva(farmaciaImagen.getCodigoFarmacia())));
				} catch (IOException e) {
					e.printStackTrace();
					return ConversionsTasks.serializar("guardarFotosCentrosCosto","guardarFotosCentrosCosto","Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ","404");
					
				}
				if (actulizacionFybecaAdmin(fybecaAdmin))
					return ConversionsTasks.serializar("guardarFotosCentrosCosto","guardarFotosCentrosCosto","Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ","404");
			
		} catch (FileUploadException ex) {
			
			return ConversionsTasks.serializar(ex, "guardarFotosCentrosCosto", ex.toString(), "404");
		} catch (UnsupportedEncodingException e) {
			
			return ConversionsTasks.serializar(e, "guardarFotosCentrosCosto", e.toString(), "404");
		}
		return ConversionsTasks.serializar("guardarFotosCentrosCosto", "guardarFotosCentrosCosto", "Fotos centros costo guardado", "104");
	}
	

	/*******************************************  VER IMAGEN ***********************************************/
	
	   @GET
	   @Path("/verImagen/{codigoImagen}")
	   @Produces({"image/bmp","image/gif","image/jpeg","image/pjpeg","image/jpeg","image/pjpeg","image/jpeg","image/pjpeg","image/png","image/x-png","image/tiff","image/tiff"})
	   public byte[] ListaPrecioProductoIMG(@PathParam("codigoImagen") String codigoImagen) {
			try {
				ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
				return servicios.getImagenCodigoWfArchivos(codigoImagen);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	   }
	   
	   
	   
		/*******************************************  VER IMAGEN ***********************************************/
		
	   @GET
	   @Path("/verImagenSql/{codigoImagen}")
	   @Produces({"image/bmp","image/gif","image/jpeg","image/pjpeg","image/jpeg","image/pjpeg","image/jpeg","image/pjpeg","image/png","image/x-png","image/tiff","image/tiff"})
	   public byte[] verImagenSql(@PathParam("codigoImagen") String codigoImagen) {
			try {
				ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
				return servicios.getImagenCodigoSqServer(codigoImagen);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	   }
	   /************************************** ELIMINAR WF_ARCHIVOS AD_FARMACIAS_IMAGEN******************************/

	@GET
	@Path("/borrarFotoCentrosCostos/{codigoArchivo}/{codigoFarmacia}")
	@Produces(MediaType.TEXT_PLAIN)

	public String borrarFotoCentrosCostos(@PathParam("codigoArchivo") String codigoArchivo,@PathParam("codigoFarmacia")String codigoFarmacia) throws SQLException  {
		
		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		
		int codArchivo=servicios.buscarImgenActiva(Long.valueOf(codigoFarmacia));
		if( codArchivo != 0){
			if( ( null != codigoArchivo  && codArchivo ==Integer.parseInt(codigoArchivo )) ){
				return ConversionsTasks.serializar("borrarFotoCentrosCostos", "borrarFotoCentrosCostos","Debe existe una imgen Activa", "404");
			}
		}
		
		if(servicios.eliminarFotoCentosCosto(codigoArchivo, codigoFarmacia)){
			if (servicios.eliminarWfArchivos(codigoArchivo)) {

				FybecaAdminBean fybecaAdmin = new FybecaAdminBean();
				fybecaAdmin.setCom_codigo(codigoFarmacia);
				try {
					fybecaAdmin.setCon_imagen(servicios
							.getImagenCodigoWfArchivos(servicios
									.buscarCodigoImgenActiva(Long
											.parseLong(codigoFarmacia))));
				} catch (IOException e) {
					e.printStackTrace();
					return ConversionsTasks.serializar("guardarFotosCentrosCosto","guardarFotosCentrosCosto","Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ","404");

				}

				if (actulizacionFybecaAdmin(fybecaAdmin))
					return ConversionsTasks.serializar("guardarFotosCentrosCosto","guardarFotosCentrosCosto","Hubo un error al Actualizar la aplicacion movil Consulte con el administrador ","404");

				return ConversionsTasks.serializar("borrarFotoCentrosCostos",
						"borrarFotoCentrosCostos", "Imagen Borrada", "104");
			} else {
				return ConversionsTasks.serializar("borrarFotoCentrosCostos",
						"borrarFotoCentrosCostos",
						"No se puedo eliminar la imagen", "404");

			}
			
			
			
		}else{
			return ConversionsTasks.serializar("borrarFotoCentrosCostos", "borrarFotoCentrosCostos", "No se puedo eliminar la imagen", "404");
		}
		
	
	}		
	
	@Path("/cargarServiciosFarmaciasCombo")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarFotoCentrosCostoCombo() throws FacadeException {
		log.info("...cargando Servicios Farmacias Combo...");
		return new ParametrizacionKioskoServicios().getServiciosFarmaciasCombo();
		
	}

	
	
	
	
	/*********************************************EDN FOTOS CENTROS COSTOS *****************************/


/***********************************************  SERVICIOS FARMACIAS   n ******************************/
	/*
	@GET
	@Path("/guardarServiciosFarmacias/{codigo}/{nombre}/{activo}")
	@Produces(MediaType.TEXT_PLAIN)
	

	public String guardarServiciosFarmacias(@PathParam("codigo") String codigo,@PathParam("nombre") String nombre,@PathParam("activo") String activo) throws FileUploadException, FacadeException, SQLException{
	*/	
	@POST
	@Path("/guardarServiciosFarmacias")
	@Produces("text/html")
	@Consumes("multipart/form-data")

	public String guardarServiciosFarmacias(@Context HttpServletRequest request) throws FileUploadException, FacadeException, SQLException, UnsupportedEncodingException{
		log.info("...guardando Servicios Farmacias ...");

		String nuevo="";
		String codigo=null;
		String nombre=null;
		String activo=null;
		try{
			for (FileItem item:ConversionsTasks.obtenerListadoItemsRequest(request)){
				//System.out.println(item.getFieldName());
				String value = ConversionsTasks.obtenerValorItemRequest(item);

				if (item.getFieldName().equals("hiddenFieldCodigo")){
					log.info("hiddenFieldCodigo:"+value);
					codigo=value;
				}
				if (item.getFieldName().equals("myTextFieldNombre")){
					log.info("myTextFieldNombre:"+value);
					nombre=value;
				}
				
				if (item.getFieldName().equals("rbDisminuye1")){
					log.info("rbDisminuye1:"+value);
					activo=value;
				}
				if (item.getFieldName().equals("rbDisminuye0")){
					log.info("rbDisminuye0:"+value);
					activo=value;
				}
				if (item.getFieldName().equals("hiddenFieldIsNew")){
					log.info("myHiddenFieldIsNew:"+value);
					nuevo=value;
				}
				
			}		
			
			ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
			//if (nuevo.equals(SiNoA.SI.getVALOR())){
			if (nuevo.equals(SiNo.S.name())){
				if(servicios.buscarServiciosFarmacias(nombre)){
					codigo=servicios.obtenerMaxServiciosFarmacias().toString();
					servicios.insertServiciosFarmacias(codigo, nombre, activo);
					
				}else{
				//	servicios.updateServiciosFarmacias(codigo,nombre,activo);
					return ConversionsTasks.serializar("guardarServiciosFarmacias", "guardarServiciosFarmacias","Ya existe el servicio", "404");
				
				}
					

			}else{//editar
					servicios.updateServiciosFarmacias(codigo, nombre, activo);

			}
	} catch (FileUploadException ex) {
		
		return ConversionsTasks.serializar(ex, "guardarServiciosFarmacias", ex.toString(), "404");
	} catch (UnsupportedEncodingException e) {
		
		return ConversionsTasks.serializar(e, "guardarServiciosFarmacias", e.toString(), "404");
	}
	return ConversionsTasks.serializar("guardarServiciosFarmacias", "guardarServiciosFarmacias", "Servicio Guardado", "104");
	}		
    
	/*********************************************** ELIMINAR  SERVICIOS FARMACIA     ******************************/

	@GET
	@Path("/borrarServiciosFarmacias/{codigo}")
	@Produces(MediaType.TEXT_PLAIN)

	public String borrarServiciosFarmacias(@PathParam("codigo") String codigo) throws SQLException  {
		
		log.info("...borrando Servicios Farmacias...");

		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		servicios.eliminarServiciosFarmacias(codigo);
		return ConversionsTasks.serializar("borrarServiciosFarmacias", "borrarServiciosFarmacias", "Servicio Borrado", "104");
	}		
	
	@Path("/cargarServiciosFarmaciasGrid")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarServiciosFarmaciasGrid() throws FacadeException {
		log.info("...cargando Servicios Farmacias...");
		return new ParametrizacionKioskoServicios().getServiciosFarmaciasGrid();
		
	}
	@Path("/getDatosBasicosEmpleado/{cedula}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getDatosBasicosEmpleado(@PathParam("cedula") String cedula){
		DatosBasicosEmpleado datosBasicosEmpleado=new DatosBasicosEmpleado(cedula);
		return datosBasicosEmpleado.getResultado();
	}
	
	/*CAMBIOS KIOSKO*/
	
	
	
	public Boolean actulizacionFybecaAdmin(FybecaAdminBean fybecaAdmin) {
		
		if(new ParametrizacionKioskoServicios().validarFarmacia(fybecaAdmin.getCom_codigo()))
			return Boolean.FALSE;
		
		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		fybecaAdmin.setSit_id(servicios.buscarFarnaciasCodigoSitio(fybecaAdmin.getCom_codigo()));

		if (fybecaAdmin.getSit_id() == 0) {
			fybecaAdmin = inifybecaAdmin(fybecaAdmin);
			if (servicios.ingresarBboSitio(fybecaAdmin)) {
				fybecaAdmin.setSit_id(servicios.buscarMaxSitId());
				if (!servicios.ingresarBboComercio(fybecaAdmin)) {
					return Boolean.TRUE;
				}
			}
		}

		if (fybecaAdmin.getCon_imagen() != null)
			return !servicios.actulizarComImagen(fybecaAdmin);
		if (fybecaAdmin.getCon_horario() != null)
			//return !servicios.actulizarComHorarios(fybecaAdmin);
			return actulizarHorarios( fybecaAdmin);
		if (fybecaAdmin.getSit_latitud() != null && fybecaAdmin.getSit_longitud() != null)
			return !servicios.actulizarSitLatitudSitLongitud(fybecaAdmin);
		if (fybecaAdmin.getSer_servicio() > 0 && !fybecaAdmin.getEliminar())
			return !servicios.ingresarServicio(fybecaAdmin);
		if (fybecaAdmin.getSer_servicio() > 0 && fybecaAdmin.getEliminar()) {
			return !servicios.eliminarServicio(fybecaAdmin);
		}

		return Boolean.TRUE;

	}
	

	public Boolean actulizarHorarios(FybecaAdminBean fybecaAdmin){
		
		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		int codigoHorario = servicios.buscarHorario(fybecaAdmin.getCom_codigo());
		if(codigoHorario == 0){
			if(servicios.ingresarSitHorario("HORARIO "+fybecaAdmin.getCom_codigo())){
				codigoHorario =servicios.buscarMaxSitHorario();
				servicios.ingresarSitHorarioSitio(codigoHorario,fybecaAdmin.getSit_id());
			}
		}

		try {
			
			List<HashMap<String,String>> listHorariosWeb =servicios.obtenerListadoAdFarmaciasHorarios(fybecaAdmin.getCom_codigo());
			List<HashMap<String,String>> listHorariosFybecaAdmin = servicios.buscarSitDetalleHorario(codigoHorario);
			
			for (HashMap<String,String> horarios:listHorariosWeb ){
				String horario = "LUNES;MARTES;MIERCOLES;JUEVES;VIERNES";
				if(!"LUNES A VIERNES".equals(horarios.get("nombre")))
					horario =horarios.get("nombre");
				
				if("SABADOS".equals(horarios.get("nombre")))
					horario ="SABADO";
				
				if("DOMINGOS".equals(horarios.get("nombre")))
					horario ="DOMINGO";
				
				String [] desde = horarios.get("desde").split(":");
				String [] hasta = horarios.get("hasta").split(":");
				
				Boolean nuevoValor = Boolean.TRUE;
				for (HashMap<String,String> dato: listHorariosFybecaAdmin ){
					if( horario.contains(dato.get("nombreDia"))){
						int codigoDia =Integer.parseInt(dato.get("codigoDia"));
						servicios.actulizarIntervalos(Integer.parseInt(desde[0])
													,Integer.parseInt(desde[1])
													,Integer.parseInt(hasta[0])
													,Integer.parseInt(hasta[1])
													,codigoDia);
						nuevoValor = Boolean.FALSE;
					}
				}
				
				if(nuevoValor){
				  for(String nombreDia: horario.split(";")){
					  String horDiaSemana = servicios.buscarSitDetalleHorario(nombreDia);
					  if(servicios.ingresarDetalleHorario(codigoHorario , Integer.parseInt(horDiaSemana))){
						  int dhoId =servicios.buscarMaxDetalleHorario();
						  servicios.ingresarItervalo(dhoId
								,Integer.parseInt(desde[0])
								,Integer.parseInt(hasta[0])
								,Integer.parseInt(desde[1])
								,Integer.parseInt(hasta[1])) ;
					  }
				  }
				}
			}
			
			
			
			
			for (HashMap<String,String> dato: listHorariosFybecaAdmin ){
				String horario = "";
				if("LUNES,MARTES,MIERCOLES,JUEVES,VIERNES".contains(dato.get("nombreDia")))
					horario ="LUNES A VIERNES";
				
				if("SABADO".equals(dato.get("nombreDia")))
					horario ="SABADOS";
				
				if("DOMINGO".equals(dato.get("nombreDia")))
					horario ="DOMINGOS";
				Boolean eliminarValor = Boolean.TRUE;
				for (HashMap<String,String> horarios:listHorariosWeb ){
					if(horario.equals(horarios.get("nombre"))){
						eliminarValor = Boolean.FALSE;
					}
				}
				if(eliminarValor){
					int codigoDia =Integer.parseInt(dato.get("codigoDia"));
					if(servicios.eliminarIntervalo(codigoDia)){
						servicios.eliminarDetalleHorario(codigoDia);
					}
					
				}
				
				
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return !servicios.actulizarComHorarios(fybecaAdmin);
	}
	
	public FybecaAdminBean inifybecaAdmin(FybecaAdminBean fybecaAdmin) {
		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		fybecaAdmin = servicios.buscarAdFarmacias(fybecaAdmin);
		if (fybecaAdmin.getSit_latitud() == null)
			fybecaAdmin.setSit_latitud("");
		if (fybecaAdmin.getSit_longitud() == null)
			fybecaAdmin.setSit_longitud("");
		fybecaAdmin.setSit_estado(1);
		fybecaAdmin.setCom_tipo(195);
		return fybecaAdmin;
	}
	
	
	@Path("/actualizarWebImg")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String actualizarWebImg() throws FacadeException  {

		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		WfArchivosBean archivo = new WfArchivosBean();
		AdFarmaciasImagenBean farmaciaImagen = new AdFarmaciasImagenBean();

		
		for(WfArchivosBean dato:servicios.buscarImgenesFybecaAdmin()){
			try {
			archivo.setCodigo(servicios.obtenerMaxWfArchivos());
			archivo.setContenido(dato.getContenido());
			archivo.setFechaCreacion(new Date(new java.util.Date().getTime() ));
			archivo.setMinyType("image/jpeg");
			archivo.setUsuarioCreacion("rpriverae");
			archivo.setFullFileName(dato.getFileName());
			archivo.setFileName(dato.getFileName());
			archivo.setTamanio(dato.getContenido().length);
				if (servicios.insertWfArchivos(archivo)) {
					farmaciaImagen.setCodigoFarmacia(Long.parseLong(dato.getInstanceId()));
					farmaciaImagen.setMostar("S");
					farmaciaImagen.setCodigoArchivo(archivo.getCodigo());
					servicios.insertFotosCentrosCosto(farmaciaImagen);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return ConversionsTasks.serializar("actualizarWeb","actualizarWeb","Error al guardar la imgen", "404");
			}catch (Exception es) {
				es.printStackTrace();
				return ConversionsTasks.serializar("actualizarWeb","actualizarWeb","Error al guardar la imgen", "404");			}
		}
		return ConversionsTasks.serializar("actualizarWeb","actualizarWeb", "exitoso", "404");
	}
	
	


	@Path("/actualizarMapeo")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String actualizarMapeo() throws FacadeException  {

		ParametrizacionKioskoServicios servicios = new ParametrizacionKioskoServicios();
		
		 for (HashMap<String,String> dato: servicios.buscarCatalogo("1") ){
			 
			 String codProvincia =servicios.getCodProvincia(dato.get("nombre"));
			 if(!"".equals(codProvincia))
			 try {
				servicios.insertMapeoProvincias(dato.get("codigo"),codProvincia);
			 } catch (SQLException e) {
				e.printStackTrace();
			 } 
		 }
		 for (HashMap<String,String> dato: servicios.buscarCatalogo("2") ){
			 String codCiuad =servicios.getCodCiudad(dato.get("nombre"));
			 if(!"".equals(codCiuad))
				 try {
					 servicios.insertMapeoCiudades(dato.get("codigo"),codCiuad);
				 } catch (SQLException e) {
					e.printStackTrace();
				}
		}
		 return ConversionsTasks.serializar("actualizarWeb","actualizarWeb", "exitoso", "404");
	}
	
	
	
	
	@Path("/cargarFotosCentrosCosto/{codigoLocal}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarFotosCentrosCostos(@PathParam("codigoLocal") Long codigoLocal) throws FacadeException {
		log.info("...cargando Fotos Centros Costo...");
		return new ParametrizacionKioskoServicios().getFarmaciasFotosCentrosCostos(codigoLocal);
		
	}
	

    @Path("/cargarAdSucursales")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarAdSucursales() throws FacadeException {
    	log.info("...cargando AdSucursal...");
    	return new ParametrizacionKioskoServicios().getSucursales();
	
		
	}
 
    @Path("/cargarAdFarmaciasPorSucursal/{codigoSucursal}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String cargarAdFarmaciasPorSucursal(@PathParam("codigoSucursal") String codigoSucursal) throws FacadeException {
    	log.info("...cargando AdSucursal...");
    	return new ParametrizacionKioskoServicios().getFarmaciasPorSucursal(codigoSucursal);
	
		
	}
    
 
 
    @Path("/datosCentroCostosRest/{codigoCentroCosto}")
	@GET
	@Produces("text/html")
	public String DatosCentroCostosRest(@PathParam("codigoCentroCosto") String codigoCentroCosto) throws FacadeException {		
		return new DatosCentroCostos(codigoCentroCosto).getResultadoJson();
		
	}
    @Path("/datosCargosCentroCostosRest/{codigoCargo}")
	@GET
	@Produces("text/html")
	public String DatosCargosCentroCostosRest(@PathParam("codigoCargo") String codigoCargo) throws FacadeException {		
		return new DatosCentroCostos().getCargosCentroCostos(codigoCargo);
		
	}
		
    @Path("/datosCompletosEmpleado/{cedulaEmpleado}")
	@GET
	@Produces("text/html")
	public String DatosCompletosEmpleadoRest(@PathParam("cedulaEmpleado") String cedulaEmpleado) throws FacadeException {		
		return new DatosCompletosEmpleado(cedulaEmpleado).getResultadoJson();
		
	}
 
	@Path("/copiarEstadistica/{origen}/{destino}/{periodoInicio}/{periodoFin}/{tipoNegocio}/{email}")
	@GET
	@Produces("text/xml")
	public String copiarEstadistica(@PathParam("origen") String origen, @PathParam("destino") String destino,
			@PathParam("periodoInicio") String periodoInicio,@PathParam("periodoFin")  String periodoFin
			,@PathParam("tipoNegocio")  String tipoNegocio,@PathParam("email")  String email,@Context HttpServletRequest requestContext) {
		log.info("copiarEstadistica desde :"+requestContext.getRemoteAddr().toString());
		new PeEstadisticasVentas(origen, destino, periodoInicio, periodoFin, tipoNegocio,email).start();
		//return "EMPIEZA COPIA DE INFORMACION";
		
		return Constantes.respuestaXmlObjeto("OK","EMPIEZA LA COPIA DE ESTADISTICAS","EMEPIEZA A LAS "+new java.util.Date());
	}
	
	@Path("/mdv")
	@GET
	//@Produces("text/html")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDatosMDV(@QueryParam("fechaDesde") String fechaDesde,@QueryParam("fechaHasta") String fechaHasta){
		if(fechaDesde==null || fechaHasta==null)
			return null;
		else
			return new VentasConsolidadasMDV().getVentasConsolidadas(fechaDesde,fechaHasta);
	}
	
}
