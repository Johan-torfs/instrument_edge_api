package com.example.instrument_api.repository;

import com.example.instrument_api.model.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
//    List<Musician> findByYear(int year_of_birth);



}
