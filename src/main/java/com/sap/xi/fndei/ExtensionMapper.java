/**
 * ExtensionMapper.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** ExtensionMapper class */
@SuppressWarnings({"unchecked", "unused"})
public class ExtensionMapper {

  public static java.lang.Object getTypeObject(
      java.lang.String namespaceURI,
      java.lang.String typeName,
      javax.xml.stream.XMLStreamReader reader)
      throws java.lang.Exception {

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "content_type1".equals(typeName)) {

      return Content_type1.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "listID_type0".equals(typeName)) {

      return ListID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type6".equals(typeName)) {

      return SchemeAgencyID_type6.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type4".equals(typeName)) {

      return SchemeAgencyID_type4.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type5".equals(typeName)) {

      return SchemeAgencyID_type5.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "CountryDiallingCode".equals(typeName)) {

      return CountryDiallingCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "Indicator".equals(typeName)) {

      return Indicator.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "ContactPersonInternalID".equals(typeName)) {

      return ContactPersonInternalID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "EmailURI".equals(typeName)) {

      return EmailURI.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "listVersionID_type0".equals(typeName)) {

      return ListVersionID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessScopeTypeCode.Content".equals(typeName)) {

      return BusinessScopeTypeCodeContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "AgencyIdentificationCode".equals(typeName)) {

      return AgencyIdentificationCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "LANGUAGEINDEPENDENT_LONG_Name".equals(typeName)) {

      return LANGUAGEINDEPENDENT_LONG_Name.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "PhoneNumber".equals(typeName)) {

      return PhoneNumber.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "content_type1".equals(typeName)) {

      return Content_type1.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "PartyInternalID".equals(typeName)) {

      return PartyInternalID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessDocumentMessageID.Content".equals(typeName)) {

      return BusinessDocumentMessageIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessScopeTypeCode".equals(typeName)) {

      return BusinessScopeTypeCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "Description".equals(typeName)) {

      return Description.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "UnitOfMeasurementReplicationRequestMessage".equals(typeName)) {

      return UnitOfMeasurementReplicationRequestMessage.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "UUID.Content".equals(typeName)) {

      return UUIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "LANGUAGEINDEPENDENT_MEDIUM_Name".equals(typeName)) {

      return LANGUAGEINDEPENDENT_MEDIUM_Name.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "MEDIUM_Name.Content".equals(typeName)) {

      return MEDIUM_NameContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "NumberValue".equals(typeName)) {

      return NumberValue.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "codeListName_type0".equals(typeName)) {

      return CodeListName_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type2".equals(typeName)) {

      return SchemeAgencyID_type2.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "UnitOfMeasurementCode".equals(typeName)) {

      return UnitOfMeasurementCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type3".equals(typeName)) {

      return SchemeAgencyID_type3.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "CountryCode".equals(typeName)) {

      return CountryCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type0".equals(typeName)) {

      return SchemeAgencyID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "PartyStandardID.Content".equals(typeName)) {

      return PartyStandardIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "BusinessScopeID".equals(typeName)) {

      return BusinessScopeID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeAgencyID_type1".equals(typeName)) {

      return SchemeAgencyID_type1.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "listAgencySchemeID_type0".equals(typeName)) {

      return ListAgencySchemeID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "PartyInternalID.Content".equals(typeName)) {

      return PartyInternalIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "BusinessScope".equals(typeName)) {

      return BusinessScope.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "PhoneNumberAreaID".equals(typeName)) {

      return PhoneNumberAreaID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessDocumentMessageHeaderParty".equals(typeName)) {

      return BusinessDocumentMessageHeaderParty.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type1".equals(typeName)) {

      return SchemeID_type1.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type2".equals(typeName)) {

      return SchemeID_type2.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "listAgencyID_type0".equals(typeName)) {

      return ListAgencyID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type3".equals(typeName)) {

      return SchemeID_type3.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type4".equals(typeName)) {

      return SchemeID_type4.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type5".equals(typeName)) {

      return SchemeID_type5.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "GLOBAL_DateTime".equals(typeName)) {

      return GLOBAL_DateTime.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type6".equals(typeName)) {

      return SchemeID_type6.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "PartyStandardID".equals(typeName)) {

      return PartyStandardID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessScopeInstanceID.Content".equals(typeName)) {

      return BusinessScopeInstanceIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessScopeInstanceID".equals(typeName)) {

      return BusinessScopeInstanceID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessDocumentMessageHeaderPartyContactPerson".equals(typeName)) {

      return BusinessDocumentMessageHeaderPartyContactPerson.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "schemeID_type0".equals(typeName)) {

      return SchemeID_type0.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "BusinessSystemID".equals(typeName)) {

      return BusinessSystemID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessDocumentMessageHeader".equals(typeName)) {

      return BusinessDocumentMessageHeader.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "UnitOfMeasurement".equals(typeName)) {

      return UnitOfMeasurement.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "UnitOfMeasurementMasterDataReplicationBundleRequestMessage".equals(typeName)) {

      return UnitOfMeasurementMasterDataReplicationBundleRequestMessage.Factory
          .parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessDocumentMessageID".equals(typeName)) {

      return BusinessDocumentMessageID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "ContactPersonInternalID.Content".equals(typeName)) {

      return ContactPersonInternalIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "LanguageCode".equals(typeName)) {

      return LanguageCode.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "BusinessScopeID.Content".equals(typeName)) {

      return BusinessScopeIDContent.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "PhoneNumberSubscriberID".equals(typeName)) {

      return PhoneNumberSubscriberID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "UUID".equals(typeName)) {

      return UUID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI)
        && "PhoneNumberExtensionID".equals(typeName)) {

      return PhoneNumberExtensionID.Factory.parse(reader);
    }

    if ("http://sap.com/xi/FNDEI".equals(namespaceURI) && "MEDIUM_Name".equals(typeName)) {

      return MEDIUM_Name.Factory.parse(reader);
    }

    throw new org.apache.axis2.databinding.ADBException(
        "Unsupported type " + namespaceURI + " " + typeName);
  }
}
