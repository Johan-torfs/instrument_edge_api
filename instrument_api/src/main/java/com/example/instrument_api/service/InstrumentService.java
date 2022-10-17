package com.example.instrument_api.service;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InstrumentService {
    @Autowired
    InstrumentRepository instrumentRepository;
    public List<Instrument>getAllInstruments()
    {
        List<Instrument> results = instrumentRepository.findAll();
        return results;
    }
    public Instrument getInstrumentByName(String name) {
        List<Instrument> instruments = instrumentRepository.findAll();
        Instrument instrument = null;
        for (Instrument ints: instruments)
        {
            if(ints.getName().equalsIgnoreCase(name))
            {
                instrument = ints;
            }
        }
        return instrument;
    }
    public List<Instrument> getInstrumentsByNamePeriod(String name) {
        return instrumentRepository.findByPeriod(name);
    }
    public Instrument getInstrumentById(int id) {
        return instrumentRepository.findById(id).get();
    }
}
