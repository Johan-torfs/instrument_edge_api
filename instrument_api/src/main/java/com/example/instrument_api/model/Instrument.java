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
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    @Lob
    private String description;

    @Column(name="period")
    private String period;

    @Column(name="collection")
    private String collection;

    @OneToMany(mappedBy="instrument", cascade = CascadeType.ALL)
    private Set<Musician> musicians = new HashSet<>();

    @PrePersist
    public void onCreation(){
        musicians.forEach(m->{
            m.setInstrument(this);
        });
    }

}
