package com.serviciosweb.gpf.facturacion.farmacias.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PropuestaNivelBean implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="Item")
	private String item;
	@Column(name="COD_PUNTO_VENTA")
	private String destLoc;
	@Column(name="PROVEEDOR")
	private String supplier;
	@Column(name="IND_PREP")
	private Integer indProp;
	@Column(name="FECHA")
    private Date dueDatePrd;
	@Column(name="CANTIDAD_PEDIDA")
	private Float qProp;
	@Column(name="FECHAGENERACION")
    private Date fechaGeneracion;
	
	
	/*NUEVOS CAMPOS CHRIS NAVARRO*/
	@Column(name="TSF_NO")
	private String tsfNo;
	@Column(name="FROM_LOC")
	private String fromLoc;
	@Column(name="PLANIFICABLE")
	private String planificable;
	@Column(name="cod_ped")
	private String codPed;
	
	@Column(name="CONSULTADO_CENTRAL")
	private String consultadoCentral;
	
    
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDestLoc() {
		return destLoc;
	}
	public void setDestLoc(String destLoc) {
		this.destLoc = destLoc;
	}
	public String getSupplier() {
		return supplier!=null?supplier.trim().length()>0?supplier:"0":"0";
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public Integer getIndProp() {
		return indProp;
	}
	public void setIndProp(Integer indProp) {
		this.indProp = indProp;
	}
	public Date getDueDatePrd() {
		return dueDatePrd;
	}
	public void setDueDatePrd(Date dueDatePrd) {
		this.dueDatePrd = dueDatePrd;
	}
	
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public Float getqProp() {
		return qProp;
	}
	public void setqProp(Float qProp) {
		this.qProp = qProp;
	}
	public String getTsfNo() {
		return tsfNo;
	}
	public void setTsfNo(String tsfNo) {
		this.tsfNo = tsfNo;
	}
	public String getFromLoc() {
		return fromLoc;
	}
	public void setFromLoc(String fromLoc) {
		this.fromLoc = fromLoc;
	}
	public String getPlanificable() {
		String response="";
		if(this.planificable!=null)
			if(this.planificable.trim().length()>0)				
				response=planificable;
		return response;
	}
	public void setPlanificable(String planificable) {
		this.planificable = planificable;
	}
	public String getCodPed() {
		return codPed;
	}
	public void setCodPed(String codPed) {
		this.codPed = codPed;
	}
	public String getConsultadoCentral() {
		return consultadoCentral;
	}
	public void setConsultadoCentral(String consultadoCentral) {
		this.consultadoCentral = consultadoCentral;
	}
    
    
}
