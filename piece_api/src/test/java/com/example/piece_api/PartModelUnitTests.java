package com.example.piece_api;

import com.example.piece_api.model.Part;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

@SpringBootTest
public class PartModelUnitTests {

    @Test
    public void setName_method() throws Exception {
        Part part = new Part();
        part.setName("Test");
        Assertions.assertEquals(part.getName(), "Test");
    }

    @Test
    public void setInstrument_method() throws Exception {
        Part part = new Part();
        part.setInstrument("Guitar");
        Assertions.assertEquals(part.getInstrument(), "Guitar");
    }
}
