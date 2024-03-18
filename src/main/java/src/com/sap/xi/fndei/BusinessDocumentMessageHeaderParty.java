/**
 * BusinessDocumentMessageHeaderParty.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** BusinessDocumentMessageHeaderParty bean class */
@SuppressWarnings({"unchecked", "unused"})
public class BusinessDocumentMessageHeaderParty implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = BusinessDocumentMessageHeaderParty
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for InternalID */
  protected com.sap.xi.fndei.PartyInternalID localInternalID;

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
   * @return com.sap.xi.fndei.PartyInternalID
   */
  public com.sap.xi.fndei.PartyInternalID getInternalID() {
    return localInternalID;
  }

  /**
   * Auto generated setter method
   *
   * @param param InternalID
   */
  public void setInternalID(com.sap.xi.fndei.PartyInternalID param) {
    localInternalIDTracker = param != null;

    this.localInternalID = param;
  }

  /** field for StandardID This was an Array! */
  protected com.sap.xi.fndei.PartyStandardID[] localStandardID;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localStandardIDTracker = false;

  public boolean isStandardIDSpecified() {
    return localStandardIDTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.PartyStandardID[]
   */
  public com.sap.xi.fndei.PartyStandardID[] getStandardID() {
    return localStandardID;
  }

  /** validate the array for StandardID */
  protected void validateStandardID(com.sap.xi.fndei.PartyStandardID[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param StandardID
   */
  public void setStandardID(com.sap.xi.fndei.PartyStandardID[] param) {

    validateStandardID(param);

    localStandardIDTracker = param != null;

    this.localStandardID = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.PartyStandardID
   */
  public void addStandardID(com.sap.xi.fndei.PartyStandardID param) {
    if (localStandardID == null) {
      localStandardID = new com.sap.xi.fndei.PartyStandardID[] {};
    }

    // update the setting tracker
    localStandardIDTracker = true;

    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localStandardID);
    list.add(param);
    this.localStandardID =
        (com.sap.xi.fndei.PartyStandardID[])
            list.toArray(new com.sap.xi.fndei.PartyStandardID[list.size()]);
  }

  /** field for ContactPerson */
  protected com.sap.xi.fndei.BusinessDocumentMessageHeaderPartyContactPerson localContactPerson;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localContactPersonTracker = false;

  public boolean isContactPersonSpecified() {
    return localContactPersonTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.BusinessDocumentMessageHeaderPartyContactPerson
   */
  public com.sap.xi.fndei.BusinessDocumentMessageHeaderPartyContactPerson getContactPerson() {
    return localContactPerson;
  }

  /**
   * Auto generated setter method
   *
   * @param param ContactPerson
   */
  public void setContactPerson(
      com.sap.xi.fndei.BusinessDocumentMessageHeaderPartyContactPerson param) {
    localContactPersonTracker = param != null;

    this.localContactPerson = param;
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
            namespacePrefix + ":BusinessDocumentMessageHeaderParty",
            xmlWriter);
      } else {
        writeAttribute(
            "xsi",
            "http://www.w3.org/2001/XMLSchema-instance",
            "type",
            "BusinessDocumentMessageHeaderParty",
            xmlWriter);
      }
    }
    if (localInternalIDTracker) {
      if (localInternalID == null) {
        throw new org.apache.axis2.databinding.ADBException("InternalID cannot be null!!");
      }
      localInternalID.serialize(new javax.xml.namespace.QName("", "InternalID"), xmlWriter);
    }
    if (localStandardIDTracker) {
      if (localStandardID != null) {
        for (int i = 0; i < localStandardID.length; i++) {
          if (localStandardID[i] != null) {
            localStandardID[i].serialize(
                new javax.xml.namespace.QName("", "StandardID"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("StandardID cannot be null!!");
      }
    }
    if (localContactPersonTracker) {
      if (localContactPerson == null) {
        throw new org.apache.axis2.databinding.ADBException("ContactPerson cannot be null!!");
      }
      localContactPerson.serialize(new javax.xml.namespace.QName("", "ContactPerson"), xmlWriter);
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
    public static BusinessDocumentMessageHeaderParty parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      BusinessDocumentMessageHeaderParty object = new BusinessDocumentMessageHeaderParty();

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

            if (!"BusinessDocumentMessageHeaderParty".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (BusinessDocumentMessageHeaderParty)
                  com.sap.xi.fndei.ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        reader.next();

        java.util.ArrayList list2 = new java.util.ArrayList();

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "InternalID").equals(reader.getName())) {

          object.setInternalID(com.sap.xi.fndei.PartyInternalID.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "StandardID").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list2.add(com.sap.xi.fndei.PartyStandardID.Factory.parse(reader));

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
              if (new javax.xml.namespace.QName("", "StandardID").equals(reader.getName())) {
                list2.add(com.sap.xi.fndei.PartyStandardID.Factory.parse(reader));

              } else {
                loopDone2 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setStandardID(
              (com.sap.xi.fndei.PartyStandardID[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.PartyStandardID.class, list2));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ContactPerson").equals(reader.getName())) {

          object.setContactPerson(
              com.sap.xi.fndei.BusinessDocumentMessageHeaderPartyContactPerson.Factory.parse(
                  reader));

          reader.next();

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
