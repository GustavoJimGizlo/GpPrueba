/**
 * 
 */
package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author gizloCorp
 * @version $Revision: 1.0 $
 */


@Entity
@Table(name = "TB_PERSONA",schema="ADM")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="ADM",name = "SEQ_PERSONA", sequenceName = "SEQ_PERSONA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSONA")
	private long id;

	private String apellidos;

	private String celular;

	@Column(name="CIUDAD_NACIMIENTO")
	private java.math.BigDecimal ciudadNacimiento;

	@Column(name="CIUDAD_RECIDENCIAS")
	private java.math.BigDecimal ciudadRecidencias;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name="ESTADO_CIVIL")
	private java.math.BigDecimal estadoCivil;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	private String foto;

	private java.math.BigDecimal genero;

	@Column(name="ID_ORGANIZACION")
	private java.math.BigDecimal idOrganizacion;

	private String identificacion;

	private String nombres;

	private String telefono;

	@Column(name="TIPO_ID")
	private java.math.BigDecimal tipoId;




	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public java.math.BigDecimal getCiudadNacimiento() {
		return this.ciudadNacimiento;
	}

	public void setCiudadNacimiento(java.math.BigDecimal ciudadNacimiento) {
		this.ciudadNacimiento = ciudadNacimiento;
	}

	public java.math.BigDecimal getCiudadRecidencias() {
		return this.ciudadRecidencias;
	}

	public void setCiudadRecidencias(java.math.BigDecimal ciudadRecidencias) {
		this.ciudadRecidencias = ciudadRecidencias;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public java.math.BigDecimal getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(java.math.BigDecimal estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public java.math.BigDecimal getGenero() {
		return this.genero;
	}

	public void setGenero(java.math.BigDecimal genero) {
		this.genero = genero;
	}

	public java.math.BigDecimal getIdOrganizacion() {
		return this.idOrganizacion;
	}

	public void setIdOrganizacion(java.math.BigDecimal idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public java.math.BigDecimal getTipoId() {
		return this.tipoId;
	}

	public void setTipoId(java.math.BigDecimal tipoId) {
		this.tipoId = tipoId;
	}





}