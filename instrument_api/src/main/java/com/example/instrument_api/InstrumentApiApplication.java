package com.example.instrument_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tinylog.Logger;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Instrument Api", version = "3.0", description = "Description"))
public class InstrumentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstrumentApiApplication.class, args);
        Logger.info("start applicatie");
    }

}
