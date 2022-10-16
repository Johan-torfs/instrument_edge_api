package com.example.instrument_api.controller;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.service.InstrumentService;
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
    InstrumentService instrumentService;

    @GetMapping
    public List<Instrument>getInstruments()
    {
        return instrumentService.getAllInstruments();
    }


    // GET /instrument/{id}: Instrument
    @GetMapping("/{id}")
    public Instrument getInstrument(@PathVariable(value= "id") int id)
    {
        try{
            return instrumentService.getInstrumentById(id);
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
            return instrumentService.getInstrumentByName(name);
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
        return instrumentService.getInstrumentsByNamePeriod(name);
    }
}
