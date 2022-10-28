package com.example.piece_api;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import com.example.piece_api.repository.PieceRepository;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class PieceControllerUnitTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PieceRepository pieceRepository;
    private List<Piece> pieceList = new ArrayList<Piece>();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        pieceList = new ArrayList<Piece>();
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Guitar", "Solo"));

        Piece piece1 = new Piece("Requerdos de la Alhambra", "19th century", "Francisco Tárrega");
        piece1.setId("634d92404b48d018675459e4");
        Piece piece2 = new Piece("Julia Florida", "20th century", "Agustin Barrios Mangoré", parts);
        piece2.setId("634d92404b48d018675459e5");

        parts = new ArrayList<Part>();
        parts.add(new Part("Banjo", "Baseline"));
        Piece piece3 = new Piece("Asturias", "19th century", "Isaac Albéniz", parts);
        piece2.setId("634d92404b48d018675459e6");
        Piece piece4 = new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", parts);
        piece2.setId("634d92404b48d018675459e7");

        pieceList.add(piece1);
        pieceList.add(piece2);
        pieceList.add(piece3);
        pieceList.add(piece4);
    }

    @Test
    public void onCall_getPieces_returnAllPieces() throws Exception {
        given(pieceRepository.findAll()).willReturn(pieceList);

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
                .andExpect(jsonPath("$[1].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[1].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[2].name",is("Asturias")))
                .andExpect(jsonPath("$[2].period",is("19th century")))
                .andExpect(jsonPath("$[2].composer",is("Isaac Albéniz")))
                .andExpect(jsonPath("$[2].parts",hasSize(1)))
                .andExpect(jsonPath("$[2].parts[0].instrument",is("Banjo")))
                .andExpect(jsonPath("$[2].parts[0].name",is("Baseline")))
                .andExpect(jsonPath("$[3].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[3].period",is("20th century")))
                .andExpect(jsonPath("$[3].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[3].parts",hasSize(1)))
                .andExpect(jsonPath("$[3].parts[0].instrument",is("Banjo")))
                .andExpect(jsonPath("$[3].parts[0].name",is("Baseline")));
    }

    @Test
    public void onCallGivenIdOfPieceWithNoParts_getPieceById_returnPiece() throws Exception {
        given(pieceRepository.findPieceById(pieceList.get(0).getId())).willReturn(pieceList.get(0));

        mockMvc.perform(get("/{id}", pieceList.get(0).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$.period",is("19th century")))
                .andExpect(jsonPath("$.composer",is("Francisco Tárrega")));
    }

    @Test
    public void onCallGivenIdOfPieceWithOnePart_getPieceById_returnPiece() throws Exception {
        given(pieceRepository.findPieceById(pieceList.get(1).getId())).willReturn(pieceList.get(1));

        mockMvc.perform(get("/{id}", pieceList.get(1).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Julia Florida")))
                .andExpect(jsonPath("$.period",is("20th century")))
                .andExpect(jsonPath("$.composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$.parts",hasSize(1)))
                .andExpect(jsonPath("$.parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$.parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenNameOfPiece_getPieceByName_returnPiece() throws Exception {
        given(pieceRepository.findPieceByNameRegex(pieceList.get(1).getName())).willReturn(pieceList.subList(1, 2));

        mockMvc.perform(get("/name/{name}", pieceList.get(1).getName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenPartOfNameOfPiece_getPieceByName_returnPiece() throws Exception {
        given(pieceRepository.findPieceByNameRegex("Julia")).willReturn(pieceList.subList(1, 2));

        mockMvc.perform(get("/name/{name}", "Julia"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenInstrumentId_getPieceByInstrument_returnPiece() throws Exception {
        given(pieceRepository.findPieceByInstrument("Guitar")).willReturn(pieceList.subList(1, 2));

        mockMvc.perform(get("/instrument/{name}", "Guitar"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")));
    }

    @Test
    public void onCallGivenComposer_getPieceByComposer_returnPiece() throws Exception {
        List<Piece> returnList = new ArrayList<>();
        returnList.add(pieceList.get(1));
        returnList.add(pieceList.get(3));
        given(pieceRepository.findPieceByComposer("Agustin Barrios Mangoré")).willReturn(returnList);

        mockMvc.perform(get("/composer/{name}", "Agustin Barrios Mangoré"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Julia Florida")))
                .andExpect(jsonPath("$[0].period",is("20th century")))
                .andExpect(jsonPath("$[0].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[0].parts",hasSize(1)))
                .andExpect(jsonPath("$[0].parts[0].instrument",is("Guitar")))
                .andExpect(jsonPath("$[0].parts[0].name",is("Solo")))
                .andExpect(jsonPath("$[1].name",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[1].period",is("20th century")))
                .andExpect(jsonPath("$[1].composer",is("Agustin Barrios Mangoré")))
                .andExpect(jsonPath("$[1].parts",hasSize(1)))
                .andExpect(jsonPath("$[1].parts[0].instrument",is("Banjo")))
                .andExpect(jsonPath("$[1].parts[0].name",is("Baseline")));
    }

    @Test
    public void onCallGivenPeriod_getPieceByPeriod_returnPiece() throws Exception {
        List<Piece> returnList = new ArrayList<>();
        returnList.add(pieceList.get(0));
        returnList.add(pieceList.get(2));
        given(pieceRepository.findPieceByPeriod("19th century")).willReturn(returnList);

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
                .andExpect(jsonPath("$[1].parts[0].instrument",is("Banjo")))
                .andExpect(jsonPath("$[1].parts[0].name",is("Baseline")));
    }
}
