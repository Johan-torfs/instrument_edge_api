package com.example.edge_api;
import com.example.edge_api.model.Review;
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

import java.util.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerUnitTests {
    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseurl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Review review1ForPiece1 = new Review("Una Limosna por el Amor de Dios", 9, "Nice!");
    private Review review2ForPiece1 = new Review("Una Limosna por el Amor de Dios", 7, "Its oke, I guess...");
    private List<Review> reviewList = new ArrayList<>() {{ add(review1ForPiece1); add(review2ForPiece1); }};

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void onCall_getReview_returnAllReviews() throws Exception {
        // GET Piece 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewList))
                );

        mockMvc.perform(get("/review"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].pieceName",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[0].comment",is("Nice!")))
                .andExpect(jsonPath("$[0].rating",is(9)))
                .andExpect(jsonPath("$[1].pieceName",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$[1].comment",is("Its oke, I guess...")))
                .andExpect(jsonPath("$[1].rating",is(7)));
    }

    @Test
    void onCall_getReviewById_returnReview() throws Exception {
        // GET Piece 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(review1ForPiece1))
                );

        mockMvc.perform(get("/review/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$.comment",is("Nice!")))
                .andExpect(jsonPath("$.rating",is(9)));
    }

    @Test
    void onCall_postReview_returnReview() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(review1ForPiece1))
                );

        mockMvc.perform(post("/review")
                .param("pieceName", review1ForPiece1.getPieceName())
                .param("rating", Integer.toString(review1ForPiece1.getRating()))
                .param("comment", review1ForPiece1.getComment())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$.comment",is("Nice!")))
                .andExpect(jsonPath("$.rating",is(9)));
    }

    @Test
    void onCall_putReview_returnReview() throws Exception {
        Review review = new Review("Una Limosna por el Amor de Dios", 9, "Nice!");
        Review reviewUpdated = new Review("Una Limosna por el Amor de Dios", 10, "Perfect!");
        reviewUpdated.setId("634d92404b48d018675459e4");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/634d92404b48d018675459e4")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(review))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/634d92404b48d018675459e4")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewUpdated))
                );

        mockMvc.perform(put("/review/{id}", "634d92404b48d018675459e4")
                .param("rating", "10")
                .param("comment", "Perfect!")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is("634d92404b48d018675459e4")))
                .andExpect(jsonPath("$.pieceName",is("Una Limosna por el Amor de Dios")))
                .andExpect(jsonPath("$.comment",is("Perfect!")))
                .andExpect(jsonPath("$.rating",is(10)));
    }

    @Test
    void onCall_deleteReview_returnHttpOk() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(reviewServiceBaseurl + "/634d92404b48d018675459e4")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/review/{id}", "634d92404b48d018675459e4"))
                .andExpect(status().isOk());
    }
}
