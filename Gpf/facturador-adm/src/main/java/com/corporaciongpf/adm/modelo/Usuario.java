package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TB_USUARIO database table.
 * 
 */
@Entity
@Table(name="TB_USUARIO",schema="adm")
@NamedQuery(name="Usuario.findAll", query="SELECT t FROM Usuario t")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="adm",name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")	
	private long id;

	@Column(name="ES_CLV_AUTOGENERADA")
	private String esClvAutogenerada;

	private String estado;

	private String password;

	private String tipo;

	private String username;

	//bi-directional many-to-one association to TbPersona
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona tbPersona;



	public Persona getTbPersona() {
		return tbPersona;
	}

	public void setTbPersona(Persona tbPersona) {
		this.tbPersona = tbPersona;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEsClvAutogenerada() {
		return this.esClvAutogenerada;
	}

	public void setEsClvAutogenerada(String esClvAutogenerada) {
		this.esClvAutogenerada = esClvAutogenerada;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}