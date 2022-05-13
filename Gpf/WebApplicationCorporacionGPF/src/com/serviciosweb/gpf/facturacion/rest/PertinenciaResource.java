package com.serviciosweb.gpf.facturacion.rest;
import java.util.Collections;
import java.util.Iterator;
import java.io.StringWriter;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.map.ObjectMapper;

import com.serviciosweb.gpf.facturacion.servicios.MapeoAseguradoraAbfServices;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.AseguradoraController;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.VOUsuario;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.persona.DatosGeneralesGenericoBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecPerBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaGenBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RecetaPerBean;
import com.serviciosweb.gpf.facturacion.servicios.aseguradora.bean.generico.recetapos.RespuestaPerBean;



@Path("Pertinencia")

public class PertinenciaResource {
	//final String PATH_CONSULTA_DATOS ="consultaDatos";
	//final String PATH_CONFIRMACION_DATOS ="confirmacionDatos";
	//final String PATH_CONSULTA_PLANES ="ConsultaPlanes";

	// metodos para test de usuarios login
	@POST
	@Path("/val")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public VOUsuario validaUsuario(VOUsuario vo)
	 {
		vo.setUservalido(false);
        if(vo.getUsuario().equalsIgnoreCase("java")){
        vo.setUservalido(true);}
        else {
        	vo.setUservalido(false);}
        return vo;        
     }
	
	
   // consultar datos
	@POST
	@Path("/consultar")
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	//@Produces("text/xml; charset=UTF-8")
	//@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	
	
	
	public String confirmacionDatos(RecetaPerBean xmlentrada) {
		int numreg;
		String cpro = "";
		String cdia = "";
		String caseg = "";
		String respuesta = "";
		int cont =0;
		Boolean  v_estado = null;
		String banaseg ="0";
		
			
		
		// definimos objetos para guarda aqui entrada de xml del usuario
		RecetaPerBean objeto = new RecetaPerBean();
		
		
		// definimos objetos para respuesta de pertinencia
		//ArrayList<RespuestaPerBean> res_per = new <espuestaPerBean>();

		
		
		objeto= xmlentrada;
        numreg = objeto.getRec_items().size();
        int numregd = objeto.getDiagnosticos().size();
        int contadorp= 0;
        Boolean valida = new Boolean(false);
        Boolean validaa = new Boolean(false);
        
        PertinenciaController Per = new PertinenciaController();
		
        
        
        // recorremos el arreglo del objeto para  buscar las pertinencias en tabla fa_det_pertienencia
        for(int i=0;i< numreg;i++){
        	caseg =objeto.getCodaseguradora();//codigo de aseguradora
            
        	cpro = objeto.getRec_items().get(i).getCodigo();//codigo de producto
	    	// creamos instancia de pertinencia controller
			//PertinenciaController Per = new PertinenciaController();
			//recorremos diagnosticos
			for(int j=0;j< numregd;j++)
			{
			cdia= objeto.getDiagnosticos().get(j).getCodigo();
			 // llamamos pertinencia para buscar estado , regresamos estado en variable
			 valida = Per.Estadoper(cpro, cdia, caseg);
			 if (Boolean.TRUE.equals(valida))
			 {
				 contadorp = contadorp + 1;
			 }
			}//finde for de diagnticos
			//if (contadorp == numregd)
			if (contadorp == numregd || contadorp >=1 )
			{
				objeto.getRec_items().get(i).setRespuesta("0");
			    objeto.getRec_items().get(i).setMensaje("OK");
			}
			else
			{
				
				validaa = Per.BuscarAseguradora(caseg);
				if (Boolean.TRUE.equals(validaa))
				{
				
					objeto.getRec_items().get(i).setRespuesta("1");
				objeto.getRec_items().get(i).setMensaje("El item no tiene diagnostico parametrizado");
						
				}
				else
				{	
				
				objeto.getRec_items().get(i).setCodigo("0");
				banaseg="1";
				objeto.getRec_items().get(i).setRespuesta("1");
				objeto.getRec_items().get(i).setMensaje("La aseguradora no tiene esta funcion");
				}
			}
				
	   }// fin de for
			
		// quitamos los estados falsos para devolver solo los que tienen pertinencia médica
		// definimos iteractor park borrar los estados falsos Creado por sfba 13 abril 2020
		   
        
        
        
        if ( banaseg == "1")
        	
        {
       
		Iterator lista = objeto.getRec_items().iterator();
		int con = 0;
		
		
		
		    while (lista.hasNext()) 
		    { 
			          lista.next();
		 	          if (cont >0) {
		 	            lista.remove();
		                cont = cont -1;
		               }
			           cont = cont + 1;  
		   }// fin de iterator 
	     
        }
        
	       // finalmente dejamos el estado en null
		 
		//    numreg = objeto.getRec_items().size();
		 //   for(int i=0;i< numreg;i++){
		//	  	objeto.getRec_items().remove(i);
			  	
			  	//objeto.getRec_items().get(i).setMensaje(null);;
			   	//objeto.getRec_items().get(i).setDiag_codigo(null);
			 	//objeto.getRec_items().get(i).setId_pertinencia(null);
			     
		 //   }
		
		    
		    
		    
		    
		    
		    objeto.getDiagnosticos().clear();
		   	// construimos la salidad del xml
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RecetaPerBean.class);
			//JAXBContext jaxbContext = JAXBContext.newInstance(objeto.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			//jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(objeto, sw);
			String xmlContent = sw.toString();
			System.out.println(xmlContent);
			// asignamos respuesta al web service 
			respuesta =  xmlContent;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		//devolvemos xmltexto
		return respuesta;
	}
	}
