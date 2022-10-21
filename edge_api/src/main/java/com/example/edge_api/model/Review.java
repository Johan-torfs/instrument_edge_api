package com.example.edge_api.model;

import org.springframework.data.annotation.Id;

public class Review {
    @Id
    private String id;
    private int rating;
    private String comment;
    private Piece piece;
    
    public Review() {}

    public Review(Piece piece, int rating) {
        this.piece = piece;
        this.rating = rating;
    }

    public Review(Piece piece, int rating, String comment) {
        this.piece = piece;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Piece getPiece() {
        return piece;
    }
}
