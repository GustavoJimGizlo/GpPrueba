package com.gpf.serviciosweb.facturacion.farmacias;

import java.sql.ResultSet;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

public class ConsultaCupoDisponible extends ObtenerNuevaConexion {
    String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
    Logger log=Logger.getLogger("ConsultaDetalle");
    String valorCupo=null;
	public ConsultaCupoDisponible(String idCliente) {
		super(ConsultaCupoDisponible.class);
		ConexionVarianteSid conexion=obtenerNuevaConexionVarianteSid("1");
		if(conexion.getConn()==null){
            resultadoXml="FALLA LA CONEXION A LA BDD, ESPERE UN UNOS SEGUNDOS E INTENTE NUEVAMENTE";
            return;
        }
		ResultSet rs = conexion.consulta("select farmacias.fa_cupo_disponible_cliente("+idCliente+") as cupo from dual");
		while(conexion.siguiente(rs)){
			valorCupo=conexion.getString(rs, "cupo");
		}				
		conexion.cerrarConexion(rs);		
	}
	public String getResultadoXml() {
	       if(valorCupo!=null)
	          return  Constantes.respuestaXmlObjeto("OK",valorCupo) ;
	       else
	         return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
	}

}
