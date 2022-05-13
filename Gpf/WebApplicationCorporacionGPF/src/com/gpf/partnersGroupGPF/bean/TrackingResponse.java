package com.gpf.partnersGroupGPF.bean;

import java.io.Serializable;
import java.util.List;

public class TrackingResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sql_error;
	private String msg_error;
	private String guia;
	private String servicio;
	private String origen;
	private String f_ingreso;
	private String destino;
	private String chk;
	private String estado;
	private String sub_estado;
	private String fecha;
	private String hora;
	private String docref;
	private String codigo_cliente;
	private String cliente;
	private String direccion;
	private String cli_refere;
	private String ciudad;
	private String tipo_zona;
	private String cli_telefono;
	private String contenido;
	private String piezas;
	private String peso;
	private String peso_volumen;
	private String remite;
	private String dir_remite;
	private String ciudad_ubigeo;
	private String cod_service;
	private String cod_montos;
	private String cod_estado;
	private String cod_fecha;
	private List<Movimientos> movimientos;
	private List<Imagen> img;

	public List<Imagen> getImg() {
		return img;
	}

	public void setImg(List<Imagen> img) {
		this.img = img;
	}

	public String getSql_error() {
		return sql_error;
	}

	public void setSql_error(String sql_error) {
		this.sql_error = sql_error;
	}

	public String getMsg_error() {
		return msg_error;
	}

	public void setMsg_error(String msg_error) {
		this.msg_error = msg_error;
	}

	public String getGuia() {
		return guia;
	}

	public void setGuia(String guia) {
		this.guia = guia;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getF_ingreso() {
		return f_ingreso;
	}

	public void setF_ingreso(String f_ingreso) {
		this.f_ingreso = f_ingreso;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSub_estado() {
		return sub_estado;
	}

	public void setSub_estado(String sub_estado) {
		this.sub_estado = sub_estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDocref() {
		return docref;
	}

	public void setDocref(String docref) {
		this.docref = docref;
	}

	public String getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCli_refere() {
		return cli_refere;
	}

	public void setCli_refere(String cli_refere) {
		this.cli_refere = cli_refere;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTipo_zona() {
		return tipo_zona;
	}

	public void setTipo_zona(String tipo_zona) {
		this.tipo_zona = tipo_zona;
	}

	public String getCli_telefono() {
		return cli_telefono;
	}

	public void setCli_telefono(String cli_telefono) {
		this.cli_telefono = cli_telefono;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getPiezas() {
		return piezas;
	}

	public void setPiezas(String piezas) {
		this.piezas = piezas;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getPeso_volumen() {
		return peso_volumen;
	}

	public void setPeso_volumen(String peso_volumen) {
		this.peso_volumen = peso_volumen;
	}

	public String getRemite() {
		return remite;
	}

	public void setRemite(String remite) {
		this.remite = remite;
	}

	public String getDir_remite() {
		return dir_remite;
	}

	public void setDir_remite(String dir_remite) {
		this.dir_remite = dir_remite;
	}

	public String getCiudad_ubigeo() {
		return ciudad_ubigeo;
	}

	public void setCiudad_ubigeo(String ciudad_ubigeo) {
		this.ciudad_ubigeo = ciudad_ubigeo;
	}

	public String getCod_service() {
		return cod_service;
	}

	public void setCod_service(String cod_service) {
		this.cod_service = cod_service;
	}

	public String getCod_montos() {
		return cod_montos;
	}

	public void setCod_montos(String cod_montos) {
		this.cod_montos = cod_montos;
	}

	public String getCod_estado() {
		return cod_estado;
	}

	public void setCod_estado(String cod_estado) {
		this.cod_estado = cod_estado;
	}

	public String getCod_fecha() {
		return cod_fecha;
	}

	public void setCod_fecha(String cod_fecha) {
		this.cod_fecha = cod_fecha;
	}

	public List<Movimientos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimientos> movimientos) {
		this.movimientos = movimientos;
	}

	public static class Movimientos implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String chk;
		private String fecha;
		private String hora;
		private String estado;
		private String sub_estado;
		private String apunts;
		private String agencia;
		private String gps_px;
		private String gps_py;
		private List<Imagen> img;

		public List<Imagen> getImg() {
			return img;
		}

		public void setImg(List<Imagen> img) {
			this.img = img;
		}

		public String getChk() {
			return chk;
		}

		public void setChk(String chk) {
			this.chk = chk;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getHora() {
			return hora;
		}

		public void setHora(String hora) {
			this.hora = hora;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getSub_estado() {
			return sub_estado;
		}

		public void setSub_estado(String sub_estado) {
			this.sub_estado = sub_estado;
		}

		public String getApunts() {
			return apunts;
		}

		public void setApunts(String apunts) {
			this.apunts = apunts;
		}

		public String getAgencia() {
			return agencia;
		}

		public void setAgencia(String agencia) {
			this.agencia = agencia;
		}

		public String getGps_px() {
			return gps_px;
		}

		public void setGps_px(String gps_px) {
			this.gps_px = gps_px;
		}

		public String getGps_py() {
			return gps_py;
		}

		public void setGps_py(String gps_py) {
			this.gps_py = gps_py;
		}

	}
	
	public static class Imagen implements Serializable {



		private static final long serialVersionUID = 2069758171754926882L;
		private String img_path;
		private String img_fecha;
		private String img_hora;
		private String n_visita;
		private String img_px;
		private String img_py;
		
		 
			
			


		public String getImg_px() {
			return img_px;
		}

		public void setImg_px(String img_px) {
			this.img_px = img_px;
		}

		public String getImg_py() {
			return img_py;
		}

		public void setImg_py(String img_py) {
			this.img_py = img_py;
		}

		public String getImg_path() {
			return img_path;
		}

		public void setImg_path(String img_path) {
			this.img_path = img_path;
		}

		public String getImg_fecha() {
			return img_fecha;
		}

		public void setImg_fecha(String img_fecha) {
			this.img_fecha = img_fecha;
		}

		public String getImg_hora() {
			return img_hora;
		}

		public void setImg_hora(String img_hora) {
			this.img_hora = img_hora;
		}

		public String getN_visita() {
			return n_visita;
		}

		public void setN_visita(String n_visita) {
			this.n_visita = n_visita;
		}

	}

}
