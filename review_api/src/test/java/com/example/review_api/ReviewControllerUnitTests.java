package com.example.review_api;

import com.example.review_api.model.Review;
import com.example.review_api.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
class ReviewControllerUnitTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ReviewRepository reviewRepository;
    private List<Review> reviewList = new ArrayList<Review>();
    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        reviewList = new ArrayList<Review>();

        Review review1 = new Review("Requerdos de la Alhambra", 4, "Not a fan");
        review1.setId("634d92404b48d018675459r4");
        Review review2 = new Review("Julia Florida", 9, "Sublime, truly amazing piece");
        review2.setId("634d92404b48d018675459r5");
        Review review3 = new Review("Asturias", 10);
        review3.setId("634d92404b48d018675459r6");
        Review review4 = new Review("Asturias", 6, "It's okay");
        review4.setId("634d92404b48d018675459r7");

        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.add(review3);
        reviewList.add(review4);
        for (Review review : reviewList) {
            System.out.println(review.getPieceName() + " (" + review.getRating() + "): " + review.getComment() );
        }
    }

    @Test
    void onCall_getReviews_returnAllReviews() throws Exception {
        given(reviewRepository.findAll()).willReturn(reviewList);

        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].pieceName",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$[0].rating",is(4)))
                .andExpect(jsonPath("$[0].comment",is("Not a fan")))
                .andExpect(jsonPath("$[1].pieceName",is("Julia Florida")))
                .andExpect(jsonPath("$[1].rating",is(9)))
                .andExpect(jsonPath("$[1].comment",is("Sublime, truly amazing piece")))
                .andExpect(jsonPath("$[2].pieceName",is("Asturias")))
                .andExpect(jsonPath("$[2].rating",is(10)))
                .andExpect(jsonPath("$[2].comment",is("")))
                .andExpect(jsonPath("$[3].pieceName",is("Asturias")))
                .andExpect(jsonPath("$[3].rating",is(6)))
                .andExpect(jsonPath("$[3].comment",is("It's okay")));
    }

    @Test
    void onCallGivenIdOfReviewWithComment_getReviewById_returnReview() throws Exception {
        given(reviewRepository.findReviewById(reviewList.get(0).getId())).willReturn(reviewList.get(0));

        mockMvc.perform(get("/{id}", reviewList.get(0).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Requerdos de la Alhambra")))
                .andExpect(jsonPath("$.rating",is(4)))
                .andExpect(jsonPath("$.comment",is("Not a fan")));
    }

    @Test
    void onCallGivenIdOfReviewWithoutComment_getReviewById_returnReview() throws Exception {
        given(reviewRepository.findReviewById(reviewList.get(2).getId())).willReturn(reviewList.get(2));

        mockMvc.perform(get("/{id}", reviewList.get(2).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Asturias")))
                .andExpect(jsonPath("$.rating",is(10)))
                .andExpect(jsonPath("$.comment",is("")));
    }

    @Test
    void onCallGivenNameOfPiece_getReviewsByPieceName_returnReviews() throws Exception {
        given(reviewRepository.findReviewsByPieceName(reviewList.get(2).getPieceName())).willReturn(reviewList.subList(2, 4));
        mockMvc.perform(get("/piece/{name}", reviewList.get(2).getPieceName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].pieceName",is("Asturias")))
                .andExpect(jsonPath("$[0].rating",is(10)))
                .andExpect(jsonPath("$[0].comment",is("")))
                .andExpect(jsonPath("$[1].pieceName",is("Asturias")))
                .andExpect(jsonPath("$[1].rating",is(6)))
                .andExpect(jsonPath("$[1].comment",is("It's okay")));
    }

    @Test
    void whenPostReview_thenReturnJsonReview() throws Exception {
        given(reviewRepository.findAll()).willReturn(reviewList);
        Review newReview = new Review("Asturias", 2, ":(");
        mockMvc.perform(post("/")
                    .content(mapper.writeValueAsString(newReview))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pieceName",is("Asturias")))
                .andExpect(jsonPath("$.rating",is(2)))
                .andExpect(jsonPath("$.comment",is(":(")));
    }

    @Test
    void givenReview_whenPutReview_thenReturnJsonReview() throws Exception {
        Review newReview = new Review("Asturias", 2, ":(");
        newReview.setId("634d92404b48d018675459r8");
        given(reviewRepository.findReviewById("634d92404b48d018675459r8")).willReturn(newReview);
        Review updatedReview = new Review("Asturias", 5, "Maybe I was a bit drastic");
        mockMvc.perform(put("/{id}", newReview.getId())
                    .content(mapper.writeValueAsString(updatedReview))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pieceName",is("Asturias")))
                .andExpect(jsonPath("$.rating",is(5)))
                .andExpect(jsonPath("$.comment",is("Maybe I was a bit drastic")));
    }

    @Test
    void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
        Review review = new Review("Asturias", 2, "A comment");
        review.setId("E3");
        given(reviewRepository.findReviewById(review.getId())).willReturn(review);

        mockMvc.perform(delete("/{id}", review.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}