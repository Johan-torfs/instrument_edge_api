package com.example.counter_api;

import com.example.counter_api.model.Counter;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class CounterModelUnitTests {

    @Test
    void setId_method() throws Exception {
        Counter counter = new Counter();
        counter.setId("Test");
        Assertions.assertEquals("Test", counter.getId());
    }

    @Test
    void setInstrumentName_method() throws Exception {
        Counter counter = new Counter();
        counter.setInstrumentName("Test");
        Assertions.assertEquals("Test", counter.getInstrumentName());
    }

    @Test
    void setCount_method() throws Exception {
        Counter counter = new Counter();
        counter.setCount(5);
        Assertions.assertEquals(5, counter.getCount());
    }

    @Test
    void increment_method() throws Exception {
        Counter counter = new Counter();
        counter.increment();
        Assertions.assertEquals(1, counter.getCount());
        counter.increment();
        Assertions.assertEquals(2, counter.getCount());
    }
}
