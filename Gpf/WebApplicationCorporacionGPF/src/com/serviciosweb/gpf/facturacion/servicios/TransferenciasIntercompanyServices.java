package com.serviciosweb.gpf.facturacion.servicios;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.serviciosweb.gpf.facturacion.farmacias.bean.CredencialDS;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FaTransferenciaFarmaciasMas;
import com.serviciosweb.gpf.facturacion.farmacias.bean.FaTransfernciaFarmacias;

import com.serviciosweb.gpf.facturacion.farmacias.bean.FaDetalleTransferencias;

public class TransferenciasIntercompanyServices extends ObtenerNuevaConexion implements Serializable, Runnable {

	private static final long serialVersionUID = 298314373521717692L;
	private static Logger log = Logger.getLogger(TransferenciasIntercompanyServices.class.getName());
	
	//private static final String SANA_SID = "SANAUAT_CLOUD_M2C.UIO"; //test
	private static final String SANA_SID = "SANA.UIO"; //produccion
	
	//private static final String FYBECA_SID = "PRODUAT_CLOUD_M2C.UIO"; //test
	private static final String FYBECA_SID = "PROD.UIO"; //produccion
	
	//private static final String FRANQUICIA_SID = "FRANQUICUAT_CLOUD_M2C"; //test
	private static final String FRANQUICIA_SID = "FRANQUICIA.UIO"; //produccion
	
    //private static final String OKI_DOKI_SID = "OKIUAT_CLOUD_M2C"; //test
	private static final String OKI_DOKI_SID = "OKIMASTER.UIO"; //prod
	
	private static final String DB_USER = "weblink";
	private static final String DB_PASSWORD = "weblink_2013";
	private FaTransferenciaFarmaciasMas transrenciaMasOrigen;
	private  List<FaDetalleTransferencias> listaDetalleTransferenciaFarmacias;
	private FaTransfernciaFarmacias transferenciaFarmacia;
	private long documento;
	private int farmacia;
	private Connection conexionCentralOrigen;
	private Connection conexionCentralDestino;

	public TransferenciasIntercompanyServices(int empresaOrigen, int empresaDestino, long documento, int farmacia) {
		super(ObtenerNuevaConexion.class);
		conexionCentralOrigen = conectarAEmpresa(empresaOrigen);
		conexionCentralDestino = conectarAEmpresa(empresaDestino);
		this.documento = documento;
		this.farmacia = farmacia;
	}
	
