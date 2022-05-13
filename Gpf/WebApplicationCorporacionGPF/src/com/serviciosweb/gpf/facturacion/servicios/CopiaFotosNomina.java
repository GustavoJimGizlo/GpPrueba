/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 *
 * @author mftellor
 */
public class CopiaFotosNomina extends ObtenerNuevaConexion{

    private String queryBusqueda="";
    private ResultSet rsBusqueda;
    private ConexionVarianteSid conexionVarianteSidNomina;
    private ConexionVarianteSid  conexionVarianteSidNominaInsertUpdate;
    private File file;
    private FileInputStream fis;
    private PreparedStatement ps = null;
    private Long codigoEmpleado;
    private File fileImagen;
    private Image img = null;
    private ImageUtils imageUtils=new ImageUtils();
    private final static String PARAMETRO_BDD="2";
    
    public CopiaFotosNomina(){
    	 super(CopiaFotosNomina.class);
       
    }
    
    
    public CopiaFotosNomina(String cedula){
        super(CopiaFotosNomina.class);
        try {
            queryBusqueda="select b.identificacion CEDULA ,a.imagen FOTO from ab_imagenes  a, ab_personas b " +
                          " where a.persona = b.codigo and a.tipo_imagen=1 ";
            String filtroCedula="";
            if(cedula.trim().length()>10)
                filtroCedula=" and b.identificacion='"+cedula+"'";            
            conexionVarianteSidNominaInsertUpdate= obtenerNuevaConexionVarianteSidSidDataBase("dbnomina.uio","hcms","fybeca");
            conexionVarianteSidNominaInsertUpdate.getConn().setAutoCommit(false);
            String pathImagenes = "/data/imagenes/empleados/";
            OutputStream out = null;
            List<HashMap<String, String>> listadoEmpleados = new ArrayList<HashMap<String, String>>();
            ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid("1");
            ResultSet rs = conexionVarianteSid.consulta(queryBusqueda+filtroCedula);
            System.out.println("queryBusqueda prod "+queryBusqueda+filtroCedula);
            while (conexionVarianteSid.siguiente(rs)) {
                try {
                    HashMap<String, String> datoEmpleado = new HashMap<String, String>();
                    datoEmpleado.put("CEDULA", conexionVarianteSid.getString(rs, "CEDULA"));
                    listadoEmpleados.add(datoEmpleado);

                    

                    out = new FileOutputStream(pathImagenes + conexionVarianteSid.getString(rs, "CEDULA") + ".jpg");
                    out.write(conexionVarianteSid.getByte(rs, "FOTO"));
                    out.close();

                    //getImagenMiniatura(conexionVarianteSid.getByte(rs, "FOTO"),250,250,conexionVarianteSid.getString(rs, "CEDULA")+".jpg");
                    imageUtils.procesar(pathImagenes + conexionVarianteSid.getString(rs, "CEDULA") + ".jpg");
                } catch (IOException ex) {
                    Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            conexionVarianteSid.cerrarConexion();
            rs.close();
            
            for(HashMap<String, String> dato:listadoEmpleados){
               // insertarFoto((String)dato.get("CEDULA"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertarFoto(String cedula){//,byte[] foto){
        try {
            validarConexionVarianteSid("dbnomina.uio", conexionVarianteSidNominaInsertUpdate);
            this.file = new File("/data/imagenes/empleados/" + cedula + ".jpg");
            this.fis = new FileInputStream(file);
            codigoEmpleado=verificarFotoEmpleadoBuxis(cedula);
            if(codigoEmpleado!=null)
                queryBusqueda="UPDATE maefoto SET cod_mf=?,foto_mf=? WHERE cod_mf="+codigoEmpleado;
            else{
                codigoEmpleado=getCodigoEmpleadoBuxis(cedula);
                queryBusqueda="INSERT INTO maefoto (cod_mf,foto_mf) VALUES(?,?)";
            }
            if(codigoEmpleado==null)
                return;
            this.ps = conexionVarianteSidNominaInsertUpdate.getConn().prepareStatement(queryBusqueda);
            this.ps.setLong(1, codigoEmpleado);
            this.ps.setBinaryStream(2, fis, (int) file.length());
            //((OraclePreparedStatement) ps).setRAW(2, new oracle.sql.RAW(foto));
            this.ps.executeUpdate();
            this.conexionVarianteSidNominaInsertUpdate.getConn().commit();
            this.ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private Long getCodigoEmpleadoBuxis(String cedula){
        Long codigoEmpleado=null;
        try {
            //validarConexionVarianteSidsidDataBase("dbnomina.uio", conexionVarianteSidNomina);
            conexionVarianteSidNomina = obtenerNuevaConexionVarianteSidSidDataBase("dbnomina.uio","hcms","fybeca");
            queryBusqueda = "select cod_mf CODIGO_EMPLEADO from maefunc where  trim(trim(cedide_mf)) = '" + cedula + "'";
            rsBusqueda = conexionVarianteSidNomina.consulta(queryBusqueda);
            while (conexionVarianteSidNomina.siguiente(rsBusqueda)) {
                codigoEmpleado=conexionVarianteSidNomina.getLong(rsBusqueda, "CODIGO_EMPLEADO");
            }
            conexionVarianteSidNomina.cerrarConexion();
            rsBusqueda.close();
        } catch (SQLException ex) {
            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigoEmpleado;
    }

        private Long verificarFotoEmpleadoBuxis(String cedula){
            Long codigoEmpleado=null;
        try {
            conexionVarianteSidNomina = obtenerNuevaConexionVarianteSidSidDataBase("dbnomina.uio","hcms","fybeca");
            queryBusqueda = "select a.cod_mf CODIGO_EMPLEADO from maefoto  a, maefunc b where  a.cod_mf = b.cod_mf and trim(trim(cedide_mf)) = '" + cedula + "'";
            rsBusqueda = conexionVarianteSidNomina.consulta(queryBusqueda);
            while (conexionVarianteSidNomina.siguiente(rsBusqueda)) {
                codigoEmpleado=conexionVarianteSidNomina.getLong(rsBusqueda, "CODIGO_EMPLEADO");
            }
            conexionVarianteSidNomina.cerrarConexion();
            rsBusqueda.close();
        } catch (SQLException ex) {
            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigoEmpleado;
    }
 @SuppressWarnings({ "rawtypes"})
 public  void getImagenMiniatura(byte[] imagen, int ancho,int alto,String fileName) throws IOException  {
	OutputStream out = null;
	this.img=null;
        InputStream iiInputStreams= new FileInputStream("/data/imagenes/empleados/"+fileName);

        ByteArrayInputStream in=new ByteArrayInputStream(iiInputStreams.toString().getBytes());
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        //Iterator readers = ImageIO.getImageReadersByFormatName("tiff");
        ImageReader reader = (ImageReader)readers.next();

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            int anchoOriginal = reader.getWidth(0);
            int altoOriginal = reader.getHeight(0);
            if ((ancho != 0)&&(alto!=0)&&(ancho<=anchoOriginal)&&(alto<=altoOriginal)){
                double proporcion = (altoOriginal / anchoOriginal) - (alto / ancho);
                int radio;
                if(proporcion < 0){
                    radio = (int)(anchoOriginal / ancho);
                }else{
                    radio = (int)(altoOriginal / alto);
                }
                param.setSourceSubsampling(radio, radio, 0, 0);
            }
            this.img = reader.read(0,param);
            this.fileImagen=new File("/data/imagenes/empleados/"+fileName);
            ImageIO.write(reader.read(0,param), "jpg", fileImagen);
            this.fileImagen=null;
            reader.dispose();
            //in.close();
            iis.close();
            System.out.println("PROCESA  " +fileName);
        }catch (Exception e) {
             //e.printStackTrace();
             System.out.println("FALLA  " +fileName+" ERROR "+e.getMessage());
        } finally {
            in.close();
            
        }
    }
 
	public String copiaImagenesProd(String codigoItem) {
		
		 byte[] imagen = imagen(codigoItem);
		 
		 if(imagen== null){
			 return Constantes.respuestaXmlObjeto("ERROR",
						"REGISTROS ACTUALIZADOS ");
		 }

		if (ConsultaImagenesProd(codigoItem)) {
			return ActulizaImagenesProd(codigoItem);
		}

		try {
			ConexionVarianteSid conexionVarianteSid = null;
			conexionVarianteSid = obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
			PreparedStatement ps = null;

			conexionVarianteSid.getConn().setAutoCommit(false);
			String queryInser = "";
			try {
				ps = null;
				queryInser = "INSERT INTO productos.pr_imagenes_items(ITEM, TIPO,FOTO, FECHA)"
						+ " VALUES(?,?,?,sysdate)";

				ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
				ps.setInt(1, Integer.parseInt(codigoItem));
				ps.setString(2, "1");
				ps.setBytes(3,imagen);
				ps.executeUpdate();
				conexionVarianteSid.getConn().commit();
				ps.close();

			
			} catch (SQLException ex) {
				Logger.getLogger(CopiaFotosNomina.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			conexionVarianteSid.cerrarConexion();
			return Constantes.respuestaXmlObjeto("OK",
					"REGISTROS ACTUALIZADOS ");
		} catch (SQLException ex) {

			Logger.getLogger(CopiaFotosNomina.class.getName()).log(
					Level.SEVERE, null, ex);
			return Constantes.respuestaXmlObjeto("ERROR",
					"REGISTROS ACTUALIZADOS ");

		} catch (Exception e) {
			Logger.getLogger(CopiaFotosNomina.class.getName()).log(
					Level.SEVERE, null, e);
			return Constantes.respuestaXmlObjeto("ERROR",
					"REGISTROS ACTUALIZADOS ");
		}

	}
	
	
	public byte[] imagen(String codigoItem) {

		byte[] binaryData = null;
		try {

			URL u = new URL("http://wservices02.gfybeca.int/copia/289317.jpg");
			int contentLength = u.openConnection().getContentLength();
			InputStream openStream = u.openStream();
			binaryData = new byte[contentLength];
			openStream.read(binaryData);
			openStream.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return binaryData;

	}
  
  
  public String  ActulizaImagenesProd(String codigoItem){
	  
		
		 byte[] imagen = imagen(codigoItem);
		 
		 if(imagen== null){
			 return Constantes.respuestaXmlObjeto("ERROR",
						"REGISTROS ACTUALIZADOS ");
		 }
		 
	        try {
	            ConexionVarianteSid conexionVarianteSid = null;
	            conexionVarianteSid = obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
	            PreparedStatement ps = null;
	           
	            conexionVarianteSid.getConn().setAutoCommit(false);
	            String queryInser = "";
	                try {
	                    ps = null;
	                    queryInser = "update productos.pr_imagenes_items  set foto = ? where item = ?";
	                   
	                    ps = conexionVarianteSid.getConn().prepareStatement(queryInser);
	                    ps.setBytes(1, imagen);
	                    ps.setInt(2, Integer.parseInt(codigoItem));
	                    ps.executeUpdate();
	                    ps.close();
	                    
	                }catch (SQLException ex){
	                    Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
	                }
	                conexionVarianteSid.cerrarConexion();
	                return Constantes.respuestaXmlObjeto("OK","REGISTROS ACTUALIZADOS");
	        } catch (SQLException ex) {
	            Logger.getLogger(CopiaFotosNomina.class.getName()).log(Level.SEVERE, null, ex);
	            return Constantes.respuestaXmlObjeto("ERROR","REGISTROS ACTUALIZADOS");
	        }
	        
	     
	        
  }
  
  
  
  
  
  public Boolean  ConsultaImagenesProd(String codigoItem){
	  
	  ConexionVarianteSid conexionVarianteSid = null;
      conexionVarianteSid = obtenerNuevaConexionVarianteSid(PARAMETRO_BDD);
      ResultSet rs = conexionVarianteSid.consulta("select * from productos.pr_imagenes_items where item ="+codigoItem);
      while (conexionVarianteSid.siguiente(rs)) {
    	  return true;      
      }
      return false;
  }
  
	  
  
  }

