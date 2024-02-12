
package com.example.datamodel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessScope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessScope"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeCode" type="{http://sap.com/xi/FNDEI}BusinessScopeTypeCode"/&gt;
 *         &lt;element name="InstanceID" type="{http://sap.com/xi/FNDEI}BusinessScopeInstanceID" minOccurs="0"/&gt;
 *         &lt;element name="ID" type="{http://sap.com/xi/FNDEI}BusinessScopeID" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessScope", propOrder = {
    "typeCode",
    "instanceID",
    "id"
})
public class BusinessScope {

    @XmlElement(name = "TypeCode", required = true)
    protected BusinessScopeTypeCode typeCode;
    @XmlElement(name = "InstanceID")
    protected BusinessScopeInstanceID instanceID;
    @XmlElement(name = "ID")
    protected BusinessScopeID id;

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessScopeTypeCode }
     *     
     */
    public BusinessScopeTypeCode getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessScopeTypeCode }
     *     
     */
    public void setTypeCode(BusinessScopeTypeCode value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the instanceID property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessScopeInstanceID }
     *     
     */
    public BusinessScopeInstanceID getInstanceID() {
        return instanceID;
    }

    /**
     * Sets the value of the instanceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessScopeInstanceID }
     *     
     */
    public void setInstanceID(BusinessScopeInstanceID value) {
        this.instanceID = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessScopeID }
     *     
     */
    public BusinessScopeID getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessScopeID }
     *     
     */
    public void setID(BusinessScopeID value) {
        this.id = value;
    }

}
