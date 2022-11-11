package com.example.counter_api;

import com.example.counter_api.model.Counter;
import com.example.counter_api.repository.CounterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class CounterControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CounterRepository counterRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void onCall_getCounters_returnAllCounters() throws Exception {
        Counter counter1 = new Counter("Guitar", 5);
        Counter counter2 = new Counter("Violin", 7);
        List<Counter> counterList = new ArrayList<>();
        counterList.add(counter1);
        counterList.add(counter2);
        given(counterRepository.findAll()).willReturn(counterList);

        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].instrumentName",is("Guitar")))
                .andExpect(jsonPath("$[0].count",is(5)))
                .andExpect(jsonPath("$[1].instrumentName",is("Violin")))
                .andExpect(jsonPath("$[1].count",is(7)));
    }

    @Test
    void onCallGivenIdOfCounter_getCounterById_returnCounter() throws Exception {
        Counter counter = new Counter("Guitar", 5);
        counter.setId("634d92404b48d018675459r4");
        given(counterRepository.findCounterById(counter.getId())).willReturn(counter);

        mockMvc.perform(get("/{id}", "634d92404b48d018675459r4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName",is("Guitar")))
                .andExpect(jsonPath("$.count",is(5)));
    }

    @Test
    void onCallGivenNameOfInstrument_getCountersByInstrumentName_returnCounter() throws Exception {
        Counter counter = new Counter("Guitar", 5);
        given(counterRepository.findCounterByInstrumentName("Guitar")).willReturn(counter);

        mockMvc.perform(get("/instrument/{name}", "Guitar"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName",is("Guitar")))
                .andExpect(jsonPath("$.count",is(5)));
    }

    @Test
    void whenPutCounterExisting_thenReturnJsonCounter() throws Exception {
        Counter counter = new Counter("Guitar", 5);
        given(counterRepository.findCounterByInstrumentName("Guitar")).willReturn(counter);

        mockMvc.perform(put("/{instrument}", "Guitar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName", is("Guitar")))
                .andExpect(jsonPath("$.count", is(6)));
    }

    @Test
    void whenPutCounterNonExisting_thenReturnJsonCounter() throws Exception {
        given(counterRepository.findCounterByInstrumentName("Piano")).willReturn(null);

        mockMvc.perform(put("/{instrument}", "Piano")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName", is("Piano")))
                .andExpect(jsonPath("$.count", is(1)));
    }
}