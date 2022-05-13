package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "TB_USUARIO_ROL",schema="adm")
public class UsuarioRol implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="adm",name = "SEQ_USUARIO_ROL", sequenceName = "SEQ_USUARIO_ROL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO_ROL")
	private long id;

	@Column(name="ID_ROL")
	private java.math.BigDecimal idRol;

	//bi-directional many-to-one association to TbUsuario
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;





	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.math.BigDecimal getIdRol() {
		return this.idRol;
	}

	public void setIdRol(java.math.BigDecimal idRol) {
		this.idRol = idRol;
	}



}