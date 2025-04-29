package com.abdulrahman.cityxcore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Cityx Core District",
                version = "1.0",
                description = "API Documentation for Cityx Core District"
        )
)
@SpringBootApplication
public class CityxCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityxCoreApplication.class, args);
    }
}
