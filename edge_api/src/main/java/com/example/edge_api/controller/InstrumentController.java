package com.example.edge_api.controller;

import com.example.edge_api.model.Instrument;
import com.example.edge_api.model.Musician;
import com.example.edge_api.model.Piece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class InstrumentController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${instrumentservice.baseurl}") //needs to be defined in Application Properties, but I'm too lazy to test now anyway (and probably too busy tomorrow)
    private String instrumentServiceBaseUrl;
    @Value("${musicianservice.baseurl}")
    private String musicianServiceBaseUrl;
    @Value("${pieceservice.baseurl}")
    private String pieceServiceBaseUrl;

    @GetMapping("/instrument/{name}")
    public Instrument getInstrumentWithMusiciansAndPieces(@PathVariable String name) {
        Instrument instrument = restTemplate.getForObject("http://" + instrumentServiceBaseUrl + "/instrument/name/{name}", Instrument.class, name);
        if (instrument == null) return null;
        List<Musician> musicians = Arrays.asList(restTemplate.getForObject("http://" + instrumentServiceBaseUrl + "/musician/instrument/name/{name}", Musician[].class, name));
        instrument.setMusicians(musicians);
        List<Piece> pieces = Arrays.asList(restTemplate.getForObject("http://" + pieceServiceBaseUrl + "/name/{name}", Piece[].class, name));
        instrument.setPieces(pieces);
        return instrument;
    }
}
