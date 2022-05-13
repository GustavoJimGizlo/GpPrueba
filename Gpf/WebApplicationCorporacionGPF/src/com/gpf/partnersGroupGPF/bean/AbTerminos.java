package com.gpf.partnersGroupGPF.bean;
// Creado por Santiago Barrionuevo A 24 03 2020
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
@Entity

public class AbTerminos implements Serializable {

	private static final long serialVersionUID = 1L;
// definimos metodos de la clase 
	@Column(name = "CODIGO")
	private long codigo; 
	
	@Column(name = "CODIGO_EMPRESA")
	private long codigoempresa; 

	@Column(name = "CODIGO_CLUB")
	private long codigoclub; 
	
	@Column(name = "ACEPTA_TERMINOS")
	private String aceptaterminos; 

	@Column(name = "DEBE_FIRMAR")
	private String debefirmar; 
	
	@Column(name = "FECHA_ACEPTA")
	private Date fechaacepta;
	
	@Column(name = "CODIGO_FARMACIA")
	private String codigofarmacia;
	
	@Column(name = "MIGRADO")
	private String migrado; 
	
	@Column(name = "NUMERO_INTENTOS")
	private String numerointentos;
	
	@Column(name = "CODIGO_ASESOR")
	private String codigoasesor;

	//private List<AbMediosContacto> mediosContacto;
	
	//@Transient
//	private List<AbDirecciones> direcciones;
	

	// codigo 
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	// codigo empresa
	
	public long getCodigoEmpresa() {
		return codigoempresa;
	}
	public void setCodigoEmpresa(long codigoempresa) {
		this.codigoempresa = codigoempresa;
	}
   // codigo club
	public long getCodigoClub() {
		return codigoclub;
	}
	public void setCodigoClub(long codigoclub) {
		this.codigoclub = codigoclub;
	}
	// acepta terminos
	public String getaceptaterminos() {
		return aceptaterminos;
	}
	public void setaceptaterminos(String aceptaterminos) {
		this.aceptaterminos = aceptaterminos;
	}
   // debe firmar
	public String getdebefirmar() {
		return debefirmar;
	}
	public void setdebefirmar(String debefirmar) {
		this.debefirmar = debefirmar;
	}
	// fecha acepta
	public Date getfechaacepta() {
		return fechaacepta;
	}
	public void setfechaacepta(Date fechaacepta) {
		this.fechaacepta = fechaacepta;
	}
	
	// codigo de farmacia
	public String getcodigofarmacia() {
		return codigofarmacia;
	}
	public void setcodigofarmacia(String codigofarmacia) {
		this.codigofarmacia = codigofarmacia;
	}
  //migrado
	public String getmigrado() {
		return migrado;
	}
	public void setmigrado(String migrado) {
		this.migrado = migrado;
	}
	// numero de intentos
	public String getnumerointentos() {
		return numerointentos;
	}
	public void setnumerointentos(String numerointentos) {
		this.numerointentos = numerointentos;
	}
	// codigo asesor
	public String getcodigoasesor() {
		return codigoasesor;
	}
	public void setcodigoasesor(String codigoasesor) {
		this.codigoasesor = codigoasesor;
	}
	// fin de metodos get y set
	
	
	
	
	
}
