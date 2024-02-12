
package com.example.datamodel.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitOfMeasurementReplicationRequestMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitOfMeasurementReplicationRequestMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ListCompleteTransmissionIndicator" type="{http://sap.com/xi/FNDEI}Indicator"/&gt;
 *         &lt;element name="TransmissionStartDateTime" type="{http://sap.com/xi/FNDEI}GLOBAL_DateTime"/&gt;
 *         &lt;element name="UnitOfMeasurement" type="{http://sap.com/xi/FNDEI}UnitOfMeasurement" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitOfMeasurementReplicationRequestMessage", propOrder = {
    "listCompleteTransmissionIndicator",
    "transmissionStartDateTime",
    "unitOfMeasurement"
})
public class UnitOfMeasurementReplicationRequestMessage {

    @XmlElement(name = "ListCompleteTransmissionIndicator")
    protected boolean listCompleteTransmissionIndicator;
    @XmlElement(name = "TransmissionStartDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transmissionStartDateTime;
    @XmlElement(name = "UnitOfMeasurement")
    protected List<UnitOfMeasurement> unitOfMeasurement;

    /**
     * Gets the value of the listCompleteTransmissionIndicator property.
     * 
     */
    public boolean isListCompleteTransmissionIndicator() {
        return listCompleteTransmissionIndicator;
    }

    /**
     * Sets the value of the listCompleteTransmissionIndicator property.
     * 
     */
    public void setListCompleteTransmissionIndicator(boolean value) {
        this.listCompleteTransmissionIndicator = value;
    }

    /**
     * Gets the value of the transmissionStartDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransmissionStartDateTime() {
        return transmissionStartDateTime;
    }

    /**
     * Sets the value of the transmissionStartDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransmissionStartDateTime(XMLGregorianCalendar value) {
        this.transmissionStartDateTime = value;
    }

    /**
     * Gets the value of the unitOfMeasurement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the unitOfMeasurement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitOfMeasurement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnitOfMeasurement }
     * 
     * 
     */
    public List<UnitOfMeasurement> getUnitOfMeasurement() {
        if (unitOfMeasurement == null) {
            unitOfMeasurement = new ArrayList<UnitOfMeasurement>();
        }
        return this.unitOfMeasurement;
    }

}
