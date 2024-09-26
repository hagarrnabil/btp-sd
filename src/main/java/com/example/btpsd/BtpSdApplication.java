package com.example.btpsd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.sap.hcp.cf.logging.servlet.filter.RequestLoggingFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "1.0",
                description = "Library Management APIs"
        )
)
public class BtpSdApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtpSdApplication.class, args);



    }
//    @Bean
//    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
//        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RequestLoggingFilter());
//        registrationBean.setName("request-logging");
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registrationBean;
//    }
}
