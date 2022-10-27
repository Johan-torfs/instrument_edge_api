package com.example.edge_api.model;

import java.util.List;

public class Instrument {
    private String id;
    private String name;
    private String period;
    private String description;
    private String group;
    private List<Musician> musicians;
    private List<Piece> pieces;

    public Instrument() {}

    public Instrument(String name, String period, String description, String group) {
        this.name = name;
        this.period = period;
        this.description = description;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public void setMusicians(List<Musician> musicians) {
        this.musicians = musicians;
    }

    public List<Musician> getMusicians() {
        return musicians;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
