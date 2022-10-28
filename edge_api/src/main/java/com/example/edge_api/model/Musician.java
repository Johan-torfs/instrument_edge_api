package com.example.edge_api.model;

public class Musician {
    private String id;
    private String name;
    private int birthYear;
    private int deathYear;
    private Instrument instrument;

    public Musician() {}

    public Musician(String name, int birthYear, int deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Musician(String name, int birthYear, int deathYear, Instrument instrument) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.instrument = instrument;
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
