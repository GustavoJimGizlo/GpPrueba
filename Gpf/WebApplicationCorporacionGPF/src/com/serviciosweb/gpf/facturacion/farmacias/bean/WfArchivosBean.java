package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;
import java.sql.Date;

public class WfArchivosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long codigo;				
	private String minyType;				
	private String  fileName;				
	private String  fullFileName;				
	private Date fechaCreacion;				
	private String usuarioCreacion;				
	private String titulo;				
	private String descripcion;				
	private String instanceId;				
	private  byte[] contenido;				
	private String taskId;				
	private Long codigoActividadProceso;				
	private long userKey1;				
	private long tamanio;				
	private long codigoProceso;				
	private long codigoSolicitudColaborador;
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getMinyType() {
		return minyType;
	}
	public void setMinyType(String minyType) {
		this.minyType = minyType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFullFileName() {
		return fullFileName;
	}
	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getCodigoActividadProceso() {
		return codigoActividadProceso;
	}
	public void setCodigoActividadProceso(Long codigoActividadProceso) {
		this.codigoActividadProceso = codigoActividadProceso;
	}
	public long getUserKey1() {
		return userKey1;
	}
	public void setUserKey1(long userKey1) {
		this.userKey1 = userKey1;
	}
	public long getTamanio() {
		return tamanio;
	}
	public void setTamanio(long tamanio) {
		this.tamanio = tamanio;
	}
	public long getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(long codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	public long getCodigoSolicitudColaborador() {
		return codigoSolicitudColaborador;
	}
	public void setCodigoSolicitudColaborador(long codigoSolicitudColaborador) {
		this.codigoSolicitudColaborador = codigoSolicitudColaborador;
	}
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}				
	
	
	
	

}
