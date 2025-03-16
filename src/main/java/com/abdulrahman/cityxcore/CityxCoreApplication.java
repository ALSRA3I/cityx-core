package com.abdulrahman.cityxcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CityxCoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(CityxCoreApplication.class, args);
    }

    @GetMapping
    public String helloRihal() {
        return "Hello Rihal";
    }

}
