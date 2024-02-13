
package com.example.datamodel.wsdl;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.datamodel.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UnitOfMeasurementMasterDataReplicationBundleRequest_QNAME = new QName("http://sap.com/xi/FNDEI", "UnitOfMeasurementMasterDataReplicationBundleRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.datamodel.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnitOfMeasurementMasterDataReplicationBundleRequestMessage }
     * 
     */
    public UnitOfMeasurementMasterDataReplicationBundleRequestMessage createUnitOfMeasurementMasterDataReplicationBundleRequestMessage() {
        return new UnitOfMeasurementMasterDataReplicationBundleRequestMessage();
    }

    /**
     * Create an instance of {@link GetUnitOfMeasurementRequest }
     * 
     */
    public GetUnitOfMeasurementRequest createGetUnitOfMeasurementRequest() {
        return new GetUnitOfMeasurementRequest();
    }

    /**
     * Create an instance of {@link Description }
     * 
     */
    public Description createDescription() {
        return new Description();
    }

    /**
     * Create an instance of {@link GetUnitOfMeasurementResponse }
     * 
     */
    public GetUnitOfMeasurementResponse createGetUnitOfMeasurementResponse() {
        return new GetUnitOfMeasurementResponse();
    }

    /**
     * Create an instance of {@link UnitOfMeasurement }
     * 
     */
    public UnitOfMeasurement createUnitOfMeasurement() {
        return new UnitOfMeasurement();
    }

    /**
     * Create an instance of {@link BusinessDocumentMessageHeader }
     * 
     */
    public BusinessDocumentMessageHeader createBusinessDocumentMessageHeader() {
        return new BusinessDocumentMessageHeader();
    }

    /**
     * Create an instance of {@link BusinessDocumentMessageHeaderParty }
     * 
     */
    public BusinessDocumentMessageHeaderParty createBusinessDocumentMessageHeaderParty() {
        return new BusinessDocumentMessageHeaderParty();
    }

    /**
     * Create an instance of {@link BusinessDocumentMessageHeaderPartyContactPerson }
     * 
     */
    public BusinessDocumentMessageHeaderPartyContactPerson createBusinessDocumentMessageHeaderPartyContactPerson() {
        return new BusinessDocumentMessageHeaderPartyContactPerson();
    }

    /**
     * Create an instance of {@link BusinessDocumentMessageID }
     * 
     */
    public BusinessDocumentMessageID createBusinessDocumentMessageID() {
        return new BusinessDocumentMessageID();
    }

    /**
     * Create an instance of {@link BusinessScope }
     * 
     */
    public BusinessScope createBusinessScope() {
        return new BusinessScope();
    }

    /**
     * Create an instance of {@link BusinessScopeID }
     * 
     */
    public BusinessScopeID createBusinessScopeID() {
        return new BusinessScopeID();
    }

    /**
     * Create an instance of {@link BusinessScopeInstanceID }
     * 
     */
    public BusinessScopeInstanceID createBusinessScopeInstanceID() {
        return new BusinessScopeInstanceID();
    }

    /**
     * Create an instance of {@link BusinessScopeTypeCode }
     * 
     */
    public BusinessScopeTypeCode createBusinessScopeTypeCode() {
        return new BusinessScopeTypeCode();
    }

    /**
     * Create an instance of {@link ContactPersonInternalID }
     * 
     */
    public ContactPersonInternalID createContactPersonInternalID() {
        return new ContactPersonInternalID();
    }

    /**
     * Create an instance of {@link EmailURI }
     * 
     */
    public EmailURI createEmailURI() {
        return new EmailURI();
    }

    /**
     * Create an instance of {@link MEDIUMName }
     * 
     */
    public MEDIUMName createMEDIUMName() {
        return new MEDIUMName();
    }

    /**
     * Create an instance of {@link PartyInternalID }
     * 
     */
    public PartyInternalID createPartyInternalID() {
        return new PartyInternalID();
    }

    /**
     * Create an instance of {@link PartyStandardID }
     * 
     */
    public PartyStandardID createPartyStandardID() {
        return new PartyStandardID();
    }

    /**
     * Create an instance of {@link PhoneNumber }
     * 
     */
    public PhoneNumber createPhoneNumber() {
        return new PhoneNumber();
    }

    /**
     * Create an instance of {@link UUID }
     * 
     */
    public UUID createUUID() {
        return new UUID();
    }

    /**
     * Create an instance of {@link UnitOfMeasurementCode }
     * 
     */
    public UnitOfMeasurementCode createUnitOfMeasurementCode() {
        return new UnitOfMeasurementCode();
    }

    /**
     * Create an instance of {@link UnitOfMeasurementReplicationRequestMessage }
     * 
     */
    public UnitOfMeasurementReplicationRequestMessage createUnitOfMeasurementReplicationRequestMessage() {
        return new UnitOfMeasurementReplicationRequestMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnitOfMeasurementMasterDataReplicationBundleRequestMessage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UnitOfMeasurementMasterDataReplicationBundleRequestMessage }{@code >}
     */
    @XmlElementDecl(namespace = "http://sap.com/xi/FNDEI", name = "UnitOfMeasurementMasterDataReplicationBundleRequest")
    public JAXBElement<UnitOfMeasurementMasterDataReplicationBundleRequestMessage> createUnitOfMeasurementMasterDataReplicationBundleRequest(UnitOfMeasurementMasterDataReplicationBundleRequestMessage value) {
        return new JAXBElement<UnitOfMeasurementMasterDataReplicationBundleRequestMessage>(_UnitOfMeasurementMasterDataReplicationBundleRequest_QNAME, UnitOfMeasurementMasterDataReplicationBundleRequestMessage.class, null, value);
    }

}
