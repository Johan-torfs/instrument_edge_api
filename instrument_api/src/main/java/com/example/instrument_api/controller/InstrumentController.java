package com.example.instrument_api.controller;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.InstrumentRepository;
import com.example.instrument_api.repository.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
@RequestMapping("/instrument")
public class InstrumentController {
    @Autowired
    InstrumentRepository instrumentRepository;
    @Autowired
    MusicianRepository musicianRepository;

    @PostConstruct
       public void fillDB() {
            for (Musician musician : musicianRepository.findAll()) {
                musicianRepository.deleteAll();
            }

            for (Instrument instrument : instrumentRepository.findAll()) {
                instrumentRepository.deleteAll();
            }

            Instrument instrument1 = new Instrument("Violin", "When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980", "String");
            Instrument instrument2 = new Instrument("Flute", "The flute is the oldest of all instruments that produce pitched sounds (not just rhythms), and was originally made from wood, stone, clay or hollow reeds like bamboo. Modern flutes are made of silver, gold or platinum; there are generally 2 to 4 flutes in an orchestra. A standard flute is a little over 2 feet long and is often featured playing the melody. You play the flute by holding it sideways with both hands and blowing across a hole in the mouthpiece, much like blowing across the top of a bottle. Your fingers open and close the keys, which changes the pitch.", "1980", "Woodwind");
            Instrument instrument4 = new Instrument("Trumpet", "If you think the brass family got its name because the instruments are made of brass, you're right! This family of instruments can play louder than any other in the orchestra and can also be heard from far away. Although their early ancestors are known to have been made of wood, tusks, animal horns or shells, today's modern instruments are made entirely of brass. Brass instruments are essentially very long pipes that widen at their ends into a bell-like shape. The pipes have been curved and twisted into different shapes to make them easier to hold and play.", "1980", "Brass");
            Instrument instrument3 = new Instrument("Piano", "This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate", "1980", "Percussion");

            instrumentRepository.save(instrument1);
            instrumentRepository.save(instrument2);
            instrumentRepository.save(instrument3);
            instrumentRepository.save(instrument4);


            System.out.println("Instruments added:");
            for (Instrument instrument : instrumentRepository.findAll()) {
                System.out.println(instrument);
            }

            musicianRepository.save(new Musician("Svend Asmussen", 1916, 2017, instrument1));
            musicianRepository.save(new Musician("Bartolo Alvarez", 1914, 2017, instrument2));
            musicianRepository.save(new Musician("Ray Anthony", 1922, 2022, instrument3));
            musicianRepository.save(new Musician("Irving Fields", 1915, 2016, instrument4));

            System.out.println("Musicians added:");
            for (Musician musician: musicianRepository.findAll()) {
                System.out.println(musician);
            }

        }
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
