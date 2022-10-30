package com.example.instrument_api;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InstrumentModelTests {

    @Test
    void setId_method() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setId(1);
        Assertions.assertEquals(1,instrument.getId());
    }

    @Test
    void setName_method() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setName("Trumpet");
        Assertions.assertEquals("Trumpet",instrument.getName());
    }
    @Test
    void setDescription_method() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setDescription("Info");
        Assertions.assertEquals("Info",instrument.getDescription());
    }
    @Test
    void setPeriod_method() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setPeriod("1980");
        Assertions.assertEquals("1980",instrument.getPeriod());
    }
    @Test
    void setCollection_method() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setCollection("Brass");
        Assertions.assertEquals("Brass",instrument.getCollection());
    }
    @Test
    void setMusicians_method() throws Exception {
        List<Musician> musicianList= new ArrayList<>();
        Instrument instrument = new Instrument();

        Musician musician1 = new Musician("Svend Asmussen",1916,2017,null);
        Musician musician2 = new Musician("Bartolo Alvarez",1914,2017,null);

        musicianList.add(musician1);
        musicianList.add(musician2);

        instrument.setMusicians(musicianList);
        Assertions.assertEquals(musicianList,instrument.getMusicians());
    }

    @Test
    void onCreation_method() throws Exception {
        List<Musician> musicianList= new ArrayList<>();
        Instrument instrument = new Instrument();

        Musician musician1 = new Musician("Svend Asmussen",1916,2017,null);
        Musician musician2 = new Musician("Bartolo Alvarez",1914,2017,null);

        musicianList.add(musician1);
        musicianList.add(musician2);

        instrument.setMusicians(musicianList);
        instrument.onCreation();
        Assertions.assertEquals(musicianList.get(0).getInstrument(),instrument);
        Assertions.assertEquals(musicianList.get(1).getInstrument(),instrument);

    }
}
