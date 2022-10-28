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
import org.tinylog.Logger;

@RestController
public class ReviewController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    @PutMapping("/review/{id}") //Here and in post, might have to be RequestBody instead, as done in ReviewApi, but this works for now
    public Review updateReview(@PathVariable String id, @RequestParam Integer rating, @RequestParam String comment) {
        Review review = restTemplate.getForObject(reviewServiceBaseUrl + "/{id}", Review.class, id);
        if (review == null) return null;
        review.setRating(rating);
        review.setComment(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> entity = new HttpEntity<Review>(review, headers);
        Review editedReview = restTemplate.exchange(reviewServiceBaseUrl + "/{id}", HttpMethod.PUT, entity, Review.class, id).getBody();
        return editedReview;
    }

    @GetMapping("/review")
    public List<Review> getReviews() {
        List<Review> reviews = Arrays.asList(restTemplate.getForObject(reviewServiceBaseUrl + "/", Review[].class));
        return reviews;
    }

    @GetMapping("/review/{id}")
    public Review getReviewById(@PathVariable String id) {
        Review review = restTemplate.getForObject(reviewServiceBaseUrl + "/{id}", Review.class, id);
        return review;
    }

    @PostMapping("/review")
    public Review addReview(@RequestParam String pieceName, @RequestParam int rating, @RequestParam String comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> entity = new HttpEntity<Review>(new Review(pieceName, rating, comment), headers);
        Review newReview = restTemplate.postForObject(reviewServiceBaseUrl + "/", entity, Review.class);
        return newReview;
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id) {
        restTemplate.delete(reviewServiceBaseUrl + "/{id}", id);
        return ResponseEntity.ok().build();
    }
}
