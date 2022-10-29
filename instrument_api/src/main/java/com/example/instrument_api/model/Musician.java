package com.example.instrument_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Musician")
public class Musician implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "year_of_death")
    private int yearOfDeath;

    public Musician(String name, int yearOfBirth, int yearOfDeath, Instrument instrument) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
        this.instrument = instrument;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "instrument_id", nullable = false)
    private Instrument instrument;



}

