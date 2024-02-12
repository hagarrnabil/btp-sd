
package com.example.datamodel.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessDocumentMessageHeaderParty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessDocumentMessageHeaderParty"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InternalID" type="{http://sap.com/xi/FNDEI}PartyInternalID" minOccurs="0"/&gt;
 *         &lt;element name="StandardID" type="{http://sap.com/xi/FNDEI}PartyStandardID" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ContactPerson" type="{http://sap.com/xi/FNDEI}BusinessDocumentMessageHeaderPartyContactPerson" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDocumentMessageHeaderParty", propOrder = {
    "internalID",
    "standardID",
    "contactPerson"
})
public class BusinessDocumentMessageHeaderParty {

    @XmlElement(name = "InternalID")
    protected PartyInternalID internalID;
    @XmlElement(name = "StandardID")
    protected List<PartyStandardID> standardID;
    @XmlElement(name = "ContactPerson")
    protected BusinessDocumentMessageHeaderPartyContactPerson contactPerson;

    /**
     * Gets the value of the internalID property.
     * 
     * @return
     *     possible object is
     *     {@link PartyInternalID }
     *     
     */
    public PartyInternalID getInternalID() {
        return internalID;
    }

    /**
     * Sets the value of the internalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyInternalID }
     *     
     */
    public void setInternalID(PartyInternalID value) {
        this.internalID = value;
    }

    /**
     * Gets the value of the standardID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the standardID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartyStandardID }
     * 
     * 
     */
    public List<PartyStandardID> getStandardID() {
        if (standardID == null) {
            standardID = new ArrayList<PartyStandardID>();
        }
        return this.standardID;
    }

    /**
     * Gets the value of the contactPerson property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessDocumentMessageHeaderPartyContactPerson }
     *     
     */
    public BusinessDocumentMessageHeaderPartyContactPerson getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the value of the contactPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessDocumentMessageHeaderPartyContactPerson }
     *     
     */
    public void setContactPerson(BusinessDocumentMessageHeaderPartyContactPerson value) {
        this.contactPerson = value;
    }

}
