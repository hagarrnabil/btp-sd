//package com.example.btpsd.config;
//
//import com.example.datamodel.wsdl.GetUnitOfMeasurementRequest;
//import com.example.datamodel.wsdl.GetUnitOfMeasurementResponse;
//import com.example.datamodel.wsdl.UnitOfMeasurement;
//import org.springframework.ws.server.endpoint.annotation.Endpoint;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
//
//@Endpoint
//public class UomEndpoint {
//
//    @PayloadRoot(namespace = "http://sap.com/xi/FNDEI", localPart = "GetUnitOfMeasurementRequest")
//    @ResponsePayload
//    public GetUnitOfMeasurementResponse processtUnitOfMeasurementRequest(@RequestPayload GetUnitOfMeasurementRequest request) {
//        GetUnitOfMeasurementResponse response = new GetUnitOfMeasurementResponse();
//
//        UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
//        unitOfMeasurement.setCode(request.getCode());
////        unitOfMeasurement.setISOCode("DAY");
//        unitOfMeasurement.setCategory(1);
//        unitOfMeasurement.setAllownonwholeIndicator(true);
//        unitOfMeasurement.setPreferredMappingIndicator(false);
//        unitOfMeasurement.setNumberOfDecimalPlaces(0);
//
//        response.setUnitOfMeasurement(unitOfMeasurement);
//
//        return response;
//    }
//
//}
