/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;

import com.gpf.postg.pedidos.util.ConexionMSSQLServer;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.EnvioParametros;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Convert;
import com.gpf.postg.pedidos.util.ObtenerConexion;
import com.serviciosweb.gpf.facturacion.servicios.bean.ResultadoTarjetaVCBean;
import com.serviciosweb.gpf.facturacion.servicios.bean.ResultadoTransaccionesBean;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mftellor
 */
public class MedContinua implements ObtenerConexion{

 public  ResultadoTarjetaVCBean consultaVC(String cedula,String usuario,String clave){
     ResultadoTarjetaVCBean resp = null;
     String BDDResponse = "";
     String msgUsuario = "";
     ResultSet rs = null;
     String tienePlan = null;
     CallableStatement comm=null;

     try {
     	resp = new ResultadoTarjetaVCBean();
     	resp.setEstadoTrx(new Boolean(false));


        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");

	    ArrayList<Integer> Out = new ArrayList();
        ArrayList<EnvioParametros> OutLista = new ArrayList();
        ArrayList<Integer> In = new ArrayList();
        ArrayList<String>  Variables=new ArrayList();
        ArrayList<EnvioParametros> InLista = new ArrayList();
        String procedimiento;
        Out.add(OracleTypes.CURSOR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        OutLista=EntradaSalidaObj(1,Out,Variables);
        In.add(Types.VARCHAR);
        Variables.add(cedula);
        InLista=EntradaSalidaObj(Out.size()+1,In,Variables);
        procedimiento="{ call  VITALCARD.pckwb_medicacion_continua.consulta_tarjeta_plan(?,?,?,?,?) }";
        comm=conexionVarianteSid.Extraer(procedimiento,InLista,OutLista,usuario,clave);
        //comm = conexion.invocaProcedimiento(cedula);
        BDDResponse = comm.getString(2);
        msgUsuario = comm.getString(3);
        tienePlan = comm.getString(4);
        //if(!BDDResponse.equalsIgnoreCase("OK")){
        resp.setMensaje(msgUsuario);
        //        throw new SQLException(BDDResponse);
        //}
        resp.setTienePlan(tienePlan);
        rs = (ResultSet)comm.getObject(1);
        if(rs.next()){
                resp.setPrimerNombre((String)Convert.getDato(rs,"PRIMER_NOMBRE"));
                resp.setSegundoNombre((String)Convert.getDato(rs,"SEGUNDO_NOMBRE"));
                resp.setPrimerApellido((String)Convert.getDato(rs,"PRIMER_APELLIDO"));
                resp.setSegundoApellido((String)Convert.getDato(rs,"SEGUNDO_APELLIDO"));
                rs.close();
                resp.setEstadoTrx(new Boolean(true));
               // resp.setMensaje((String) Convert.getDato(rs,"PRIMER_NOMBRE"));
               // resp.setMensaje(Constants.SUCCESSFUL_TRANSACTION);
        }
        else {
                rs.close();
                resp.setEstadoTrx(new Boolean(false));
                resp.setMensaje("Paciente consultado no tiene tarjeta Vitalcard o no está Activa para afiliarse al club PMC ");

        }
    } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            resp.setEstadoTrx(new Boolean(false));
            if(resp.getMensaje() == null || resp.getMensaje().equals("") )
               resp.setMensaje(Constantes.ERROR_TRANSACTION);
    } finally {
            try {
                    if (comm != null) {
                        comm.close();
                    }
                    //if (sess != null) sess.close();
            } catch (Exception e1) {
                    //logger.error(e1.getMessage(), e1);
                    comm = null;
                    //sess = null;
            }
    }
return resp;
 }//consultaVC

public  ArrayList<EnvioParametros> EntradaSalidaObj(int indice,ArrayList<Integer> lista,ArrayList<String> Variables){
    ArrayList<EnvioParametros> Out = new ArrayList();
    int num=indice;
    int num1=0;
    for(Integer tipo : lista){
        EnvioParametros objeto=new EnvioParametros();
        objeto.setNumero(num++);
        objeto.setTipo(tipo);
        if(Variables.size()>0)
             objeto.setVariable(Variables.get(num1++));
        Out.add(objeto);
    }

    return Out;
}

public  ResultadoTarjetaVCBean ingresoVC(String cedula,String usuario,String clave){
 	ResultadoTarjetaVCBean resp = null;

     String BDDResponse = "";
     String msgUsuario = "";
     CallableStatement comm=null;
     try {
     	resp = new ResultadoTarjetaVCBean();
     	resp.setEstadoTrx(new Boolean(false));
        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
	ArrayList<Integer> Out = new ArrayList();
        ArrayList<EnvioParametros> OutLista = new ArrayList();
        ArrayList<Integer> In = new ArrayList();
        ArrayList<String>  Variables=new ArrayList();
        ArrayList<EnvioParametros> InLista = new ArrayList();
        String procedimiento;
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        Out.add(Types.VARCHAR);
        OutLista=EntradaSalidaObj(1,Out,Variables);
        In.add(Types.VARCHAR);
        Variables.add(cedula);
        InLista=EntradaSalidaObj(Out.size()+1,In,Variables);
        procedimiento="{ call  VITALCARD.pckwb_medicacion_continua.graba_paciente_pmc(?,?,?,?,?,?,?) }";
        comm=conexionVarianteSid.Extraer(procedimiento,InLista,OutLista,usuario,clave);
        //comm = conexion.invocaProcedimiento(cedula);
        BDDResponse = comm.getString(1);
        msgUsuario = comm.getString(2);
        //tienePlan = comm.getString(4);
        //if(!BDDResponse.equalsIgnoreCase("OK")){
        resp.setMensaje(msgUsuario);
        //        throw new SQLException(BDDResponse);
       // }
        resp.setPrimerNombre(comm.getString(3));
        resp.setSegundoNombre(comm.getString(4));
        resp.setPrimerApellido(comm.getString(5));
        resp.setSegundoApellido(comm.getString(6));
        resp.setEstadoTrx(new Boolean(true));
        //resp.setMensaje(Constants.SUCCESSFUL_TRANSACTION);
        resp.setMensaje(msgUsuario);

    } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            resp.setEstadoTrx(new Boolean(false));
            if(resp.getMensaje() == null || resp.getMensaje().equals("") )
                    resp.setMensaje(Constantes.ERROR_TRANSACTION);
    } finally {
            try {
                    if (comm != null) comm.close();
                    //if (sess != null) sess.close();
            } catch (Exception e1) {
                    //logger.error(e1.getMessage(), e1);
                    comm = null;
                    //sess = null;
            }
    }
return resp;
 }

