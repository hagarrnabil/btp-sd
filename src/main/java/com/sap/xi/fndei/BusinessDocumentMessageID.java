/**
 * BusinessDocumentMessageID.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** BusinessDocumentMessageID bean class */
@SuppressWarnings({"unchecked", "unused"})
public class BusinessDocumentMessageID extends BusinessDocumentMessageIDContent
    implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = BusinessDocumentMessageID
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for BusinessDocumentMessageIDContent */

  /**
   * Auto generated getter method
   *
   * @return org.apache.axis2.databinding.types.Token
   */
  public org.apache.axis2.databinding.types.Token getBusinessDocumentMessageIDContent() {
    return localBusinessDocumentMessageIDContent;
  }

  /**
   * Auto generated setter method
   *
   * @param param BusinessDocumentMessageIDContent
   */
  public void setBusinessDocumentMessageIDContent(org.apache.axis2.databinding.types.Token param) {

    if ((1 <= java.lang.String.valueOf(param).length())
        && (java.lang.String.valueOf(param).length() <= 35)) {
      this.localBusinessDocumentMessageIDContent = param;
    } else {
      throw new java.lang.RuntimeException("Input values do not follow defined XSD restrictions");
    }
  }

  public java.lang.String toString() {

    return localBusinessDocumentMessageIDContent.toString();
  }

  /** field for SchemeID This was an Attribute! */
  protected SchemeID_type0 localSchemeID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.SchemeID_type0
   */
  public SchemeID_type0 getSchemeID() {
    return localSchemeID;
  }

  /**
   * Auto generated setter method
   *
   * @param param SchemeID
   */
  public void setSchemeID(SchemeID_type0 param) {

    this.localSchemeID = param;
  }

  /** field for SchemeAgencyID This was an Attribute! */
  protected SchemeAgencyID_type0 localSchemeAgencyID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.SchemeAgencyID_type0
   */
  public SchemeAgencyID_type0 getSchemeAgencyID() {
    return localSchemeAgencyID;
  }

  /**
   * Auto generated setter method
   *
   * @param param SchemeAgencyID
   */
  public void setSchemeAgencyID(SchemeAgencyID_type0 param) {

    this.localSchemeAgencyID = param;
  }

  /** field for SchemeAgencySchemeAgencyID This was an Attribute! */
  protected AgencyIdentificationCode localSchemeAgencySchemeAgencyID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.AgencyIdentificationCode
   */
  public AgencyIdentificationCode getSchemeAgencySchemeAgencyID() {
    return localSchemeAgencySchemeAgencyID;
  }

  /**
   * Auto generated setter method
   *
   * @param param SchemeAgencySchemeAgencyID
   */
  public void setSchemeAgencySchemeAgencyID(AgencyIdentificationCode param) {

    this.localSchemeAgencySchemeAgencyID = param;
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

    java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sap.com/xi/FNDEI");
    if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
      writeAttribute(
          "xsi",
          "http://www.w3.org/2001/XMLSchema-instance",
          "type",
          namespacePrefix + ":BusinessDocumentMessageID",
          xmlWriter);
    } else {
      writeAttribute(
          "xsi",
          "http://www.w3.org/2001/XMLSchema-instance",
          "type",
          "BusinessDocumentMessageID",
          xmlWriter);
    }

    if (localSchemeID != null) {
      writeAttribute("", "schemeID", localSchemeID.toString(), xmlWriter);
    }

    if (localSchemeAgencyID != null) {
      writeAttribute("", "schemeAgencyID", localSchemeAgencyID.toString(), xmlWriter);
    }

    if (localSchemeAgencySchemeAgencyID != null) {
      writeAttribute(
          "", "schemeAgencySchemeAgencyID", localSchemeAgencySchemeAgencyID.toString(), xmlWriter);
    }

    if (localBusinessDocumentMessageIDContent == null) {
      // write the nil attribute

      throw new org.apache.axis2.databinding.ADBException(
          "BusinessDocumentMessageID.Content cannot be null!!");

    } else {

      xmlWriter.writeCharacters(
          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
              localBusinessDocumentMessageIDContent));
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

    public static BusinessDocumentMessageID fromString(
        java.lang.String value, java.lang.String namespaceURI) {
      BusinessDocumentMessageID returnValue = new BusinessDocumentMessageID();

      returnValue.setBusinessDocumentMessageIDContent(
          org.apache.axis2.databinding.utils.ConverterUtil.convertToToken(value));

      return returnValue;
    }

    public static BusinessDocumentMessageID fromString(
        javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {
      if (content.indexOf(":") > -1) {
        java.lang.String prefix = content.substring(0, content.indexOf(":"));
        java.lang.String namespaceUri =
            xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
        return BusinessDocumentMessageID.Factory.fromString(content, namespaceUri);
      } else {
        return BusinessDocumentMessageID.Factory.fromString(content, "");
      }
    }

    /**
     * static method to create the object Precondition: If this object is an element, the current or
     * next start element starts this object and any intervening reader events are ignorable If this
     * object is not an element, it is a complex type and the reader is at the event just after the
     * outer start element Postcondition: If this object is an element, the reader is positioned at
     * its end element If this object is a complex type, the reader is positioned at the end element
     * of its outer element
     */
    public static BusinessDocumentMessageID parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      BusinessDocumentMessageID object = new BusinessDocumentMessageID();

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

            if (!"BusinessDocumentMessageID".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (BusinessDocumentMessageID)
                  ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        // handle attribute "schemeID"
        java.lang.String tempAttribSchemeID = reader.getAttributeValue(null, "schemeID");

        if (tempAttribSchemeID != null) {
          java.lang.String content = tempAttribSchemeID;

          object.setSchemeID(
              SchemeID_type0.Factory.fromString(reader, tempAttribSchemeID));

        } else {

        }
        handledAttributes.add("schemeID");

        // handle attribute "schemeAgencyID"
        java.lang.String tempAttribSchemeAgencyID =
            reader.getAttributeValue(null, "schemeAgencyID");

        if (tempAttribSchemeAgencyID != null) {
          java.lang.String content = tempAttribSchemeAgencyID;

          object.setSchemeAgencyID(
              SchemeAgencyID_type0.Factory.fromString(
                  reader, tempAttribSchemeAgencyID));

        } else {

        }
        handledAttributes.add("schemeAgencyID");

        // handle attribute "schemeAgencySchemeAgencyID"
        java.lang.String tempAttribSchemeAgencySchemeAgencyID =
            reader.getAttributeValue(null, "schemeAgencySchemeAgencyID");

        if (tempAttribSchemeAgencySchemeAgencyID != null) {
          java.lang.String content = tempAttribSchemeAgencySchemeAgencyID;

          object.setSchemeAgencySchemeAgencyID(
              AgencyIdentificationCode.Factory.fromString(
                  reader, tempAttribSchemeAgencySchemeAgencyID));

        } else {

        }
        handledAttributes.add("schemeAgencySchemeAgencyID");

        while (!reader.isEndElement()) {
          if (reader.isStartElement() || reader.hasText()) {

            if (reader.isStartElement() || reader.hasText()) {

              nillableValue =
                  reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
              if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
                throw new org.apache.axis2.databinding.ADBException(
                    "The element: " + "BusinessDocumentMessageID.Content" + "  cannot be null");
              }

              java.lang.String content = reader.getElementText();

              object.setBusinessDocumentMessageIDContent(
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToToken(content));

            } // End of if for expected property start element
            else {
              // 3 - A start element we are not expecting indicates an invalid parameter was passed

              throw new org.apache.axis2.databinding.ADBException(
                  "Unexpected subelement " + reader.getName());
            }

          } else {
            reader.next();
          }
        } // end of while loop

      } catch (javax.xml.stream.XMLStreamException e) {
        throw new java.lang.Exception(e);
      }

      return object;
    }
  } // end of factory class
}
