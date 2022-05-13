/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciosweb.gpf.facturacion.farmacias.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mftellor
 */
@Entity
public class RecetaAbfBean {
	@Column(name = "RECETA_ID")
	private Integer recetaId;
	@Column(name = "CODIGO_RECETA")
    private String codigoReceta;
	@Column(name = "CANAL")
    private String canal;  
	@Column(name = "FECHA")
    private String fecha;
	@Column(name = "USUARIO")
    private String usuario;
	@Column(name = "CODIGO_CONVENIO")
    private String codigoConvenio;
	@Column(name = "IDENTIFICACION")	
    private String identificacionPaciente;
	@Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacionPaciente;
	@Column(name = "NOMBRES")
    private String nombresPaciente;
	@Column(name = "IDENTIFICACION_MEDICO")
    private String identificacionMedico;
	@Column(name = "TIPO_IDENTIFICACION_MEDICO")
    private String tipoIdentificacionMedico;
	
	
	@Column(name = "PRIMER_NOMBRE_MED")
	private String primerNombreMedico;
	@Column(name = "SEGUNDO_NOMBRE_MED")
	private String segundoNombresMedico;
    @Column(name = "PRIMER_APELLIDO_MED")
    private String primerApellidodMedico;
	@Column(name = "SEGUNDO_APELLIDO_MED")
	private String segundoApellidoMedico;
	  
	
    
	@Column(name = "DIAGNOSTICO")
    private String diagnostico;        
    private List<ItemRecetaAbfBean> itemsRecetaAbf;
    
          
    /**
     *@descricion Código único de receta generado por cada cliente
     */
    public String getCodigoReceta() {
        
        return codigoReceta.substring(0, codigoReceta.length()>=50?50:codigoReceta.length()).toUpperCase();
    }
    /**
     *@descricion Código único de receta generado por cada cliente
     */
    public void setCodigoReceta(String codigoReceta) {
        this.codigoReceta = codigoReceta;
    }
    /**
     *@descricion indica la fecha de registro de la receta electrónica
     */
    public String getFecha() {
        return fecha;
    }
    /**
     *@descricion indica la fecha de registro de la receta electrónica
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    /**
     *@descricion 
     */
    public String getUsuario() {
        if(usuario!=null)
           return usuario.substring(0, usuario.length()>=50?50:usuario.length()).toUpperCase();
        else
            return "N/D";
    }
    /**
     *@descricion 
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     *@descricion Código único de convenio asignado por ABF
     */
    public String getCodigoConvenio() {
        if(codigoConvenio!=null)
           return codigoConvenio.substring(0, codigoConvenio.length()>=10?10:codigoConvenio.length()).toUpperCase();
        else
            return "N/D";
    }
    /**
     *@descricion Código único de convenio asignado por ABF
     */
    public void setCodigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }
    /**
     *@descricion Nombre completo del beneficiario
     */
    public String getNombresPaciente() {
        return nombresPaciente.substring(0, nombresPaciente.length()>=100?100:nombresPaciente.length()).toUpperCase();
    }
    /**
     *@descricion Nombre completo del beneficiario
     */
    public void setNombresPaciente(String nombresPaciente) {
        this.nombresPaciente = nombresPaciente;
    }
   
    public String getPrimerNombreMedico() {
		return primerNombreMedico;
	}
	public void setPrimerNombreMedico(String primerNombreMedico) {
		this.primerNombreMedico = primerNombreMedico;
	}
	public String getSegundoNombresMedico() {
		return segundoNombresMedico;
	}
	public void setSegundoNombresMedico(String segundoNombresMedico) {
		this.segundoNombresMedico = segundoNombresMedico;
	}
	
	public String getPrimerApellidodMedico() {
		return primerApellidodMedico;
	}
	public void setPrimerApellidodMedico(String primerApellidodMedico) {
		this.primerApellidodMedico = primerApellidodMedico;
	}
	public String getSegundoApellidoMedico() {
		return segundoApellidoMedico;
	}
	public void setSegundoApellidoMedico(String segundoApellidoMedico) {
		this.segundoApellidoMedico = segundoApellidoMedico;
	}
	/**
     *@descricion Código del diagnóstico según formato CI10
     */
    public String getDiagnostico() {
        return diagnostico.substring(0, diagnostico.length()>=10?10:diagnostico.length()).toUpperCase();
    }
    /**
     *@descricion Código del diagnóstico según formato CI10
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<ItemRecetaAbfBean> getItemsRecetaAbf() {
    	if(itemsRecetaAbf==null)
    		itemsRecetaAbf=new ArrayList<ItemRecetaAbfBean>();
        return itemsRecetaAbf;
    }

    public void setItemsRecetaAbf(List<ItemRecetaAbfBean> itemsRecetaAbf) {
        this.itemsRecetaAbf = itemsRecetaAbf;
    }

    public String getIdentificacionPaciente() {
        if(identificacionPaciente!=null)
           return identificacionPaciente.substring(0, identificacionPaciente.length()>=13?13:identificacionPaciente.length()).toUpperCase();
        else
           return "N/D";  

    }

    public void setIdentificacionPaciente(String identificacionPaciente) {
        this.identificacionPaciente = identificacionPaciente;
    }

    public String getTipoIdentificacionPaciente() {
        if(tipoIdentificacionPaciente!=null)
           return tipoIdentificacionPaciente.substring(0, tipoIdentificacionPaciente.length()>3?3:tipoIdentificacionPaciente.length()).toUpperCase();
        else
           return "N/D";
    }

    public void setTipoIdentificacionPaciente(String tipoIdentificacionPaciente) {
        this.tipoIdentificacionPaciente = tipoIdentificacionPaciente;
    }

    public String getIdentificacionMedico() {
        return identificacionMedico.substring(0, identificacionMedico.length()>=13?13:identificacionMedico.length()).toUpperCase();
    }

    public void setIdentificacionMedico(String identificacionMedico) {
        this.identificacionMedico = identificacionMedico;
    }

    public String getTipoIdentificacionMedico() {
        return tipoIdentificacionMedico.substring(0, tipoIdentificacionMedico.length()>=3?3:tipoIdentificacionMedico.length()).toUpperCase();
    }

    public void setTipoIdentificacionMedico(String tipoIdentificacionMedico) {
        this.tipoIdentificacionMedico = tipoIdentificacionMedico;
    }
	public Integer getRecetaId() {
		return recetaId;
	}
	public void setRecetaId(Integer recetaId) {
		this.recetaId = recetaId;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}

    
}
