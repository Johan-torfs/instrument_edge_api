package com.example.review_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.review_api.dto.ReviewDTO;

@Document(collection = "reviews")
public class Review extends ReviewDTO{
    @Id
    private String id;

    public Review() {}

    public Review(String pieceName, int rating) {
        this.pieceName = pieceName;
        this.rating = rating;
        this.comment = "";
    }

    public Review(String pieceName, int rating, String comment) {
        this.pieceName = pieceName;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
