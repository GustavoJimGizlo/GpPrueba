package com.gpf.serviciosweb.facturacion.farmacias;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

import com.gpf.partnersGroupGPF.bean.CreditoCorpBean;




public class CreditoCorp extends ObtenerNuevaConexion implements Serializable{
	/**
	 * 
	 */
	private List<CreditoCorpBean> listRespuesta = new ArrayList<CreditoCorpBean>();
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(this.getClass().getName());
	private String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA, CON LOS PARAMETROS ENVIADOS";
	private String idBdd="44"; // 1 PRODUCCION 44 PORD_DESA


	
	public CreditoCorp(
			String codigo_convenio_corporativo,
			String tipo_movimiento,
			String clasificacion_movimiento,
			String farmacia,
			String documento,
			String numero_factura, 
			String valor,
			String valor_iva,
			String fecha_factura,
			String llevado,
			String estado,
			String usuario) {
				
		super(CreditoCorp.class);
			
			
			 try {
				 this.insertFarmacias(codigo_convenio_corporativo,tipo_movimiento,clasificacion_movimiento,Integer.valueOf(farmacia),documento,numero_factura,Double.valueOf(valor), Double.valueOf(valor_iva),fecha_factura,llevado,estado,usuario);	
						} catch (Exception e) {
								e.printStackTrace();
		} 
		 
		
		
	}
	public String insertFarmacias(
			String codigo_convenio_corporativo,
			String tipo_movimiento,
			String clasificacion_movimiento,
			Integer farmacia,
			String documento,
			String numero_factura, 
			Double valor,
			Double valor_iva,
			String fecha_factura,
			String llevado,
			String estado,
			String usuario){
		 xstream.alias("CreditoCorp", CreditoCorpBean.class);
     	
         String fechatra = "SYSDATE";
         
         String fechafac = "to_date('"+fecha_factura+"', 'dd/mm/yyyy')";
         
         String insert=" INSERT INTO VITALCARD.VC_TRANSACCIONES_CORP (CODIGO ,CODIGO_CONENIO_CORPORATIVO ,TIPO_MOVIMIENTO ,CLASIFICACION_MOVIMIENTO,FARMACIA,DOCUMENTO,NUMERO_FACTURA,VALOR,VALOR_IVA,FECHA_TRANSACCION,FECHA_FACTURA,LLEVADO ,ESTADO,USUARIO)"+
					 " VALUES('"+ this.secuenciaCreditoCorporativo(farmacia.toString())+"','"+codigo_convenio_corporativo+"','"+tipo_movimiento+"','"+clasificacion_movimiento+"',"+farmacia+",'"+documento+"','"+numero_factura+"',"+valor+","+valor_iva+","+fechatra+","+fechafac+",'"+llevado+"','"+estado+"','"+usuario+"')";
			
				ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(idBdd);
				System.out.println(insert);
		      	String respuesta = conexionVarianteSid.ejecutarQuery(insert);
				conexionVarianteSid.cerrarConexion();
				System.out.print("RESPUESTA TRANSACCION :"+respuesta);
		this.resultadoXml = respuesta;
	
	return respuesta;
	
		
	}		
	
	private String secuenciaCreditoCorporativo(String codigoFarmacia) {
		boolean secuencia = false;
		PreparedStatement ps = null;
		ResultSet rows = null;
		int longCodFar = 0;
		String result = "";
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(idBdd);
		Connection conn = null;
		conn = conexionVarianteSid.getConn();

		try {
					String e = " select length(codigo) codigo  from ad_farmacias  where codigo = "+ codigoFarmacia;
					ps = conn.prepareStatement(e);
					rows = ps.executeQuery();

						while (rows.next()) {
						try {
								longCodFar = rows.getInt("codigo");
						} catch (SQLException arg23) {
							arg23.printStackTrace();
					}
				}

		int secuencia1;
				if (longCodFar >= 5) {
						secuencia1 = 10000000;
					} else {
						secuencia1 = 100000000;
					}

		int secuenciaVitalcard = Integer.parseInt(codigoFarmacia) * secuencia1;
		
						String querys = " select vc_tsn_corp_seq.nextval + "
						+ secuenciaVitalcard + " as  vcTsnSeq from dual";
						ps = conn.prepareStatement(querys);
						rows = ps.executeQuery();

						while (rows.next()) {
							try {
								result = rows.getString("vcTsnSeq");
							} catch (SQLException arg22) {
								arg22.printStackTrace();
							}
						}
			} catch (SQLException arg24) {
				arg24.printStackTrace();
		} finally {
			try {
				//this.close(rows);
				conn.close();
			conexionVarianteSid.cerrarConexion();
			} catch (SQLException arg21) {
				;
			}

		}

		return result;
	}
	
	
	public String getResultadoXml() {
		if(resultadoXml.equals("OK"))
			return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
		else
			return  Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA") ;
	}	

	 
	


/*public static void main(String[] args) {
		 new CreditoCorp("15225", "12","01","01","1244","002-023-000000043","20","20.24","15-04-2018","15-06-2018","S","N","KMTOAQUIZA");
		//1716848634/15225/12/1244/002-023-000000043/20/20.24/15-04-2018/15-06-2018/KMTOAQUIZAM
 
		 
	}*/
}
