package com.example.edge_api.controller;

import com.example.edge_api.model.Musician;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class MusicianController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseUrl;

    @GetMapping("/musician/{name}")
    public Musician getMusicianWithInstrument(@PathVariable String name) {
        return restTemplate.getForObject(instrumentServiceBaseUrl + "/musician/name/{name}", Musician.class, name);
    }

    @GetMapping("/musician")
    public List<Musician> getMusicianList() {
        return Arrays.asList(restTemplate.getForObject(instrumentServiceBaseUrl + "/musician", Musician[].class)); //might includes instrument
    }
}
