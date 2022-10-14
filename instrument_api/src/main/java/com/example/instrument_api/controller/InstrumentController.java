package com.example.instrument_api.controller;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Instrument> getInstrument(@PathVariable(value= "id") int id)
    {
        try{
            Instrument instrument = instrumentService.getInstrumentById(id);
            return new ResponseEntity<Instrument>(instrument, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Instrument>(HttpStatus.NOT_FOUND);
        }

    }

    // GET /instrument/name/{name}: Instrument
    @GetMapping("/name/{name}")
    public ResponseEntity<Instrument> getInstrumentByName(@PathVariable String name)
    {
        try{
            Instrument instrument = instrumentService.getInstrumentByName(name);
            return new ResponseEntity<Instrument>(instrument, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Instrument>(HttpStatus.NOT_FOUND);
        }

    }

    // GET /instrument/period/{name}: InstrumentList
    @GetMapping("/period/{name}")
    public List<Instrument> getInstrumentsPeriodByName(@PathVariable String name)
    {
        return instrumentService.getInstrumentsByNamePeriod(name);
    }
}
