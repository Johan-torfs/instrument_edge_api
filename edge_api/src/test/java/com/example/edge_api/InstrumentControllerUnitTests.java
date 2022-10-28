package com.example.edge_api;

import com.example.edge_api.model.Instrument;
import com.example.edge_api.model.Musician;
import com.example.edge_api.model.Piece;
import com.example.edge_api.model.Part;
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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
class InstrumentControllerUnitTests {
    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseurl;

    @Value("${pieceservice.baseurl}")
    private String pieceServiceBaseurl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Instrument instrumentGuitar = new Instrument("Guitar","The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.","1300","String");
    private Instrument instrumentViolin = new Instrument("Violin","When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.","1980","String");

    private Musician musicianGuitar1 = new Musician("Svend Asmussen", 1916,2017);
    private Musician musicianGuitar2 = new Musician("Jorgen Asmussen", 1915,2012);
    private Musician musicianViolin1 = new Musician("Beethoven Asmussen", 1915,2012);

    private Piece pieceGuitar1 = new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", new ArrayList<>() {{ add(new Part("Guitar", "Solo")); }});

    private List<Instrument> instrumentList = new ArrayList<>() {{ 
        add(instrumentGuitar); 
        add(instrumentViolin);
    }};
    private List<Musician> musicianListGuitar = Arrays.asList(musicianGuitar1, musicianGuitar2);
    private List<Piece> pieceListGuitar = new ArrayList<>() {{ add(pieceGuitar1); }};

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void onCall_getInstrumentByName_returnInstrument() throws Exception {
        // GET Instrument 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/instrument/name/Guitar")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(instrumentGuitar))
                );

        // GET musicians info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/musician/instrument/name/Guitar")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(musicianListGuitar))
                );

        // GET pieces info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(pieceServiceBaseurl + "/instrument/Guitar")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(pieceListGuitar))
                );

        mockMvc.perform(get("/instrument/{name}", "Guitar"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Guitar")))
                .andExpect(jsonPath("$.description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$.period",is("1300")))
                .andExpect(jsonPath("$.collection",is("String")))
                .andExpect(jsonPath("$.musicians", hasSize(2)))
                .andExpect(jsonPath("$.musicians[0].name", is("Svend Asmussen")))
                .andExpect(jsonPath("$.musicians[0].birthYear", is(1916)))
                .andExpect(jsonPath("$.musicians[0].deathYear", is(2017)))
                .andExpect(jsonPath("$.musicians[1].name", is("Jorgen Asmussen")))
                .andExpect(jsonPath("$.musicians[1].birthYear", is(1915)))
                .andExpect(jsonPath("$.musicians[1].deathYear", is(2012)))
                .andExpect(jsonPath("$.pieces", hasSize(1)))
                .andExpect(jsonPath("$.pieces[0].name", is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$.pieces[0].period", is("20th century")))
                .andExpect(jsonPath("$.pieces[0].composer", is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$.pieces[0].parts", hasSize(1)))
                .andExpect(jsonPath("$.pieces[0].parts[0].instrument", is("Guitar")))
                .andExpect(jsonPath("$.pieces[0].parts[0].name", is("Solo")));
    }

    @Test
    void onCall_getInstruments_returnAllInstruments() throws Exception {
        // GET Instrument 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/instrument")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(instrumentList))
                );

        mockMvc.perform(get("/instrument"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Guitar")))
                .andExpect(jsonPath("$[0].description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$[0].period",is("1300")))
                .andExpect(jsonPath("$[0].collection",is("String")))
                .andExpect(jsonPath("$[1].name",is("Violin")))
                .andExpect(jsonPath("$[1].description",is("When you look at a string instrument, the first thing you'll probably notice is that it's made of wood, so why is it called a string instrument? The bodies of the string instruments, which are hollow inside to allow sound to vibrate within them, are made of different kinds of wood, but the part of the instrument that makes the sound is the strings, which are made of nylon, steel or sometimes gut. The strings are played most often by drawing a bow across them. The handle of the bow is made of wood and the strings of the bow are actually horsehair from horses' tails! Sometimes the musicians will use their fingers to pluck the strings, and occasionally they will turn the bow upside down and play the strings with the wooden handle.")))
                .andExpect(jsonPath("$[1].period",is("1980")))
                .andExpect(jsonPath("$[1].collection",is("String")));
    }
}
