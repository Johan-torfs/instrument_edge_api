package com.example.instrument_api.repository;

import com.example.instrument_api.model.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
    List<Musician> findByName(int year_of_birth);
}
