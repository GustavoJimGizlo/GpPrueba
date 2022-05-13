package com.serviciosweb.gpf.facturacion.farmacias.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.gpf.postg.pedidos.util.Constantes;

@Entity
public class DatosCompletoEmpleadoBean {
	@Column(name = "EMPRESA")
	private String empresa;
	@Column(name = "CEDULA")
	private String cedula;
	@Column(name = "NOMBRES")
	private String nombres;
	@Column(name = "FEC_ING")
	private String fechaIngreso;
	@Column(name = "FEC_DESV")
	private String fechaDesvinculacion;
	@Column(name = "SUCURSAL")
	private String sucursal;
	@Column(name = "CCT")
	private String codigoCentroCostos;
	@Column(name = "NOM_CCT")
	private String nombreCentroCostos;	
	@Column(name = "SUELDO")
	private String sueldo;
	@Column(name = "TIPO_CONTRATO")
	private String tipoContrato;
	@Column(name = "PUESTO")
	private String cargo;
	@Column(name = "MESES")
	private String meses;
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getFechaIngreso() {
		return fechaIngreso.substring(0, 10);
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getFechaDesvinculacion() {
		return fechaDesvinculacion!=null?fechaDesvinculacion.substring(0, 10):"";
	}
	public void setFechaDesvinculacion(String fechaDesvinculacion) {
		this.fechaDesvinculacion = fechaDesvinculacion;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getCodigoCentroCostos() {
		return codigoCentroCostos;
	}
	public void setCodigoCentroCostos(String codigoCentroCostos) {
		this.codigoCentroCostos = codigoCentroCostos;
	}
	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}
	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}
	public String getSueldo() {
		return sueldo;
	}
	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}
	public String getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getMeses() {
		return meses;
	}
	public void setMeses(String meses) {
		this.meses = meses;
	}
	
	

}
