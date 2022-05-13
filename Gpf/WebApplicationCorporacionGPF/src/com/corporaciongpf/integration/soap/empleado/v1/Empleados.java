
package com.corporaciongpf.integration.soap.empleado.v1;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="Empleado" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codEmp" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="codCct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="nombreCct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Posicion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="numEmpleados" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="presupuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "empleado"
})
@XmlRootElement(name = "Empleados")
public class Empleados {

    @XmlElement(name = "Empleado", required = true)
    protected List<Empleados.Empleado> empleado;

    /**
     * Gets the value of the empleado property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the empleado property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmpleado().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Empleados.Empleado }
     * 
     * 
     */
    public List<Empleados.Empleado> getEmpleado() {
        if (empleado == null) {
            empleado = new ArrayList<Empleados.Empleado>();
        }
        return this.empleado;
    }


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
     *         &lt;element name="codEmp" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="codCct" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="nombreCct" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Posicion" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="numEmpleados" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="presupuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "codEmp",
        "codCct",
        "nombreCct",
        "posicion",
        "numEmpleados",
        "presupuesto"
    })
    public static class Empleado {

        protected int codEmp;
        @XmlElement(required = true)
        protected String codCct;
        @XmlElement(required = true)
        protected String nombreCct;
        @XmlElement(name = "Posicion", required = true)
        protected String posicion;
        protected int numEmpleados;
        @XmlElement(required = true)
        protected String presupuesto;

        /**
         * Obtiene el valor de la propiedad codEmp.
         * 
         */
        public int getCodEmp() {
            return codEmp;
        }

        /**
         * Define el valor de la propiedad codEmp.
         * 
         */
        public void setCodEmp(int value) {
            this.codEmp = value;
        }

        /**
         * Obtiene el valor de la propiedad codCct.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodCct() {
            return codCct;
        }

        /**
         * Define el valor de la propiedad codCct.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodCct(String value) {
            this.codCct = value;
        }

        /**
         * Obtiene el valor de la propiedad nombreCct.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNombreCct() {
            return nombreCct;
        }

        /**
         * Define el valor de la propiedad nombreCct.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNombreCct(String value) {
            this.nombreCct = value;
        }

        /**
         * Obtiene el valor de la propiedad posicion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPosicion() {
            return posicion;
        }

        /**
         * Define el valor de la propiedad posicion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPosicion(String value) {
            this.posicion = value;
        }

        /**
         * Obtiene el valor de la propiedad numEmpleados.
         * 
         */
        public int getNumEmpleados() {
            return numEmpleados;
        }

        /**
         * Define el valor de la propiedad numEmpleados.
         * 
         */
        public void setNumEmpleados(int value) {
            this.numEmpleados = value;
        }

        /**
         * Obtiene el valor de la propiedad presupuesto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPresupuesto() {
            return presupuesto;
        }

        /**
         * Define el valor de la propiedad presupuesto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPresupuesto(String value) {
            this.presupuesto = value;
        }

    }

}
