/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.farmacias.bean;

/**
 *
 * @author mftellor
 */
public class DataGrupoEnvioNewBean implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String grupo;
    private String bodega;
    private String documentoBodega;

    public DataGrupoEnvioNewBean()
    {
    }

    public DataGrupoEnvioNewBean(String grupo){
            this.grupo = grupo;
    }

	/**
	 * @return Returns the grupo.
	 */
	public String getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getDocumentoBodega() {
		return documentoBodega;
	}

	public void setDocumentoBodega(String documentoBodega) {
		this.documentoBodega = documentoBodega;
	}
	
}
