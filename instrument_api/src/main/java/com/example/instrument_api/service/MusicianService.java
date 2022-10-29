package com.example.instrument_api.service;

import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.InstrumentRepository;
import com.example.instrument_api.repository.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicianService {
    @Autowired
    MusicianRepository musicianRepository;
    InstrumentRepository instrumentRepository;

    public List<Musician>getAllMusicians()
    {
        List<Musician> results = musicianRepository.findAll();
        return results;
    }

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

    public List<Musician> getMusiciansByYear(int year) {
        return musicianRepository.findAllByFromYear(year);
    }

    public List<Musician> getMusiciansByInstrumentId(int id) {
        return musicianRepository.findMusicianByInstrument_Id(id);
    }

    public List<Musician> getMusiciansByNameContaining(String name) {
        return musicianRepository.findMusicianByInstrument_Name(name);
    }

    public void save(Musician musician) {
        musicianRepository.save(musician);
    } 

    public void delete(Musician musician) {
        musicianRepository.delete(musician);
    } 
}
