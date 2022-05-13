
package com.serviciosweb.intCoperah.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para InfoCarga complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InfoCarga">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificadorCliente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoSeguridad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaInicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaTermino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InfoTransaccional" type="{WS_Transaccional}ArrayOfInfoTransaccional" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoCarga", propOrder = {
    "identificadorCliente",
    "codigoSeguridad",
    "descripcion",
    "fechaInicio",
    "fechaTermino",
    "infoTransaccional"
})
public class InfoCarga {

    @XmlElement(name = "IdentificadorCliente")
    protected int identificadorCliente;
    @XmlElement(name = "CodigoSeguridad")
    protected String codigoSeguridad;
    @XmlElement(name = "Descripcion")
    protected String descripcion;
    @XmlElement(name = "FechaInicio")
    protected String fechaInicio;
    @XmlElement(name = "FechaTermino")
    protected String fechaTermino;
    @XmlElement(name = "InfoTransaccional")
    protected ArrayOfInfoTransaccional infoTransaccional;

    /**
     * Obtiene el valor de la propiedad identificadorCliente.
     * 
     */
    public int getIdentificadorCliente() {
        return identificadorCliente;
    }

    /**
     * Define el valor de la propiedad identificadorCliente.
     * 
     */
    public void setIdentificadorCliente(int value) {
        this.identificadorCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoSeguridad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * Define el valor de la propiedad codigoSeguridad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSeguridad(String value) {
        this.codigoSeguridad = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaInicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Define el valor de la propiedad fechaInicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicio(String value) {
        this.fechaInicio = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaTermino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaTermino() {
        return fechaTermino;
    }

    /**
     * Define el valor de la propiedad fechaTermino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaTermino(String value) {
        this.fechaTermino = value;
    }

    /**
     * Obtiene el valor de la propiedad infoTransaccional.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInfoTransaccional }
     *     
     */
    public ArrayOfInfoTransaccional getInfoTransaccional() {
        return infoTransaccional;
    }

    /**
     * Define el valor de la propiedad infoTransaccional.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInfoTransaccional }
     *     
     */
    public void setInfoTransaccional(ArrayOfInfoTransaccional value) {
        this.infoTransaccional = value;
    }

}
