package com.example.btpsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.example.btpsd.model"})
@SpringBootApplication
public class BtpSdApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtpSdApplication.class, args);
    }

}
