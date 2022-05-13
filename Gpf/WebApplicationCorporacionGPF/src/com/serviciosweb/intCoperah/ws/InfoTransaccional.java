
package com.serviciosweb.intCoperah.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para InfoTransaccional complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InfoTransaccional">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodigoEstablecimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreEstablecimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnidadComercial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DriverComercial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Canal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Valores" type="{WS_Transaccional}ArrayOfDouble" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoTransaccional", propOrder = {
    "codigoEstablecimiento",
    "nombreEstablecimiento",
    "unidadComercial",
    "driverComercial",
    "canal",
    "fecha",
    "valores"
})
public class InfoTransaccional {

    @XmlElement(name = "CodigoEstablecimiento")
    protected String codigoEstablecimiento;
    @XmlElement(name = "NombreEstablecimiento")
    protected String nombreEstablecimiento;
    @XmlElement(name = "UnidadComercial")
    protected String unidadComercial;
    @XmlElement(name = "DriverComercial")
    protected String driverComercial;
    @XmlElement(name = "Canal")
    protected String canal;
    @XmlElement(name = "Fecha")
    protected String fecha;
    @XmlElement(name = "Valores")
    protected ArrayOfDouble valores;

    /**
     * Obtiene el valor de la propiedad codigoEstablecimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    /**
     * Define el valor de la propiedad codigoEstablecimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEstablecimiento(String value) {
        this.codigoEstablecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEstablecimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    /**
     * Define el valor de la propiedad nombreEstablecimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEstablecimiento(String value) {
        this.nombreEstablecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadComercial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidadComercial() {
        return unidadComercial;
    }

    /**
     * Define el valor de la propiedad unidadComercial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidadComercial(String value) {
        this.unidadComercial = value;
    }

    /**
     * Obtiene el valor de la propiedad driverComercial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverComercial() {
        return driverComercial;
    }

    /**
     * Define el valor de la propiedad driverComercial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverComercial(String value) {
        this.driverComercial = value;
    }

    /**
     * Obtiene el valor de la propiedad canal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanal() {
        return canal;
    }

    /**
     * Define el valor de la propiedad canal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanal(String value) {
        this.canal = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad valores.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getValores() {
        return valores;
    }

    /**
     * Define el valor de la propiedad valores.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setValores(ArrayOfDouble value) {
        this.valores = value;
    }

}
