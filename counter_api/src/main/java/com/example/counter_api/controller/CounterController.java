package com.example.counter_api.controller;

import com.example.counter_api.model.Counter;
import com.example.counter_api.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class CounterController {
    @Autowired
    private CounterRepository counterRepository;
    
    @GetMapping("/")
    public List<Counter> getCounters() {
        return counterRepository.findAll();
    }

    @GetMapping("/{id}")
    public Counter getCounterById(@PathVariable String id) {
        return counterRepository.findCounterById(id);
    }

    @GetMapping("/instrument/{name}")
    public Counter getCounterByInstrumentName(@PathVariable String name) {
        return counterRepository.findCounterByInstrumentName(name);
    }

    @PutMapping("/{instrument}")
    public Counter incrementCounter(@PathVariable String instrument) {
        Counter counter = counterRepository.findCounterByInstrumentName(instrument);
        if (counter == null) {
            counter = new Counter(instrument);
        }
        counter.increment();
        counterRepository.save(counter);
        return counter;
    }
}
