package com.example.edge_api.model;

public class Review {
    private String id;
    private int rating;
    private String comment;
    private String pieceName;
    private Piece piece;
    
    public Review() {}

    public Review(String pieceName, int rating) {
        this.pieceName = pieceName;
        this.rating = rating;
    }

    public Review(String pieceName, int rating, String comment) {
        this.pieceName = pieceName;
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

    public String getPieceName() {
        return pieceName;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
