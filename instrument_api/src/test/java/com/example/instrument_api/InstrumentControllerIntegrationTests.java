package com.example.instrument_api;


import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.InstrumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentControllerIntegrationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    InstrumentRepository instrumentRepository;

    Set<Musician> musicians = new HashSet<Musician>() {{
        add(new Musician(0,"Svend Asmussen",1916,2017,null));
        add(new Musician(0,"Bartolo Alvarez",1914,2017,null));
        add(new Musician(0,"Ray Anthony",1922,2022,null));
        add(new Musician(0,"Irving Fields",1915,2016,null));
    }};

    List<Musician> mainList = new ArrayList<Musician>(musicians);
    Instrument instrument1 = new Instrument(0,"Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980","String", Collections.singleton(mainList.get(3)));
    Instrument instrument2 = new Instrument(0,"Flute","The flute is the oldest of all instruments that produce pitched sounds (not just rhythms), and was originally made from wood, stone, clay or hollow reeds like bamboo. Modern flutes are made of silver, gold or platinum; there are generally 2 to 4 flutes in an orchestra. A standard flute is a little over 2 feet long and is often featured playing the melody. You play the flute by holding it sideways with both hands and blowing across a hole in the mouthpiece, much like blowing across the top of a bottle. Your fingers open and close the keys, which changes the pitch.", "1981","Woodwind", Collections.singleton(mainList.get(2)));
    Instrument instrument3 = new Instrument(0,"Trumpet","If you think the brass family got its name because the instruments are made of brass, you're right! This family of instruments can play louder than any other in the orchestra and can also be heard from far away. Although their early ancestors are known to have been made of wood, tusks, animal horns or shells, today's modern instruments are made entirely of brass. Brass instruments are essentially very long pipes that widen at their ends into a bell-like shape. The pipes have been curved and twisted into different shapes to make them easier to hold and play.", "1982","Brass", Collections.singleton(mainList.get(1)));
    Instrument instrument4 = new Instrument(0,"Piano","This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate", "1980","Percussion", Collections.singleton(mainList.get(0)));


    @BeforeEach
    public void beforeAllTests()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        if(instrumentRepository.count()==0)
        {
            instrumentRepository.save(instrument1);
            instrumentRepository.save(instrument2);
            instrumentRepository.save(instrument3);
            instrumentRepository.save(instrument4);
        }


    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        //instrumentRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void givenInstrument_whenGetInstrumentById_thenReturnJsonReview() throws Exception {
        mockMvc.perform(get("/instrument/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Violin")))
                .andExpect(jsonPath("$.description", is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
                .andExpect(jsonPath("$.period", is("1980")))
                .andExpect(jsonPath("$.collection", is("String")));
    }

    @Test
    public void givenInstrument_whenGetInstrumentByAllId_thenReturnJsonReview() throws Exception {
        mockMvc.perform(get("/instrument").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(4)))
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

                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].name", is("Flute")))
                .andExpect(jsonPath("$[1].description", is("The flute is the oldest of all instruments that produce pitched sounds (not just rhythms), and was originally made from wood, stone, clay or hollow reeds like bamboo. Modern flutes are made of silver, gold or platinum; there are generally 2 to 4 flutes in an orchestra. A standard flute is a little over 2 feet long and is often featured playing the melody. You play the flute by holding it sideways with both hands and blowing across a hole in the mouthpiece, much like blowing across the top of a bottle. Your fingers open and close the keys, which changes the pitch.")))
                .andExpect(jsonPath("$[1].period", is("1981")))
                .andExpect(jsonPath("$[1].collection", is("Woodwind")))
                .andExpect(jsonPath("$[1].musicians", hasSize(1)))
                .andExpect(jsonPath("$[1].musicians[0].id", is(4)))
                .andExpect(jsonPath("$[1].musicians[0].name", is("Bartolo Alvarez")))
                .andExpect(jsonPath("$[1].musicians[0].yearOfBirth", is(1914)))
                .andExpect(jsonPath("$[1].musicians[0].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[2].id", is(5)))
                .andExpect(jsonPath("$[2].name", is("Trumpet")))
                .andExpect(jsonPath("$[2].description", is("If you think the brass family got its name because the instruments are made of brass, you're right! This family of instruments can play louder than any other in the orchestra and can also be heard from far away. Although their early ancestors are known to have been made of wood, tusks, animal horns or shells, today's modern instruments are made entirely of brass. Brass instruments are essentially very long pipes that widen at their ends into a bell-like shape. The pipes have been curved and twisted into different shapes to make them easier to hold and play.")))
                .andExpect(jsonPath("$[2].period", is("1982")))
                .andExpect(jsonPath("$[2].collection", is("Brass")))
                .andExpect(jsonPath("$[2].musicians", hasSize(1)))
                .andExpect(jsonPath("$[2].musicians[0].id", is(6)))
                .andExpect(jsonPath("$[2].musicians[0].name", is("Irving Fields")))
                .andExpect(jsonPath("$[2].musicians[0].yearOfBirth", is(1915)))
                .andExpect(jsonPath("$[2].musicians[0].yearOfDeath", is(2016)))

                .andExpect(jsonPath("$[3].id", is(7)))
                .andExpect(jsonPath("$[3].name", is("Piano")))
                .andExpect(jsonPath("$[3].description", is("This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate")))
                .andExpect(jsonPath("$[3].period", is("1980")))
                .andExpect(jsonPath("$[3].collection", is("Percussion")))
                .andExpect(jsonPath("$[3].musicians", hasSize(1)))
                .andExpect(jsonPath("$[3].musicians[0].id", is(8)))
                .andExpect(jsonPath("$[3].musicians[0].name", is("Ray Anthony")))
                .andExpect(jsonPath("$[3].musicians[0].yearOfBirth", is(1922)))
                .andExpect(jsonPath("$[3].musicians[0].yearOfDeath", is(2022)))

        ;

    }
    // GET /instrument/name/{name}: Instrument
    @Test
    public void givenInstrument_whenGetInstrumentByName_thenReturnJsonReview() throws Exception {

        mockMvc.perform(get("/instrument/name/{name}","Violin").contentType(MediaType.APPLICATION_JSON))
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
                .andExpect(jsonPath("$.musicians[0].yearOfDeath", is(2017)))
        ;

    }
    // GET /instrument/period/{name}: InstrumentList
    @Test
    public void givenInstrument_whenGetInstrumentByPeriodName_thenReturnJsonReview() throws Exception {

        mockMvc.perform(get("/instrument/period/{name}", "1980").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
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
