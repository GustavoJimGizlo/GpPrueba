package com.serviciosweb.intCoperah.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;

public class ConnectionOki extends ObtenerNuevaConexion{

	private static Connection con = null;
	
	//Test
    //String db = "OKIUAT_CLOUD_M2C.UIO";

    //Prod
    String db = "OKIMASTER.UIO";

	public ConnectionOki(){
		super(ConnectionOki.class);
	}
	
	public Connection ConectToOki(){
        con = null;
        try {
                        
            //String[] conexionProd = Constantes.getContenidoFileUTF8("ConexionDB.txt", "integraciones")
           		 //.split(";");
            System.out.println("OKI CONN: "+db);

            con=obtenerNuevaConexionVarianteSidSidDataBase(db).getConn();
            System.out.println("Conecto OKI");
        } catch (Exception e) {
        	System.out.println(e);
        }
        return con;
    }
	
	public void saveResponse(String tipo, String respuesta, List<String> detalle, int codCarga){
		ResultSet result = null;

		try {
			
			PreparedStatement ps;
			
			
			
			if(detalle.size() > 1){
				for (String det : detalle) {
					Statement stament = ConectToOki().createStatement();
					String sql = "select max(codigo) integraciones.from hcms_int_coperah";
					
					result = stament.executeQuery(sql);
					int codigo = 0;
					while(result.next()){
						codigo = result.getInt(1);
					}
					if(codigo > 0){
						sql = "INSERT into integraciones.hcms_int_coperah values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'))";
						
						ps = ConectToOki().prepareCall(sql);
						
						ps.setInt(1, codigo + 1);
						
						ps.setString(2, respuesta);
						ps.setString(3, det);
						ps.setInt(4, codCarga);
						ps.setString(5, tipo);
						Date fecha = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						ps.setString(6, sdf.format(fecha));
						
						ps.executeUpdate();

					}else{
						sql = "INSERT into integraciones.hcms_int_coperah values(1,?,?,?,?,to_date(?,'dd/mm/yyyy'))";

						ps = ConectToOki().prepareCall(sql);
						
						ps.setString(1, respuesta);
						ps.setString(2, det);
						ps.setInt(3, codCarga);
						ps.setString(4, tipo);
						Date fecha = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						ps.setString(5, sdf.format(fecha));
						
						ps.executeUpdate();
					}
					
					
					
				}
			}else{
				Statement stament = ConectToOki().createStatement();
				String sql = "select max(codigo) from integraciones.hcms_int_coperah";
				
				result = stament.executeQuery(sql);
				int codigo = 0;
				while(result.next()){
					codigo = result.getInt(1);
				}
				
				if(codigo > 0){
					sql = "INSERT into integraciones.hcms_int_coperah values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'))";
					
					ps = ConectToOki().prepareCall(sql);
					
					ps.setInt(1, codigo + 1);
					
					ps.setString(2, respuesta);
					ps.setString(3, "");
					ps.setInt(4, codCarga);
					ps.setString(5, tipo);
					Date fecha = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					ps.setString(6, sdf.format(fecha));
					
					ps.executeUpdate();
				}else{
					sql = "INSERT into integraciones.hcms_int_coperah values(1,?,?,?,?,to_date(?,'dd/mm/yyyy'))";

					ps = ConectToOki().prepareCall(sql);
					
					ps.setString(1, respuesta);
					if(detalle.size() == 1){
						ps.setString(2, detalle.get(0));
					}else{
						ps.setString(2, "");
					}
					
					ps.setInt(3, codCarga);
					ps.setString(4, tipo);
					Date fecha = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					ps.setString(5, sdf.format(fecha));
					
					ps.executeUpdate();
				}
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("INSERT LOG: "+e.getMessage());
		}
		
		close();

	}

	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
