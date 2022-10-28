package com.example.review_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private int rating;
    private String comment;
    private String pieceName;
    
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }
}
