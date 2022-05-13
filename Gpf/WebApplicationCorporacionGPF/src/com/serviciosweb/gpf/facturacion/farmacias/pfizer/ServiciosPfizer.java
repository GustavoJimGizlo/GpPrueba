/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.farmacias.pfizer;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.jboss.logging.Logger;

import mx.com.lms.pfizer.fragua.ConsultaDevolucion;
import mx.com.lms.pfizer.fragua.Devolucion;
import mx.com.lms.pfizer.fragua.FolioDevolucion;
import mx.com.lms.pfizer.fragua.PedidoDevolucion;
import mx.com.lms.pfizer.fragua.TicketConsultaDevolucion;
import mx.com.lms.webservices.prepago.Compra;
import mx.com.lms.webservices.prepago.Comprobante;
import mx.com.lms.webservices.prepago.ConsultaTarjeta;
import mx.com.lms.webservices.prepago.ConsultaTarjetaResult;
import mx.com.lms.webservices.prepago.Folio;
import mx.com.lms.webservices.prepago.FolioConsultaTarjeta;
import mx.com.lms.webservices.prepago.Pedido;
import mx.com.lms.webservices.prepago.Ticket;
import mx.com.lms.webservices.prepago.TicketDevolucion;
import mx.com.lms.webservices.prepago.Token;
import mx.com.lms.webservices.prepagoecardvzla.ArrayOfConsultarTransacciones;
import mx.com.lms.webservices.prepagoecardvzla.ArrayOfTicketConsultaDevolucion;
import mx.com.lms.webservices.prepagoecardvzla.ConsultarTransacciones;
import mx.com.lms.webservices.prepagoecardvzla.WsEcardPrePago;
import mx.com.lms.webservices.prepagoecardvzla.WsEcardPrePagoSoap;

//import org.apache.log4j.Logger;

import com.gpf.postg.pedidos.util.Constantes;
import com.sun.xml.internal.ws.client.BindingProviderProperties;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import flexjson.JSONSerializer;
/**
 *
 * @author mftellor
 */