	private void transferirACentral() {
		
		
		log.info("Iniciando Transferencia para el documento: " + documento + ", desde la el PDV(Cod.): " + farmacia);
		
		this.transrenciaMasOrigen = this.obtenerTransferenciasMas(farmacia, documento);
		if (this.transrenciaMasOrigen != null) {
			log.info("Tranferencia Origen: " + this.transrenciaMasOrigen.toString());
			
			if (this.actualizarEstadoTransferenciaMas(this.transrenciaMasOrigen, "X")) {
				log.info("Se actualizo el estado de la transferencia a X");
				
				this.transferenciaFarmacia = this.obtenerCabeceraTransferencia(this.transrenciaMasOrigen);
				if (this.transferenciaFarmacia != null) {
					log.info("Tranferencia: " + this.transferenciaFarmacia.toString());
					
					this.listaDetalleTransferenciaFarmacias = this.obtenerListaDetalleTransferencia(this.transferenciaFarmacia);
					
					log.info("DETALLE TRANSFERENCIA: ");
					log.info(this.listaDetalleTransferenciaFarmacias);
					if(this.listaDetalleTransferenciaFarmacias != null){

						log.info("Copiando a central destino....");						
						if(this.insertarCabeceraTransferenciaEnCentralDestino(this.transferenciaFarmacia)){
							log.info("Cabecera de la transferencia insertada en el central destino.");
							
							if(this.insertarDetalleTransferenciaEnCentralDestino(this.listaDetalleTransferenciaFarmacias)){
								log.info("Detalle de la transferencia insertada en el central destino");
								
								if(this.actualizarEstadoTransferenciaMas(this.transrenciaMasOrigen, "C")){
									log.info("Estado de la transferencia a cambio a C");
									
									if(this.insertarTransferenciaMasEnCentralDestino(this.transrenciaMasOrigen)){
										log.info("TransferenciaMas insertada en central");
										log.info("Finaliza transferencia entre centrales");
										
										try {
											conexionCentralOrigen.close();
											conexionCentralDestino.close();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									} else {
										log.info("Error al insertar cabera transfernciaMas");
									}
								} else {
									log.info("Error al cambiar el estado a C");
								}
							} else {
								log.info("Error al insertar detalle de transferencia");
							}
						} else {
							log.info("Error al insertar cabecera de transferencia");
						}
					} else {
						log.info("[FA_DETALLE_TRANSFERENCIA] No Existe detalle");
					}
				} else {
					log.info("[FA_TRANSFERENCIA] No Existe transferencia");
				}
			} else {
				log.info("Error al cambiar el estado a X");
			}
		} else {
			log.info("[FA_TRANSFERENCIA_MAS] No existe transferencia para el documento: " + documento + " con estado P");
		}

	}

	private Connection conectarAEmpresa(int empresa) {
		CredencialDS credenciales = new CredencialDS();
		if (empresa == 1) {
			credenciales.setDatabaseId(FYBECA_SID);
		} else if (empresa == 8) {
			credenciales.setDatabaseId(SANA_SID);
		} else if (empresa == 11) {
			credenciales.setDatabaseId(OKI_DOKI_SID);
		} else if (empresa == 16) {
			credenciales.setDatabaseId(FRANQUICIA_SID);
		} 
		credenciales.setUsuario(DB_USER);
		credenciales.setClave(DB_PASSWORD);
		ConexionVarianteSid conexionVarianteSid = obtenerNuevaConexionVarianteSidSidDataBase(credenciales.getDatabaseId());
		return conexionVarianteSid.getConn();
	}

	

	private boolean actualizarEstadoTransferenciaMas(FaTransferenciaFarmaciasMas faTransferenciaFarmaciasMas, String nuevoEstado) {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE FARMACIAS.FA_TRANSFERENCIA_FARMACIAS_MAS     ");
		sql.append("SET CP_CHAR1 = ?				 				   	");
		sql.append("WHERE DOCUMENTO = ? 		    					");
		sql.append("AND  FARMACIA = ?									");
		try{
			
			ps = this.conexionCentralOrigen.prepareStatement(sql.toString());
			ps.setString(1, nuevoEstado);
			ps.setLong(2, faTransferenciaFarmaciasMas.getDocumento());
			ps.setInt(3, faTransferenciaFarmaciasMas.getFarmacia());
			ps.executeQuery();
			this.transrenciaMasOrigen.setCp_char1(nuevoEstado);
			ps.close();
			return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	private FaTransferenciaFarmaciasMas obtenerTransferenciasMas(int farmacia, long documento) {
		FaTransferenciaFarmaciasMas transferenciaMasAux = new FaTransferenciaFarmaciasMas();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT FARMACIA,									");
		sql.append("DOCUMENTO,											");
		sql.append("NVL(TIPO_TRANSFERENCIA, '') TIPO_TRANSFERENCIA,		");
		sql.append("NVL(CP_VAR1, '') CP_VAR1,							");
		sql.append("NVL(CP_VAR2, '') CP_VAR2,							");
		sql.append("NVL(CP_VAR3, '') CP_VAR3,							");
		sql.append("NVL(CP_CHAR1, '') CP_CHAR1,							");
		sql.append("NVL(CP_CHAR2, '') CP_CHAR2,							");
		sql.append("NVL(CP_CHAR3, '') CP_CHAR3,							");
		sql.append("NVL(CP_NUM1, 0) CP_NUM1,							");
		sql.append("NVL(CP_NUM2, 0) CP_NUM2,							");
		sql.append("NVL(CP_NUM3, 0) CP_NUM3,							");
		sql.append("NVL(CP_DEC1, 0) CP_DEC1,							");
		sql.append("NVL(CP_DEC2, 0) CP_DEC2,							");
		sql.append("NVL(CP_DEC3, 0) CP_DEC3,							");
		sql.append("NVL(CP_DATE1, '') CP_DATE1,							");
		sql.append("NVL(CP_DATE2, '') CP_DATE2,							");
		sql.append("NVL(CP_DATE3, '') CP_DATE3							");
		sql.append("FROM FARMACIAS.FA_TRANSFERENCIA_FARMACIAS_MAS		");
		sql.append("WHERE CP_CHAR1 = 'P'								");
		sql.append("AND FARMACIA = ?									");
		sql.append("AND DOCUMENTO = ?									");

		try {
			ps = this.conexionCentralOrigen.prepareStatement(sql.toString());
			ps.setInt(1, farmacia);
			ps.setLong(2, documento);
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				transferenciaMasAux.setFarmacia(resultSet.getInt("FARMACIA"));
				transferenciaMasAux.setDocumento(resultSet.getInt("DOCUMENTO"));
				transferenciaMasAux.setTipo_transferencia(resultSet.getString("TIPO_TRANSFERENCIA"));
				transferenciaMasAux.setCp_var1(resultSet.getString("CP_VAR1"));
				transferenciaMasAux.setCp_var2(resultSet.getString("CP_VAR2"));
				transferenciaMasAux.setCp_var3(resultSet.getString("CP_VAR3"));
				transferenciaMasAux.setCp_char1(resultSet.getString("CP_CHAR1"));
				transferenciaMasAux.setCp_char2(resultSet.getString("CP_CHAR2"));
				transferenciaMasAux.setCp_char3(resultSet.getString("CP_CHAR3"));
				transferenciaMasAux.setCp_num1(resultSet.getInt("CP_NUM1"));
				transferenciaMasAux.setCp_num2(resultSet.getInt("CP_NUM2"));
				transferenciaMasAux.setCp_num3(resultSet.getInt("CP_NUM3"));
				transferenciaMasAux.setCp_dec1(resultSet.getInt("CP_DEC1"));
				transferenciaMasAux.setCp_dec2(resultSet.getInt("CP_DEC2"));
				transferenciaMasAux.setCp_dec3(resultSet.getInt("CP_DEC3"));
				transferenciaMasAux.setCp_date1(resultSet.getDate("CP_DATE1"));
				transferenciaMasAux.setCp_date1(resultSet.getDate("CP_DATE2"));
				transferenciaMasAux.setCp_date1(resultSet.getDate("CP_DATE3"));
				return transferenciaMasAux;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private FaTransfernciaFarmacias obtenerCabeceraTransferencia(FaTransferenciaFarmaciasMas faTransferenciaFarmaciasMas) {
		FaTransfernciaFarmacias transferenciaAux = new FaTransfernciaFarmacias();

		PreparedStatement ps = null;
		ResultSet resultSet = null;

		StringBuilder sql = new StringBuilder();
	    sql.append("SELECT NVL(F.EMPRESA,0) empresa_destino,						");          
	    sql.append("NVL(FTF.documento,0) documento,									");                   
	    sql.append("NVL(FTF.farmacia,0) farmacia,									");                   
	    sql.append("NVL(FTF.tipo_mercaderia,0) tipo_mercaderia,						");               
	    sql.append("NVL(FTF.farmacia_envio,0) farmacia_envio,						"); 	                
	    sql.append("NVL(FTF.guia_remision,'') guia_remision,						");                 
	    sql.append("NVL(FTF.venta,0) venta,											");                     
	    sql.append("NVL(FTF.iva,0) iva,												");                     
	    sql.append("NVL(FTF.fecha,'') fecha,										");                     
	    sql.append("NVL(FTF.costo,0) costo,											");                     
	    sql.append("NVL(FTF.tipo_negocio,'') tipo_negocio							");                 
	    sql.append("FROM FARMACIAS.FA_TRANSFERENCIAS_FARMACIAS FTF,					");    
	    sql.append("ADMINISTRACION.AD_FARMACIAS F									");           
	    sql.append("WHERE FTF.FARMACIA_ENVIO = F.CODIGO								");          
	    sql.append("AND FTF.DOCUMENTO = ?											");                
	    sql.append("AND FTF.FARMACIA = ?											");               
	    sql.append("AND FTF.FARMACIA_ENVIO = ?										");   

		try {
			ps = this.conexionCentralOrigen.prepareStatement(sql.toString());
			ps.setLong(1, faTransferenciaFarmaciasMas.getDocumento());
			ps.setInt(2, faTransferenciaFarmaciasMas.getFarmacia());
			ps.setInt(3, faTransferenciaFarmaciasMas.getCp_num1());
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				transferenciaAux.setDocumento(resultSet.getInt("documento"));
				transferenciaAux.setFarmacia(resultSet.getInt("farmacia"));
				transferenciaAux.setTipo_mercaderia(resultSet.getInt("tipo_mercaderia"));
				transferenciaAux.setFarmacia_envio(resultSet.getInt("farmacia_envio"));
				transferenciaAux.setGuia_remision(resultSet.getString("guia_remision"));
				transferenciaAux.setVenta(resultSet.getDouble("venta"));
				transferenciaAux.setIva(resultSet.getDouble("tipo_mercaderia"));
				transferenciaAux.setTipo_negocio(resultSet.getString("tipo_negocio"));
				transferenciaAux.setEmpresa_destino(resultSet.getInt("empresa_destino"));
				transferenciaAux.setCosto(resultSet.getDouble("costo"));
				return transferenciaAux;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ps.close();
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	private List<FaDetalleTransferencias> obtenerListaDetalleTransferencia(FaTransfernciaFarmacias faTransfernciaFarmacias) {
		List<FaDetalleTransferencias> listaDetalleTransferenciaAux = new ArrayList<FaDetalleTransferencias>();
		FaDetalleTransferencias detalleTransferenciaAux = new FaDetalleTransferencias();

		PreparedStatement ps = null;
		ResultSet resultSet = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT NVL(documento, 0) documento,						");                 
		sql.append("NVL(farmacia, 0) farmacia,								");                     
		sql.append("NVL(secuencial, '') secuencial,							");                   
		sql.append("NVL(item, 0) item,										");                       
		sql.append("NVL(cantidad, 0) cantidad,								");                     
		sql.append("NVL(comisariato_con_iva, 0) comisariato_con_iva,		");               
		sql.append("NVL(comisariato_sin_iva, 0) comisariato_sin_iva,		");               
		sql.append("NVL(unidades, 0) unidades,								");                     
		sql.append("NVL(gravamen, 0) gravamen,								");                     
		sql.append("NVL(costo_compra, 0) costo_compra,						");                   
		sql.append("NVL(costo_promedio, 0) costo_promedio,					");                 
		sql.append("NVL(pvp_sin_iva, 0) pvp_sin_iva,						");                   
		sql.append("NVL(pvp_con_iva, 0) pvp_con_iva,						");                   
		sql.append("NVL(venta, 0) venta										");                       
		sql.append("FROM farmacias.fa_detalle_transferencias				");     
		sql.append("WHERE documento = ?										");               
		sql.append("AND farmacia = ?										");    	
		try {
			ps = this.conexionCentralOrigen.prepareStatement(sql.toString());
			ps.setInt(1,   faTransfernciaFarmacias.getDocumento());
			ps.setInt(2,  faTransfernciaFarmacias.getFarmacia());
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				detalleTransferenciaAux = new FaDetalleTransferencias();
				detalleTransferenciaAux.setDocumento(resultSet.getInt("documento"));
				detalleTransferenciaAux.setFarmacia(resultSet.getInt("farmacia"));
				detalleTransferenciaAux.setSecuencial(resultSet.getString("secuencial"));
				detalleTransferenciaAux.setItem(resultSet.getInt("item"));
				detalleTransferenciaAux.setCantidad(resultSet.getDouble("cantidad"));
				detalleTransferenciaAux.setComisariato_con_iva(resultSet.getDouble("comisariato_con_iva"));
				detalleTransferenciaAux.setComisariato_sin_iva(resultSet.getDouble("comisariato_sin_iva"));
				detalleTransferenciaAux.setUnidades(resultSet.getDouble("unidades"));
				detalleTransferenciaAux.setGravamen(resultSet.getDouble("gravamen"));
				detalleTransferenciaAux.setCosto_compra(resultSet.getDouble("costo_compra"));
				detalleTransferenciaAux.setCosto_promedio(resultSet.getDouble("costo_promedio"));
				detalleTransferenciaAux.setPvp_sin_iva(resultSet.getDouble("pvp_sin_iva"));
				detalleTransferenciaAux.setPvp_con_iva(resultSet.getDouble("pvp_con_iva"));
				detalleTransferenciaAux.setVenta(resultSet.getDouble("venta"));
				
				listaDetalleTransferenciaAux.add(detalleTransferenciaAux);
			}
			return listaDetalleTransferenciaAux;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ps.close();
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	private boolean insertarCabeceraTransferenciaEnCentralDestino(FaTransfernciaFarmacias faTransfernciaFarmacias) {

		PreparedStatement ps = null;
		ResultSet resultSet = null;

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO farmacias.fa_transferencias_farmacias (		");
		sql.append("documento,												");
		sql.append("farmacia,												");
		sql.append("tipo_mercaderia,										");
		sql.append("farmacia_envio,											");
		sql.append("guia_remision,											");
		sql.append("venta,													");
		sql.append("iva,													");
		sql.append("fecha,													");
		sql.append("costo,													");
		sql.append("tipo_negocio)											");
		sql.append("VALUES (												");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("?,														");
		sql.append("? )														");

		try {
			ps = this.conexionCentralDestino.prepareStatement(sql.toString());
			ps.setInt(1,  faTransfernciaFarmacias.getDocumento());
			ps.setInt(2,  faTransfernciaFarmacias.getFarmacia());
			ps.setInt(3,  faTransfernciaFarmacias.getTipo_mercaderia());
			ps.setInt(4,  faTransfernciaFarmacias.getFarmacia_envio());
			ps.setString(5, faTransfernciaFarmacias.getGuia_remision());
			ps.setDouble(6,  faTransfernciaFarmacias.getVenta());
			ps.setDouble(7,  faTransfernciaFarmacias.getIva());
			ps.setDate(8, (Date) faTransfernciaFarmacias.getFecha());
			ps.setDouble(9,  faTransfernciaFarmacias.getCosto());
			ps.setString(10, faTransfernciaFarmacias.getTipo_negocio()); 
			resultSet = ps.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean insertarDetalleTransferenciaEnCentralDestino(
			List<FaDetalleTransferencias> listaFaDetalleTransferencias) {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO farmacias.fa_detalle_transferencias (	");
		sql.append("documento,											");
		sql.append("farmacia,											");
		sql.append("secuencial,											");
		sql.append("item,												");
		sql.append("cantidad,											");
		sql.append("comisariato_con_iva,								");
		sql.append("comisariato_sin_iva,								");
		sql.append("unidades,											");
		sql.append("gravamen,											");
		sql.append("costo_compra,										");
		sql.append("costo_promedio,										");
		sql.append("pvp_sin_iva,										");
		sql.append("pvp_con_iva,										");
		sql.append("venta )												");
		sql.append("VALUES (											");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("?,													");
		sql.append("? )													");

		try {
			ps = this.conexionCentralDestino.prepareStatement(sql.toString());
			for (FaDetalleTransferencias faDetalleTransferencias : listaFaDetalleTransferencias) {
				ps.setLong(1,  faDetalleTransferencias.getDocumento());
				ps.setInt(2,  faDetalleTransferencias.getFarmacia());
				ps.setString(3, faDetalleTransferencias.getSecuencial());
				ps.setDouble(4, faDetalleTransferencias.getItem());
				ps.setDouble(5, faDetalleTransferencias.getCantidad());
				ps.setDouble(6, faDetalleTransferencias.getComisariato_con_iva());
				ps.setDouble(7, faDetalleTransferencias.getComisariato_sin_iva());
				ps.setDouble(8, faDetalleTransferencias.getUnidades());
				ps.setDouble(9, faDetalleTransferencias.getGravamen());
				ps.setDouble(10, faDetalleTransferencias.getCosto_compra());
				ps.setDouble(11, faDetalleTransferencias.getCosto_promedio());
				ps.setDouble(12, faDetalleTransferencias.getPvp_sin_iva());
				ps.setDouble(13, faDetalleTransferencias.getPvp_con_iva());
				ps.setDouble(14,  faDetalleTransferencias.getVenta());
				ps.addBatch();
			}
			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean insertarTransferenciaMasEnCentralDestino(FaTransferenciaFarmaciasMas faTransferenciaFarmaciasMas) {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO farmacias.fa_transferencia_farmacias_mas (	");
		sql.append("farmacia,												");
		sql.append("documento,												");
		sql.append("tipo_transferencia,										");
		sql.append("cp_var1,												");
		sql.append("cp_var2,												");
		sql.append("cp_var3,												");
		sql.append("cp_char1,												");
		sql.append("cp_char2,												");
		sql.append("cp_char3,												");
		sql.append("cp_num1,												");
		sql.append("cp_num2,												");
		sql.append("cp_num3,												");
		sql.append("cp_dec1,												");
		sql.append("cp_dec2,												");
		sql.append("cp_dec3,												");
		sql.append("cp_date1,												");
		sql.append("cp_date2,												");
		sql.append("cp_date3)												");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)			");
		try {
			ps = this.conexionCentralDestino.prepareStatement(sql.toString());
			ps.setInt(1, faTransferenciaFarmaciasMas.getFarmacia());
			ps.setLong(2, faTransferenciaFarmaciasMas.getDocumento());
			ps.setString(3, faTransferenciaFarmaciasMas.getTipo_transferencia());
			ps.setString(4, faTransferenciaFarmaciasMas.getCp_var1());
			ps.setString(5, faTransferenciaFarmaciasMas.getCp_var2());
			ps.setString(6, faTransferenciaFarmaciasMas.getCp_var3());
			ps.setString(7, faTransferenciaFarmaciasMas.getCp_char1());
			ps.setString(8, faTransferenciaFarmaciasMas.getCp_char2());
			ps.setString(9, faTransferenciaFarmaciasMas.getCp_char3());
			ps.setInt(10, faTransferenciaFarmaciasMas.getCp_num1());
			ps.setInt(11, faTransferenciaFarmaciasMas.getCp_num2());
			ps.setInt(12, faTransferenciaFarmaciasMas.getCp_num3());
			ps.setInt(13, faTransferenciaFarmaciasMas.getCp_dec1());
			ps.setInt(14, faTransferenciaFarmaciasMas.getCp_dec2());
			ps.setInt(15, faTransferenciaFarmaciasMas.getCp_dec3());
			ps.setDate(16, (Date) faTransferenciaFarmaciasMas.getCp_date1());
			ps.setDate(17, (Date) faTransferenciaFarmaciasMas.getCp_date2());
			ps.setDate(18, (Date) faTransferenciaFarmaciasMas.getCp_date3());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		try{
			transferirACentral();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}

}
