package com.example.piece_api;

import com.example.piece_api.model.Part;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class PartModelUnitTests {

    @Test
    void setName_method() throws Exception {
        Part part = new Part();
        part.setName("Test");
        Assertions.assertEquals("Test", part.getName());
    }

    @Test
    void setInstrument_method() throws Exception {
        Part part = new Part();
        part.setInstrument("Guitar");
        Assertions.assertEquals("Guitar", part.getInstrument());
    }
}
