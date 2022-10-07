package com.example.piece_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "pieces")
public class Piece {
    @Id
    private String id;
    private String name;
    private String period;
    private String composer;

    @JsonProperty("parts")
    private ArrayList<Part> parts;

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
}