public  ArrayList<ResultadoTransaccionesBean> TransaccionesVC( String fechaInicial, String empresa,String usuario,String clave){
     CallableStatement comm=null;
     ArrayList<ResultadoTransaccionesBean> respuesta = new ArrayList<ResultadoTransaccionesBean>();
     String BDDResponse = "";
     String msgUsuario = "";
     try {
        String queryBusqueda="SELECT persona,identificacion,documento_venta,tipo_documento,unidades,"+
                             " unidad_bonif,farmacia,nombre_farmacia,primer_apellido,segundo_apellido,"+
                             " primer_nombre,segundo_nombre,item,descripcion_item,cantidad,"+
                             " tipo_transaccion,cantidad_bonificacion,"+
                             " numero_sri,empresa,TO_CHAR (FECHA, 'dd/mm/yyyy') AS FECHA "+
                             " FROM WB_VENTAS_PMC@WEB " +
                             " WHERE FECHA>=TO_DATE ('"+fechaInicial+"' || ' 00:00:00','dd/mm/yyyy hh24:mi:ss')"+
                             " AND FECHA<=TO_DATE ('"+fechaInicial+"' || ' 23:59:59','dd/mm/yyyy hh24:mi:ss')"+
                             " AND EMPRESA='"+empresa+"'";
        ResultadoTransaccionesBean resp;
        ConexionVarianteSid conexionVarianteSid=obtenerNuevaConexionVarianteSid("1");
        ResultSet rs = conexionVarianteSid.consulta(queryBusqueda);
            while(rs.next()){
                    resp=new ResultadoTransaccionesBean();
                    resp.setEstadoTrx(new Boolean(false));
                    resp.setMensaje(BDDResponse);
                    resp.setCodigoFamacia(Long.decode(conexionVarianteSid.getString(rs, "FARMACIA")));
                    //resp.setCodigoDocumento((Long)Convert.getDato(rs,"documento_venta"));
                    resp.setCedula((String)Convert.getDato(rs,"identificacion"));
                    resp.setPrimerNombre((String)Convert.getDato(rs,"PRIMER_NOMBRE"));
                    resp.setSegundoNombre((String)Convert.getDato(rs,"SEGUNDO_NOMBRE"));
                    resp.setPrimerApellido((String)Convert.getDato(rs,"PRIMER_APELLIDO"));
                    resp.setSegundoApellido((String)Convert.getDato(rs,"SEGUNDO_APELLIDO"));
                    resp.setCodigoProducto(Long.decode(conexionVarianteSid.getString(rs, "item")));
                    resp.setDescripcionProducto((String)Convert.getDato(rs,"descripcion_item"));
                    resp.setCantidadCompras(Long.decode(conexionVarianteSid.getString(rs, "cantidad")));
                    resp.setEspecificacionTipoTrx((String)Convert.getDato(rs,"tipo_transaccion"));
                    resp.setEspecificacionCantidad(Convert.getDato(rs,"cantidad_bonificacion").toString());
                    resp.setFechaCompra(conexionVarianteSid.getString(rs,"FECHA"));
                    resp.setTipoDocumento((String)Convert.getDato(rs,"TIPO_DOCUMENTO"));
                    resp.setNumeroFactura((String)Convert.getDato(rs,"numero_sri"));
                    resp.setUnidadBonificacion(Long.decode(conexionVarianteSid.getString(rs, "unidad_bonif")));
                    resp.setUnidadVenta(Long.decode(conexionVarianteSid.getString(rs, "unidades")));
                    resp.setEstadoTrx(new Boolean(true));
                    resp.setMensaje(msgUsuario);
                    respuesta.add(resp);
            }
        return respuesta;

    } catch (Exception e) {
        ResultadoTransaccionesBean resp =new ResultadoTransaccionesBean();
        resp.setMensaje(msgUsuario);
        resp.setEstadoTrx(new Boolean(false));
        respuesta.add(resp);
        return respuesta;
    } finally {
            try {
                    if (comm != null) comm.close();
                    //if (sess != null) sess.close();
            } catch (Exception e1) {
                    //logger.error(e1.getMessage(), e1);
                    comm = null;
                    //sess = null;
            }
    }

 }

    public ConexionVarianteSid obtenerNuevaConexionVarianteSid(String parametro) {
        try {
            return new ConexionVarianteSid(Integer.decode(parametro));
        } catch (NamingException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }

    @SuppressWarnings("static-access")
    public void validarConexionVarianteSid(String empresa, ConexionVarianteSid conexionVarianteSid) {
        try {
            if (conexionVarianteSid.getConn()==null)
                 conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
            else if (conexionVarianteSid.getConn().isClosed())
                conexionVarianteSid=obtenerNuevaConexionVarianteSid(empresa);
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConexionVarianteSid obtenerNuevaConexionVarianteSidSidDataBase(String sidDataBase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public ConexionMSSQLServer obtenerNuevaConexionConexionMSSQLServerDataBase(
			String sidDataBase) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}
}
