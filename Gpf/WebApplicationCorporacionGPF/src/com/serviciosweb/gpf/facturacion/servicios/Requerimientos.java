package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.ResultSet;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.EnviarCorreo;
import com.gpf.postg.pedidos.util.GetDatosGenerica;
import com.serviciosweb.gpf.facturacion.farmacias.bean.RequerimientosBean;
//import com.sun.org.apache.xml.internal.serializer.utils.Messages;

public class Requerimientos extends GetDatosGenerica<RequerimientosBean> implements Runnable{

	public String BDD_CONECTA="1";
	private String numeroRequerimiento;
	private String mensajeRespuesta=null;
	
	
	public Requerimientos() {
		super(RequerimientosBean.class);
	}
	public Requerimientos(String numeroRequerimiento) {
		super(RequerimientosBean.class);
		this.numeroRequerimiento=numeroRequerimiento;
	}
	public static void main(String[] args) {
		new Requerimientos("4705").run();
		//new Requerimientos().autorizarRequerimiento("a78e17c964d3593d89cde3fb678f6a14","81357b872490d4f3ce774a639ff6c153","S");
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run(){
		try {
			Thread.sleep(1*60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequerimientosBean req=this.getRequerimientosBean(this.numeroRequerimiento);
		if(req==null)
		   return;
		String contenidoEmail=Constantes.getContenidoFile("APROBAR_REQUERIMIENTO_V1.TXT", "plantillas");
		contenidoEmail=contenidoEmail.replace("NOMBRE_APRUEBA_REQUERIMIENTO", req.getNombrePersonaRechaza()).replace("NUMERO_REQUERIMIENTO", req.getCodigoRequerimiento());
		contenidoEmail=contenidoEmail.replace("NOMBRE_INGRESA_REQUERIMIENTO", req.getNombreSolicita()).replace("DESCRIPCION_REQUERIMIENTO", req.getDescripcionRequerimiento().replace("\n", "<br>"));
		
		String contenidoEmailLinks=Constantes.getContenidoFile("APROBAR_REQUERIMIENTO_LINKS.TXT", "plantillas");
		contenidoEmailLinks=contenidoEmailLinks.replace("LINK_APROBAR", MD5(req.getCodigoRequerimiento()));		
		contenidoEmailLinks=contenidoEmailLinks.replace("USER_APROBADOR", MD5(req.getUserAprobador()));		
		
	    log.info("contenidoEmail:  "+contenidoEmail);
	    
		new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", "mftellor@corporaciongpf.com", "XXXXXXX Aprobar Req. "+ req.getCodigoRequerimiento(), contenidoEmail);
		contenidoEmail=contenidoEmail.replace("LINK_APROBAR", contenidoEmailLinks);
		log.info("contenidoEmail user:  "+contenidoEmail);		
		new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", req.getEmailUtorizaRechaza(), "Aprobar Req. "+ req.getCodigoRequerimiento(), contenidoEmail);
		log.info("EMAIL ENVIADO A:  "+req.getEmailUtorizaRechaza());
		Thread.currentThread().interrupted();
	}
	public String autorizarRequerimiento(String numeroRequerimiento,String userAprobador,String valor){
		String resultado="";

		
		String descripcionAccionUsuario=" Autorizado";
		valor=valor.equals("S")?"2":"5";
		if(valor.equals("5"))
			descripcionAccionUsuario=" Rechazado";
		String queryBusqueda="SELECT a.codigo,descripcion_req,estado_req,B.DESCRIPCION "
				+ " ,(select nombre_usuario from WF_USUARIOS where dbms_obfuscation_toolkit.md5(input => UTL_RAW.cast_to_raw(nombre_usuario))=UPPER('"+userAprobador+"')) as user_aprobador "
				+ " FROM ad_ordenes_requerimiento a, ad_estados b "
				+ " where A.ESTADO_REQ=B.CODIGO "// order by codigo desc";
				+ " AND dbms_obfuscation_toolkit.md5(input => UTL_RAW.cast_to_raw(a.codigo))=UPPER('"+numeroRequerimiento+"')";
		ConexionVarianteSid con = obtenerNuevaConexionVarianteSidMaster(BDD_CONECTA);
		ResultSet rs = con.consulta(queryBusqueda);
		while(con.siguiente(rs)){
			RequerimientosBean req=new RequerimientosBean();
			req.setDescripcionRequerimientoClob(con.getCLOB(rs, "descripcion_req"));
			req.setCodigoRequerimiento(con.getString(rs, "codigo"));
			req.setUserAprobador(con.getString(rs, "user_aprobador"));
			if(MD5(req.getCodigoRequerimiento()).equals(numeroRequerimiento)){
				req=getRequerimientosBean(req.getCodigoRequerimiento());
					//VALIDA NUMERO MAX REQ PERMITIDOS POR CC
					if(!validarAprobacion(numeroRequerimiento)) {
						new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", req.getEmailUsuario()+","+req.getEmailUtorizaRechaza(), "Req. "+req.getCodigoRequerimiento()+descripcionAccionUsuario, this.mensajeRespuesta);
						resultado="EL REQUERIMIENTO NO PUEDE SER APROBADO, REVISE SU EMAIL";
						log.info("EMAIL ENVIADO A:  "+req.getEmailUtorizaRechaza()+" & "+req.getEmailUsuario());
						return resultado;
					}
				if(con.getInt(rs, "estado_req")!=1){
					resultado="EL REQUERIMIENTO: "+req.getCodigoRequerimiento()+" SE ENCUENTRA "+con.getString(rs, "DESCRIPCION")+" Y NO PUEDE SER REPROCESADO";
					break;					
				}
				//CAMBIO DE ESTADO REQUERIMIENTO
				String cuerpoEmail="<html>Estimad@,<br><br>El req. "+req.getCodigoRequerimiento()+" fue <b><i>"+descripcionAccionUsuario+"</i></b><br><br>Descripcion requerimiento:<br><br>"+req.getDescripcionRequerimiento().replace("\n", "<br>")+"</html>";
				//System.out.println("req.getCodigoRequerimiento()  "+req.getCodigoRequerimiento());
				queryBusqueda="UPDATE ad_ordenes_requerimiento SET fecha_aprobacion=sysdate,USUARIO_APROBACION='"+req.getUserAprobador()+"',estado_req="+valor+"  WHERE CODIGO="+req.getCodigoRequerimiento();
				log.info("queryUpdate "+queryBusqueda);
				con.ejecutar(queryBusqueda);
				String emailNotifica=Constantes.getContenidoFile("MAIL_REQUERIMIENTO.TXT", "plantillas");;
				//new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", emailNotifica, "Ingresa Nuevo Req. "+ req.getCodigoRequerimiento()+descripcionAccionUsuario, cuerpoEmail);
				if(valor.equals("2")){
					new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", emailNotifica, "Req. "+req.getCodigoRequerimiento()+descripcionAccionUsuario, cuerpoEmail);
				}
				new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com", req.getEmailUsuario(), "Req. "+req.getCodigoRequerimiento()+descripcionAccionUsuario, cuerpoEmail);
				new EnviarCorreo("aprobacionrequerimientosWeb@corporaciongpf.com",req.getEmailUtorizaRechaza(), "Req. "+req.getCodigoRequerimiento()+descripcionAccionUsuario, cuerpoEmail);
				resultado="EL REQUERIMIENTO: "+req.getCodigoRequerimiento()+" FUE "+descripcionAccionUsuario.toUpperCase()+" EXITOSAMENTE";
				log.info("EMAIL ENVIADO A:  "+req.getEmailUtorizaRechaza()+" & "+req.getEmailUsuario());
				break;
			}
		}		
		return resultado;
	}

	private Boolean validarAprobacion(String numeroRequerimiento) {
		try {
			String centroCostos=null;
			Integer totalReqPermitidos=null;
			Integer totalReqCurso=null;
			String queryBusqueda="select centro_costos from ad_ordenes_requerimiento where "
					+ " dbms_obfuscation_toolkit.md5(input => UTL_RAW.cast_to_raw(codigo))=UPPER('"+numeroRequerimiento+"')";
			ConexionVarianteSid conn = obtenerNuevaConexionVarianteSidMaster(BDD_CONECTA);
			ResultSet rs = conn.consulta(queryBusqueda);
			while(conn.siguiente(rs)){
				centroCostos=conn.getString(rs, "centro_costos");
			}
			queryBusqueda="SELECT count(1) as totalReqCurso  FROM   ad_ordenes_requerimiento WHERE  centro_costos="+centroCostos 
					+" AND    avance_trabajo >= 95 AND    avance_trabajo < 100 AND    estado_req NOT IN (1, 9)";
			rs = conn.consulta(queryBusqueda);
			while(conn.siguiente(rs)){
				totalReqCurso=conn.getInt(rs, "totalReqCurso");
			}
			queryBusqueda=" SELECT TO_NUMBER(valor) as totalReq  FROM   ad_parametros_req_det   WHERE  tipo = 'MAXRE' "  
					+"      AND  parametro="+centroCostos+"  AND    fecha_fin IS NULL";
			rs = conn.consulta(queryBusqueda);
			while(conn.siguiente(rs)){
				totalReqPermitidos=conn.getInt(rs, "totalReq");
			}
	        if(totalReqCurso>totalReqPermitidos) {
	        	this.mensajeRespuesta="<html>Estimad@,<br><br>No se puede enviar más requerimientos, debido a que su área tiene: "+totalReqCurso+" req. en fase de pruebas, favor validar y confirmar su paso a producción. El No.de req. permitidos en fase de pruebas es: "+totalReqPermitidos+"</html";
	        	return false;
	        }else
	        	return true;
		}catch (Exception e) {
			return true;
		}
	}
	private RequerimientosBean getRequerimientosBean(String numeroRequerimiento){
		String queryBusqueda="select codigo,obtiene_nombres(usuario_genera) usgenera, nombre_solicita,descripcion_req, usuario_aprobacion, "+
				" (select descripcion from ad_estados where codigo = estado_req) estado, estado_req, "+
				" (select obtiene_nombres(usuario_jefe) from ad_lideres_jefe where usuario_lider = usuario_genera) autoriza_rechaza, "+
				" (select email from ad_lideres_jefe where usuario_lider = usuario_genera) email_usuario," +
				"(select email from ad_lideres_jefe p where p.usuario_jefe = (select x.usuario_jefe from ad_lideres_jefe x where x.usuario_lider=usuario_genera and x.usuario_jefe = p.usuario_lider)) email_autoriza_rechaza "+
				",(select nombre_usuario from WF_USUARIOS where codigo_persona=(select usuario_jefe from ad_lideres_jefe where usuario_lider = usuario_genera)) user_aprobador "+
				" from ad_ordenes_requerimiento where codigo = "+numeroRequerimiento;
		List<RequerimientosBean> listado=getDatosOracle(BDD_CONECTA, queryBusqueda);
		if(listado==null)
			return null;
		RequerimientosBean req=listado.get(0);
		return req;
	}
	
	public String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}

}
