/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;

/**
 *
 * @author mftellor
 */
public class PagoAgil extends Thread{
    public String idCadena;
    public String idEmpleado;
    public String idFarmacia;
    public String resultado;
    Logger log=Logger.getLogger("PagoAgil");
    public PagoAgil(String idCadena,String idFarmacia,String idEmpleado){
        this.idCadena=idCadena;
      this.idEmpleado=idEmpleado;
      this.idFarmacia=idFarmacia;      
      //log.info("INICIO Pago Agil idCadena="+idCadena+" idFarmacia="+idFarmacia+" idEmpleado="+idEmpleado);
      validarConexionVarianteSid(idCadena);
        
      if(!Constantes.conexionVarianteSid.isValid()){
          resultado="Error al conectar a la BDD";
          this.interrupt();   
      }else
          resultado="OK";
     //log.info("FIN Pago Agil idCadena="+idCadena+" idFarmacia="+idFarmacia+" idEmpleado="+idEmpleado);
    }

 public void run(){
     try {
          String queryEjecutar="update fa_ubicacion_empleados set farmacia= null "+
                               " where  empleado = "+ idEmpleado+
                               " and farmacia <> "+idFarmacia;
          String resultadoQuery=Constantes.conexionVarianteSid.ejecutarQuery(queryEjecutar);
          Constantes.conexionVarianteSid.cerrarConexion();
          //log.info("RESULTADO Pago Agil "+resultadoQuery+"   idCadena="+idCadena+" idFarmacia="+idFarmacia+" idEmpleado="+idEmpleado);
      } catch (Exception ex) {
          ex.printStackTrace();
        }
     this.interrupt();
 }

    public String getResultado() {
        return resultado;
    }
    
    public ConexionVarianteSid obtenerNuevaConexionVarianteSid(String parametro) {
        try {
            return new ConexionVarianteSid(Integer.decode(parametro));
        } catch (NamingException ex) {
            Logger.getLogger(PagoAgil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PagoAgil.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
    public void validarConexionVarianteSid(String empresa) {
    	    if(Constantes.conexionVarianteSid==null)
    	    	Constantes.conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
    	    else if(!Constantes.conexionVarianteSid.isValid())
    	    	Constantes.conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
    	    //System.out.println("");
    }

}
