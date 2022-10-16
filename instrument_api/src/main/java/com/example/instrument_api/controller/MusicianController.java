package com.example.instrument_api.controller;

import com.example.instrument_api.model.Musician;
import com.example.instrument_api.service.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/musician")
public class MusicianController {

    @Autowired
    MusicianService musicianService;

    // GET /{id}: Musician
    @GetMapping("/{id}") //path parameter
    public Object getMusician(@PathVariable int id)
    {
        try{
            Musician musician = musicianService.musicianById(id);
            return new ResponseEntity<Musician>(musician, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Musician>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /name/{name}: Musician
    @GetMapping("/name/{name}") //path parameter
    public ResponseEntity<Musician> getMusicianName(@PathVariable String name)
    {

        try{
            Musician musician = musicianService.getMusicianByName(name);
            return new ResponseEntity<Musician>(musician, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Musician>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /year/{year}: MusicianList
    @GetMapping("/year/{year}") //path parameter
    public List<Musician> getMusicianYear(@PathVariable int year)
    {
        return musicianService.getMusiciansByYear(year);
    }

//    // GET /instrument/{id}: MusicianList
    @GetMapping(path="/instrument/{id}") //path parameter
    public List<Musician> getMusicianInstrument(@PathVariable int id)
    {
        return musicianService.getMusiciansByInstrumentId(id);
    }

    // GET /instrument/name/{name}: MusicianList
    @GetMapping(path="instrument/name/{name}") //path parameter
    public List<Musician> getMusicianInstrumentName(@PathVariable String name)
    {
        return musicianService.getMusiciansByNameContaining(name);
    }

}
