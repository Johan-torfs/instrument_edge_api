package com.example.instrument_api;

import com.example.instrument_api.model.Instrument;
import com.example.instrument_api.model.Musician;
import com.example.instrument_api.repository.InstrumentRepository;
import com.example.instrument_api.repository.MusicianRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class MusicianControllerIntegrationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    MusicianRepository musicianRepository;

    List<Instrument> instrumentList= new ArrayList<>();
    List<Musician>musicianList= new ArrayList<>();

    @BeforeEach
    public void beforeAllTests()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        musicianRepository.deleteAll();
        instrumentRepository.deleteAll();

        Instrument instrument1 = new Instrument("Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.", "1980","String");
        Instrument instrument2 = new Instrument("Flute","The flute is the oldest of all instruments that produce pitched sounds (not just rhythms), and was originally made from wood, stone, clay or hollow reeds like bamboo. Modern flutes are made of silver, gold or platinum; there are generally 2 to 4 flutes in an orchestra. A standard flute is a little over 2 feet long and is often featured playing the melody. You play the flute by holding it sideways with both hands and blowing across a hole in the mouthpiece, much like blowing across the top of a bottle. Your fingers open and close the keys, which changes the pitch.", "1981","Woodwind");
        Instrument instrument3 = new Instrument("Piano","This class of musical instruments requires you to blow into a specific wind instrument by following an order to ensure that the sound that you desire is produced. The instruments can be expected to work depending on the principles of frequencies, sound waves, acoustics, resonance and harmonics. The pitch of the produced sound when you start blowing the instrument is actually dependent on the length of the air column through which the waves of the sounds vibrate", "1980","Percussion");
        Instrument instrument4 = new Instrument("Trumpet","If you think the brass family got its name because the instruments are made of brass, you're right! This family of instruments can play louder than any other in the orchestra and can also be heard from far away. Although their early ancestors are known to have been made of wood, tusks, animal horns or shells, today's modern instruments are made entirely of brass. Brass instruments are essentially very long pipes that widen at their ends into a bell-like shape. The pipes have been curved and twisted into different shapes to make them easier to hold and play.", "1982","Brass");


        Musician musician1 = new Musician("Svend Asmussen",1916,2017,instrument1);
        Musician musician2 = new Musician("Bartolo Alvarez",1914,2017,instrument2);
        Musician musician3 = new Musician("Ray Anthony",1922,2022,instrument3);
        Musician musician4 = new Musician("Irving Fields",1915,2016,instrument4);

        musicianList.add(musician1);
        musicianList.add(musician2);
        musicianList.add(musician3);
        musicianList.add(musician4);

        instrumentList.add(instrument1);
        instrumentList.add(instrument2);
        instrumentList.add(instrument3);
        instrumentList.add(instrument4);

        musicianRepository.save(musicianList.get(0));
        musicianRepository.save(musicianList.get(1));
        musicianRepository.save(musicianList.get(2));
        musicianRepository.save(musicianList.get(3));

        instrumentRepository.save(instrumentList.get(0));
        instrumentRepository.save(instrumentList.get(1));
        instrumentRepository.save(instrumentList.get(2));
        instrumentRepository.save(instrumentList.get(3));

    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!

    }

    // GET : Musician
    @Test
    public void whenGetMusicianByAllId_thenReturnJsonMusician() throws Exception {
        mockMvc.perform(get("/musician"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name", is("Svend Asmussen")))
                .andExpect(jsonPath("$[0].yearOfBirth", is(1916)))
                .andExpect(jsonPath("$[0].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[1].name", is("Bartolo Alvarez")))
                .andExpect(jsonPath("$[1].yearOfBirth", is(1914)))
                .andExpect(jsonPath("$[1].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[2].name", is("Ray Anthony")))
                .andExpect(jsonPath("$[2].yearOfBirth", is(1922)))
                .andExpect(jsonPath("$[2].yearOfDeath", is(2022)))

                .andExpect(jsonPath("$[3].name", is("Irving Fields")))
                .andExpect(jsonPath("$[3].yearOfBirth", is(1915)))
                .andExpect(jsonPath("$[3].yearOfDeath", is(2016)));

    }

    // GET /musician/{id}: Musician
    @Test
    public void whenGetMusicianById_thenReturnJsonMusician() throws Exception {
        mockMvc.perform(get("/musician/{id}", musicianList.get(0).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Svend Asmussen")))
                .andExpect(jsonPath("$.yearOfBirth", is(1916)))
                .andExpect(jsonPath("$.yearOfDeath", is(2017)));
    }
    // GET /musician/name/{name}: Musician
    @Test
    public void whenGetMusicianByName_thenReturnJsonMusician() throws Exception {
        mockMvc.perform(get("/musician/name/{name}", "Svend Asmussen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Svend Asmussen")))
                .andExpect(jsonPath("$.yearOfBirth", is(1916)))
                .andExpect(jsonPath("$.yearOfDeath", is(2017)));
    }

    // GET /musician/year/{year}: MusicianList
    @Test
    public void whenGetMusicianByYear_thenReturnJsonMusician() throws Exception {
        mockMvc.perform(get("/musician/year/{year}", "2000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name",  is("Svend Asmussen")))
                .andExpect(jsonPath("$[0].yearOfBirth", is(1916)))
                .andExpect(jsonPath("$[0].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[1].name",  is("Bartolo Alvarez")))
                .andExpect(jsonPath("$[1].yearOfBirth", is(1914)))
                .andExpect(jsonPath("$[1].yearOfDeath", is(2017)))

                .andExpect(jsonPath("$[2].name",  is("Ray Anthony")))
                .andExpect(jsonPath("$[2].yearOfBirth", is(1922)))
                .andExpect(jsonPath("$[2].yearOfDeath", is(2022)))

                .andExpect(jsonPath("$[3].name",  is("Irving Fields")))
                .andExpect(jsonPath("$[3].yearOfBirth", is(1915)))
                .andExpect(jsonPath("$[3].yearOfDeath", is(2016)))
        ;

    }

    // GET /musician/instrument/name/{name}: MusicianList
    @Test
    public void whenGetMusicianInstrumentByName_thenReturnJsonMusician() throws Exception {
        mockMvc.perform(get("/musician/instrument/name/{name}", "Trumpet").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Irving Fields")))
                .andExpect(jsonPath("$[0].yearOfBirth", is(1915)))
                .andExpect(jsonPath("$[0].yearOfDeath", is(2016)));
    }



}
