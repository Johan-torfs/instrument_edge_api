package com.example.piece_api.model;

public class Part {
    private int instrument;
    private String name;

    public Part(int instrument, String name) {
        this.instrument = instrument;
        this.name = name;
    }

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
