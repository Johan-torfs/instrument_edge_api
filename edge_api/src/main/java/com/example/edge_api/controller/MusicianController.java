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
        Musician musician = restTemplate.getForObject(instrumentServiceBaseUrl + "/musician/name/{name}", Musician.class, name);
        if (musician == null) return null;
        /* //Is instrument included in musician get? // Nu well :)
        Instrument instrument = restTemplate.getForObject(instrumentServiceBaseUrl + "/instrument/musician/name/{name}", Instrument.class, name);
        musician.setInstrument(instrument);*/
        return musician;
    }

    @GetMapping("/musician")
    public List<Musician> getMusicianList() {
        List<Musician> musicians = Arrays.asList(restTemplate.getForObject(instrumentServiceBaseUrl + "/musician", Musician[].class));
        return musicians; //might include instruments? Depends on instrument api
    }
}
