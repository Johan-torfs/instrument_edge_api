package com.example.counter_api.repository;

import com.example.counter_api.model.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends MongoRepository<Counter, String> {
    Counter findCounterByInstrumentName(String name);
    Counter findCounterById(String id);
}
