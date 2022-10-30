package com.example.instrument_api;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MusicianUnitTests {

    @Test
    void setId_method() throws Exception {
        Musician musician = new Musician();
        musician.setId(1);
        Assertions.assertEquals(1,musician.getId());
    }
    @Test
    void setName_method() throws Exception {
        Musician musician = new Musician();
        musician.setName("Bross");
        Assertions.assertEquals("Bross", musician.getName());
    }

    @Test
    void setYearOfBirth_method() throws Exception {
        Musician musician = new Musician();
        musician.setYearOfBirth(1916);
        Assertions.assertEquals(1916, musician.getYearOfBirth());
    }

    @Test
    void setYearOfDeath_method() throws Exception {
        Musician musician = new Musician();
        musician.setYearOfDeath(2017);
        Assertions.assertEquals(2017, musician.getYearOfDeath());
    }

    @Test
    void setInstrument_method() throws Exception {
        Instrument instrument = new Instrument("Violin", "When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980", "String");
        Musician musician = new Musician("Svend Asmussen",1916,2017,instrument);
        Assertions.assertEquals(instrument,musician.getInstrument());
    }
}
