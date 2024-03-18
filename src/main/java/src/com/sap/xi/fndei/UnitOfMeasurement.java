/**
 * UnitOfMeasurement.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:18 EDT)
 */
package com.sap.xi.fndei;

/** UnitOfMeasurement bean class */
@SuppressWarnings({"unchecked", "unused"})
public class UnitOfMeasurement implements org.apache.axis2.databinding.ADBBean {
  /* This type was generated from the piece of schema that had
  name = UnitOfMeasurement
  Namespace URI = http://sap.com/xi/FNDEI
  Namespace Prefix = ns1
  */

  /** field for Code */
  protected com.sap.xi.fndei.UnitOfMeasurementCode localCode;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.UnitOfMeasurementCode
   */
  public com.sap.xi.fndei.UnitOfMeasurementCode getCode() {
    return localCode;
  }

  /**
   * Auto generated setter method
   *
   * @param param Code
   */
  public void setCode(com.sap.xi.fndei.UnitOfMeasurementCode param) {

    this.localCode = param;
  }

  /** field for ISOCode */
  protected com.sap.xi.fndei.UnitOfMeasurementCode localISOCode;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localISOCodeTracker = false;

  public boolean isISOCodeSpecified() {
    return localISOCodeTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.UnitOfMeasurementCode
   */
  public com.sap.xi.fndei.UnitOfMeasurementCode getISOCode() {
    return localISOCode;
  }

  /**
   * Auto generated setter method
   *
   * @param param ISOCode
   */
  public void setISOCode(com.sap.xi.fndei.UnitOfMeasurementCode param) {
    localISOCodeTracker = param != null;

    this.localISOCode = param;
  }

  /** field for CommercialDescription This was an Array! */
  protected com.sap.xi.fndei.Description[] localCommercialDescription;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localCommercialDescriptionTracker = false;

  public boolean isCommercialDescriptionSpecified() {
    return localCommercialDescriptionTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Description[]
   */
  public com.sap.xi.fndei.Description[] getCommercialDescription() {
    return localCommercialDescription;
  }

  /** validate the array for CommercialDescription */
  protected void validateCommercialDescription(com.sap.xi.fndei.Description[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param CommercialDescription
   */
  public void setCommercialDescription(com.sap.xi.fndei.Description[] param) {

    validateCommercialDescription(param);

    localCommercialDescriptionTracker = param != null;

    this.localCommercialDescription = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.Description
   */
  public void addCommercialDescription(com.sap.xi.fndei.Description param) {
    if (localCommercialDescription == null) {
      localCommercialDescription = new com.sap.xi.fndei.Description[] {};
    }

    // update the setting tracker
    localCommercialDescriptionTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localCommercialDescription);
    list.add(param);
    this.localCommercialDescription =
        (com.sap.xi.fndei.Description[])
            list.toArray(new com.sap.xi.fndei.Description[list.size()]);
  }

  /** field for TechnicalDescription This was an Array! */
  protected com.sap.xi.fndei.Description[] localTechnicalDescription;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localTechnicalDescriptionTracker = false;

  public boolean isTechnicalDescriptionSpecified() {
    return localTechnicalDescriptionTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Description[]
   */
  public com.sap.xi.fndei.Description[] getTechnicalDescription() {
    return localTechnicalDescription;
  }

  /** validate the array for TechnicalDescription */
  protected void validateTechnicalDescription(com.sap.xi.fndei.Description[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param TechnicalDescription
   */
  public void setTechnicalDescription(com.sap.xi.fndei.Description[] param) {

    validateTechnicalDescription(param);

    localTechnicalDescriptionTracker = param != null;

    this.localTechnicalDescription = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.Description
   */
  public void addTechnicalDescription(com.sap.xi.fndei.Description param) {
    if (localTechnicalDescription == null) {
      localTechnicalDescription = new com.sap.xi.fndei.Description[] {};
    }

    // update the setting tracker
    localTechnicalDescriptionTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localTechnicalDescription);
    list.add(param);
    this.localTechnicalDescription =
        (com.sap.xi.fndei.Description[])
            list.toArray(new com.sap.xi.fndei.Description[list.size()]);
  }

  /** field for LongDescription This was an Array! */
  protected com.sap.xi.fndei.Description[] localLongDescription;

  /*  This tracker boolean wil be used to detect whether the user called the set method
   *   for this attribute. It will be used to determine whether to include this field
   *   in the serialized XML
   */
  protected boolean localLongDescriptionTracker = false;

  public boolean isLongDescriptionSpecified() {
    return localLongDescriptionTracker;
  }

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Description[]
   */
  public com.sap.xi.fndei.Description[] getLongDescription() {
    return localLongDescription;
  }

  /** validate the array for LongDescription */
  protected void validateLongDescription(com.sap.xi.fndei.Description[] param) {}

  /**
   * Auto generated setter method
   *
   * @param param LongDescription
   */
  public void setLongDescription(com.sap.xi.fndei.Description[] param) {

    validateLongDescription(param);

    localLongDescriptionTracker = param != null;

    this.localLongDescription = param;
  }

  /**
   * Auto generated add method for the array for convenience
   *
   * @param param com.sap.xi.fndei.Description
   */
  public void addLongDescription(com.sap.xi.fndei.Description param) {
    if (localLongDescription == null) {
      localLongDescription = new com.sap.xi.fndei.Description[] {};
    }

    // update the setting tracker
    localLongDescriptionTracker = true;

    java.util.List list =
        org.apache.axis2.databinding.utils.ConverterUtil.toList(localLongDescription);
    list.add(param);
    this.localLongDescription =
        (com.sap.xi.fndei.Description[])
            list.toArray(new com.sap.xi.fndei.Description[list.size()]);
  }

  /** field for AllownonwholeIndicator */
  protected com.sap.xi.fndei.Indicator localAllownonwholeIndicator;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Indicator
   */
  public com.sap.xi.fndei.Indicator getAllownonwholeIndicator() {
    return localAllownonwholeIndicator;
  }

  /**
   * Auto generated setter method
   *
   * @param param AllownonwholeIndicator
   */
  public void setAllownonwholeIndicator(com.sap.xi.fndei.Indicator param) {

    this.localAllownonwholeIndicator = param;
  }

  /** field for PreferredMappingIndicator */
  protected com.sap.xi.fndei.Indicator localPreferredMappingIndicator;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.Indicator
   */
  public com.sap.xi.fndei.Indicator getPreferredMappingIndicator() {
    return localPreferredMappingIndicator;
  }

  /**
   * Auto generated setter method
   *
   * @param param PreferredMappingIndicator
   */
  public void setPreferredMappingIndicator(com.sap.xi.fndei.Indicator param) {

    this.localPreferredMappingIndicator = param;
  }

  /** field for Category */
  protected com.sap.xi.fndei.NumberValue localCategory;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.NumberValue
   */
  public com.sap.xi.fndei.NumberValue getCategory() {
    return localCategory;
  }

  /**
   * Auto generated setter method
   *
   * @param param Category
   */
  public void setCategory(com.sap.xi.fndei.NumberValue param) {

    this.localCategory = param;
  }

  /** field for NumberOfDecimalPlaces */
  protected com.sap.xi.fndei.NumberValue localNumberOfDecimalPlaces;

  /**
   * Auto generated getter method
   *
   * @return com.sap.xi.fndei.NumberValue
   */
  public com.sap.xi.fndei.NumberValue getNumberOfDecimalPlaces() {
    return localNumberOfDecimalPlaces;
  }

  /**
   * Auto generated setter method
   *
   * @param param NumberOfDecimalPlaces
   */
  public void setNumberOfDecimalPlaces(com.sap.xi.fndei.NumberValue param) {

    this.localNumberOfDecimalPlaces = param;
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
            namespacePrefix + ":UnitOfMeasurement",
            xmlWriter);
      } else {
        writeAttribute(
            "xsi",
            "http://www.w3.org/2001/XMLSchema-instance",
            "type",
            "UnitOfMeasurement",
            xmlWriter);
      }
    }

    if (localCode == null) {
      throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
    }
    localCode.serialize(new javax.xml.namespace.QName("", "Code"), xmlWriter);
    if (localISOCodeTracker) {
      if (localISOCode == null) {
        throw new org.apache.axis2.databinding.ADBException("ISOCode cannot be null!!");
      }
      localISOCode.serialize(new javax.xml.namespace.QName("", "ISOCode"), xmlWriter);
    }
    if (localCommercialDescriptionTracker) {
      if (localCommercialDescription != null) {
        for (int i = 0; i < localCommercialDescription.length; i++) {
          if (localCommercialDescription[i] != null) {
            localCommercialDescription[i].serialize(
                new javax.xml.namespace.QName("", "CommercialDescription"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException(
            "CommercialDescription cannot be null!!");
      }
    }
    if (localTechnicalDescriptionTracker) {
      if (localTechnicalDescription != null) {
        for (int i = 0; i < localTechnicalDescription.length; i++) {
          if (localTechnicalDescription[i] != null) {
            localTechnicalDescription[i].serialize(
                new javax.xml.namespace.QName("", "TechnicalDescription"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException(
            "TechnicalDescription cannot be null!!");
      }
    }
    if (localLongDescriptionTracker) {
      if (localLongDescription != null) {
        for (int i = 0; i < localLongDescription.length; i++) {
          if (localLongDescription[i] != null) {
            localLongDescription[i].serialize(
                new javax.xml.namespace.QName("", "LongDescription"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }
        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("LongDescription cannot be null!!");
      }
    }
    if (localAllownonwholeIndicator == null) {
      throw new org.apache.axis2.databinding.ADBException(
          "AllownonwholeIndicator cannot be null!!");
    }
    localAllownonwholeIndicator.serialize(
        new javax.xml.namespace.QName("", "AllownonwholeIndicator"), xmlWriter);

    if (localPreferredMappingIndicator == null) {
      throw new org.apache.axis2.databinding.ADBException(
          "PreferredMappingIndicator cannot be null!!");
    }
    localPreferredMappingIndicator.serialize(
        new javax.xml.namespace.QName("", "PreferredMappingIndicator"), xmlWriter);

    if (localCategory == null) {
      throw new org.apache.axis2.databinding.ADBException("Category cannot be null!!");
    }
    localCategory.serialize(new javax.xml.namespace.QName("", "Category"), xmlWriter);

    if (localNumberOfDecimalPlaces == null) {
      throw new org.apache.axis2.databinding.ADBException("NumberOfDecimalPlaces cannot be null!!");
    }
    localNumberOfDecimalPlaces.serialize(
        new javax.xml.namespace.QName("", "NumberOfDecimalPlaces"), xmlWriter);

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
    public static UnitOfMeasurement parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      UnitOfMeasurement object = new UnitOfMeasurement();

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

            if (!"UnitOfMeasurement".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (UnitOfMeasurement)
                  com.sap.xi.fndei.ExtensionMapper.getTypeObject(nsUri, type, reader);
            }
          }
        }

        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();

        reader.next();

        java.util.ArrayList list3 = new java.util.ArrayList();

        java.util.ArrayList list4 = new java.util.ArrayList();

        java.util.ArrayList list5 = new java.util.ArrayList();

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "Code").equals(reader.getName())) {

          object.setCode(com.sap.xi.fndei.UnitOfMeasurementCode.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "ISOCode").equals(reader.getName())) {

          object.setISOCode(com.sap.xi.fndei.UnitOfMeasurementCode.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "CommercialDescription")
                .equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list3.add(com.sap.xi.fndei.Description.Factory.parse(reader));

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
              if (new javax.xml.namespace.QName("", "CommercialDescription")
                  .equals(reader.getName())) {
                list3.add(com.sap.xi.fndei.Description.Factory.parse(reader));

              } else {
                loopDone3 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setCommercialDescription(
              (com.sap.xi.fndei.Description[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.Description.class, list3));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "TechnicalDescription").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list4.add(com.sap.xi.fndei.Description.Factory.parse(reader));

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
              if (new javax.xml.namespace.QName("", "TechnicalDescription")
                  .equals(reader.getName())) {
                list4.add(com.sap.xi.fndei.Description.Factory.parse(reader));

              } else {
                loopDone4 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setTechnicalDescription(
              (com.sap.xi.fndei.Description[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.Description.class, list4));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "LongDescription").equals(reader.getName())) {

          // Process the array and step past its final element's end.

          list5.add(com.sap.xi.fndei.Description.Factory.parse(reader));

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
              if (new javax.xml.namespace.QName("", "LongDescription").equals(reader.getName())) {
                list5.add(com.sap.xi.fndei.Description.Factory.parse(reader));

              } else {
                loopDone5 = true;
              }
            }
          }
          // call the converter utility  to convert and set the array

          object.setLongDescription(
              (com.sap.xi.fndei.Description[])
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                      com.sap.xi.fndei.Description.class, list5));

        } // End of if for expected property start element
        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "AllownonwholeIndicator")
                .equals(reader.getName())) {

          object.setAllownonwholeIndicator(com.sap.xi.fndei.Indicator.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "PreferredMappingIndicator")
                .equals(reader.getName())) {

          object.setPreferredMappingIndicator(com.sap.xi.fndei.Indicator.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "Category").equals(reader.getName())) {

          object.setCategory(com.sap.xi.fndei.NumberValue.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
        }

        while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "NumberOfDecimalPlaces")
                .equals(reader.getName())) {

          object.setNumberOfDecimalPlaces(com.sap.xi.fndei.NumberValue.Factory.parse(reader));

          reader.next();

        } // End of if for expected property start element
        else {
          // 1 - A start element we are not expecting indicates an invalid parameter was passed
          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());
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
