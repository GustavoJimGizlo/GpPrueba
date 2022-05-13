/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpf.serviciosweb.facturacion.farmacias;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 *
 * @author mftellor
 */
    public class AutorizarCheque{

    private String response = null;
    private XStream xstream = new XStream(new StaxDriver());
    Logger log=Logger.getLogger("AutorizarCheque");
    public AutorizarCheque(
            String tipocedula,String cedula,String anyoNacimiento,String banco,
            String numerocuenta,String numerocheque,String monto,
            String codestablecimiento,String sexo,String telefono){
    	log.info("INICIA AUTORIZAR CHEQUE");
        //super(AutorizarCheque.class);
    /*
		Service service=new Service();
    	
       V001C100Result respuesta = service.getServiceSoap().v001C100("fybeca", "ges45adm89cheq32", tipocedula, cedula, Integer.parseInt(anyoNacimiento), Integer.parseInt(banco)
					, numerocuenta, numerocheque, BigDecimal.valueOf(Double.parseDouble(monto)), codestablecimiento, Integer.parseInt(sexo), telefono);
		
    	System.out.println("V001C100Result   "+xstream.toXML(respuesta));
    	response=xstream.toXML(respuesta);
    	*/
    	
        GetMethod getMethod = new GetMethod("http://www.gestiona.ec/ws_autoriza_cheques_fybeca/Service.asmx/v001c100");
        HttpClient httpClient = new HttpClient();
        //httpClient.getHostConfiguration().setProxy("UIOPROXY01", 3128);
        try {
	               getMethod.setQueryString(
                               "user="+ "fybeca" +
                               "&passwd="+ "ges45adm89cheq32" +
                               "&tipocedula="+ tipocedula+
                               "&cedula="+ cedula+
                               "&annac="+ anyoNacimiento+
                               "&banco="+ banco+
                               "&numerocuenta="+ numerocuenta+
                               "&numerocheque="+ numerocheque+
                               "&monto="+ monto+
                               "&codestablecimiento="+ codestablecimiento+
                               "&sexo="+ sexo+
                               "&telefono="+telefono);

		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
        
        try {
        	log.info("URL CHEQUES "+getMethod.getURI());
            httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede llamar a servicio autorizador</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede llamar a servicio autorizador");
        }

        
        try {
            response = readInputStreamAsString(getMethod.getResponseBodyAsStream());
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede leer respuesta</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede leer respuesta",e);
        }
        getMethod.releaseConnection();
        log.info("FIN AUTORIZAR CHEQUE "+response);
        
    }
    /**
     * @Descripcion Metodo para confirmar la autorizacion del cheque, luego de autorizado el cheque con
     *  este metodo se cierra el caso confirmandole al emiso que se acepto la autorizacion
     * */
    public AutorizarCheque(
            String cedula,String banco,
            String numerocuenta,String numerocheque,String resultado,
            String codigoC){
    	/*
    	Service service=new Service();
    	Object respuesta = service.getServiceSoap().v001C101("fybeca", "ges45adm89cheq32", cedula, Integer.parseInt(banco), numerocuenta, numerocheque, resultado, codigoC);
    	System.out.println("V001C101Result   "+xstream.toXML(respuesta));
    	response=xstream.toXML(respuesta);
    	*/
    	log.info("INICIO CONFIRMAR CHEQUE ");
    	GetMethod getMethod = new GetMethod("http://www.gestiona.ec/ws_autoriza_cheques_fybeca/Service.asmx/v001c101");
        HttpClient httpClient = new HttpClient();
        //httpClient.getHostConfiguration().setProxy("UIOPROXY01", 3128);
        try {
	               getMethod.setQueryString(
                               "user="+ "fybeca" +
                               "&passwd="+ "ges45adm89cheq32" +
                               "&cedulac="+ cedula+
                               "&bancoc="+ banco+
                               "&numerocuentac="+ numerocuenta+
                               "&numerochequec="+ numerocheque+
                               "&resultadoc="+ resultado+
                               "&codigoc="+ codigoC);

		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}

        try {
        	log.info("URL CHEQUES "+getMethod.getURI());
            httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede llamar a servicio autorizador</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede llamar a servicio autorizador");
        }

        
        try {
            response = readInputStreamAsString(getMethod.getResponseBodyAsStream());
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede leer respuesta</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede leer respuesta",e);
        }
        getMethod.releaseConnection();
        log.info("FIN CONFIRMAR CHEQUE "+response);
    	
    }
    
    //-----------------camnbio anulacion cheque ------------
    public AutorizarCheque(
    		 String cedula,
    		 String numeroCuenta,
    		 String numeroCheque,
    		 String resultadoDoc,
    		 String codigoC){
    	/*
    	Service service=new Service();
    	Object respuesta = service.getServiceSoap().v001C101("fybeca", "ges45adm89cheq32", cedula, Integer.parseInt(banco), numerocuenta, numerocheque, resultado, codigoC);
    	System.out.println("V001C101Result   "+xstream.toXML(respuesta));
    	response=xstream.toXML(respuesta);
    	*/
    	log.info("INICIO ANULACION CHEQUE ");
    	GetMethod getMethod = new GetMethod("http://www.gestiona.ec/ws_autoriza_cheques_fybeca/Service.asmx/v001c102");
        HttpClient httpClient = new HttpClient();
        //httpClient.getHostConfiguration().setProxy("UIOPROXY01", 3128);
        try {
	               getMethod.setQueryString(
                               "user="+ "fybeca" +
                               "&passwd="+ "ges45adm89cheq32" +
                               "&cedulac="+ cedula+
                               "&numerocuentac="+ numeroCuenta+
                               "&numerochequec="+ numeroCheque+
                               "&resultadoc="+ resultadoDoc+
                               "&codigoc="+ codigoC);

		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}

        try {
        	log.info("URL CHEQUES "+getMethod.getURI());
            httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede llamar a servicio autorizador</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede llamar a servicio autorizador");
        }

        
        try {
            response = readInputStreamAsString(getMethod.getResponseBodyAsStream());
        } catch (IOException e) {
            response="<Transaccion><Respuesta>No se puede leer respuesta</Respuesta></Transaccion>";
            throw new RuntimeException("No se puede leer respuesta",e);
        }
        getMethod.releaseConnection();
        log.info("FIN CONFIRMAR CHEQUE "+response);
    	
    }
       
    //------------------------------------------------------
    
    
    
     private String readInputStreamAsString(InputStream inputStream) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
   }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
        
}
