package com.example.instrument_api.model;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Instrument")
public class Instrument implements Serializable {
    @Id
    @GeneratedValue
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

    public Instrument(String name, String description, String period, String collection) {
        this.name = name;
        this.description = description;
        this.period = period;
        this.collection = collection;
        this.musicians = new ArrayList<>();
    }

    @OneToMany(mappedBy="instrument",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Musician> musicians;

    @PrePersist
    public void onCreation(){
        musicians.forEach(musician->{
            musician.setInstrument(this);
        });
    }

}
