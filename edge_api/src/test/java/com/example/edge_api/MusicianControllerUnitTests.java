package com.example.edge_api;

import com.example.edge_api.model.Instrument;
import com.example.edge_api.model.Musician;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class MusicianControllerUnitTests {
    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseurl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Instrument instrumentGuitar = new Instrument("Guitar","The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.","1300","String");
    private Instrument instrumentViolin = new Instrument("Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.","1980","String");

    private Musician musicianGuitar1 = new Musician("Svend Asmussen", 1916,2017, instrumentGuitar);
    private Musician musicianGuitar2 = new Musician("Jorgen Asmussen", 1915,2012, instrumentGuitar);
    private Musician musicianViolin1 = new Musician("Beethoven Asmussen", 1915,2012, instrumentViolin);

    private List<Musician> musicianList = Arrays.asList(musicianGuitar1, musicianGuitar2, musicianViolin1);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void onCall_getMusicianByName_returnMusician() throws Exception {
        // GET Musician 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/musician/name/Svend%20Asmussen")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(musicianGuitar1))
                );

        mockMvc.perform(get("/musician/{name}", "Svend Asmussen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Svend Asmussen")))
                .andExpect(jsonPath("$.birthYear",is(1916)))
                .andExpect(jsonPath("$.deathYear",is(2017)))
                .andExpect(jsonPath("$.instrument.name",is("Guitar")))
                .andExpect(jsonPath("$.instrument.description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$.instrument.period",is("1300")))
                .andExpect(jsonPath("$.instrument.collection",is("String")));
    }

    @Test
    void onCall_getMusician_returnAllMusicians() throws Exception {
        // GET Musician 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/musician")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(musicianList))
                );

        mockMvc.perform(get("/musician"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("Svend Asmussen")))
                .andExpect(jsonPath("$[0].birthYear",is(1916)))
                .andExpect(jsonPath("$[0].deathYear",is(2017)))
                .andExpect(jsonPath("$[0].instrument.name",is("Guitar")))
                .andExpect(jsonPath("$[0].instrument.description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$[0].instrument.period",is("1300")))
                .andExpect(jsonPath("$[0].instrument.collection",is("String")))
                .andExpect(jsonPath("$[1].name",is("Jorgen Asmussen")))
                .andExpect(jsonPath("$[1].birthYear",is(1915)))
                .andExpect(jsonPath("$[1].deathYear",is(2012)))
                .andExpect(jsonPath("$[1].instrument.name",is("Guitar")))
                .andExpect(jsonPath("$[1].instrument.description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$[1].instrument.period",is("1300")))
                .andExpect(jsonPath("$[1].instrument.collection",is("String")))
                .andExpect(jsonPath("$[2].name",is("Beethoven Asmussen")))
                .andExpect(jsonPath("$[2].birthYear",is(1915)))
                .andExpect(jsonPath("$[2].deathYear",is(2012)))
                .andExpect(jsonPath("$[2].instrument.name",is("Violin")))
                .andExpect(jsonPath("$[2].instrument.description",is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
                .andExpect(jsonPath("$[2].instrument.period",is("1980")))
                .andExpect(jsonPath("$[2].instrument.collection",is("String")));
    }
}
