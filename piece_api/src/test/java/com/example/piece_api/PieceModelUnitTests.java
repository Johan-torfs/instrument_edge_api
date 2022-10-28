package com.example.piece_api;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

@SpringBootTest
class PieceModelUnitTests {

    @Test
    void setName_method() throws Exception {
        Piece piece = new Piece();
        piece.setName("Test");
        Assertions.assertEquals(piece.getName(), "Test");
    }

    @Test
    void setPeriod_method() throws Exception {
        Piece piece = new Piece();
        piece.setPeriod("Test");
        Assertions.assertEquals(piece.getPeriod(), "Test");
    }

    @Test
    void setComposer_method() throws Exception {
        Piece piece = new Piece();
        piece.setComposer("Test");
        Assertions.assertEquals(piece.getComposer(), "Test");
    }

    @Test
    void setParts_method() throws Exception {
        Piece piece = new Piece();

        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Guitar", "Solo"));

        piece.setParts(parts);
        Assertions.assertEquals(piece.getParts(), parts);
    }
}
