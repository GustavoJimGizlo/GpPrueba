
package com.corporaciongpf.integration.soap.empleado.services.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.corporaciongpf.integration.soap.empleado.v1.Empleados;


/**
 * <p>Clase Java para EmpleadoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EmpleadoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.corporaciongpf.com/integration/empleado/v1}Empleados"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmpleadoResponse", propOrder = {
    "empleados"
})
public class EmpleadoResponse {

    @XmlElement(name = "Empleados", namespace = "http://www.corporaciongpf.com/integration/empleado/v1", required = true)
    protected Empleados empleados;

    /**
     * Obtiene el valor de la propiedad empleados.
     * 
     * @return
     *     possible object is
     *     {@link Empleados }
     *     
     */
    public Empleados getEmpleados() {
        return empleados;
    }

    /**
     * Define el valor de la propiedad empleados.
     * 
     * @param value
     *     allowed object is
     *     {@link Empleados }
     *     
     */
    public void setEmpleados(Empleados value) {
        this.empleados = value;
    }

}
