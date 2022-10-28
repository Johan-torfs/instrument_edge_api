package com.example.piece_api.controller;

import com.example.piece_api.model.Part;
import com.example.piece_api.model.Piece;
import com.example.piece_api.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

        // Guitar
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Guitar", "Solo"));

        pieceRepository.save(new Piece("Una Limosna por el Amor de Dios", "20th century", "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Julia Florida", "20th century", "Agustin Barrios Mangoré", parts));
        pieceRepository.save(new Piece("Requerdos de la Alhambra", "19th century", "Francisco Tárrega", parts));
        pieceRepository.save(new Piece("Asturias", "19th century", "Isaac Albéniz", parts));

        // Piano
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Piano", "Solo"));

        pieceRepository.save(new Piece("Canon in D (Pachelbel)", "17th century", "Johann Pachelbel", parts));
        pieceRepository.save(new Piece("Piano Sonata No. 14 (Beethoven)", "19th century", "Ludwig van Beethoven", parts));
        pieceRepository.save(new Piece("Für Elise", "19th century", "Ludwig van Beethoven", parts));

        // Violin
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Violin", "Solo"));

        pieceRepository.save(new Piece("Sonatas and Partitas for Solo Violin (Bach)", "18th century", "Johann Sebastian Bach", parts));
        pieceRepository.save(new Piece("Sonata for Solo Violin (Bartók)", "20th century", "Béla Bartók", parts));

        // Orchestral
        ArrayList<Part> parts = new ArrayList<Part>();
        parts.add(new Part("Violin", "Main"));
        parts.add(new Part("Cello", "Base"));
        parts.add(new Part("Harpsichord", "Base"));

        pieceRepository.save(new Piece("The Four Seasons (Vivaldi): La primavera", "18th century", "Antonio Vivaldi", parts));

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
