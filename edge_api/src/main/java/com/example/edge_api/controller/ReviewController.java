package com.example.edge_api.controller;

import com.example.edge_api.model.Review;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ReviewController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    private String idString = "/{id}";

    @PutMapping("/review/{id}") //Here and in post, might have to be RequestBody instead, as done in ReviewApi, but this works for now
    public Review updateReview(@PathVariable String id, @RequestParam Integer rating, @RequestParam String comment) {
        Review review = restTemplate.getForObject(reviewServiceBaseUrl + idString, Review.class, id);
        if (review == null) return null;
        review.setRating(rating);
        review.setComment(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> entity = new HttpEntity<>(review, headers);
        return restTemplate.exchange(reviewServiceBaseUrl + idString, HttpMethod.PUT, entity, Review.class, id).getBody();
    }

    @GetMapping("/review")
    public List<Review> getReviews() {
        return Arrays.asList(restTemplate.getForObject(reviewServiceBaseUrl + "/", Review[].class));
    }

    @GetMapping("/review/{id}")
    public Review getReviewById(@PathVariable String id) {
        return restTemplate.getForObject(reviewServiceBaseUrl + idString, Review.class, id);
    }

    @PostMapping("/review")
    public Review addReview(@RequestParam String pieceName, @RequestParam int rating, @RequestParam String comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> entity = new HttpEntity<>(new Review(pieceName, rating, comment), headers);
        return restTemplate.postForObject(reviewServiceBaseUrl + "/", entity, Review.class);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id) {
        restTemplate.delete(reviewServiceBaseUrl + idString, id);
        return ResponseEntity.ok().build();
    }
}
