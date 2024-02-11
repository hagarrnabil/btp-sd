//package com.example.btpsd.config;
//
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.ws.config.annotation.WsConfigurerAdapter;
//import org.springframework.ws.transport.http.MessageDispatcherServlet;
//
//public class WebServiceConfig extends WsConfigurerAdapter {
//
//    @Bean
//    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
//        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//        servlet.setApplicationContext(applicationContext);
//
//        return new ServletRegistrationBean(servlet, "/javainuse/ws/*");
//    }
//
//    @Bean(name="helloworld")
//    public Wsdl11Definition defaultWsdl11Definition() {
//        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
//        wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/helloworld.wsdl"));
//
//        return wsdl11Definition;
//    }
//}
