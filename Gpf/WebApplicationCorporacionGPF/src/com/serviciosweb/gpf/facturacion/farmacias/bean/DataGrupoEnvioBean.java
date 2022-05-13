/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.farmacias.bean;

/**
 *
 * @author mftellor
 */
public class DataGrupoEnvioBean implements java.io.Serializable {
    private String grupo;

    public DataGrupoEnvioBean()
    {
    }

    public DataGrupoEnvioBean(String grupo){
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
}
