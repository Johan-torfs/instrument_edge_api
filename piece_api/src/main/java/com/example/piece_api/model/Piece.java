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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }
}
