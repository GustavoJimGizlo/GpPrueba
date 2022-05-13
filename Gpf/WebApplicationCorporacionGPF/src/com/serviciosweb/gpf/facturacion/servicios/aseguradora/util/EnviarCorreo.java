package com.serviciosweb.gpf.facturacion.servicios.aseguradora.util;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * @date 09/07/2012
 * @user mftellor
 * @project_name pedidos-commons
 * @company Corporacion GPF
 */
public class EnviarCorreo extends Thread {

    public  String remitente;
    private String destinatario;
    private String asunto;
    private String mensaje;
    private String menUser="";
    private String servidorCorreo="mail2";
    public EnviarCorreo(String remitente, String destinatario, String asunto, String mensaje){
        this.remitente=remitente;
        this.destinatario=destinatario;
        this.asunto=asunto;
        this.mensaje=mensaje;
        this.run();
    }  

    public EnviarCorreo(String remitente, String destinatario, String asunto, String mensaje,String servidorMail){
        this.remitente=remitente;
        this.destinatario=destinatario;
        this.asunto=asunto;
        this.mensaje=mensaje;
        this.servidorCorreo=servidorMail;
        this.run();
    }  

    
    @Override
    @SuppressWarnings("static-access")
    public void run() {        
    	//System.out.println("<<<servidorCorreo>>>> "+this.servidorCorreo);
        Properties props = new Properties();
        props.setProperty("mail.smtp.host",this.servidorCorreo);
        props.setProperty("mail.smtp.port","25");
        props.setProperty("mail.smtp.auth", "false");        
        Session mailSession = Session.getDefaultInstance(props, null);
        MimeMessage Correo= new MimeMessage(mailSession);
        try {
            Correo.setFrom(new InternetAddress(this.remitente));
            if(this.destinatario.contains(",")){            	
            	for(String emailCC:this.destinatario.split(","))
            	  Correo.addRecipient(Message.RecipientType.CC, new InternetAddress(emailCC));
            }else
            	  Correo.addRecipient(Message.RecipientType.TO, new InternetAddress(this.destinatario));
            
            Correo.setSubject(this.asunto);

            Correo.setContent(this.mensaje+"<br><br><img src=\"https://www.corporaciongpf.com/img/logotip.png\">", "text/html");
            Transport transportador = mailSession.getTransport("smtp");
            //mailSession.setDebug(true);
            transportador.send(Correo);
            this.menUser="Email enviado correctamente";
         } catch (Exception ex) {            
             ex.printStackTrace();
            this.menUser = ex.toString();
            this.menUser= this.menUser.replaceAll("[\n\r]","");
        }
        this.interrupt();
        System.gc();
    }
    
    
    public String getMenUser() {
        return menUser;
    }
}