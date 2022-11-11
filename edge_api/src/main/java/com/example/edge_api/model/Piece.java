package com.example.edge_api.model;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private String id;
    private String name;
    private String period;
    private String composer;

    private List<Part> parts;
    private List<Review> reviews;

    public Piece() {}

    public Piece(String name, String period, String composer, List<Part> parts) {
        this.name = name;
        this.period = period;
        this.composer = composer;
        this.parts = parts;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPeriod() {
        return period;
    }

    public String getComposer() {
        return composer;
    }

    public List<Part> getParts() {
        return parts;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
