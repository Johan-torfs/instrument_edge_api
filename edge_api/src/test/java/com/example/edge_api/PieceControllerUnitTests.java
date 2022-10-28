package com.example.edge_api;

import com.example.edge_api.model.Instrument;
import com.example.edge_api.model.Musician;
import com.example.edge_api.model.Piece;
import com.example.edge_api.model.Part;
import com.example.edge_api.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.net.URI;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
class PieceControllerUnitTests {
    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseurl;

    @Value("${pieceservice.baseurl}")
    private String pieceServiceBaseurl;

    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseurl;

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

    private Piece piece1 = new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", new ArrayList<>() {{ add(new Part("Guitar", "Solo")); }});
    private Piece piece2 = new Piece("Julia Florida", "20th century", "Agustin Barrios Mangoré", new ArrayList<>() {{ add(new Part("Guitar", "Solo")); }});

    private Review review1ForPiece1 = new Review("Una Limosna por el Amor de Dios", 9, "Nice!");
    private List<Musician> musicianList = Arrays.asList(musicianGuitar1, musicianGuitar2, musicianViolin1);
    private List<Piece> piece1List = new ArrayList<>() {{ add(piece1); }};
    private List<Piece> pieceList = new ArrayList<>() {{ add(piece1); add(piece2); }};
    private List<Review> reviewList = new ArrayList<>() {{ add(review1ForPiece1); }};

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void onCall_getPieceByName_returnPiece() throws Exception {
        // GET Piece 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(pieceServiceBaseurl + "/name/Una%20Limosna%20por%20el%20Amor%20de%20Dios")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(piece1List))
                );

        // GET Instrument 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(instrumentServiceBaseurl + "/instrument/Guitar")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(instrumentGuitar))
                );

        // GET reviews
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/piece/Una%20Limosna%20por%20el%20Amor%20de%20Dios")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewList))
                );

        mockMvc.perform(get("/piece/{name}", "Una Limosna por el Amor de Dios"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[0].parts[0].instrumentObj.name",is("Guitar")))
                .andExpect(jsonPath("$[0].parts[0].instrumentObj.description",is("The guitar is a fretted musical instrument that typically has six strings. It is usually held flat against the player's body and played by strumming or plucking the strings with the dominant hand, while simultaneously pressing selected strings against frets with the fingers of the opposite hand. A plectrum or individual finger picks may also be used to strike the strings.")))
                .andExpect(jsonPath("$[0].parts[0].instrumentObj.period",is("1300")))
                .andExpect(jsonPath("$[0].parts[0].instrumentObj.collection",is("String")))
                .andExpect(jsonPath("$[0].reviews",hasSize(1)))
                .andExpect(jsonPath("$[0].reviews[0].rating",is(9)))
                .andExpect(jsonPath("$[0].reviews[0].comment",is("Nice!")));
    }

    @Test
    void onCall_getPieceByUnknownName_returnEmptyArray() throws Exception {
        // GET Empty Array info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(pieceServiceBaseurl + "/name/Sevilla")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(new ArrayList<>()))
                );

        mockMvc.perform(get("/piece/{name}", "Sevilla"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void onCall_getPiece_returnAllPieces() throws Exception {
        // GET Musician 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(pieceServiceBaseurl + "/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(pieceList))
                );

        mockMvc.perform(get("/piece"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[0].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[1].name",is("Julia Florida")))
                .andExpect(jsonPath("$[1].period",is("20th century")))
                .andExpect(jsonPath("$[1].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[1].parts",hasSize(1)))
                .andExpect(jsonPath("$[1].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[1].parts[0].instrument",is("Guitar")));
    }
}
