
package com.example.datamodel.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitOfMeasurementMasterDataReplicationBundleRequestMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitOfMeasurementMasterDataReplicationBundleRequestMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageHeader" type="{http://sap.com/xi/FNDEI}BusinessDocumentMessageHeader"/&gt;
 *         &lt;element name="UnitOfMeasurementReplicationRequestMessage" type="{http://sap.com/xi/FNDEI}UnitOfMeasurementReplicationRequestMessage" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitOfMeasurementMasterDataReplicationBundleRequestMessage", propOrder = {
    "messageHeader",
    "unitOfMeasurementReplicationRequestMessage"
})
public class UnitOfMeasurementMasterDataReplicationBundleRequestMessage {

    @XmlElement(name = "MessageHeader", required = true)
    protected BusinessDocumentMessageHeader messageHeader;
    @XmlElement(name = "UnitOfMeasurementReplicationRequestMessage")
    protected List<UnitOfMeasurementReplicationRequestMessage> unitOfMeasurementReplicationRequestMessage;

    /**
     * Gets the value of the messageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessDocumentMessageHeader }
     *     
     */
    public BusinessDocumentMessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the value of the messageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessDocumentMessageHeader }
     *     
     */
    public void setMessageHeader(BusinessDocumentMessageHeader value) {
        this.messageHeader = value;
    }

    /**
     * Gets the value of the unitOfMeasurementReplicationRequestMessage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the unitOfMeasurementReplicationRequestMessage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitOfMeasurementReplicationRequestMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnitOfMeasurementReplicationRequestMessage }
     * 
     * 
     */
    public List<UnitOfMeasurementReplicationRequestMessage> getUnitOfMeasurementReplicationRequestMessage() {
        if (unitOfMeasurementReplicationRequestMessage == null) {
            unitOfMeasurementReplicationRequestMessage = new ArrayList<UnitOfMeasurementReplicationRequestMessage>();
        }
        return this.unitOfMeasurementReplicationRequestMessage;
    }

}
