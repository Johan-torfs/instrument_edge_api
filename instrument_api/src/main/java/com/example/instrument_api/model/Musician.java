package com.example.instrument_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Musician")
public class Musician implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="year_of_birth")
    private int yearOfBirth;

    @Column(name="year_of_death")
    private int yearOfDeath;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="instrument_id", nullable=false)
    private Instrument instrument;

}
