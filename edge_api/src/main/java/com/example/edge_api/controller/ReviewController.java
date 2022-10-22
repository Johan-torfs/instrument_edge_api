package com.example.edge_api.controller;

import com.example.edge_api.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PutMapping("/review")
    public Review updateReview(@RequestParam Integer reviewId, @RequestParam Integer rating, @RequestParam String comment) {
        Review review = restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/{id}", Review.class, reviewId);
        if (review == null) return null;
        review.setRating(rating);
        review.setComment(comment);
        ResponseEntity<Review> responseEntity = restTemplate.exchange("http://" + reviewServiceBaseUrl + "/", HttpMethod.PUT, new HttpEntity<>(review), Review.class);
        Review editedReview = responseEntity.getBody();
        return editedReview;
    }

    @PostMapping("/review")
    public Review addReview(@RequestParam int pieceId, @RequestParam int rating, @RequestParam String comment) {
        Review review = restTemplate.postForObject("http://" + reviewServiceBaseUrl + "/", new Review(pieceId, rating, comment), Review.class);
        return review;
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int id) {
        restTemplate.delete("http://" + reviewServiceBaseUrl + "/{id}", id);
        return ResponseEntity.ok().build();
    }
}
