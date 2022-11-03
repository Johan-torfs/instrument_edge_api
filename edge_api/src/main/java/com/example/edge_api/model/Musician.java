package com.example.edge_api.model;

public class Musician {
    private String id;
    private String name;
    private int yearOfBirth;
    private int yearOfDeath;
    private Instrument instrument;

    public Musician() {}

    public Musician(String name, int yearOfBirth, int yearOfDeath) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
    }

    public Musician(String name, int yearOfBirth, int yearOfDeath, Instrument instrument) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
        this.instrument = instrument;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public int getYearOfDeath() {
        return yearOfDeath;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
