package com.example.btpsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

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
}
