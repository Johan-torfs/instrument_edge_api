package com.example.instrument_api.repository;

import com.example.instrument_api.model.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
    @Query("SELECT u FROM Musician u where :year between u.yearOfBirth and u.yearOfDeath")
    List<Musician> findAllByFromYear(@Param("year") int year);
    List<Musician> findMusicianByInstrument_Id(int id);
    List<Musician> findMusicianByInstrument_Name(String name);
}
