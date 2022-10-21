package com.example.edge_api.model;

import org.springframework.data.annotation.Id;

public class Instrument {
    @Id
    private String id;
    private String name;
    private String period;
    private String description;
    private String group;

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
}
