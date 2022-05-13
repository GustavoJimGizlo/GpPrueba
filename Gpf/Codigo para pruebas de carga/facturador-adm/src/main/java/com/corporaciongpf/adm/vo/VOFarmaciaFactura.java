package com.corporaciongpf.adm.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VOFarmaciaFactura {


	
	private Long   farmacia;
	private Long   documentoVenta;
	
	private Date   fecha;
	private String   numeroSRI;
	private BigDecimal   costoTotalFactura;
	private BigDecimal   pvpTotalFactura;
	private BigDecimal   ventaTotalFactura;

	private String   canalVenta;
	private BigDecimal   valorIva;
	private String   cancelada;
	private String   tipoDocumento;
	private Long   caja;
	private String   tipoMovimiento;
	private String   clasificacionMovimiento;
	private Long   cliente;
	private Long   documentoVentaPadre;
	private Long   farmaciaPadre;
	private String   usuario;
	private Long   empleadoRealiza;
	private Long   empleadoCobra;
	private Long   persona;
	private String   primerApellido;
	private String   segundoApellido;
	private String   nombres;
	private String   identificacion;
	private String   direccion;
	private Date   fechaSistema;
	private String   donacion;
	private String   tratamientoContinuo;
	private Long   empleadoEntrega;
	private String   incluyeIva;
	private Long   tomaPedidoDomicilio;
	private Integer   direccionDomicilio;
	private String   generaNotaCredito;
	public Long getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Long farmacia) {
		this.farmacia = farmacia;
	}

	public Long getDocumentoVenta() {
		return documentoVenta;
	}
	public void setDocumentoVenta(Long documentoVenta) {
		this.documentoVenta = documentoVenta;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNumeroSRI() {
		return numeroSRI;
	}
	public void setNumeroSRI(String numeroSRI) {
		this.numeroSRI = numeroSRI;
	}
	public BigDecimal getCostoTotalFactura() {
		return costoTotalFactura;
	}
	public void setCostoTotalFactura(BigDecimal costoTotalFactura) {
		this.costoTotalFactura = costoTotalFactura;
	}
	public BigDecimal getPvpTotalFactura() {
		return pvpTotalFactura;
	}
	public void setPvpTotalFactura(BigDecimal pvpTotalFactura) {
		this.pvpTotalFactura = pvpTotalFactura;
	}
	public BigDecimal getVentaTotalFactura() {
		return ventaTotalFactura;
	}
	public void setVentaTotalFactura(BigDecimal ventaTotalFactura) {
		this.ventaTotalFactura = ventaTotalFactura;
	}
	public String getCanalVenta() {
		return canalVenta;
	}
	public void setCanalVenta(String canalVenta) {
		this.canalVenta = canalVenta;
	}
	public BigDecimal getValorIva() {
		return valorIva;
	}
	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}
	public String getCancelada() {
		return cancelada;
	}
	public void setCancelada(String cancelada) {
		this.cancelada = cancelada;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Long getCaja() {
		return caja;
	}
	public void setCaja(Long caja) {
		this.caja = caja;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public String getClasificacionMovimiento() {
		return clasificacionMovimiento;
	}
	public void setClasificacionMovimiento(String clasificacionMovimiento) {
		this.clasificacionMovimiento = clasificacionMovimiento;
	}
	public Long getCliente() {
		return cliente;
	}
	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Long getDocumentoVentaPadre() {
		return documentoVentaPadre;
	}
	public void setDocumentoVentaPadre(Long documentoVentaPadre) {
		this.documentoVentaPadre = documentoVentaPadre;
	}
	public Long getFarmaciaPadre() {
		return farmaciaPadre;
	}
	public void setFarmaciaPadre(Long farmaciaPadre) {
		this.farmaciaPadre = farmaciaPadre;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Long getEmpleadoRealiza() {
		return empleadoRealiza;
	}
	public void setEmpleadoRealiza(Long empleadoRealiza) {
		this.empleadoRealiza = empleadoRealiza;
	}

	public Long getEmpleadoCobra() {
		return empleadoCobra;
	}
	public void setEmpleadoCobra(Long empleadoCobra) {
		this.empleadoCobra = empleadoCobra;
	}
	public Long getPersona() {
		return persona;
	}
	public void setPersona(Long persona) {
		this.persona = persona;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
	}
	public String getDonacion() {
		return donacion;
	}
	public void setDonacion(String donacion) {
		this.donacion = donacion;
	}
	public String getTratamientoContinuo() {
		return tratamientoContinuo;
	}
	public void setTratamientoContinuo(String tratamientoContinuo) {
		this.tratamientoContinuo = tratamientoContinuo;
	}

	public Long getEmpleadoEntrega() {
		return empleadoEntrega;
	}
	public void setEmpleadoEntrega(Long empleadoEntrega) {
		this.empleadoEntrega = empleadoEntrega;
	}
	public String getIncluyeIva() {
		return incluyeIva;
	}
	public void setIncluyeIva(String incluyeIva) {
		this.incluyeIva = incluyeIva;
	}

	public Long getTomaPedidoDomicilio() {
		return tomaPedidoDomicilio;
	}
	public void setTomaPedidoDomicilio(Long tomaPedidoDomicilio) {
		this.tomaPedidoDomicilio = tomaPedidoDomicilio;
	}
	public Integer getDireccionDomicilio() {
		return direccionDomicilio;
	}
	public void setDireccionDomicilio(Integer direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}
	public String getGeneraNotaCredito() {
		return generaNotaCredito;
	}
	public void setGeneraNotaCredito(String generaNotaCredito) {
		this.generaNotaCredito = generaNotaCredito;
	}


	
	
	
}
