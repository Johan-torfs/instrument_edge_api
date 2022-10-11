package com.example.review_api.controller;

import javax.annotation.PostConstruct;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.review_api.model.Review;
import com.example.review_api.repository.ReviewRepository;

@RestController
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void fillDB() {
        for (Review review: reviewRepository.findAll()) {
            reviewRepository.delete(review);
        }

        reviewRepository.save(new Review("Una Limosna por el Amor de Dios", 10, "My favourite piece of all time!"));
        reviewRepository.save(new Review("Una Limosna por el Amor de Dios", 7, "Gets repetitive after a while"));
        reviewRepository.save(new Review("Julia Florida", 3, "I don't get how anyone can like this..."));
        reviewRepository.save(new Review("Asturias", 7));

        System.out.println("Reviews added:");
        for (Review review: reviewRepository.findAll()) {
            System.out.println(review);
        }

    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable String id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<Review> getReviewsByPieceName(@PathVariable String name) {
        return reviewRepository.findReviewsByPieceName(name);
    }

    @PostMapping("/")
    public Review newReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @PutMapping("/{id}")
    public Review editReview(@RequestBody Review newReview, @PathVariable String id) {
        return reviewRepository.findById(id)
            .map(review -> {
                review.setComment(newReview.getComment());
                review.setRating(newReview.getRating());
                return reviewRepository.save(review);
            })
            .orElseGet(() -> {
                return reviewRepository.save(newReview);
            });

    }
}
