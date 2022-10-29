package com.example.edge_api.model;

public class Part {
    private int instrumentId;
    private String name;
    private Instrument instrument;

    public Part(){}
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

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getName() {
        return name;
    }
}
