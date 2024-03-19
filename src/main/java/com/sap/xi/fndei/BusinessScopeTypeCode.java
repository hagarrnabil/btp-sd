/**
 * BusinessScopeTypeCode.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** BusinessScopeTypeCode bean class */
@SuppressWarnings({"unchecked", "unused"})
public class BusinessScopeTypeCode extends BusinessScopeTypeCodeContent
    implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = BusinessScopeTypeCode
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for BusinessScopeTypeCodeContent */

  /**
   * Auto generated getter method
   *
   * @return org.apache.axis2.databinding.types.Token
   */
  public org.apache.axis2.databinding.types.Token getBusinessScopeTypeCodeContent() {
    return localBusinessScopeTypeCodeContent;
  }

  /**
   * Auto generated setter method
   *
   * @param param BusinessScopeTypeCodeContent
   */
  public void setBusinessScopeTypeCodeContent(org.apache.axis2.databinding.types.Token param) {

    if ((1 <= java.lang.String.valueOf(param).length())
        && (java.lang.String.valueOf(param).length() <= 4)) {
      this.localBusinessScopeTypeCodeContent = param;
    } else {
      throw new java.lang.RuntimeException("Input values do not follow defined XSD restrictions");
    }
  }

  public java.lang.String toString() {

    return localBusinessScopeTypeCodeContent.toString();
  }

  /** field for ListID This was an Attribute! */
  protected ListID_type0 localListID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.ListID_type0
   */
  public ListID_type0 getListID() {
    return localListID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ListID
   */
  public void setListID(ListID_type0 param) {

    this.localListID = param;
  }

  /** field for ListVersionID This was an Attribute! */
  protected ListVersionID_type0 localListVersionID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.ListVersionID_type0
   */
  public ListVersionID_type0 getListVersionID() {
    return localListVersionID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ListVersionID
   */
  public void setListVersionID(ListVersionID_type0 param) {

    this.localListVersionID = param;
  }

  /** field for ListAgencyID This was an Attribute! */
  protected ListAgencyID_type0 localListAgencyID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.ListAgencyID_type0
   */
  public ListAgencyID_type0 getListAgencyID() {
    return localListAgencyID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ListAgencyID
   */
  public void setListAgencyID(ListAgencyID_type0 param) {

    this.localListAgencyID = param;
  }

  /** field for ListAgencySchemeID This was an Attribute! */
  protected ListAgencySchemeID_type0 localListAgencySchemeID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.ListAgencySchemeID_type0
   */
  public ListAgencySchemeID_type0 getListAgencySchemeID() {
    return localListAgencySchemeID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ListAgencySchemeID
   */
  public void setListAgencySchemeID(ListAgencySchemeID_type0 param) {

    this.localListAgencySchemeID = param;
  }

  /** field for ListAgencySchemeAgencyID This was an Attribute! */
  protected AgencyIdentificationCode localListAgencySchemeAgencyID;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.AgencyIdentificationCode
   */
  public AgencyIdentificationCode getListAgencySchemeAgencyID() {
    return localListAgencySchemeAgencyID;
  }

  /**
   * Auto generated setter method
   *
   * @param param ListAgencySchemeAgencyID
   */
  public void setListAgencySchemeAgencyID(AgencyIdentificationCode param) {

    this.localListAgencySchemeAgencyID = param;
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
          namespacePrefix + ":BusinessScopeTypeCode",
          xmlWriter);
    } else {
      writeAttribute(
          "xsi",
          "http://www.w3.org/2001/XMLSchema-instance",
          "type",
          "BusinessScopeTypeCode",
          xmlWriter);
    }

    if (localListID != null) {
      writeAttribute("", "listID", localListID.toString(), xmlWriter);
    }

    if (localListVersionID != null) {
      writeAttribute("", "listVersionID", localListVersionID.toString(), xmlWriter);
    }

    if (localListAgencyID != null) {
      writeAttribute("", "listAgencyID", localListAgencyID.toString(), xmlWriter);
    }

    if (localListAgencySchemeID != null) {
      writeAttribute("", "listAgencySchemeID", localListAgencySchemeID.toString(), xmlWriter);
    }

    if (localListAgencySchemeAgencyID != null) {
      writeAttribute(
          "", "listAgencySchemeAgencyID", localListAgencySchemeAgencyID.toString(), xmlWriter);
    }

    if (localBusinessScopeTypeCodeContent == null) {
      // write the nil attribute

      throw new org.apache.axis2.databinding.ADBException(
          "BusinessScopeTypeCode.Content cannot be null!!");

    } else {

      xmlWriter.writeCharacters(
          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
              localBusinessScopeTypeCodeContent));
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

    public static BusinessScopeTypeCode fromString(
        java.lang.String value, java.lang.String namespaceURI) {
      BusinessScopeTypeCode returnValue = new BusinessScopeTypeCode();

      returnValue.setBusinessScopeTypeCodeContent(
          org.apache.axis2.databinding.utils.ConverterUtil.convertToToken(value));

      return returnValue;
    }

    public static BusinessScopeTypeCode fromString(
        javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {
      if (content.indexOf(":") > -1) {
        java.lang.String prefix = content.substring(0, content.indexOf(":"));
        java.lang.String namespaceUri =
            xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
        return BusinessScopeTypeCode.Factory.fromString(content, namespaceUri);
      } else {
        return BusinessScopeTypeCode.Factory.fromString(content, "");
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
    public static BusinessScopeTypeCode parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      BusinessScopeTypeCode object = new BusinessScopeTypeCode();

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

            if (!"BusinessScopeTypeCode".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (BusinessScopeTypeCode)
                  ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        // handle attribute "listID"
        java.lang.String tempAttribListID = reader.getAttributeValue(null, "listID");

        if (tempAttribListID != null) {
          java.lang.String content = tempAttribListID;

          object.setListID(
              ListID_type0.Factory.fromString(reader, tempAttribListID));

        } else {

        }
        handledAttributes.add("listID");

        // handle attribute "listVersionID"
        java.lang.String tempAttribListVersionID = reader.getAttributeValue(null, "listVersionID");

        if (tempAttribListVersionID != null) {
          java.lang.String content = tempAttribListVersionID;

          object.setListVersionID(
              ListVersionID_type0.Factory.fromString(
                  reader, tempAttribListVersionID));

        } else {

        }
        handledAttributes.add("listVersionID");

        // handle attribute "listAgencyID"
        java.lang.String tempAttribListAgencyID = reader.getAttributeValue(null, "listAgencyID");

        if (tempAttribListAgencyID != null) {
          java.lang.String content = tempAttribListAgencyID;

          object.setListAgencyID(
              ListAgencyID_type0.Factory.fromString(
                  reader, tempAttribListAgencyID));

        } else {

        }
        handledAttributes.add("listAgencyID");

        // handle attribute "listAgencySchemeID"
        java.lang.String tempAttribListAgencySchemeID =
            reader.getAttributeValue(null, "listAgencySchemeID");

        if (tempAttribListAgencySchemeID != null) {
          java.lang.String content = tempAttribListAgencySchemeID;

          object.setListAgencySchemeID(
              ListAgencySchemeID_type0.Factory.fromString(
                  reader, tempAttribListAgencySchemeID));

        } else {

        }
        handledAttributes.add("listAgencySchemeID");

        // handle attribute "listAgencySchemeAgencyID"
        java.lang.String tempAttribListAgencySchemeAgencyID =
            reader.getAttributeValue(null, "listAgencySchemeAgencyID");

        if (tempAttribListAgencySchemeAgencyID != null) {
          java.lang.String content = tempAttribListAgencySchemeAgencyID;

          object.setListAgencySchemeAgencyID(
              AgencyIdentificationCode.Factory.fromString(
                  reader, tempAttribListAgencySchemeAgencyID));

        } else {

        }
        handledAttributes.add("listAgencySchemeAgencyID");

        while (!reader.isEndElement()) {
          if (reader.isStartElement() || reader.hasText()) {

            if (reader.isStartElement() || reader.hasText()) {

              nillableValue =
                  reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
              if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
                throw new org.apache.axis2.databinding.ADBException(
                    "The element: " + "BusinessScopeTypeCode.Content" + "  cannot be null");
              }

              java.lang.String content = reader.getElementText();

              object.setBusinessScopeTypeCodeContent(
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
