
package com.serviciosweb.intCoperah.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ProcesarResult" type="{WS_Transaccional}InfoRespuesta" minOccurs="0"/>
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
    "procesarResult"
})
@XmlRootElement(name = "ProcesarResponse")
public class ProcesarResponse {

    @XmlElement(name = "ProcesarResult")
    protected InfoRespuesta procesarResult;

    /**
     * Obtiene el valor de la propiedad procesarResult.
     * 
     * @return
     *     possible object is
     *     {@link InfoRespuesta }
     *     
     */
    public InfoRespuesta getProcesarResult() {
        return procesarResult;
    }

    /**
     * Define el valor de la propiedad procesarResult.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoRespuesta }
     *     
     */
    public void setProcesarResult(InfoRespuesta value) {
        this.procesarResult = value;
    }

}
