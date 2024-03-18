/**
 * CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:03 EDT)
 */
package com.example.datamodel.wsdl;

/*
 *  CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub java implementation
 */

public class CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub extends org.apache.axis2.client.Stub
    implements CO_FNDEI_UNITOFMEASUREMENT_RL_service {
  protected org.apache.axis2.description.AxisOperation[] _operations;

  // hashmaps to keep the fault mapping
  private java.util.Map<org.apache.axis2.client.FaultMapKey, java.lang.String>
      faultExceptionNameMap =
          new java.util.HashMap<org.apache.axis2.client.FaultMapKey, java.lang.String>();
  private java.util.Map<org.apache.axis2.client.FaultMapKey, java.lang.String>
      faultExceptionClassNameMap =
          new java.util.HashMap<org.apache.axis2.client.FaultMapKey, java.lang.String>();
  private java.util.Map<org.apache.axis2.client.FaultMapKey, java.lang.String> faultMessageMap =
      new java.util.HashMap<org.apache.axis2.client.FaultMapKey, java.lang.String>();

  private static int counter = 0;

  private static synchronized java.lang.String getUniqueSuffix() {
    // reset the counter if it is greater than 99999
    if (counter > 99999) {
      counter = 0;
    }
    counter = counter + 1;
    return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
  }

  private void populateAxisService() throws org.apache.axis2.AxisFault {

    // creating the Service with a unique name
    _service =
        new org.apache.axis2.description.AxisService(
            "CO_FNDEI_UNITOFMEASUREMENT_RL_service" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[1];

    __operation = new org.apache.axis2.description.OutOnlyAxisOperation();

    __operation.setName(
        new javax.xml.namespace.QName(
            "http://sap.com/xi/FNDEI", "unitOfMeasurementMasterDataReplicationBundleRequest_Out"));
    _service.addOperation(__operation);

    _operations[0] = __operation;
  }

  // populates the faults
  private void populateFaults() {}

  /** Constructor that takes in a configContext */
  public CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub(
      org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint)
      throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }

  /** Constructor that takes in a configContext and useseperate listner */
  public CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub(
      org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint,
      boolean useSeparateListener)
      throws org.apache.axis2.AxisFault {
    // To populate AxisService
    populateAxisService();
    populateFaults();

    _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);

    _serviceClient
        .getOptions()
        .setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
    _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

    // Set the soap version
    _serviceClient
        .getOptions()
        .setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
  }

  /** Default Constructor */
  public CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub(
      org.apache.axis2.context.ConfigurationContext configurationContext)
      throws org.apache.axis2.AxisFault {

    this(configurationContext, "https://host:port/");
  }

  /** Default Constructor */
  public CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub() throws org.apache.axis2.AxisFault {

    this("https://host:port/");
  }

  /** Constructor taking the target endpoint */
  public CO_FNDEI_UNITOFMEASUREMENT_RL_serviceStub(java.lang.String targetEndpoint)
      throws org.apache.axis2.AxisFault {
    this(null, targetEndpoint);
  }

  /** Auto generated method signature */
  public void unitOfMeasurementMasterDataReplicationBundleRequest_Out(
      com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest
          unitOfMeasurementMasterDataReplicationBundleRequest0)
      throws java.rmi.RemoteException {

    org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();

    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[0].getName());
    _operationClient.getOptions().setAction("\"\"");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

    addPropertyToOperationClient(
        _operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
        "&");

    org.apache.axiom.soap.SOAPEnvelope env = null;

    // Style is Doc.

    env =
        toEnvelope(
            getFactory(_operationClient.getOptions().getSoapVersionURI()),
            unitOfMeasurementMasterDataReplicationBundleRequest0,
            optimizeContent(
                new javax.xml.namespace.QName(
                    "http://sap.com/xi/FNDEI",
                    "unitOfMeasurementMasterDataReplicationBundleRequest_Out")),
            new javax.xml.namespace.QName(
                "http://sap.com/xi/FNDEI", "UnitOfMeasurementMasterDataReplicationBundleRequest"));

    // adding SOAP soap_headers
    _serviceClient.addHeadersToEnvelope(env);
    // create message context with that soap envelope

    _messageContext.setEnvelope(env);

    // add the message contxt to the operation client
    _operationClient.addMessageContext(_messageContext);

    _operationClient.execute(true);

    if (_messageContext.getTransportOut() != null) {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    }

    return;
  }

  private javax.xml.namespace.QName[] opNameArray = null;

  private boolean optimizeContent(javax.xml.namespace.QName opName) {

    if (opNameArray == null) {
      return false;
    }
    for (int i = 0; i < opNameArray.length; i++) {
      if (opName.equals(opNameArray[i])) {
        return true;
      }
    }
    return false;
  }
  // https://host:port/
  private org.apache.axiom.om.OMElement toOM(
      com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest param,
      boolean optimizeContent)
      throws org.apache.axis2.AxisFault {

    try {
      return param.getOMElement(
          com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
      org.apache.axiom.soap.SOAPFactory factory,
      com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest param,
      boolean optimizeContent,
      javax.xml.namespace.QName elementQName)
      throws org.apache.axis2.AxisFault {

    try {

      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope
          .getBody()
          .addChild(
              param.getOMElement(
                  com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest.MY_QNAME,
                  factory));
      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  /* methods to provide back word compatibility */

  /** get the default envelope */
  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory) {
    return factory.getDefaultEnvelope();
  }

  private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type)
      throws org.apache.axis2.AxisFault {

    try {

      if (com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest.class.equals(type)) {

        javax.xml.stream.XMLStreamReader reader = param.getXMLStreamReaderWithoutCaching();
        java.lang.Object result =
            com.sap.xi.fndei.UnitOfMeasurementMasterDataReplicationBundleRequest.Factory.parse(
                reader);
        reader.close();
        return result;
      }

    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
    return null;
  }
}
