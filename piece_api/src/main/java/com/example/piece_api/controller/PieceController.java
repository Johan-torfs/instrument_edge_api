package com.example.piece_api.controller;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import com.example.piece_api.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PieceController {
    @Autowired
    private PieceRepository pieceRepository;

    @PostConstruct
    public void fillDB() {
        for (Piece piece: pieceRepository.findAll()) {
            pieceRepository.delete(piece);
        }
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part(5, "Solo"));

        pieceRepository.save(new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Julia Florida", "20th century", "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Requerdos de la Alhambra", "19th century", "Francisco Tárrega", parts));
        pieceRepository.save(new Piece("Asturias", "19th century", "Isaac Albéniz", parts));

        System.out.println("Pieces added:");
        for (Piece piece: pieceRepository.findAll()) {
            System.out.println(piece);
        }

    }

    @GetMapping("/")
    public List<Piece> getPieces() {
        return pieceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Piece getPieceById(@PathVariable String id) {
        return pieceRepository.findPieceById(id);
    }

    @GetMapping("/name/{name}")
    public List<Piece> getPieceByName(@PathVariable String name) {
        return pieceRepository.findPieceByName(name);
    }

    @GetMapping("/instrument/{id}")
    public List<Piece> getPieceByInstrument(@PathVariable String id) {
        return pieceRepository.findPieceByInstrument(id);
    }

    @GetMapping("/composer/{name}")
    public List<Piece> getPieceByComposer(@PathVariable String name) {
        return pieceRepository.findPieceByComposer(name);
    }

    @GetMapping("/period/{name}")
    public List<Piece> getPieceByPeriod(@PathVariable String name) {
        return pieceRepository.findPieceByPeriod(name);
    }
}
