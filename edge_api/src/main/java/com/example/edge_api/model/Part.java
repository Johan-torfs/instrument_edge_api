package com.example.edge_api.model;

public class Part {
    private String instrument;
    private String name;
    private Instrument instrumentObj;

    public Part(){}
    public Part(String instrument, String name) {
        this.instrument = instrument;
        this.name = name;
    }

    public String getInstrument() {
        return instrument;
    }

    public Instrument getInstrumentObj() {
        return instrumentObj;
    }

    public void setInstrumentObj(Instrument instrumentObj) {
        this.instrumentObj = instrumentObj;
    }

    public String getName() {
        return name;
    }
}
