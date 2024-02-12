//package com.example.btpsd.config;
//
//import com.example.datamodel.wsdl.ObjectFactory;
//import com.example.datamodel.wsdl.UnitOfMeasurement;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//import org.springframework.ws.config.annotation.WsConfigurerAdapter;
//import org.springframework.ws.transport.http.MessageDispatcherServlet;
//
//public class UomClient extends WebServiceGatewaySupport {
//
//    private static final Logger log = LoggerFactory.getLogger(UomClient.class);
//    public UnitOfMeasurement getUom(String uom) {
//
//        ObjectFactory request = new ObjectFactory();
//        request.createUnitOfMeasurement();
//
//        log.info("Requesting uom for " + uom);
//
//        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
//                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
//                        new SoapActionCallback(
//                                "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));
//
//        return response;
//    }
//
//}
