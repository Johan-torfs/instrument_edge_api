package com.example.instrument_api.controller;

import com.example.instrument_api.model.Musician;
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
@RequestMapping("/musician")
public class MusicianController {

    @Autowired
    MusicianRepository musicianRepository;

    @GetMapping
    public List<Musician>getMusicians()
    {
        return musicianRepository.findAll();
    }

    // GET /{id}: Musician
    @GetMapping("/{id}") //path parameter
    public Musician getInstrumentById(@PathVariable int id)
    {
        try{
            return musicianRepository.findMusicianById(id);
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", e);
        }
    }

    // GET /name/{name}: Musician
    @GetMapping("/name/{name}") //path parameter
    public Musician getMusicianName(@PathVariable String name)
    {
        try{
            return musicianRepository.findMusicianByName(name);
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", e);
        }
    }

    // GET /year/{year}: MusicianList
    @GetMapping("/year/{year}") //path parameter
    public List<Musician> getMusicianYear(@PathVariable int year)
    {
        return musicianRepository.findAllByFromYear(year);
    }

    // GET /instrument/{id}: MusicianList
    @GetMapping("/instrument/{id}") //path parameter
    public List<Musician> getMusicianInstrument(@PathVariable int id)
    {
        return musicianRepository.findMusicianByInstrument_Id(id);
    }

    // GET /instrument/name/{name}: MusicianList
    @GetMapping("instrument/name/{name}") //path parameter
    public List<Musician> getMusicianInstrumentName(@PathVariable String name)
    {
        return musicianRepository.findMusicianByInstrument_Name(name);
    }

}
