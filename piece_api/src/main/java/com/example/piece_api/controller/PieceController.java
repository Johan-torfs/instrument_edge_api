package com.example.piece_api.controller;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import com.example.piece_api.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import javax.annotation.PostConstruct;
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

        String century20 = "20th century";
        String century19 = "19th century";
        String century18 = "18th century";
        String century17 = "17th century";

        // Guitar
        ArrayList<Part> parts = new ArrayList<>();
        parts.add(new Part("Guitar", "Solo"));

        pieceRepository.save(new Piece("Una Limosna por el Amor de Dios", century20, "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Julia Florida", century20, "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Requerdos de la Alhambra", century19, "Francisco Tárrega", parts));
        pieceRepository.save(new Piece("Asturias", century19, "Isaac Albéniz", parts));

        // Piano
        parts = new ArrayList<>();
        parts.add(new Part("Piano", "Solo"));

        pieceRepository.save(new Piece("Pachelbel Canon in D", century17, "Johann Pachelbel", parts));
        pieceRepository.save(new Piece("Beethoven Piano Sonata No. 14", century19, "Ludwig van Beethoven", parts));
        pieceRepository.save(new Piece("Für Elise", century19, "Ludwig van Beethoven", parts));

        // Violin
        parts = new ArrayList<>();
        parts.add(new Part("Violin", "Solo"));

        pieceRepository.save(new Piece("Bach Sonatas and Partitas for Solo Violin", century18, "Johann Sebastian Bach", parts));
        pieceRepository.save(new Piece("Bartók Sonata for Solo Violin", century20, "Béla Bartók", parts));

        // Orchestral
        parts = new ArrayList<>();
        parts.add(new Part("Violin", "Main"));
        parts.add(new Part("Cello", "Base"));
        parts.add(new Part("Harpsichord", "Base"));

        pieceRepository.save(new Piece("Vivaldi The Four Seasons La primavera", "18th century", "Antonio Vivaldi", parts));

        Logger.info("Pieces added");

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
        return pieceRepository.findPieceByNameRegex(name);
    }

    @GetMapping("/instrument/{name}")
    public List<Piece> getPieceByInstrument(@PathVariable String name) {
        return pieceRepository.findPieceByInstrument(name);
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
