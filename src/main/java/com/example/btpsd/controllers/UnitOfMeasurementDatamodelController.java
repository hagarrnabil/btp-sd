package com.example.btpsd.controllers;

import com.example.datamodel.wsdl.COFNDEIUNITOFMEASUREMENTRLService;
import com.example.datamodel.wsdl.ObjectFactory;
import com.example.datamodel.wsdl.UnitOfMeasurement;
import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.datamodel.odata.client.exception.ODataException;
import com.sap.cloud.sdk.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.datamodel.odatav4.core.BatchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors.execute;

@RestController
public class UnitOfMeasurementDatamodelController {

    private static final Logger logger = LoggerFactory.getLogger(UnitOfMeasurementController.class);

    @RequestMapping(value = "/measurements", method = RequestMethod.GET )
    public String getAllUnitOfMeasures()
    {


        // DESTINATION 3:  Destination to a SAP S/4HANA Cloud (public edition) tenant
        // Uncomment this section to test with actual SAP S/4HANA Cloud
        final HttpDestination destination = DefaultDestination.builder()
                                                 .property("Name", "mydestination")
                                                 .property("URL", "https://my405604-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_UOM_CDS/YY1_UOM?$format=json")
                                                 .property("Type", "HTTP")
                                                 .property("Authentication", "BasicAuthentication")
                                                 .property("User", "CU_UOM")
                                                 .property("Password", "CgAHz8WybHmmnybVCPMuYJlBjXMLjESQp\\YmNLAa")
                                                 .build().asHttp();

//        final SoapQuery<COFNDEIUNITOFMEASUREMENTRLService> soapQuery = new SoapQuery<>(ManageContractAccountServiceStub.class, configContext);


        final List<UnitOfMeasurement> unitOfMeasures =
                new ObjectFactory()
                        .createGetUnitOfMeasurementResponse()
//                        .withHeader(APIKEY_HEADER, SANDBOX_APIKEY)
                        .executeRequest(destination);

//        logger.info(String.format("Found %d unit of measure(s).", unitOfMeasures.size()));

        return new Gson().toJson(unitOfMeasures);

    }
}
