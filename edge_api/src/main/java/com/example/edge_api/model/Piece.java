package com.example.edge_api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Piece {
    @Id
    private String id;
    private String name;
    private String period;
    private String composer;

    private ArrayList<Part> parts;

    public Piece() {}

    public Piece(String name) {
        this.name = name;
    }

    public Piece(String name, String period, String composer, ArrayList<Part> parts) {
        this.name = name;
        this.period = period;
        this.composer = composer;
        this.parts = parts;
    }

    public Piece(String name, String period, String composer) {
        this.name = name;
        this.period = period;
        this.composer = composer;
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

    public ArrayList<Part> getParts() {
        return parts;
    }
}
