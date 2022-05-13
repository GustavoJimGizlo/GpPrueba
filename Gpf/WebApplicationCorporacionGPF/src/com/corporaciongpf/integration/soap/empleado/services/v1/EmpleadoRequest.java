
package com.corporaciongpf.integration.soap.empleado.services.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.corporaciongpf.integration.soap.empleado.v1.EstadoEmpleado;


/**
 * <p>Clase Java para EmpleadoRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EmpleadoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.corporaciongpf.com/integration/empleado/v1}EstadoEmpleado"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmpleadoRequest", propOrder = {
    "estadoEmpleado"
})
public class EmpleadoRequest {

    @XmlElement(name = "EstadoEmpleado", namespace = "http://www.corporaciongpf.com/integration/empleado/v1", required = true)
    protected EstadoEmpleado estadoEmpleado;

    /**
     * Obtiene el valor de la propiedad estadoEmpleado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoEmpleado }
     *     
     */
    public EstadoEmpleado getEstadoEmpleado() {
        return estadoEmpleado;
    }

    /**
     * Define el valor de la propiedad estadoEmpleado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoEmpleado }
     *     
     */
    public void setEstadoEmpleado(EstadoEmpleado value) {
        this.estadoEmpleado = value;
    }

}
