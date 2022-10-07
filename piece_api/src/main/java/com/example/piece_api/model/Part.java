package com.example.piece_api.model;

public class Part {
    private String instrument;
    private String name;

    public Part(String instrument, String name) {
        this.instrument = instrument;
        this.name = name;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
