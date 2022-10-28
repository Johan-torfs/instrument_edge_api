package com.example.instrument_api.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Instrument")
public class Instrument implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="period")
    private String period;

    @Column(name="collection")
    private String collection;

    @OneToMany(mappedBy="instrument")
    private Set<Musician> musicians = new HashSet<>();

    public Instrument(String name, String description, String period, String collection) {
        this.name = name;
        this.description = description;
        this.period = period;
        this.collection = collection;
    }

}
