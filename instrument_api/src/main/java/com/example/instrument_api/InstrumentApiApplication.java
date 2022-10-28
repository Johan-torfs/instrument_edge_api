package com.example.instrument_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tinylog.Logger;

@SpringBootApplication
public class InstrumentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstrumentApiApplication.class, args);
        Logger.info("start applicatie");
    }

}
