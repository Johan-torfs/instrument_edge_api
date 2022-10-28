package com.example.instrument_api;


import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.InstrumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstrumentRepository instrumentRepository;

    private ObjectMapper mapper = new ObjectMapper();



    Set<Musician> musicians = new HashSet<Musician>() {{
        add(new Musician(2,"Svend Asmussen",1916,2017,null));
        add(new Musician(4,"Bartolo Alvarez",1914,2017,null));
        add(new Musician(8,"Ray Anthony",1922,2022,null));
        add(new Musician(6,"Irving Fields",1915,2016,null));


    }};
    List<Musician> mainList = new ArrayList<Musician>(musicians);
    @Test
    public void givenInstrument_whenGetInstrumentById_thenReturnJsonReview() throws Exception {
        Instrument instrument = new Instrument(1,"Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980","String", Collections.singleton(mainList.get(3)));
        given(instrumentRepository.findInstrumentById(1)).willReturn(instrument);

        mockMvc.perform(get("/instrument/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Violin")))
                .andExpect(jsonPath("$.description", is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
                .andExpect(jsonPath("$.period", is("1980")))
                .andExpect(jsonPath("$.collection", is("String")))
                .andExpect(jsonPath("$.musicians", hasSize(1)))
                .andExpect(jsonPath("$.musicians[0].id", is(2)))
                .andExpect(jsonPath("$.musicians[0].name", is("Svend Asmussen")))
                .andExpect(jsonPath("$.musicians[0].yearOfBirth", is(1916)))
                .andExpect(jsonPath("$.musicians[0].yearOfDeath", is(2017)));
    }


    // GET /instrument/name/{name}: Instrument
  @Test
    public void givenInstrument_whenGetInstrumentByName_thenReturnJsonReview() throws Exception {
      Instrument instrument1 = new Instrument(1,"Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980","String", Collections.singleton(mainList.get(3)));
      given(instrumentRepository.findInstrumentByName("Violin")).willReturn(instrument1);

      mockMvc.perform(get("/instrument/name/{name}", "Violin"))
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(1)))
              .andExpect(jsonPath("$.name", is("Violin")))
              .andExpect(jsonPath("$.description", is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
              .andExpect(jsonPath("$.period", is("1980")))
              .andExpect(jsonPath("$.collection", is("String")))
              .andExpect(jsonPath("$.musicians", hasSize(1)))
              .andExpect(jsonPath("$.musicians[0].id", is(2)))
              .andExpect(jsonPath("$.musicians[0].name", is("Svend Asmussen")))
              .andExpect(jsonPath("$.musicians[0].yearOfBirth", is(1916)))
              .andExpect(jsonPath("$.musicians[0].yearOfDeath", is(2017)));
  }
  // GET /instrument/period/{name}: InstrumentList

    @Test
    public void givenInstrument_whenGetInstrumentByPeriod_thenReturnJsonReview() throws Exception {


        Instrument instrument1 = new Instrument(1,"Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980","String", Collections.singleton(mainList.get(3)));
        Instrument instrument2 = new Instrument(7,"Piano","This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate", "1980","Percussion", Collections.singleton(mainList.get(0)));

        List<Instrument> instrumentList1 = new ArrayList<>();
        instrumentList1.add(instrument1);
        instrumentList1.add(instrument2);

        given(instrumentRepository.findInstrumentByPeriod("1980")).willReturn(instrumentList1);

        mockMvc.perform(get("/instrument/period/{name}", "1980"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Violin")))
                .andExpect(jsonPath("$[0].description", is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
                .andExpect(jsonPath("$[0].period", is("1980")))
                .andExpect(jsonPath("$[0].collection", is("String")))
                .andExpect(jsonPath("$[0].musicians", hasSize(1)))
                .andExpect(jsonPath("$[0].musicians[0].id", is(2)))
                .andExpect(jsonPath("$[0].musicians[0].name", is("Svend Asmussen")))
                .andExpect(jsonPath("$[0].musicians[0].yearOfBirth", is(1916)))
                .andExpect(jsonPath("$[0].musicians[0].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[1].id", is(7)))
                .andExpect(jsonPath("$[1].name", is("Piano")))
                .andExpect(jsonPath("$[1].description", is("This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate")))
                .andExpect(jsonPath("$[1].period", is("1980")))
                .andExpect(jsonPath("$[1].collection", is("Percussion")))
                .andExpect(jsonPath("$[1].musicians", hasSize(1)))
                .andExpect(jsonPath("$[1].musicians[0].id", is(8)))
                .andExpect(jsonPath("$[1].musicians[0].name", is("Ray Anthony")))
                .andExpect(jsonPath("$[1].musicians[0].yearOfBirth", is(1922)))
                .andExpect(jsonPath("$[1].musicians[0].yearOfDeath", is(2022)));
    }


}
