package com.example.piece_api;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import com.example.piece_api.repository.PieceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class PieceControllerIntegrationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PieceRepository pieceRepository;
    private List<Piece> pieceList = new ArrayList<Piece>();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        pieceRepository.deleteAll();
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part(5, "Solo"));

        Piece piece1 = new Piece("Requerdos de la Alhambra", "19th century", "Francisco Tárrega");
        Piece piece2 = new Piece("Julia Florida", "20th century", "Agustin Barrios Mangoré", parts);

        parts = new ArrayList<Part>();
        parts.add(new Part(3, "Baseline"));
        Piece piece3 = new Piece("Asturias", "19th century", "Isaac Albéniz", parts);
        Piece piece4 = new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", parts);

        pieceList.add(piece1);
        pieceList.add(piece2);
        pieceList.add(piece3);
        pieceList.add(piece4);

        pieceRepository.save(piece1);
        pieceRepository.save(piece2);
        pieceRepository.save(piece3);
        pieceRepository.save(piece4);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        pieceRepository.deleteAll();
    }

    @Test
    public void onCall_getPieces_returnAllPieces() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$[0].period",is("19th century")))
                .andExpect(jsonPath("$[0].composer",is("Francisco Tárrega")))
                .andExpect(jsonPath("$[1].name",is("Julia Florida")))
                .andExpect(jsonPath("$[1].period",is("20th century")))
                .andExpect(jsonPath("$[1].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[1].parts",hasSize(1)))
                .andExpect(jsonPath("$[1].parts[0].instrumentId",is(5)))
                .andExpect(jsonPath("$[1].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[2].name",is("Asturias")))
                .andExpect(jsonPath("$[2].period",is("19th century")))
                .andExpect(jsonPath("$[2].composer",is("Isaac Albéniz")))
                .andExpect(jsonPath("$[2].parts",hasSize(1)))
                .andExpect(jsonPath("$[2].parts[0].instrumentId",is(3)))
                .andExpect(jsonPath("$[2].parts[0].name",is("Baseline")))
                .andExpect(jsonPath("$[3].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[3].period",is("20th century")))
                .andExpect(jsonPath("$[3].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[3].parts",hasSize(1)))
                .andExpect(jsonPath("$[3].parts[0].instrumentId",is(3)))
                .andExpect(jsonPath("$[3].parts[0].name",is("Baseline")));
    }

    @Test
    public void onCallGivenIdOfPieceWithNoParts_getPieceById_returnPiece() throws Exception {
        mockMvc.perform(get("/{id}", pieceList.get(0).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$.period",is("19th century")))
                .andExpect(jsonPath("$.composer",is("Francisco Tárrega")));
    }

    @Test
    public void onCallGivenIdOfPieceWithOnePart_getPieceById_returnPiece() throws Exception {
        mockMvc.perform(get("/{id}", pieceList.get(1).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Julia Florida")))
                .andExpect(jsonPath("$.period",is("20th century")))
                .andExpect(jsonPath("$.composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$.parts",hasSize(1)))
                .andExpect(jsonPath("$.parts[0].instrumentId",is(5)))
                .andExpect(jsonPath("$.parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenNameOfPiece_getPieceByName_returnPiece() throws Exception {
        mockMvc.perform(get("/name/{name}", pieceList.get(1).getName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrumentId",is(5)))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenInstrumentId_getPieceByInstrument_returnPiece() throws Exception {
        mockMvc.perform(get("/instrument/{id}", 5))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrumentId",is(5)))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenComposer_getPieceByComposer_returnPiece() throws Exception {
        mockMvc.perform(get("/composer/{name}", "Agustin Barrios Mangoré"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrumentId",is(5)))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[1].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[1].period",is("20th century")))
                .andExpect(jsonPath("$[1].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[1].parts",hasSize(1)))
                .andExpect(jsonPath("$[1].parts[0].instrumentId",is(3)))
                .andExpect(jsonPath("$[1].parts[0].name",is("Baseline")));
    }

    @Test
    public void onCallGivenPeriod_getPieceByPeriod_returnPiece() throws Exception {
        mockMvc.perform(get("/period/{name}", "19th century"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$[0].period",is("19th century")))
                .andExpect(jsonPath("$[0].composer",is("Francisco Tárrega")))
                .andExpect(jsonPath("$[1].name",is("Asturias")))
                .andExpect(jsonPath("$[1].period",is("19th century")))
                .andExpect(jsonPath("$[1].composer",is("Isaac Albéniz")))
                .andExpect(jsonPath("$[1].parts",hasSize(1)))
                .andExpect(jsonPath("$[1].parts[0].instrumentId",is(3)))
                .andExpect(jsonPath("$[1].parts[0].name",is("Baseline")));
    }
}
