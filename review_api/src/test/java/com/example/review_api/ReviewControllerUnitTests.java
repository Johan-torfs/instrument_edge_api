package com.example.review_api;

import com.example.review_api.model.Review;
import com.example.review_api.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    private MockMvc mockMvc;

    @MockBean
    private ReviewRepository reviewRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void onCall_getReviews_returnAllReviews() throws Exception {
        Review review1 = new Review("Piece1", 5, "Comment1");
        Review review2 = new Review("Piece2", 7, "Comment2");
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);
        given(reviewRepository.findAll()).willReturn(reviewList);

        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].pieceName",is("Piece1")))
                .andExpect(jsonPath("$[0].rating",is(5)))
                .andExpect(jsonPath("$[0].comment",is("Comment1")))
                .andExpect(jsonPath("$[1].pieceName",is("Piece2")))
                .andExpect(jsonPath("$[1].rating",is(7)))
                .andExpect(jsonPath("$[1].comment",is("Comment2")));
    }

    @Test
    void onCallGivenIdOfReviewWithComment_getReviewById_returnReview() throws Exception {
        Review review = new Review("Piece1",5,"Comment1");
        review.setId("ABC1");
        given(reviewRepository.findReviewById(review.getId())).willReturn(review);

        mockMvc.perform(get("/{id}", review.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Piece1")))
                .andExpect(jsonPath("$.rating",is(5)))
                .andExpect(jsonPath("$.comment",is("Comment1")));
    }

    @Test
    void onCallGivenIdOfReviewWithoutComment_getReviewById_returnReview() throws Exception {
        Review review = new Review("Piece1",5);
        review.setId("ABC2");
        given(reviewRepository.findReviewById(review.getId())).willReturn(review);

        mockMvc.perform(get("/{id}", review.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pieceName",is("Piece1")))
                .andExpect(jsonPath("$.rating",is(5)))
                .andExpect(jsonPath("$.comment",is("")));
    }

    @Test
    void onCallGivenNameOfPiece_getReviewsByPieceName_returnReviews() throws Exception {
        Review review1 = new Review("Piece1", 5, "Comment1");
        Review review2 = new Review("Piece1", 7, "Comment2");
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);
        given(reviewRepository.findReviewsByPieceName(reviewList.get(0).getPieceName())).willReturn(reviewList);

        mockMvc.perform(get("/piece/{name}", reviewList.get(0).getPieceName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].pieceName",is("Piece1")))
                .andExpect(jsonPath("$[0].rating",is(5)))
                .andExpect(jsonPath("$[0].comment",is("Comment1")))
                .andExpect(jsonPath("$[1].pieceName",is("Piece1")))
                .andExpect(jsonPath("$[1].rating",is(7)))
                .andExpect(jsonPath("$[1].comment",is("Comment2")));
    }
    //For the next 3 tests, something is wrong in how the repository saves data, this returns null
    @Test
    void whenPostReview_thenReturnJsonReview() throws Exception {
        Review review1 = new Review("Piece1", 5, "Comment1");

        mockMvc.perform(post("/")
                    .content(mapper.writeValueAsString(review1))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pieceName",is("Piece1")))
                .andExpect(jsonPath("$.rating",is(5)))
                .andExpect(jsonPath("$.comment",is("Comment1")));
    }

    @Test
    void givenReview_whenPutReview_thenReturnJsonReview() throws Exception {
        Review review = new Review("Piece1",5, "Comment1");
        review.setId("ABC3");
        given(reviewRepository.findReviewById(review.getId())).willReturn(review);
        Review updatedReview = new Review("Piece1", 4, "Comment2");

        mockMvc.perform(put("/{id}", review.getId())
                    .content(mapper.writeValueAsString(updatedReview))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pieceName",is("Piece1")))
                .andExpect(jsonPath("$.rating",is(4)))
                .andExpect(jsonPath("$.comment",is("Comment2")));
    }

    @Test
    void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
        Review review = new Review("Piece1",5, "Comment1");
        review.setId("ABC4");
        given(reviewRepository.findReviewById(review.getId())).willReturn(review);

        mockMvc.perform(delete("/{id}", review.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception {
        given(reviewRepository.findReviewById("ABC5")).willReturn(null);

        mockMvc.perform(delete("/{id}", "ABC5")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}