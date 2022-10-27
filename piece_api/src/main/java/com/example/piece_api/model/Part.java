package com.example.piece_api.model;

public class Part {
    private int instrumentId;
    private String name;

    public Part() {
        this.instrumentId = 5;
        this.name = "Solo";
    }

    public Part(int instrumentId, String name) {
        this.instrumentId = instrumentId;
        this.name = name;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