@SuppressWarnings("unchecked")
public class ServiciosPfizer implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5643408312640711069L;
	private WsEcardPrePago wsEcardPrePago=new WsEcardPrePago();
	private WsEcardPrePagoSoap port = wsEcardPrePago.getWsEcardPrePagoSoap();
    private XStream xstream = new XStream(new StaxDriver());
    private JSONSerializer serializador = new JSONSerializer();
    Logger log=Logger.getLogger(ServiciosPfizer.class.getName());    
    private String ipInvoca;
    
    
    {
    	BindingProvider bindingProvider = (BindingProvider)port;
		@SuppressWarnings("rawtypes")
		Map requestContext = bindingProvider.getRequestContext();				
		requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 2 * 60 * 1000); //2 min
		requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 2 * 60 * 1000); //2 min    	
    }
    
    
    public ServiciosPfizer(){
    	/*
    	//PropertyConfigurator.configure("/data/properties/pfizer.properties");  
    	DOMConfigurator.configure("/data/properties/log4j.xml");
    	log=Logger.getLogger(ServiciosPfizer.class); 
    	
    	log.debug("debug");
    	log.info("info");
    	log.fatal("fatal message");
    	*/
    	
    }
    public String procesarCompra(String programaIntld,String cadenaIntld, String sucursalIntld,
            String terminalIntld,String tarjeta,String usuarioStrId,String usuarioCapturaStrId,String itemsCantidad){    	
        try{
            
            Compra compra=new Compra();
            compra.setCadenaIntId(Integer.parseInt(cadenaIntld));
            compra.setProgramaIntId(Integer.parseInt(programaIntld));
            compra.setSucursalIntId(Integer.parseInt(sucursalIntld));
            compra.setTarjeta(tarjeta);
            compra.setTerminalIntId(Integer.parseInt(terminalIntld));
            compra.setUsuarioTicketStrId(usuarioStrId);
            compra.setUsuarioCapturaStrId(usuarioCapturaStrId);
            String[] items=itemsCantidad.split("\\)");
            for(String itemCatidad:items){
               itemCatidad=itemCatidad.replace("(", "");
               String[] datoCompra=itemCatidad.split(",");
               Pedido pedido=new Pedido();
               pedido.setSKU(datoCompra[0]);
               pedido.setCantidad(Integer.parseInt(datoCompra[1].toString()));
               compra.getPedido().add(pedido);
            }
            //Ticket ticket=wSPrepago.getWSPrepagoSoap().procesarCompra(compra);
            //wSPrepago.getWSPrepagoSoap().procesarCompraDemo(arg0, arg1, arg2, arg3, tarjeta, tarjeta, arg6, tarjeta)

            //WSPrepago wSPrepago=new WSPrepago();   
            log.info("EMPIEZA procesarCompra ipInvoca="+ipInvoca+" programaIntld="+ programaIntld+"cadenaIntld="+cadenaIntld+" sucursalIntld="+  sucursalIntld
            		+" terminalIntld="+terminalIntld+" tarjeta="+tarjeta+" usuarioStrId="+usuarioStrId+" usuarioCapturaStrId="+usuarioCapturaStrId+
            		"itemsCantidad="+itemsCantidad);
            Ticket ticket=this.port.procesarCompra(compra);
                     /*compra.getProgramaIntId()
                    ,compra.getCadenaIntId()
                    , compra.getSucursalIntId()
                    , compra.getTerminalIntId()
                    ,compra.getUsuarioCapturaStrId()
                    , compra.getTarjeta()
                    , compra.getPedido().get(0).getCantidad()
                    , compra.getPedido().get(0).getSKU());*/

            xstream.alias("Ticket", Ticket.class);
            xstream.alias("Folio", Folio.class);
            //resultadoXml=xstream.toXML(ticket);
            //serializador.include("folio").include("Folio").serialize(ticket);
            log.info("FIN procesarCompra ipInvoca="+ipInvoca+" RESULTADO "+xstream.toXML(ticket));
            return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(ticket),serializador.include("folio").include("Folio").serialize(ticket));
        }catch(Exception e){
            e.printStackTrace();
            log.info("ERROR EN procesarCompra "+e.getMessage());
            return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
        }



        //Comprobante comprobante=wSPrepago.getWSPrepagoSoap().cerrarCompra(token);
    }
    public String cerrarCompra(String cotizacionId,String tarjeta){
    	try{
	        Token token=new Token();
	        token.setCotizacionId(cotizacionId);
	        token.setTarjeta(tarjeta);
	        log.info("EMPIEZA cerrarCompra ipInvoca="+ipInvoca+"   cotizacionId="+cotizacionId+" tarjeta="+tarjeta);
	        Comprobante comprobante=this.port.cerrarCompra(token);
	        xstream.alias("Comprobante", Comprobante.class);
	        log.info("FIN cerrarCompra ipInvoca="+ipInvoca+" RESULTADO "+xstream.toXML(comprobante));
	        return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(comprobante),serializador.serialize(comprobante));
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }
    public String cancelarCompra(String cotizacionId,String tarjeta){
    	try{
	        Token token=new Token();
	        token.setCotizacionId(cotizacionId);
	        token.setTarjeta(tarjeta);
	        
	        log.info("EMPIEZA cancelarCompra ipInvoca="+ipInvoca+"   cotizacionId="+cotizacionId+" tarjeta="+tarjeta);
	        Comprobante comprobante=this.port.cancelarCompra(token);
	        xstream.alias("Comprobante", Comprobante.class);
	        log.info("FIN cancelarCompra ipInvoca="+ipInvoca+" RESULTADO "+xstream.toXML(comprobante));
	        return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(comprobante),serializador.serialize(comprobante));
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }
    public String consultarDevolucion (String programaIntld,String cadenaIntld, String sucursalIntld,
            String terminalIntld,String tarjeta,String usuarioStrId,String referTransactionDevIntId,String cotizacionIntId ){
    	try{
	        ConsultaDevolucion consultaDevolucion=new ConsultaDevolucion();
	        consultaDevolucion.setCadenaIntId(Integer.parseInt(cadenaIntld));
	        consultaDevolucion.setProgramaIntId(Integer.parseInt(programaIntld));
	        consultaDevolucion.setReferTransactionDevIntId(Integer.parseInt(referTransactionDevIntId));
	        consultaDevolucion.setSucursalIntId(Integer.parseInt(sucursalIntld));
	        consultaDevolucion.setTarjeta(tarjeta);
	        consultaDevolucion.setTerminalIntId(Integer.parseInt(terminalIntld));
	        consultaDevolucion.setUsuarioStrId(usuarioStrId);
	        consultaDevolucion.setCotizacionIntId(cotizacionIntId);
	        log.info("EMPIEZA consultarDevolucion ipInvoca="+ipInvoca+" programaIntld="+ programaIntld+"cadenaIntld="+ cadenaIntld+"  sucursalIntld="+sucursalIntld+
	             " terminalIntld=terminalIntld"+"  tarjeta="+ tarjeta+" usuarioStrId="+ usuarioStrId+" referTransactionDevIntId="+referTransactionDevIntId+
	             " cotizacionIntId="+ cotizacionIntId );
	        ArrayOfTicketConsultaDevolucion arrayOfTicketConsultaDevolucion=this.port.consultarDevolucion(consultaDevolucion);
	        xstream.alias("TicketConsultaDevolucion", TicketConsultaDevolucion.class);
	        xstream.alias("ArrayOfTicketConsultaDevolucion", ArrayOfTicketConsultaDevolucion.class);
	        //serializador.include("ticketConsultaDevolucion").include("cotizacionDetalleConsultaDevolucion").include("CotizacionDetalleConsultaDevolucion").include("arrayOfCotizacionDetalleConsultaDevolucion").serialize(arrayOfTicketConsultaDevolucion)
	        log.info("FIN consultarDevolucion ipInvoca="+ipInvoca+" RESPUESTA"+xstream.toXML(arrayOfTicketConsultaDevolucion));
	        return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(arrayOfTicketConsultaDevolucion),serializador.include("arrayOfCotizacionDetalleConsultaDevolucion").include("cotizacionDetalleConsultaDevolucion").include("folio").include("CotizacionDetalleConsultaDevolucion").include("TicketConsultaDevolucion").serialize(arrayOfTicketConsultaDevolucion));
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }

    public String DevolverCompra (String programaIntld,String cadenaIntld, String sucursalIntld,
            String terminalIntld,String tarjeta,String usuarioStrId,String cotizacionId,String referTransactionDevIntId,String itemsCantidad){
    	try{
	         Devolucion devolucion=new Devolucion();
	        devolucion.setCadenaIntId(Integer.parseInt(cadenaIntld));
	        devolucion.setCotizacionStrId(tarjeta);
	        devolucion.setProgramaIntId(Integer.parseInt(programaIntld));
	        devolucion.setReferTransactionDevIntId(Integer.parseInt(referTransactionDevIntId));
	        devolucion.setSucursalIntId(Integer.parseInt(sucursalIntld));
	        devolucion.setTarjeta(tarjeta);
	        devolucion.setTerminalIntId(Integer.parseInt(terminalIntld));
	        devolucion.setUsuarioStrId(usuarioStrId);
	        devolucion.setCotizacionStrId(cotizacionId);
	        String[] items=itemsCantidad.split("\\)");
	        for(String itemCatidad:items){
	           itemCatidad=itemCatidad.replace("(", "");
	           String[] datoCompra=itemCatidad.split(",");
	           PedidoDevolucion pedido=new PedidoDevolucion();
	           pedido.setSKU(datoCompra[0]);
	           pedido.setCantidad(Integer.parseInt(datoCompra[1].toString()));
	           pedido.setBeneficio(Integer.parseInt(datoCompra[2].toString()));
	           devolucion.getPedido().add(pedido);
	        }
	        log.info("EMPIEZA DevolverCompra ipInvoca="+ipInvoca+"  programaIntld="+programaIntld+" cadenaIntld="+ cadenaIntld+"  sucursalIntld="+sucursalIntld+
	             " terminalIntld=terminalIntld"+" tarjeta="+tarjeta+" usuarioStrId="+usuarioStrId+" cotizacionId="+cotizacionId
	             +" referTransactionDevIntId="+referTransactionDevIntId+"itemsCantidad="+ itemsCantidad);
	        TicketDevolucion ticketDevolucion=this.port.devolverCompra(devolucion);
	        xstream.alias("TicketDevolucion", TicketDevolucion.class);
	        xstream.alias("FolioDevolucion", FolioDevolucion.class);
	
	        
	        //Date dt = ticketDevolucion.getFechaMovDev().toGregorianCalendar().getTime();
	        String folio=serializador.serialize(ticketDevolucion.getFolio()).replace("[", "").replace("]", "");
	        //folio=folio.replace("{", "").replace("}", "");
	        folio="\"FolioDevolucion\":"+folio;
	        log.info("FIN  DevolverCompra ipInvoca="+ipInvoca+" RESPUESTA "+xstream.toXML(ticketDevolucion));
	        //this.parsearXml(xstream.toXML(ticketDevolucion));
	        
	        List<FolioDevolucion> listadoResultante=this.consolidarFolioDevolucion(ticketDevolucion);
	        ticketDevolucion.getFolio().clear();
	        ticketDevolucion.getFolio().addAll(listadoResultante);
	        
	        log.info(" RESPUESTA DevolverCompra PROCESADA: "+xstream.toXML(ticketDevolucion));
	        return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(ticketDevolucion),serializador.serialize(ticketDevolucion).replace("}", ",")+folio+"}");
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }
    public String ConsultaTransacciones(String programaID,String  cadenaid, String fechaInicial, String fechaFinal){
    	try{
	    	ArrayOfConsultarTransacciones resultado = this.port.consultaTransacciones(programaID, Integer.parseInt(cadenaid), "3c4rdsPFZ", fechaInicial, fechaFinal);
	    	log.info("EMPIEZA ConsultaTransacciones ipInvoca="+ipInvoca+" programaID="+programaID+" cadenaid="+cadenaid+" fechaInicial="+fechaInicial+" fechaFinal="+fechaFinal);
	    	xstream.alias("ConsultarTransacciones", ConsultarTransacciones.class);
	    	log.info("FIN ConsultaTransacciones ipInvoca="+ipInvoca+" RESULTADO "+xstream.toXML(resultado));
	    	return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(resultado),null);
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }
    
    public String ConsultaTarjeta(String programaIntId,String cadenaIntId,String sucursalIntId,String terminalIntId,String usuarioStrId,
    		String tarjeta,String cedula,String sku){
    	try{
	    	ConsultaTarjeta consultaTarjeta=new ConsultaTarjeta();
	    	consultaTarjeta.setCadenaIntId(Integer.parseInt(cadenaIntId));
	    	
	    	List<String> listadoSku=new ArrayList<String>();
	    	sku=sku.replace("(", "");
	    	sku=sku.replace(")", "");
	    	String[] skus=sku.split(",");
	    	for(String skuIndividual:skus)
	    		listadoSku.add(skuIndividual);
		    
	    	consultaTarjeta.setCedula(cedula);
	    	if(cedula.equals("0"))
	    	 consultaTarjeta.setCedula("");
	    	
	    	consultaTarjeta.setProgramaIntId(Integer.parseInt(programaIntId));
	    	consultaTarjeta.setSucursalIntId(Integer.parseInt(sucursalIntId));
	    	
	    	consultaTarjeta.setTarjeta(tarjeta);
	    	if(tarjeta.equals("0"))
	    		consultaTarjeta.setTarjeta("");
	    	
	    	consultaTarjeta.setTerminalIntId(Integer.parseInt(terminalIntId));
	    	consultaTarjeta.setUsuarioStrId(usuarioStrId);
	    	
	    	xstream.alias("ConsultaTarjetaResult", ConsultaTarjetaResult.class);
	    	xstream.alias("FolioConsultaTarjeta", FolioConsultaTarjeta.class);
	    	log.info("EMPIEZA ConsultaTarjeta ipInvoca="+ipInvoca+" programaIntId="+programaIntId+" cadenaIntId="+cadenaIntId+"  sucursalIntId="+sucursalIntId+
	    			" terminalIntId="+terminalIntId+" usuarioStrId="+usuarioStrId+" tarjeta="+ tarjeta+" cedula="+cedula);
	    	ConsultaTarjetaResult consultaTarjetaResult=this.port.consultaTarjeta(consultaTarjeta);
	    	log.info("FIN ConsultaTarjeta ipInvoca="+ipInvoca+" RESULTADO "+xstream.toXML(consultaTarjetaResult));
	    	
	    	List<FolioConsultaTarjeta> listadoResultante=ConsultaTarjeta(listadoSku,consultaTarjetaResult);
	    	if(consultaTarjetaResult!=null)
	    		if(consultaTarjetaResult.getFolioConsultaTarjeta()!=null){
	    	      consultaTarjetaResult.getFolioConsultaTarjeta().clear();
	    	      consultaTarjetaResult.getFolioConsultaTarjeta().addAll(listadoResultante);
	    		}
	    	log.info(" RESPUESTA ConsultaTarjeta PROCESADA: "+xstream.toXML(consultaTarjetaResult));
	    	
	    	return  Constantes.respuestaXmlObjeto("OK",xstream.toXML(consultaTarjetaResult),null);
	    }catch(Exception e){
	        e.printStackTrace();
	        log.info("ERROR EN cancelarCompra "+e.getMessage());
	        return  Constantes.respuestaXmlObjeto("ERROR","ERROR "+e.getMessage(),"ERROR "+e.getMessage());
	    }
    }
    
    private List<FolioConsultaTarjeta> ConsultaTarjeta(List<String> listadoSku,ConsultaTarjetaResult consultaTarjetaResult){
    	List<FolioConsultaTarjeta> listadoResultante=new ArrayList<FolioConsultaTarjeta>();
    	if(consultaTarjetaResult==null)
    		return listadoResultante;
    	if(consultaTarjetaResult.getFolioConsultaTarjeta()==null)
    		return listadoResultante;
    	for(FolioConsultaTarjeta folioConsultaTarjetaIterador:consultaTarjetaResult.getFolioConsultaTarjeta()){
    		for(String skuIterador:listadoSku)
    			if(folioConsultaTarjetaIterador.getSKU().equals(skuIterador))
		    	   if(consolidarFolioConsultaTarjeta(folioConsultaTarjetaIterador,listadoResultante))
		    		   listadoResultante.add(folioConsultaTarjetaIterador);    		
    	}    
    	return listadoResultante;
    }
    private boolean consolidarFolioConsultaTarjeta(FolioConsultaTarjeta folioConsultaTarjeta,List<FolioConsultaTarjeta> listadoResultante){
    	for(FolioConsultaTarjeta folioConsultaTarjetaIterador:listadoResultante){
    		if(folioConsultaTarjetaIterador.getSKU().equals(folioConsultaTarjeta.getSKU()))
    			return false;
    	}
    	return true;
    }
    
	public void setIpInvoca(String ipInvoca) {
		this.ipInvoca = ipInvoca;
	}
	
	private List<FolioDevolucion>  consolidarFolioDevolucion(TicketDevolucion ticketDevolucion){		
		List<FolioDevolucion> listadoResultante=new ArrayList<FolioDevolucion>();
		for(FolioDevolucion folioDevolucionIterador:ticketDevolucion.getFolio()){
			if(consolidarFolioDevolucion(listadoResultante,folioDevolucionIterador))
				listadoResultante.add(folioDevolucionIterador);
		}
		return listadoResultante;

	}
	private boolean consolidarFolioDevolucion(List<FolioDevolucion> listadoResultante,FolioDevolucion folioDevolucion){
		for(FolioDevolucion folioDevolucionIterador:listadoResultante){
			if(folioDevolucionIterador.getSKU().equals(folioDevolucion.getSKU()) &&
					folioDevolucionIterador.getCodigoRespDev()==folioDevolucion.getCodigoRespDev()&& 
					folioDevolucionIterador.getDescCodigoRespDev().equals(folioDevolucion.getDescCodigoRespDev())){
				return false;
			}
		}
		return true;
	}
    /*
	private String parsearXml(String contenidoXml){
        String errores = "";
        try {
        	List<FolioDevolucion> listadoFolioDevolucion=new ArrayList<FolioDevolucion>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            //InputSource archivo = new InputSource();
            //archivo.setCharacterStream(new StringReader(contenidoXml));
            //Document documento = db.parse(archivo);
            File file = new File("/data/devolucion.xml");
            Document documento = db.parse(file);
            documento.getDocumentElement().normalize();
            NodeList nodeLista = documento.getElementsByTagName("FolioDevolucion");            
            for (int s = 0; s < nodeLista.getLength(); s++) {
            	 Element el = (Element)nodeLista.item(s);
            	 listadoFolioDevolucion.add(getFolioDevolucion(el));
            }

        } catch (SAXException ex) {
            Logger.getLogger(ServiciosPfizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosPfizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ServiciosPfizer.class.getName()).log(Level.SEVERE, null, ex);
        }
       return errores;
    }
	private FolioDevolucion getFolioDevolucion(Element empEl) {		
		String sku = getTextValue(empEl,"SKU");
		String descCodigoRespDev = getTextValue(empEl,"DescCodigoRespDev");
		int codigoRespDev = getIntValue(empEl,"CodigoRespDev");

		//Create a new Employee with the value read from the xml nodes
		FolioDevolucion folioDevolucion = new FolioDevolucion();
		folioDevolucion.setCodigoRespDev(codigoRespDev);
		folioDevolucion.setDescCodigoRespDev(descCodigoRespDev);
		folioDevolucion.setSKU(sku);

		return folioDevolucion;
	}
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	*/
}

