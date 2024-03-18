/**
 * BusinessDocumentMessageHeaderPartyContactPerson.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** BusinessDocumentMessageHeaderPartyContactPerson bean class */
@SuppressWarnings({"unchecked", "unused"})
public class BusinessDocumentMessageHeaderPartyContactPerson
    implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = BusinessDocumentMessageHeaderPartyContactPerson
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for InternalID */
  protected com.sap.xi.fndei.ContactPersonInternalID localInternalID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localInternalIDTracker = false;

  public boolean isInternalIDSpecified() {
    return localInternalIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.ContactPersonInternalID
   */
  public com.sap.xi.fndei.ContactPersonInternalID getInternalID() {
    return localInternalID;
  }

  /**
   * Auto generated setter method
   *
   * @param param InternalID
   */
  public void setInternalID(com.sap.xi.fndei.ContactPersonInternalID param) {
    localInternalIDTracker = param != null;

    this.localInternalID = param;
  }

  /** field for OrganisationFormattedName This was an Array! */
  protected com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[] localOrganisationFormattedName;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localOrganisationFormattedNameTracker = false;

  public boolean isOrganisationFormattedNameSpecified() {
    return localOrganisationFormattedNameTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[]
   */
  public com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[] getOrganisationFormattedName() {
    return localOrganisationFormattedName;
  }

  /** validate the array for OrganisationFormattedName */
  protected void validateOrganisationFormattedName(
      com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[] param) {

    if ((param != null) && (param.length > 4)) {
      throw new java.lang.RuntimeException("Input values do not follow defined XSD restrictions");
    }
  }

  /**
   * Auto generated setter method
   *
   * @param param OrganisationFormattedName
   */
  public void setOrganisationFormattedName(
      com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[] param) {

    validateOrganisationFormattedName(param);

    localOrganisationFormattedNameTracker = param != null;

    this.localOrganisationFormattedName = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name
   */
  public void addOrganisationFormattedName(com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name param) {
    if (localOrganisationFormattedName == null) {
      localOrganisationFormattedName = new com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[] {};
    }

    // update the setting tracker
    localOrganisationFormattedNameTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localOrganisationFormattedName);
    list.add(param);
    this.localOrganisationFormattedName =
        (com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[])
            list.toArray(new com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[list.size()]);
  }

  /** field for PersonFormattedName This was an Array! */
  protected com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[] localPersonFormattedName;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localPersonFormattedNameTracker = false;

  public boolean isPersonFormattedNameSpecified() {
    return localPersonFormattedNameTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[]
   */
  public com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[] getPersonFormattedName() {
    return localPersonFormattedName;
  }

  /** validate the array for PersonFormattedName */
  protected void validatePersonFormattedName(
      com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[] param) {

    if ((param != null) && (param.length > 4)) {
      throw new java.lang.RuntimeException("Input values do not follow defined XSD restrictions");
    }
  }

  /**
   * Auto generated setter method
   *
   * @param param PersonFormattedName
   */
  public void setPersonFormattedName(com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[] param) {

    validatePersonFormattedName(param);

    localPersonFormattedNameTracker = param != null;

    this.localPersonFormattedName = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name
   */
  public void addPersonFormattedName(com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name param) {
    if (localPersonFormattedName == null) {
      localPersonFormattedName = new com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[] {};
    }

    // update the setting tracker
    localPersonFormattedNameTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localPersonFormattedName);
    list.add(param);
    this.localPersonFormattedName =
        (com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[])
            list.toArray(new com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[list.size()]);
  }

  /** field for PhoneNumber This was an Array! */
  protected com.sap.xi.fndei.PhoneNumber[] localPhoneNumber;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localPhoneNumberTracker = false;

  public boolean isPhoneNumberSpecified() {
    return localPhoneNumberTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.PhoneNumber[]
   */
  public com.sap.xi.fndei.PhoneNumber[] getPhoneNumber() {
    return localPhoneNumber;
  }

  /** validate the array for PhoneNumber */
  protected void validatePhoneNumber(com.sap.xi.fndei.PhoneNumber[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param PhoneNumber
   */
  public void setPhoneNumber(com.sap.xi.fndei.PhoneNumber[] param) {

    validatePhoneNumber(param);

    localPhoneNumberTracker = param != null;

    this.localPhoneNumber = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.PhoneNumber
   */
  public void addPhoneNumber(com.sap.xi.fndei.PhoneNumber param) {
    if (localPhoneNumber == null) {
      localPhoneNumber = new com.sap.xi.fndei.PhoneNumber[] {};
    }

    // update the setting tracker
    localPhoneNumberTracker = true;

    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localPhoneNumber);
    list.add(param);
    this.localPhoneNumber =
        (com.sap.xi.fndei.PhoneNumber[])
            list.toArray(new com.sap.xi.fndei.PhoneNumber[list.size()]);
  }

  /** field for FaxNumber This was an Array! */
  protected com.sap.xi.fndei.PhoneNumber[] localFaxNumber;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localFaxNumberTracker = false;

  public boolean isFaxNumberSpecified() {
    return localFaxNumberTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.PhoneNumber[]
   */
  public com.sap.xi.fndei.PhoneNumber[] getFaxNumber() {
    return localFaxNumber;
  }

  /** validate the array for FaxNumber */
  protected void validateFaxNumber(com.sap.xi.fndei.PhoneNumber[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param FaxNumber
   */
  public void setFaxNumber(com.sap.xi.fndei.PhoneNumber[] param) {

    validateFaxNumber(param);

    localFaxNumberTracker = param != null;

    this.localFaxNumber = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.PhoneNumber
   */
  public void addFaxNumber(com.sap.xi.fndei.PhoneNumber param) {
    if (localFaxNumber == null) {
      localFaxNumber = new com.sap.xi.fndei.PhoneNumber[] {};
    }

    // update the setting tracker
    localFaxNumberTracker = true;

    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localFaxNumber);
    list.add(param);
    this.localFaxNumber =
        (com.sap.xi.fndei.PhoneNumber[])
            list.toArray(new com.sap.xi.fndei.PhoneNumber[list.size()]);
  }

  /** field for EmailURI This was an Array! */
  protected com.sap.xi.fndei.EmailURI[] localEmailURI;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localEmailURITracker = false;

  public boolean isEmailURISpecified() {
    return localEmailURITracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.EmailURI[]
   */
  public com.sap.xi.fndei.EmailURI[] getEmailURI() {
    return localEmailURI;
  }

  /** validate the array for EmailURI */
  protected void validateEmailURI(com.sap.xi.fndei.EmailURI[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param EmailURI
   */
  public void setEmailURI(com.sap.xi.fndei.EmailURI[] param) {

    validateEmailURI(param);

    localEmailURITracker = param != null;

    this.localEmailURI = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.EmailURI
   */
  public void addEmailURI(com.sap.xi.fndei.EmailURI param) {
    if (localEmailURI == null) {
      localEmailURI = new com.sap.xi.fndei.EmailURI[] {};
    }

    // update the setting tracker
    localEmailURITracker = true;

    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localEmailURI);
    list.add(param);
    this.localEmailURI =
        (com.sap.xi.fndei.EmailURI[]) list.toArray(new com.sap.xi.fndei.EmailURI[list.size()]);
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
            namespacePrefix + ":BusinessDocumentMessageHeaderPartyContactPerson",
            xmlWriter);
      } else {
        writeAttribute(
            "xsi",
            "http://www.w3.org/2001/XMLSchema-instance",
            "type",
            "BusinessDocumentMessageHeaderPartyContactPerson",
            xmlWriter);
      }
    }
    if (localInternalIDTracker) {
      if (localInternalID == null) {
        throw new org.apache.axis2.databinding.ADBException("InternalID cannot be null!!");
      }
      localInternalID.serialize(new javax.xml.namespace.QName("", "InternalID"), xmlWriter);
    }
    if (localOrganisationFormattedNameTracker) {
      if (localOrganisationFormattedName != null) {
        for (int i = 0; i < localOrganisationFormattedName.length; i++) {
          if (localOrganisationFormattedName[i] != null) {
            localOrganisationFormattedName[i].serialize(
                new javax.xml.namespace.QName("", "OrganisationFormattedName"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException(
            "OrganisationFormattedName cannot be null!!");
      }
    }
    if (localPersonFormattedNameTracker) {
      if (localPersonFormattedName != null) {
        for (int i = 0; i < localPersonFormattedName.length; i++) {
          if (localPersonFormattedName[i] != null) {
            localPersonFormattedName[i].serialize(
                new javax.xml.namespace.QName("", "PersonFormattedName"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("PersonFormattedName cannot be null!!");
      }
    }
    if (localPhoneNumberTracker) {
      if (localPhoneNumber != null) {
        for (int i = 0; i < localPhoneNumber.length; i++) {
          if (localPhoneNumber[i] != null) {
            localPhoneNumber[i].serialize(
                new javax.xml.namespace.QName("", "PhoneNumber"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("PhoneNumber cannot be null!!");
      }
    }
    if (localFaxNumberTracker) {
      if (localFaxNumber != null) {
        for (int i = 0; i < localFaxNumber.length; i++) {
          if (localFaxNumber[i] != null) {
            localFaxNumber[i].serialize(new javax.xml.namespace.QName("", "FaxNumber"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("FaxNumber cannot be null!!");
      }
    }
    if (localEmailURITracker) {
      if (localEmailURI != null) {
        for (int i = 0; i < localEmailURI.length; i++) {
          if (localEmailURI[i] != null) {
            localEmailURI[i].serialize(new javax.xml.namespace.QName("", "EmailURI"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("EmailURI cannot be null!!");
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
    public static BusinessDocumentMessageHeaderPartyContactPerson parse(
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
      BusinessDocumentMessageHeaderPartyContactPerson object =
          new BusinessDocumentMessageHeaderPartyContactPerson();

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

            if (!"BusinessDocumentMessageHeaderPartyContactPerson".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (BusinessDocumentMessageHeaderPartyContactPerson)
                  com.sap.xi.fndei.ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        reader.next();

        java.util.ArrayList list2 = new java.util.ArrayList();

        java.util.ArrayList list3 = new java.util.ArrayList();

        java.util.ArrayList list4 = new java.util.ArrayList();

        java.util.ArrayList list5 = new java.util.ArrayList();

        java.util.ArrayList list6 = new java.util.ArrayList();

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "InternalID").equals(reader.getName())) {

          object.setInternalID(com.sap.xi.fndei.ContactPersonInternalID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "OrganisationFormattedName")
                .equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list2.add(com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone2 = false;
          while (!loopDone2) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone2 = true;
            } else {
              if (new javax.xml.namespace.QName("", "OrganisationFormattedName")
                  .equals(reader.getName())) {
                list2.add(com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name.Factory.parse(reader));

              } else {
                loopDone2 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setOrganisationFormattedName(
              (com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.LANGUAGEINDEPENDENT_MEDIUM_Name.class, list2));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "PersonFormattedName").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list3.add(com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone3 = false;
          while (!loopDone3) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone3 = true;
            } else {
              if (new javax.xml.namespace.QName("", "PersonFormattedName")
                  .equals(reader.getName())) {
                list3.add(com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name.Factory.parse(reader));

              } else {
                loopDone3 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setPersonFormattedName(
              (com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.LANGUAGEINDEPENDENT_LONG_Name.class, list3));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "PhoneNumber").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list4.add(com.sap.xi.fndei.PhoneNumber.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone4 = false;
          while (!loopDone4) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone4 = true;
            } else {
              if (new javax.xml.namespace.QName("", "PhoneNumber").equals(reader.getName())) {
                list4.add(com.sap.xi.fndei.PhoneNumber.Factory.parse(reader));

              } else {
                loopDone4 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setPhoneNumber(
              (com.sap.xi.fndei.PhoneNumber[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.PhoneNumber.class, list4));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "FaxNumber").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list5.add(com.sap.xi.fndei.PhoneNumber.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone5 = false;
          while (!loopDone5) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone5 = true;
            } else {
              if (new javax.xml.namespace.QName("", "FaxNumber").equals(reader.getName())) {
                list5.add(com.sap.xi.fndei.PhoneNumber.Factory.parse(reader));

              } else {
                loopDone5 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setFaxNumber(
              (com.sap.xi.fndei.PhoneNumber[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.PhoneNumber.class, list5));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "EmailURI").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list6.add(com.sap.xi.fndei.EmailURI.Factory.parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone6 = false;
          while (!loopDone6) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement()) reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone6 = true;
            } else {
              if (new javax.xml.namespace.QName("", "EmailURI").equals(reader.getName())) {
                list6.add(com.sap.xi.fndei.EmailURI.Factory.parse(reader));

              } else {
                loopDone6 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setEmailURI(
              (com.sap.xi.fndei.EmailURI[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.EmailURI.class, list6));

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
