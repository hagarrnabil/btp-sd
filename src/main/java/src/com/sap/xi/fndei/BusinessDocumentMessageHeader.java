/**
 * BusinessDocumentMessageHeader.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package src.com.sap.xi.fndei;

/** BusinessDocumentMessageHeader bean class */
@SuppressWarnings({"unchecked", "unused"})
public class BusinessDocumentMessageHeader implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = BusinessDocumentMessageHeader
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for ID */
  protected com.sap.xi.fndei.BusinessDocumentMessageID localID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localIDTracker = false;

  public boolean isIDSpecified() {
    return localIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessDocumentMessageID
   */
  public com.sap.xi.fndei.BusinessDocumentMessageID getID() {
    return localID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ID
   */
  public void setID(com.sap.xi.fndei.BusinessDocumentMessageID param) {
    localIDTracker = param != null;

    this.localID = param;
  }

  /** field for UUID */
  protected com.sap.xi.fndei.UUID localUUID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localUUIDTracker = false;

  public boolean isUUIDSpecified() {
    return localUUIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.UUID
   */
  public com.sap.xi.fndei.UUID getUUID() {
    return localUUID;
  }

  /**
   * Auto generated setter method
   *
   * @param param UUID
   */
  public void setUUID(com.sap.xi.fndei.UUID param) {
    localUUIDTracker = param != null;

    this.localUUID = param;
  }

  /** field for ReferenceID */
  protected com.sap.xi.fndei.BusinessDocumentMessageID localReferenceID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localReferenceIDTracker = false;

  public boolean isReferenceIDSpecified() {
    return localReferenceIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessDocumentMessageID
   */
  public com.sap.xi.fndei.BusinessDocumentMessageID getReferenceID() {
    return localReferenceID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ReferenceID
   */
  public void setReferenceID(com.sap.xi.fndei.BusinessDocumentMessageID param) {
    localReferenceIDTracker = param != null;

    this.localReferenceID = param;
  }

  /** field for ReferenceUUID */
  protected com.sap.xi.fndei.UUID localReferenceUUID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localReferenceUUIDTracker = false;

  public boolean isReferenceUUIDSpecified() {
    return localReferenceUUIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.UUID
   */
  public com.sap.xi.fndei.UUID getReferenceUUID() {
    return localReferenceUUID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ReferenceUUID
   */
  public void setReferenceUUID(com.sap.xi.fndei.UUID param) {
    localReferenceUUIDTracker = param != null;

    this.localReferenceUUID = param;
  }

  /** field for CreationDateTime */
  protected com.sap.xi.fndei.GLOBAL_DateTime localCreationDateTime;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.GLOBAL_DateTime
   */
  public com.sap.xi.fndei.GLOBAL_DateTime getCreationDateTime() {
    return localCreationDateTime;
  }

  /**
   * Auto generated setter method
   *
   * @param param CreationDateTime
   */
  public void setCreationDateTime(com.sap.xi.fndei.GLOBAL_DateTime param) {

    this.localCreationDateTime = param;
  }

  /** field for TestDataIndicator */
  protected com.sap.xi.fndei.Indicator localTestDataIndicator;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localTestDataIndicatorTracker = false;

  public boolean isTestDataIndicatorSpecified() {
    return localTestDataIndicatorTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Indicator
   */
  public com.sap.xi.fndei.Indicator getTestDataIndicator() {
    return localTestDataIndicator;
  }

  /**
   * Auto generated setter method
   *
   * @param param TestDataIndicator
   */
  public void setTestDataIndicator(com.sap.xi.fndei.Indicator param) {
    localTestDataIndicatorTracker = param != null;

    this.localTestDataIndicator = param;
  }

  /** field for ReconciliationIndicator */
  protected com.sap.xi.fndei.Indicator localReconciliationIndicator;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localReconciliationIndicatorTracker = false;

  public boolean isReconciliationIndicatorSpecified() {
    return localReconciliationIndicatorTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Indicator
   */
  public com.sap.xi.fndei.Indicator getReconciliationIndicator() {
    return localReconciliationIndicator;
  }

  /**
   * Auto generated setter method
   *
   * @param param ReconciliationIndicator
   */
  public void setReconciliationIndicator(com.sap.xi.fndei.Indicator param) {
    localReconciliationIndicatorTracker = param != null;

    this.localReconciliationIndicator = param;
  }

  /** field for SenderBusinessSystemID */
  protected com.sap.xi.fndei.BusinessSystemID localSenderBusinessSystemID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localSenderBusinessSystemIDTracker = false;

  public boolean isSenderBusinessSystemIDSpecified() {
    return localSenderBusinessSystemIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessSystemID
   */
  public com.sap.xi.fndei.BusinessSystemID getSenderBusinessSystemID() {
    return localSenderBusinessSystemID;
  }

  /**
   * Auto generated setter method
   *
   * @param param SenderBusinessSystemID
   */
  public void setSenderBusinessSystemID(com.sap.xi.fndei.BusinessSystemID param) {
    localSenderBusinessSystemIDTracker = param != null;

    this.localSenderBusinessSystemID = param;
  }

  /** field for RecipientBusinessSystemID */
  protected com.sap.xi.fndei.BusinessSystemID localRecipientBusinessSystemID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localRecipientBusinessSystemIDTracker = false;

  public boolean isRecipientBusinessSystemIDSpecified() {
    return localRecipientBusinessSystemIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessSystemID
   */
  public com.sap.xi.fndei.BusinessSystemID getRecipientBusinessSystemID() {
    return localRecipientBusinessSystemID;
  }

  /**
   * Auto generated setter method
   *
   * @param param RecipientBusinessSystemID
   */
  public void setRecipientBusinessSystemID(com.sap.xi.fndei.BusinessSystemID param) {
    localRecipientBusinessSystemIDTracker = param != null;

    this.localRecipientBusinessSystemID = param;
  }

  /** field for SenderParty */
  protected com.sap.xi.fndei.BusinessDocumentMessageHeaderParty localSenderParty;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localSenderPartyTracker = false;

  public boolean isSenderPartySpecified() {
    return localSenderPartyTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessDocumentMessageHeaderParty
   */
  public com.sap.xi.fndei.BusinessDocumentMessageHeaderParty getSenderParty() {
    return localSenderParty;
  }

  /**
   * Auto generated setter method
   *
   * @param param SenderParty
   */
  public void setSenderParty(com.sap.xi.fndei.BusinessDocumentMessageHeaderParty param) {
    localSenderPartyTracker = param != null;

    this.localSenderParty = param;
  }

  /** field for RecipientParty This was an Array! */
  protected com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[] localRecipientParty;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localRecipientPartyTracker = false;

  public boolean isRecipientPartySpecified() {
    return localRecipientPartyTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[]
   */
  public com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[] getRecipientParty() {
    return localRecipientParty;
  }

  /** validate the array for RecipientParty */
  protected void validateRecipientParty(
      com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param RecipientParty
   */
  public void setRecipientParty(com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[] param) {

    validateRecipientParty(param);

    localRecipientPartyTracker = param != null;

    this.localRecipientParty = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.BusinessDocumentMessageHeaderParty
   */
  public void addRecipientParty(com.sap.xi.fndei.BusinessDocumentMessageHeaderParty param) {
    if (localRecipientParty == null) {
      localRecipientParty = new com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[] {};
    }

    // update the setting tracker
    localRecipientPartyTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localRecipientParty);
    list.add(param);
    this.localRecipientParty =
        (com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[])
            list.toArray(new com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[list.size()]);
  }

  /** field for BusinessScope This was an Array! */
  protected com.sap.xi.fndei.BusinessScope[] localBusinessScope;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localBusinessScopeTracker = false;

  public boolean isBusinessScopeSpecified() {
    return localBusinessScopeTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessScope[]
   */
  public com.sap.xi.fndei.BusinessScope[] getBusinessScope() {
    return localBusinessScope;
  }

  /** validate the array for BusinessScope */
  protected void validateBusinessScope(com.sap.xi.fndei.BusinessScope[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param BusinessScope
   */
  public void setBusinessScope(com.sap.xi.fndei.BusinessScope[] param) {

    validateBusinessScope(param);

    localBusinessScopeTracker = param != null;

    this.localBusinessScope = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.BusinessScope
   */
  public void addBusinessScope(com.sap.xi.fndei.BusinessScope param) {
    if (localBusinessScope == null) {
      localBusinessScope = new com.sap.xi.fndei.BusinessScope[] {};
    }

    // update the setting tracker
    localBusinessScopeTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localBusinessScope);
    list.add(param);
    this.localBusinessScope =
        (com.sap.xi.fndei.BusinessScope[])
            list.toArray(new com.sap.xi.fndei.BusinessScope[list.size()]);
  }

  /**
   * @param parentQName
   * @param factory
   * @return org.apache.axiom.om.OMElement
   */
  public org.apache.axiom.om.OMElement getOMElement(
      final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
      throws org.apache.axis2.databinding.ADBException {

    return factory.createOMElement(
        new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
  }

  public void serialize(
      final javax.xml.namespace.QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
    serialize(parentQName, xmlWriter, false);
  }

  public void serialize(
      final javax.xml.namespace.QName parentQName,
      javax.xml.stream.XMLStreamWriter xmlWriter,
      boolean serializeType)
      throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

    java.lang.String prefix = null;
    java.lang.String namespace = null;

    prefix = parentQName.getPrefix();
    namespace = parentQName.getNamespaceURI();
    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);

    if (serializeType) {

      java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sap.com/xi/FNDEI");
      if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
        writeAttribute(
            "xsi",
            "http://www.w3.org/2001/XMLSchema-instance",
            "type",
            namespacePrefix + ":BusinessDocumentMessageHeader",
            xmlWriter);
      } else {
        writeAttribute(
            "xsi",
            "http://www.w3.org/2001/XMLSchema-instance",
            "type",
            "BusinessDocumentMessageHeader",
            xmlWriter);
      }
    }
    if (localIDTracker) {
      if (localID == null) {
        throw new org.apache.axis2.databinding.ADBException("ID cannot be null!!");
      }
      localID.serialize(new javax.xml.namespace.QName("", "ID"), xmlWriter);
    }
    if (localUUIDTracker) {
      if (localUUID == null) {
        throw new org.apache.axis2.databinding.ADBException("UUID cannot be null!!");
      }
      localUUID.serialize(new javax.xml.namespace.QName("", "UUID"), xmlWriter);
    }
    if (localReferenceIDTracker) {
      if (localReferenceID == null) {
        throw new org.apache.axis2.databinding.ADBException("ReferenceID cannot be null!!");
      }
      localReferenceID.serialize(new javax.xml.namespace.QName("", "ReferenceID"), xmlWriter);
    }
    if (localReferenceUUIDTracker) {
      if (localReferenceUUID == null) {
        throw new org.apache.axis2.databinding.ADBException("ReferenceUUID cannot be null!!");
      }
      localReferenceUUID.serialize(new javax.xml.namespace.QName("", "ReferenceUUID"), xmlWriter);
    }
    if (localCreationDateTime == null) {
      throw new org.apache.axis2.databinding.ADBException("CreationDateTime cannot be null!!");
    }
    localCreationDateTime.serialize(
        new javax.xml.namespace.QName("", "CreationDateTime"), xmlWriter);
    if (localTestDataIndicatorTracker) {
      if (localTestDataIndicator == null) {
        throw new org.apache.axis2.databinding.ADBException("TestDataIndicator cannot be null!!");
      }
      localTestDataIndicator.serialize(
          new javax.xml.namespace.QName("", "TestDataIndicator"), xmlWriter);
    }
    if (localReconciliationIndicatorTracker) {
      if (localReconciliationIndicator == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "ReconciliationIndicator cannot be null!!");
      }
      localReconciliationIndicator.serialize(
          new javax.xml.namespace.QName("", "ReconciliationIndicator"), xmlWriter);
    }
    if (localSenderBusinessSystemIDTracker) {
      if (localSenderBusinessSystemID == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "SenderBusinessSystemID cannot be null!!");
      }
      localSenderBusinessSystemID.serialize(
          new javax.xml.namespace.QName("", "SenderBusinessSystemID"), xmlWriter);
    }
    if (localRecipientBusinessSystemIDTracker) {
      if (localRecipientBusinessSystemID == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "RecipientBusinessSystemID cannot be null!!");
      }
      localRecipientBusinessSystemID.serialize(
          new javax.xml.namespace.QName("", "RecipientBusinessSystemID"), xmlWriter);
    }
    if (localSenderPartyTracker) {
      if (localSenderParty == null) {
        throw new org.apache.axis2.databinding.ADBException("SenderParty cannot be null!!");
      }
      localSenderParty.serialize(new javax.xml.namespace.QName("", "SenderParty"), xmlWriter);
    }
    if (localRecipientPartyTracker) {
      if (localRecipientParty != null) {
        for (int i = 0; i < localRecipientParty.length; i++) {
          if (localRecipientParty[i] != null) {
            localRecipientParty[i].serialize(
                new javax.xml.namespace.QName("", "RecipientParty"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("RecipientParty cannot be null!!");
      }
    }
    if (localBusinessScopeTracker) {
      if (localBusinessScope != null) {
        for (int i = 0; i < localBusinessScope.length; i++) {
          if (localBusinessScope[i] != null) {
            localBusinessScope[i].serialize(
                new javax.xml.namespace.QName("", "BusinessScope"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("BusinessScope cannot be null!!");
      }
    }
    xmlWriter.writeEndElement();
  }

  private static java.lang.String generatePrefix(java.lang.String namespace) {
    if (namespace.equals("http://sap.com/xi/FNDEI")) {
      return "ns1";
    }
    return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
  }

  /** Utility method to write an element start tag. */
  private void writeStartElement(
      java.lang.String prefix,
      java.lang.String namespace,
      java.lang.String localPart,
      javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
    if (writerPrefix != null) {
      xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
    } else {
      if (namespace.length() == 0) {
        prefix = "";
      } else if (prefix == null) {
        prefix = generatePrefix(namespace);
      }

      xmlWriter.writeStartElement(prefix, localPart, namespace);
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
    }
  }

  /** Util method to write an attribute with the ns prefix */
  private void writeAttribute(
      java.lang.String prefix,
      java.lang.String namespace,
      java.lang.String attName,
      java.lang.String attValue,
      javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
    if (writerPrefix != null) {
      xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
    } else {
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
      xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
    }
  }

  /** Util method to write an attribute without the ns prefix */
  private void writeAttribute(
      java.lang.String namespace,
      java.lang.String attName,
      java.lang.String attValue,
      javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    if (namespace.equals("")) {
      xmlWriter.writeAttribute(attName, attValue);
    } else {
      xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
    }
  }

  /** Util method to write an attribute without the ns prefix */
  private void writeQNameAttribute(
      java.lang.String namespace,
      java.lang.String attName,
      javax.xml.namespace.QName qname,
      javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {

    java.lang.String attributeNamespace = qname.getNamespaceURI();
    java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
    if (attributePrefix == null) {
      attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
    }
    java.lang.String attributeValue;
    if (attributePrefix.trim().length() > 0) {
      attributeValue = attributePrefix + ":" + qname.getLocalPart();
    } else {
      attributeValue = qname.getLocalPart();
    }

    if (namespace.equals("")) {
      xmlWriter.writeAttribute(attName, attributeValue);
    } else {
      registerPrefix(xmlWriter, namespace);
      xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
    }
  }
  /** method to handle Qnames */
  private void writeQName(
      javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    java.lang.String namespaceURI = qname.getNamespaceURI();
    if (namespaceURI != null) {
      java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
      if (prefix == null) {
        prefix = generatePrefix(namespaceURI);
        xmlWriter.writeNamespace(prefix, namespaceURI);
        xmlWriter.setPrefix(prefix, namespaceURI);
      }

      if (prefix.trim().length() > 0) {
        xmlWriter.writeCharacters(
            prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      } else {
        // i.e this is the default namespace
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }

    } else {
      xmlWriter.writeCharacters(
          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
    }
  }

  private void writeQNames(
      javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {

    if (qnames != null) {
      // we have to store this data until last moment since it is not possible to write any
      // namespace data after writing the charactor data
      java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
      java.lang.String namespaceURI = null;
      java.lang.String prefix = null;

      for (int i = 0; i < qnames.length; i++) {
        if (i > 0) {
          stringToWrite.append(" ");
        }
        namespaceURI = qnames[i].getNamespaceURI();
        if (namespaceURI != null) {
          prefix = xmlWriter.getPrefix(namespaceURI);
          if ((prefix == null) || (prefix.length() == 0)) {
            prefix = generatePrefix(namespaceURI);
            xmlWriter.writeNamespace(prefix, namespaceURI);
            xmlWriter.setPrefix(prefix, namespaceURI);
          }

          if (prefix.trim().length() > 0) {
            stringToWrite
                .append(prefix)
                .append(":")
                .append(
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          } else {
            stringToWrite.append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        } else {
          stringToWrite.append(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
        }
      }
      xmlWriter.writeCharacters(stringToWrite.toString());
    }
  }

  /** Register a namespace prefix */
  private java.lang.String registerPrefix(
      javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
      throws javax.xml.stream.XMLStreamException {
    java.lang.String prefix = xmlWriter.getPrefix(namespace);
    if (prefix == null) {
      prefix = generatePrefix(namespace);
      javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
      while (true) {
        java.lang.String uri = nsContext.getNamespaceURI(prefix);
        if (uri == null || uri.length() == 0) {
          break;
        }
        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
      }
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
    }
    return prefix;
  }

  /** Factory class that keeps the parse method */
  public static class Factory {
    private static org.apache.commons.logging.Log log =
        org.apache.commons.logging.LogFactory.getLog(Factory.class);

    /**
     * static method to create the object Precondition: If this object is an element, the current or
     * next start element starts this object and any intervening reader events are ignorable If this
     * object is not an element, it is a complex type and the reader is at the event just after the
     * outer start element Postcondition: If this object is an element, the reader is positioned at
     * its end element If this object is a complex type, the reader is positioned at the end element
     * of its outer element
     */
    public static BusinessDocumentMessageHeader parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      BusinessDocumentMessageHeader object = new BusinessDocumentMessageHeader();

      int event;
      javax.xml.namespace.QName currentQName = null;
      java.lang.String nillableValue = null;
      java.lang.String prefix = "";
      java.lang.String namespaceuri = "";
      try {

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        currentQName = reader.getName();

        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          java.lang.String fullTypeName =
              reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            java.lang.String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1) {
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
            }
            nsPrefix = nsPrefix == null ? "" : nsPrefix;

            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

            if (!"BusinessDocumentMessageHeader".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (BusinessDocumentMessageHeader)
                  com.sap.xi.fndei.ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        reader.next();

        java.util.ArrayList list11 = new java.util.ArrayList();

        java.util.ArrayList list12 = new java.util.ArrayList();

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ID").equals(reader.getName())) {

          object.setID(com.sap.xi.fndei.BusinessDocumentMessageID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "UUID").equals(reader.getName())) {

          object.setUUID(com.sap.xi.fndei.UUID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ReferenceID").equals(reader.getName())) {

          object.setReferenceID(com.sap.xi.fndei.BusinessDocumentMessageID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ReferenceUUID").equals(reader.getName())) {

          object.setReferenceUUID(com.sap.xi.fndei.UUID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "CreationDateTime").equals(reader.getName())) {

          object.setCreationDateTime(com.sap.xi.fndei.GLOBAL_DateTime.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "TestDataIndicator").equals(reader.getName())) {

          object.setTestDataIndicator(com.sap.xi.fndei.Indicator.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ReconciliationIndicator")
                .equals(reader.getName())) {

          object.setReconciliationIndicator(com.sap.xi.fndei.Indicator.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "SenderBusinessSystemID")
                .equals(reader.getName())) {

          object.setSenderBusinessSystemID(com.sap.xi.fndei.BusinessSystemID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "RecipientBusinessSystemID")
                .equals(reader.getName())) {

          object.setRecipientBusinessSystemID(
              com.sap.xi.fndei.BusinessSystemID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "SenderParty").equals(reader.getName())) {

          object.setSenderParty(
              com.sap.xi.fndei.BusinessDocumentMessageHeaderParty.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "RecipientParty").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list11.add(com.sap.xi.fndei.BusinessDocumentMessageHeaderParty.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone11 = false;
          while (!loopDone11) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone11 = true;
            } else {
              if (new javax.xml.namespace.QName("", "RecipientParty").equals(reader.getName())) {
                list11.add(
                    com.sap.xi.fndei.BusinessDocumentMessageHeaderParty.Factory.parse(reader));

              } else {
                loopDone11 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setRecipientParty(
              (com.sap.xi.fndei.BusinessDocumentMessageHeaderParty[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.BusinessDocumentMessageHeaderParty.class, list11));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "BusinessScope").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list12.add(com.sap.xi.fndei.BusinessScope.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone12 = false;
          while (!loopDone12) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone12 = true;
            } else {
              if (new javax.xml.namespace.QName("", "BusinessScope").equals(reader.getName())) {
                list12.add(com.sap.xi.fndei.BusinessScope.Factory.parse(reader));

              } else {
                loopDone12 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setBusinessScope(
              (com.sap.xi.fndei.BusinessScope[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.BusinessScope.class, list12));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement())
          // 2 - A start element we are not expecting indicates a trailing invalid property

          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());

      } catch (javax.xml.stream.XMLStreamException e) {
        throw new java.lang.Exception(e);
      }

      return object;
    }
  } // end of factory class
}
