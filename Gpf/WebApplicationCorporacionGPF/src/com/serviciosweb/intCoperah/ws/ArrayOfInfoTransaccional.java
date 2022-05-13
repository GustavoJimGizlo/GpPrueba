
package com.serviciosweb.intCoperah.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfInfoTransaccional complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInfoTransaccional">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InfoTransaccional" type="{WS_Transaccional}InfoTransaccional" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInfoTransaccional", propOrder = {
    "infoTransaccional"
})
public class ArrayOfInfoTransaccional {

    @XmlElement(name = "InfoTransaccional", nillable = true)
    protected List<InfoTransaccional> infoTransaccional;

    /**
     * Gets the value of the infoTransaccional property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoTransaccional property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoTransaccional().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoTransaccional }
     * 
     * 
     */
    public List<InfoTransaccional> getInfoTransaccional() {
        if (infoTransaccional == null) {
            infoTransaccional = new ArrayList<InfoTransaccional>();
        }
        return this.infoTransaccional;
    }

}
