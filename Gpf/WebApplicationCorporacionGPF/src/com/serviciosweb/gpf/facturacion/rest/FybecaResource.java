/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.rest;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.serviciosweb.facturacion.farmacias.ConsultaPmcNew;
import com.gpf.serviciosweb.facturacion.farmacias.VentasTop;
import com.serviciosweb.gpf.facturacion.servicios.CopiaFotosNomina;
import com.serviciosweb.gpf.facturacion.servicios.PagoAgil;
import com.serviciosweb.gpf.facturacion.servicios.Servicios;
import com.serviciosweb.gpf.facturacion.servicios.VoucherController;
/**
 * REST Web Service
 *
 * @author mftellor
 */

@Path("Fybeca")
public class FybecaResource {
    @Context
    private UriInfo context;
    Logger log=Logger.getLogger("FybecaResource");

    /** Creates a new instance of FybecaResource */
    public FybecaResource() {
    }

    /**
     * Retrieves representation of an instance of com.serviciosweb.gpf.facturacion.rest.FybecaResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of FybecaResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }

   @GET
   @Path("/PagoAgil/{idCadena}/{idFarmacia}/{idEmpleado}")
   @Produces("text/xml")
   public String  PagoAgil(@PathParam("idCadena") String idCadena,
                           @PathParam("idFarmacia") String idFarmacia,
                           @PathParam("idEmpleado") String idEmpleado,
                           @Context HttpServletRequest requestContext) {
	     //log.info("IP SOLICITA PagoAgil: "+requestContext.getRemoteAddr().toString()+" idCadena="+idCadena+" idFarmacia="+idFarmacia+" idEmpleado="+idEmpleado);
         try{
	         PagoAgil pagoAgil=new PagoAgil(idCadena, idFarmacia, idEmpleado);
	         if(pagoAgil.getResultado().equals("OK"))
	            pagoAgil.start();
	         return Constantes.respuestaXmlUnica( pagoAgil.getResultado());
         }catch (Exception e) {
   		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
   	   }finally{
   		   ////System.gc();
   	   }
   }
   @GET
   @Path("/FacturasProd/{idItem}/{idProducto}/{idPersona}/{identificacion}/{fechaDesde}/{fechaHasta}/{idFarmacia}")
   @Produces("text/xml")
   public String  FacturasProd(
                           @PathParam("idItem") String idItem,
                           @PathParam("idProducto") String idProducto,
                           @PathParam("idPersona") String idPersona,
                           @PathParam("identificacion") String identificacion,
                           @PathParam("fechaDesde") String fechaDesde,
                           @PathParam("fechaHasta") String fechaHasta,
                           @PathParam("idFarmacia") String idFarmacia) {
         try{
        	 return Servicios.facturasProd(idItem,idProducto,idPersona, identificacion, fechaDesde,fechaHasta,idFarmacia);
         }catch (Exception e) {
   		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
   	    }finally{
   		   //System.gc();
   	    }
   }
   @GET
   @Path("/BodegaItem/{idCadena}/{idItem}")
   @Produces("text/xml")
   public String  BodegaItem(
                           @PathParam("idCadena") String idCadena,
                           @PathParam("idItem") String idItem,
                           @Context HttpServletRequest requestContext) {
         try{
         //log.info("IP SOLICITA BodegaItem: "+requestContext.getRemoteAddr().toString());
            return Servicios.bodegaItem(idCadena,idItem);
         }catch (Exception e) {
   		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
   	     }finally{
   		   //System.gc();
   	     }
   }

   @GET
   @Path("/PasoEstadisticasLocales/{idFarmaciaOrigen}/{idFarmaciaDestino}/{tipoEstadisticas}")
   @Produces("text/xml")
   public String PasoEstadisticasLocales( @PathParam("idFarmaciaOrigen") String idFarmaciaOrigen,
                                          @PathParam("idFarmaciaDestino") String idFarmaciaDestino,
                                          @PathParam("tipoEstadisticas") String tipoEstadisticas){
          tipoEstadisticas=tipoEstadisticas.trim().toUpperCase();
          return Servicios.pasoEstadisticasLocales(idFarmaciaOrigen, idFarmaciaDestino,tipoEstadisticas);
   }
   @GET
   @Path("/StockItemsFarmacias/{item}/{empresas}/{farmacia}")
   @Produces("text/xml")
   public String StockItemsFarmacias(@PathParam("item") String item,
           @PathParam("empresas") String empresas,
           @PathParam("farmacia") String farmacia,
           @Context HttpServletRequest requestContext){
       try{
         //log.info("IP SOLICITA BodegaItem: "+requestContext.getRemoteAddr().toString());
    	   	return Servicios.stockItemsFarmacias(item, empresas,farmacia);
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }

   @GET
   @Path("/ConsultaPmc/{p_persona}/{p_item}/{p_tipo_id}/{p_cod_club}/{empresa}")
   @Produces("text/xml")
   public String ConsultaPmc(@PathParam("p_persona") String p_persona,
           @PathParam("p_item") String p_item,
           @PathParam("p_tipo_id") String p_tipo_id,
           @PathParam("p_cod_club") String p_cod_club,
           @PathParam("empresa") String empresa,@Context HttpServletRequest requestContext){
       try{
	       //log.info("IP SOLICITA ConsultaPmcNew: "+requestContext.getRemoteAddr().toString());
	       ConsultaPmcNew consultaPmcNew=new ConsultaPmcNew(p_persona, p_item, p_tipo_id, p_cod_club, Integer.decode(empresa),null);
	       return consultaPmcNew.getResultadoXml();
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }

   
   
   @GET
   @Path("/ConsultaPmcPromocion/{p_persona}/{p_item}/{p_tipo_id}/{p_cod_club}/{empresa}/{promocion}")
   @Produces("text/xml")
   public String ConsultaPmc(@PathParam("p_persona") String p_persona,
           @PathParam("p_item") String p_item,
           @PathParam("p_tipo_id") String p_tipo_id,
           @PathParam("p_cod_club") String p_cod_club,
           @PathParam("empresa") String empresa,@PathParam("promocion") String promocion,@Context HttpServletRequest requestContext){
       try{
	       //log.info("IP SOLICITA ConsultaPmcNew: "+requestContext.getRemoteAddr().toString());
	       ConsultaPmcNew consultaPmcNew=new ConsultaPmcNew(p_persona, p_item, p_tipo_id, p_cod_club, Integer.decode(empresa),promocion);
	       return consultaPmcNew.getResultadoXml();
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }

   
   
   @GET
   @Path("/VentasTop/{fechaInicio}/{fechaFinal}/{codigoPersona}")
   @Produces("text/xml")
   public String VentasTop(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFinal") String fechaFinal , @PathParam("codigoPersona") String  codigoPersona){
       try{
    	   return new VentasTop(fechaInicio, fechaFinal, codigoPersona).getResultadoXml();       
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }
   
/*
   @GET
   @Path("/buscaImagen/{idImagen}/{ancho}/{alto}")
   @Produces("image/jpeg")
   public byte[] buscaDatos(@PathParam("idImagen") int intCodItem,
                            @PathParam("ancho") int intAncho,
                            @PathParam("alto") int intAlto){
        try {
            return Servicios.ConsultaImagenProducto(intCodItem, intAncho, intAlto);
        } catch (IOException ex) {
            Logger.getLogger(FybecaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;

   }

    @GET
    @Path("/buscaDatos/{idImagen}/")
    @Produces("text/plain")
    public String procesaDatos(@PathParam("idImagen") String strDatos){

          return Servicios.consultaDatosProducto(strDatos);

       }

*/
   @GET
   @Path("/AlertasLocal/{codigoAlerta}/{codigoLocal}/{codigoTipoAlerta}/{codigoItem}/{cantidad}/{tipoAbc}/{fechaAlerta}/{cpVar3}")
   @Produces("text/xml")
   public String AlertasLocal(@PathParam("codigoAlerta") String codigoAlerta,
           @PathParam("codigoLocal") String codigoLocal,
           @PathParam("codigoTipoAlerta") String codigoTipoAlerta,
           @PathParam("codigoItem") String codigoItem,@PathParam("cantidad") String cantidad,
           @PathParam("tipoAbc") String tipoAbc,@PathParam("fechaAlerta") String fechaAlerta,@PathParam("cpVar3") String cpVar3,@Context HttpServletRequest requestContext){
	   //log.info("INICIA IP SOLICITA AlertasLocal: "+requestContext.getRemoteAddr().toString());
	   //log.info("IP SOLICITA BodegaItem: "+requestContext.getRemoteAddr().toString());
       try{
	       //ServiciosPostgres serviciosPostgres=new ServiciosPostgres();
	       //String resultado=serviciosPostgres.AlertasLocal(codigoAlerta, codigoLocal, codigoTipoAlerta, codigoItem, cantidad, tipoAbc,fechaAlerta,cpVar3);
	       return "";
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }
   
   @GET
   @Path("/PedidoManual/{codigoLocal}/{codigoPedido}")
   @Produces("text/xml")
   public String PedidoManual(@PathParam("codigoPedido") String codigoPedido,
           @PathParam("codigoLocal") String codigoLocal){
       try{
    	   //ServiciosPostgres serviciosPostgres=new ServiciosPostgres();
    	   //return serviciosPostgres.PedidoManual(codigoLocal,codigoPedido);
    	   return "";
       }catch (Exception e) {
		  return Constantes.respuestaXmlObjeto("ERROR","FALLO EL WS",null);
	   }finally{
		   //System.gc();
	   }
   }


/*
   @GET
   @Path("/buscaImagen/{idImagen}")
   @Produces("text/plain")
   public String buscaDatos(@PathParam("idImagen") int intCodItem,
                            @Context HttpServletRequest requestContext){
        //System.gc();
        String yourIP = requestContext.getRemoteAddr().toString();
        ServiciosImagenes  serviciosImagenes=new ServiciosImagenes(intCodItem,yourIP);
        return serviciosImagenes.getRespuesta();      
   }
   */
   @GET
   @Path("/CopiaFotosNomina/{cedula}")
   @Produces("text/plain")
   public String CopiaFotosNomina(@PathParam("cedula") String cedula){
          new CopiaFotosNomina(cedula);
          return "OK";
    }
   
   @GET
   @Path("/consultarVoucher/{codigoVoucher}/{codigoPersona}/{farmacia}/{montoCompra}")
   @Produces("text/xml")
   public String consultarVoucher(@PathParam("codigoVoucher") String codigo_voucher, @PathParam("codigoPersona") int codigoPersona, @PathParam("farmacia") String farmacia, @PathParam("montoCompra") String monto_compra){
	   return new VoucherController(codigo_voucher, codigoPersona, farmacia, monto_compra).consultarVoucherXML();
	   
   }

   @GET
   @Path("/consumirVoucher/{codigoVoucher}/{farmacia}/{documentoVenta}")
   @Produces("text/xml")
   public String consumirVoucher(@PathParam("codigoVoucher") String codigo_voucher
		                        ,@PathParam("farmacia") String farmacia
		                        ,@PathParam("documentoVenta") String documentoVenta){
	   VoucherController vc = new VoucherController();
	   vc.consumirVoucher(codigo_voucher,farmacia,documentoVenta);
	   return vc.consumirVoucherXML();
	   
   }
}
