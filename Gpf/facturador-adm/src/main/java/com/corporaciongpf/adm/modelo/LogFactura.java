package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LOG_FACTURA_DIGITAL database table.
 * 
 */

/**
 * The persistent class for the LOG_FACTURA_DIGITAL database table.
 * 
 */
@Entity
@Table(name="TB_LOG_FACTURA_DIGITAL", schema="ginvoice")
@NamedQuery(name="LogFactura.findAll", query="SELECT l FROM LogFactura l")
public class LogFactura implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codigo;

	private String error;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INSERTA")
	private Date fechaInserta;
	@Id
	@SequenceGenerator(schema="ginvoice", name = "seq_tb_log_factura_digital", sequenceName = "seq_tb_log_factura_digital", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_log_factura_digital")
	@Column(name = "ID", nullable = false)

	private BigDecimal id;

	private BigDecimal intentos;

	@Lob
	private String json;

	private String mensaje;

	private String reintegrar;

	@Column(name="USUARIO_INSERTA")
	private String usuarioInserta;


	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getFechaInserta() {
		return this.fechaInserta;
	}

	public void setFechaInserta(Date fechaInserta) {
		this.fechaInserta = fechaInserta;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getIntentos() {
		return this.intentos;
	}

	public void setIntentos(BigDecimal intentos) {
		this.intentos = intentos;
	}

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getReintegrar() {
		return this.reintegrar;
	}

	public void setReintegrar(String reintegrar) {
		this.reintegrar = reintegrar;
	}

	public String getUsuarioInserta() {
		return this.usuarioInserta;
	}

	public void setUsuarioInserta(String usuarioInserta) {
		this.usuarioInserta = usuarioInserta;
	}

}

