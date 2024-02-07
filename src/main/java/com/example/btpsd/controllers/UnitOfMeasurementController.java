package com.example.btpsd.controllers;

import com.example.datamodel.odata.namespaces.unitofmeasurement.UnitOfMeasures;
import com.example.datamodel.odata.services.DefaultUnitofMeasurementService;
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

//@RequiredArgsConstructor
@CrossOrigin("*")
@RestControllerAdvice
@RestController
//@RequestMapping( "/measurements" )
public class UnitOfMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(UnitOfMeasurementController.class);
    // TODO: uncomment the lines below and insert your API key, if you are using the sandbox service
//     private static final String APIKEY_HEADER = "apikey";
//     private static final String SANDBOX_APIKEY = "gudA2NIQcQD8vR9mz7CYbiTnZSMvo4wS";
//    @RequestMapping( method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/measurements", method = RequestMethod.GET )
    public String getAllUnitOfMeasures()
    {

//        // DESTINATION 1:  Destination to the local Mock Server
//        // Uncomment this section to test with mock server
//        final HttpDestination destination = DefaultDestination.builder()
//                .property("Name", "mydestination")
//                .property("URL", "http://localhost:8081")
//                .property("Type", "HTTP")
//                .property("Authentication", "NoAuthentication")
//                .build().asHttp();

//         DESTINATION 2:  Destination to the api.sap.com Sandbox
//         Uncomment this section to test with sandbox in api.sap.com
//         final HttpDestination destination = DefaultDestination.builder()
//                                                 .property("Name", "mydestination")
//                                                 .property("URL", "https://sandbox.api.sap.com/sapreturnablepackagemanagement/odata/v4/MasterDataService/UnitOfMeasures")
//                                                 .property("Type", "HTTP")
//                                                 .property("Authentication", "NoAuthentication")
//                                                 .build().asHttp();

        // DESTINATION 3:  Destination to a SAP S/4HANA Cloud (public edition) tenant
        // Uncomment this section to test with actual SAP S/4HANA Cloud
        final HttpDestination destination = DefaultDestination.builder()
                                                 .property("Name", "mydestination")
                                                 .property("URL", "https://my405604.s4hana.cloud.sap/ui#Shell-home")
                                                 .property("Type", "HTTP")
                                                 .property("Authentication", "BasicAuthentication")
                                                 .property("User", "ziad")
                                                 .property("Password", "Ziadsolex@466")
                                                 .build().asHttp();

        final List<UnitOfMeasures> unitOfMeasures =
                new DefaultUnitofMeasurementService()
                        .getAllUnitOfMeasures()
                        .top(2)
//                        .withHeader(APIKEY_HEADER, SANDBOX_APIKEY)
                        .execute(destination);

        logger.info(String.format("Found %d unit of measure(s).", unitOfMeasures.size()));

        return new Gson().toJson(unitOfMeasures);
    }
//
//        try {
//            final Destination destination = DestinationAccessor.getDestination("myDestination");
//
//            final List<UnitOfMeasures> unitOfMeasures =
//                    new DefaultUnitofMeasurementService()
//                            .getAllUnitOfMeasures()
//                            // TODO: uncomment the line below, if you are using the sandbox service
//                             .withHeader(APIKEY_HEADER, SANDBOX_APIKEY)
//                            .execute(destination);
//
//            return ResponseEntity.ok( new Gson().toJson(unitOfMeasures));
//        } catch (final DestinationAccessException e) {
//            logger.error(e.getMessage(), e);
//            return ResponseEntity.internalServerError().body("Failed to fetch destination.");
//        } catch (final ODataException e) {
//            logger.error(e.getMessage(), e);
//            return ResponseEntity.internalServerError().body("Failed to fetch unit of measures.");
//        } catch (final Exception e) {
//            logger.error(e.getMessage(), e);
//            return ResponseEntity.internalServerError().body("Unexpected error occurred while fetching unit of measures.");
//        }
//    }

}
