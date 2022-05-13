
package com.serviciosweb.intCoperah.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infoCarga" type="{WS_Transaccional}InfoCarga" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "infoCarga"
})
@XmlRootElement(name = "Procesar")
public class Procesar {

    protected InfoCarga infoCarga;

    /**
     * Obtiene el valor de la propiedad infoCarga.
     * 
     * @return
     *     possible object is
     *     {@link InfoCarga }
     *     
     */
    public InfoCarga getInfoCarga() {
        return infoCarga;
    }

    /**
     * Define el valor de la propiedad infoCarga.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoCarga }
     *     
     */
    public void setInfoCarga(InfoCarga value) {
        this.infoCarga = value;
    }

}
