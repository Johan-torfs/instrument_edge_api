package com.example.edge_api.model;

public class Part {
    private int instrumentId;
    private Instrument instrument;
    private String name;

    public Part(int instrumentId, String name) {
        this.instrumentId = instrumentId;
        this.name = name;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public String getName() {
        return name;
    }
}
