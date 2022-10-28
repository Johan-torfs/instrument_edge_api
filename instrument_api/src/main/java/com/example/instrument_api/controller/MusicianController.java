package com.example.instrument_api.controller;

import javax.annotation.PostConstruct;
import com.example.instrument_api.model.Musician;
import com.example.instrument_api.service.MusicianService;
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
    MusicianService musicianService;

    @GetMapping
    public List<Musician>getMusicians()
    {
        return musicianService.getAllMusicians();
    }

    // GET /{id}: Musician
    @GetMapping("/{id}") //path parameter
    public Musician getMusician(@PathVariable int id)
    {
        try{
            return musicianService.musicianById(id);
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
            return musicianService.getMusicianByName(name);
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
        return musicianService.getMusiciansByYear(year);
    }

    // GET /instrument/{id}: MusicianList
    @GetMapping("/instrument/{id}") //path parameter
    public List<Musician> getMusicianInstrument(@PathVariable int id)
    {
        return musicianService.getMusiciansByInstrumentId(id);
    }

    // GET /instrument/name/{name}: MusicianList
    @GetMapping("instrument/name/{name}") //path parameter
    public List<Musician> getMusicianInstrumentName(@PathVariable String name)
    {
        return musicianService.getMusiciansByNameContaining(name);
    }

}
