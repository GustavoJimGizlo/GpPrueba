/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;


import com.gpf.postg.pedidos.util.Constantes;
import com.serviciosweb.gpf.facturacion.farmacias.bean.DatosCedulaBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.Serializable;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author mftellor
 */
public class VerificarCedula implements Serializable {
    
private String urlAccion = "http://www.corporacionregistrocivil.gov.ec/OnLine/show_cedula.asp" ;
private DatosCedulaBean datosCedulaBean=new DatosCedulaBean();
private String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
private Boolean estadoValidacion=true;
Logger log=Logger.getLogger("VerificarCedula");
public VerificarCedula(String cedula){
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("DatosCedulaBean", DatosCedulaBean.class);
        
        PostMethod post = new PostMethod(urlAccion);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded ;charset=ISO-8859-1");
        //post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=ISO-8859-1");
        post.addParameter("nroced", cedula);
        post.addParameter("strCAPTCHAsc", "123456789");
        post.addParameter("strCAPTCHA", "123456789");
        
        // execute the POST
        HttpClient client = new HttpClient();
        //client.getHostConfiguration().setProxy("uioproxy01",3128);
        
        try {
            
            @SuppressWarnings("unused")
            int status = client.executeMethod(post);
            String response = post.getResponseBodyAsString();
            Document doc = Jsoup.parse(response, urlAccion);

           // Element rt = respuesta.getElementById("container");

            /*
            Document doc =  Jsoup.connect("http://www.corporacionregistrocivil.gov.ec/OnLine/show_cedula.asp")
                    .data("strCAPTCHAsc", "123456789")
                    .data("strCAPTCHA", "123456789")
                    .data("nroced", cedula)      
                    .post();
             * */
            //Elements newsHeadlines = doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(1).text();

            String cedulaVlidada=doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(0).getElementsByTag("font").get(0).text().toString();
            datosCedulaBean.setDigitoVerificador(cedulaVlidada.substring(9));
            datosCedulaBean.setNombres(doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(1).getElementsByTag("font").get(0).text());
            datosCedulaBean.setFechaNacimiento(doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(2).getElementsByTag("font").get(0).text());
            datosCedulaBean.setCiudadania("--");
            datosCedulaBean.setEstadoCivil(doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(4).getElementsByTag("font").get(0).text());
            datosCedulaBean.setDatosConyugue(doc.getElementsByTag("table").get(2).getElementsByTag("tr").get(1).getElementsByTag("td").get(5).getElementsByTag("font").get(0).text());
            resultadoXml=xstream.toXML(datosCedulaBean);
       }catch(Exception ex){
           log.info("ERROR AL VALIDAR LA CEDULA :"+cedula);
           estadoValidacion=false;
           ex.printStackTrace();
       }
 }

    public String getResultadoXml() {
        if(estadoValidacion)
          return Constantes.respuestaXmlObjeto("OK",resultadoXml);
        else
          return Constantes.respuestaXmlObjeto("ERROR AL VALIDAR LA CEDULA",resultadoXml);
    }

}
