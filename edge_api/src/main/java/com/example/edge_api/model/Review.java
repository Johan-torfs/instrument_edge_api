package com.example.edge_api.model;

import org.springframework.data.annotation.Id;

public class Review {
    @Id
    private String id;
    private int rating;
    private String comment;
    private int pieceId;
    private Piece piece;
    
    public Review() {}

    public Review(int pieceId, int rating) {
        this.pieceId = pieceId;
        this.rating = rating;
    }

    public Review(int pieceId, int rating, String comment) {
        this.pieceId = pieceId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
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

    public int getPieceId() {
        return pieceId;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
