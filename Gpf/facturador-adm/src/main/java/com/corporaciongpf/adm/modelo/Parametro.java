package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PARAMETROS_FACTURADOR database table.
 * 
 */
@Entity
@Table(name="fa_parametros_facturador",schema="FARMACIAS")
@NamedQuery(name="Parametro.findAll", query="SELECT p FROM Parametro p")
public class Parametro implements Serializable {
	private static final long serialVersionUID = 1L;

	private String clave;
	@Id
	@SequenceGenerator(name = "seq_fa_parametro_facturador", sequenceName = "seq_fa_parametro_facturador", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fa_parametro_facturador")
	@Column(name = "ID", nullable = false)
	private BigDecimal id;

	private String valor;


	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}