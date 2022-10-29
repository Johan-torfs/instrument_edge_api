package com.example.instrument_api.controller;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.repository.InstrumentRepository;
import com.example.instrument_api.repository.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/instrument")
public class InstrumentController {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    MusicianRepository musicianRepository;


    @GetMapping
    public List<Instrument>getInstruments()
    {
        return instrumentRepository.findAll();
    }

    // GET /instrument/{id}: Instrument
    @GetMapping("/{id}")
    public Instrument getInstrumentById(@PathVariable int id)
    {
        try{
            Instrument instrument = instrumentRepository.findInstrumentById(id);
            instrument.setMusicians(musicianRepository.findMusicianByInstrument_Name(instrument.getName()));
            return instrument;
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", e);
        }

    }

    // GET /instrument/name/{name}: Instrument
    @GetMapping("/name/{name}")
    public Instrument getInstrumentByName(@PathVariable String name)
    {
        try{
            return instrumentRepository.findInstrumentByName(name);
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", e);
        }

    }

    // GET /instrument/period/{name}: InstrumentList
    @GetMapping("/period/{name}")
    public List<Instrument> getInstrumentsPeriodByName(@PathVariable String name)
    {
        try{
            return instrumentRepository.findInstrumentByPeriod(name);
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", e);
        }
    }
}
