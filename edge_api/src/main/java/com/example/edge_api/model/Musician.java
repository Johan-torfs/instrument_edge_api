package com.example.edge_api.model;

import org.springframework.data.annotation.Id;

public class Musician {
    @Id
    private String id;
    private String name;
    private int birthYear;
    private int deathYear;
    private Instrument instrument;

    public Musician() {}

    public Musician(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = -1; //as a way to clarify the not-deadness?
    }

    public Musician(String name, int birthYear, int deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
