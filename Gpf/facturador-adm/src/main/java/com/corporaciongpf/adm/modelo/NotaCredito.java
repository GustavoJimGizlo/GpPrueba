package com.corporaciongpf.adm.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author gizloCorp
 * @version $Revision: 1.0 $
 */

@Entity
@Table(name="TB_NOTA_CREDITO",schema="ginvoice")
@NamedQuery(name="NotaCredito.findAll", query="SELECT t FROM NotaCredito t")
public class NotaCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="ginvoice",name = "seq_nota_credito", sequenceName = "seq_nota_credito", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_credito")
	@Column(name = "id", nullable = false)
	private Long id;	

	private String agencia;

	private String archivo;

	@Column(name="ARCHIVO_GEN_REIM")
	private String archivoGenReim;

	@Column(name="ARCHIVO_LEGIBLE")
	private String archivoLegible;

	@Column(name="CLAVE_ACCESO")
	private String claveAcceso;

	@Column(name="CLAVE_INTERNA")
	private String claveInterna;

	@Column(name="COD_DOC")
	private String codDoc;

	@Column(name="COD_DOC_MODIFICADO")
	private String codDocModificado;

	@Column(name="COD_PUNTO_EMISION")
	private String codPuntoEmision;

	@Column(name="COD_SECUENCIAL")
	private String codSecuencial;

	@Column(name="CONTRIBUYENTE_ESPECIAL")
	private String contribuyenteEspecial;

	@Column(name="CORREO_NOTIFICACION")
	private String correoNotificacion;

	@Column(name="DIR_ESTABLECIMIENTO")
	private String dirEstablecimiento;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECH_EMI_DOC_SUSTENTO")
	private Date fechEmiDocSustento;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_AUTORIZACION")
	private Date fechaAutorizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_EMISION")
	private Date fechaEmision;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ENT_REIM")
	private Date fechaEntReim;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LEC_TRAD")
	private Date fechaLecTrad;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RECEP_MAIL")
	private Date fechaRecepMail;

	@Column(name="ID_LOTE")
	private BigDecimal idLote;

	@Column(name="IDENTIFICACION_COMPRADOR")
	private String identificacionComprador;

	@Column(name="IDENTIFICADOR_USUARIO")
	private String identificadorUsuario;

	@Column(name="INFO_ADICIONAL")
	private String infoAdicional;

	@Column(name="IS_OFERTON")
	private String isOferton;

	@Column(name="MENSAJE_REIM")
	private String mensajeReim;

	@Column(name="MODO_ENVIO")
	private String modoEnvio;

	private String moneda;

	private String motivo;

	@Column(name="NUM_DOC_MODIFICADO")
	private String numDocModificado;

	@Column(name="NUMERO_AUTORIZACION")
	private String numeroAutorizacion;

	@Column(name="OBLIGADO_CONTABILIDAD")
	private String obligadoContabilidad;

	@Column(name="ORDEN_COMPRA")
	private String ordenCompra;

	private String proceso;

	@Column(name="PTO_EMISION")
	private String ptoEmision;

	@Column(name="RAZON_SOCIAL_COMPRADOR")
	private String razonSocialComprador;

	private String rise;

	private String ruc;

	private String tarea;

	@Column(name="TIPO_AMBIENTE")
	private String tipoAmbiente;

	@Column(name="TIPO_EJECUCION")
	private String tipoEjecucion;

	@Column(name="TIPO_EMISION")
	private String tipoEmision;

	@Column(name="TIPO_GENERACION")
	private String tipoGeneracion;

	@Column(name="TIPO_ID_COMPRADOR")
	private String tipoIdComprador;

	@Column(name="TOTAL_SIN_IMPUESTOS")
	private BigDecimal totalSinImpuestos;

	@Column(name="VALOR_MODIFICACION")
	private BigDecimal valorModificacion;



	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getArchivoGenReim() {
		return this.archivoGenReim;
	}

	public void setArchivoGenReim(String archivoGenReim) {
		this.archivoGenReim = archivoGenReim;
	}

	public String getArchivoLegible() {
		return this.archivoLegible;
	}

	public void setArchivoLegible(String archivoLegible) {
		this.archivoLegible = archivoLegible;
	}

	public String getClaveAcceso() {
		return this.claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getClaveInterna() {
		return this.claveInterna;
	}

	public void setClaveInterna(String claveInterna) {
		this.claveInterna = claveInterna;
	}

	public String getCodDoc() {
		return this.codDoc;
	}

	public void setCodDoc(String codDoc) {
		this.codDoc = codDoc;
	}

	public String getCodDocModificado() {
		return this.codDocModificado;
	}

	public void setCodDocModificado(String codDocModificado) {
		this.codDocModificado = codDocModificado;
	}

	public String getCodPuntoEmision() {
		return this.codPuntoEmision;
	}

	public void setCodPuntoEmision(String codPuntoEmision) {
		this.codPuntoEmision = codPuntoEmision;
	}

	public String getCodSecuencial() {
		return this.codSecuencial;
	}

	public void setCodSecuencial(String codSecuencial) {
		this.codSecuencial = codSecuencial;
	}

	public String getContribuyenteEspecial() {
		return this.contribuyenteEspecial;
	}

	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}

	public String getCorreoNotificacion() {
		return this.correoNotificacion;
	}

	public void setCorreoNotificacion(String correoNotificacion) {
		this.correoNotificacion = correoNotificacion;
	}

	public String getDirEstablecimiento() {
		return this.dirEstablecimiento;
	}

	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechEmiDocSustento() {
		return this.fechEmiDocSustento;
	}

	public void setFechEmiDocSustento(Date fechEmiDocSustento) {
		this.fechEmiDocSustento = fechEmiDocSustento;
	}

	public Date getFechaAutorizacion() {
		return this.fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaEntReim() {
		return this.fechaEntReim;
	}

	public void setFechaEntReim(Date fechaEntReim) {
		this.fechaEntReim = fechaEntReim;
	}

	public Date getFechaLecTrad() {
		return this.fechaLecTrad;
	}

	public void setFechaLecTrad(Date fechaLecTrad) {
		this.fechaLecTrad = fechaLecTrad;
	}

	public Date getFechaRecepMail() {
		return this.fechaRecepMail;
	}

	public void setFechaRecepMail(Date fechaRecepMail) {
		this.fechaRecepMail = fechaRecepMail;
	}

	public BigDecimal getIdLote() {
		return this.idLote;
	}

	public void setIdLote(BigDecimal idLote) {
		this.idLote = idLote;
	}

	public String getIdentificacionComprador() {
		return this.identificacionComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public String getIdentificadorUsuario() {
		return this.identificadorUsuario;
	}

	public void setIdentificadorUsuario(String identificadorUsuario) {
		this.identificadorUsuario = identificadorUsuario;
	}

	public String getInfoAdicional() {
		return this.infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public String getIsOferton() {
		return this.isOferton;
	}

	public void setIsOferton(String isOferton) {
		this.isOferton = isOferton;
	}

	public String getMensajeReim() {
		return this.mensajeReim;
	}

	public void setMensajeReim(String mensajeReim) {
		this.mensajeReim = mensajeReim;
	}

	public String getModoEnvio() {
		return this.modoEnvio;
	}

	public void setModoEnvio(String modoEnvio) {
		this.modoEnvio = modoEnvio;
	}

	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNumDocModificado() {
		return this.numDocModificado;
	}

	public void setNumDocModificado(String numDocModificado) {
		this.numDocModificado = numDocModificado;
	}

	public String getNumeroAutorizacion() {
		return this.numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getObligadoContabilidad() {
		return this.obligadoContabilidad;
	}

	public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}

	public String getOrdenCompra() {
		return this.ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getPtoEmision() {
		return this.ptoEmision;
	}

	public void setPtoEmision(String ptoEmision) {
		this.ptoEmision = ptoEmision;
	}

	public String getRazonSocialComprador() {
		return this.razonSocialComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public String getRise() {
		return this.rise;
	}

	public void setRise(String rise) {
		this.rise = rise;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTarea() {
		return this.tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getTipoAmbiente() {
		return this.tipoAmbiente;
	}

	public void setTipoAmbiente(String tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}

	public String getTipoEjecucion() {
		return this.tipoEjecucion;
	}

	public void setTipoEjecucion(String tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
	}

	public String getTipoEmision() {
		return this.tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getTipoGeneracion() {
		return this.tipoGeneracion;
	}

	public void setTipoGeneracion(String tipoGeneracion) {
		this.tipoGeneracion = tipoGeneracion;
	}

	public String getTipoIdComprador() {
		return this.tipoIdComprador;
	}

	public void setTipoIdComprador(String tipoIdComprador) {
		this.tipoIdComprador = tipoIdComprador;
	}

	public BigDecimal getTotalSinImpuestos() {
		return this.totalSinImpuestos;
	}

	public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}

	public BigDecimal getValorModificacion() {
		return this.valorModificacion;
	}

	public void setValorModificacion(BigDecimal valorModificacion) {
		this.valorModificacion = valorModificacion;
	}

}