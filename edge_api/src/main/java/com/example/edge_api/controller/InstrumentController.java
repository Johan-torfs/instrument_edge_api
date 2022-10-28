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

    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseUrl;
    @Value("${pieceservice.baseurl}")
    private String pieceServiceBaseUrl;

    @GetMapping("/instrument/{name}")
    public Instrument getInstrumentWithMusiciansAndPieces(@PathVariable String name) {
        Instrument instrument = restTemplate.getForObject(instrumentServiceBaseUrl + "/instrument/name/{name}", Instrument.class, name);
        if (instrument == null) return null;
        // Instrument returns musicians by default
        // List<Musician> musicians = Arrays.asList(restTemplate.getForObject(instrumentServiceBaseUrl + "/musician/instrument/name/{name}", Musician[].class, name));
        // instrument.setMusicians(musicians);
        List<Piece> pieces = Arrays.asList(restTemplate.getForObject(pieceServiceBaseUrl + "/instrument/{name}", Piece[].class, name));
        instrument.setPieces(pieces);
        return instrument;
    }

    @GetMapping("/instrument")
    public List<Instrument> getInstrumentList() {
        List<Instrument> instruments = Arrays.asList(restTemplate.getForObject(instrumentServiceBaseUrl + "/instrument", Instrument[].class));
        return instruments; //not including pieces(, musicians)
    }
}
