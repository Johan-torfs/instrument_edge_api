package com.example.instrument_api.service;

import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicianService {
    @Autowired
    MusicianRepository musicianRepository;

    public Musician musicianById(int id) {
        return musicianRepository.findById(id).get();
    }

    public Musician getMusicianByName(String name) {
        List<Musician> musicians = musicianRepository.findAll();
        Musician musician = null;
        for (Musician ints: musicians)
        {
            if(ints.getName().equalsIgnoreCase(name))
            {
                musician = ints;
            }
        }
        return musician;
    }

//    public List<Musician> getMusiciansByYear(int year) {
//        return musicianRepository.findByYear(year);
//    }

//    public List<Musician> getMusiciansByInstrumentId(String id) {
//        return null;
//    }
//
//    public List<Musician> getMusiciansByNameContaining(String name) {
//    return null;
//    }



}
