package com.serviciosweb.gpf.facturacion.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

/**
 * @author hrizquierdom
 *
 */
public class VoucherController extends ObtenerNuevaConexion {
	private Vector xmlDato = new Vector();
	String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
	
	//String conexionBD = "44";//Pruebas
	String conexionBD = "1";//Producción
	boolean consultaVoucherCorrecto = false;
	
	
	public VoucherController(){
		super(VoucherController.class);
	}
	
	public VoucherController(String codigo_voucher, int codigoPersona, String farmacia, String monto_compra){
		super(VoucherController.class);
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(conexionBD);
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT																																");
		sb.append(" 	fcv.COD_PARAM_BC																												");
		sb.append(" 	,fcv.CODIGO_PERSONA																												");
		sb.append(" 	,fpb.VALOR_BONO																													");
		sb.append("  ,DECODE(																															");
		sb.append("    fpb.TODOS_PDV																													");
		sb.append("    ,'N'																																");
		sb.append("    ,DECODE(																															");
		sb.append("      (SELECT count(1) FROM FA_FARMACIAS_BC WHERE CODIGO_BONO = fcv.COD_PARAM_BC AND CODIGO_FARMACIA = ? )							");
		sb.append("      , 0																															");
		sb.append("      , 'El cupon no esta parametrizado en este PDV '																			");
		sb.append("      , 'OK'																															");
		sb.append("    )																																");
		sb.append("    , 'OK'																															");
		sb.append("    ) PUEDE_CANJEAR_POR_PDV																											");
		sb.append(" 	,CASE WHEN fpb.COMPRA_CANJE <= ? THEN 'OK' ELSE 'Valor menor al solicitado para el canje' END PUEDE_CANJEAR_POR_MONTO			");
		sb.append(" 	,CASE WHEN fcv.CODIGO_PERSONA = ? THEN 'OK' ELSE 'Cupon no pertenece al cliente ingresado' END PERSONA_CORRECTA					");
		sb.append(" 	,CASE WHEN UPPER(status_voucher) = 'DISPONIBLE' THEN 'OK' else 'Cupon en estado ' || status_voucher END ESTADO_VOUCHER		");
		sb.append(" FROM farmacias.fa_clientes_voucher fcv																								");
		sb.append(" INNER JOIN fa_param_bc fpb on fpb.CODIGO = fcv.COD_PARAM_BC																			");
		sb.append(" WHERE 1=1																															");
		sb.append(" 	AND cod_voucher = ?																												");
		//sb.append(" 	AND fpb.activo = 'S'																											");
		//sb.append(" 	AND UPPER(status_voucher) = 'DISPONIBLE'																						");
		sb.append(" 	AND SYSDATE BETWEEN fcv.fecha_inicio AND fcv.fecha_fin																			");
		try {
			PreparedStatement ps = conexionVarianteSid.getConn().prepareStatement(sb.toString());
			ps.setInt(1, Integer.parseInt(farmacia));
			ps.setDouble(2, Double.parseDouble(monto_compra));
			ps.setInt(3, codigoPersona);
			ps.setString(4, codigo_voucher);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("PUEDE_CANJEAR_POR_PDV").equals("OK")){
					if(rs.getString("PUEDE_CANJEAR_POR_MONTO").equals("OK")){
						if(rs.getString("PERSONA_CORRECTA").equals("OK")){
							if(rs.getString("ESTADO_VOUCHER").equals("OK")){
								this.resultadoXml = rs.getString("VALOR_BONO");
								consultaVoucherCorrecto = true;								
							}else{
								this.resultadoXml = rs.getString("ESTADO_VOUCHER");
							}
						}else{
							this.resultadoXml = rs.getString("PERSONA_CORRECTA");							
						}
					}else{
						this.resultadoXml = rs.getString("PUEDE_CANJEAR_POR_MONTO");
					}
				}else{
					this.resultadoXml = rs.getString("PUEDE_CANJEAR_POR_PDV");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void consumirVoucher(String codigo_voucher, String farmacia, String documentoVenta){
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSid(conexionBD);
		StringBuilder sb = new StringBuilder();
		sb.append("  UPDATE FARMACIAS.FA_CLIENTES_VOUCHER SET STATUS_VOUCHER = 'Usado', CP_NUMBER1 =?, CP_NUMBER2 =? WHERE COD_VOUCHER = ? ");
		try {
			PreparedStatement ps = conexionVarianteSid.getConn().prepareStatement(sb.toString());
			ps.setInt(1, Integer.parseInt(farmacia));	
			ps.setInt(2, Integer.parseInt(documentoVenta));
			ps.setString(3, codigo_voucher);
			int r = ps.executeUpdate();
			if(r > 0){
				this.resultadoXml = "OK";
			}else{
				this.resultadoXml = "No se pudo consumir voucher";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
    public String consultarVoucherXML() {
        if(consultaVoucherCorrecto){
           return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
        }else{
          return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
        }
     }
	
    public String consumirVoucherXML(){
        if(this.resultadoXml.equals("OK")){
            return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
        }else{
        	return  Constantes.respuestaXmlObjeto("ERROR",resultadoXml) ;
         }
    }
}
