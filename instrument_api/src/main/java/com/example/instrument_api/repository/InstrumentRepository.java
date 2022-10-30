package com.example.instrument_api.repository;

import com.example.instrument_api.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Integer>  {

    List<Instrument> findByPeriod(String period);
    Instrument findInstrumentByName(String name);
    Instrument findInstrumentById(int id);
    List<Instrument> findInstrumentByPeriod(String name);

}
