package com.example.counter_api;

import com.example.counter_api.model.Counter;
import com.example.counter_api.repository.CounterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class CounterControllerIntegrationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CounterRepository counterRepository;
    private List<Counter> counterList = new ArrayList<Counter>();
    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        //NOTE: deletes everything in the counter database
        counterRepository.deleteAll();

        Counter counter1 = new Counter("Guitar", 4);
        counter1.setId("634d92404b48d018675459r4");
        Counter counter2 = new Counter("Violin", 1);
        counter2.setId("634d92404b48d018675459r5");

        counterList.add(counter1);
        counterList.add(counter2);

        counterRepository.save(counter1);
        counterRepository.save(counter2);
    }

    @AfterEach
    public void afterAllTests() {
        //Also deletes other test data
        counterRepository.deleteAll();
    }

    @Test
    void onCall_getCounters_returnAllCounters() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].instrumentName",is("Guitar")))
                .andExpect(jsonPath("$[0].count",is(4)))
                .andExpect(jsonPath("$[1].instrumentName",is("Violin")))
                .andExpect(jsonPath("$[1].count",is(1)));
    }

    @Test
    void onCallGivenIdOfCounter_getCounterById_returnCounter() throws Exception {
        mockMvc.perform(get("/{id}", counterList.get(0).getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName",is("Guitar")))
                .andExpect(jsonPath("$.count",is(4)));
    }

    @Test
    void onCallGivenNameOfInstrument_getCountersByInstrumentName_returnCounter() throws Exception {
        mockMvc.perform(get("/instrument/{name}", "Guitar"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName",is("Guitar")))
                .andExpect(jsonPath("$.count",is(4)));
    }

    @Test
    void whenPutCounterExisting_thenReturnJsonCounter() throws Exception {

        mockMvc.perform(put("/{instrument}", "Violin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName", is("Violin")))
                .andExpect(jsonPath("$.count", is(2)));
    }

    @Test
    void whenPutCounterNonExisting_thenReturnJsonCounter() throws Exception {

        mockMvc.perform(put("/{instrument}", "Piano")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentName", is("Piano")))
                .andExpect(jsonPath("$.count", is(1)));
    }
}
