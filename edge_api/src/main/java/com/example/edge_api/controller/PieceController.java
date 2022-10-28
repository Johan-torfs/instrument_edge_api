package com.example.edge_api.controller;

import com.example.edge_api.model.Instrument;
import com.example.edge_api.model.Part;
import com.example.edge_api.model.Piece;
import com.example.edge_api.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class PieceController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${instrumentservice.baseurl}")
    private String instrumentServiceBaseUrl;
    @Value("${pieceservice.baseurl}")
    private String pieceServiceBaseUrl;
    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    @GetMapping("/piece/{name}") //Currently errors because of instrument absence
    public List<Piece> getPieceWithReviewsAndPartsAndInstrument(@PathVariable String name) {
        Piece[] pieces = restTemplate.getForObject(pieceServiceBaseUrl + "/name/{name}", Piece[].class, name);
        if (pieces.length < 1) {
            return new ArrayList<>();
        }
        for (Piece piece : pieces) {
            for (Part part : piece.getParts()) {
                part.setInstrumentObj(restTemplate.getForObject(instrumentServiceBaseUrl + "/instrument/{name}", Instrument.class, part.getInstrument()));
            }

            List<Review> reviews = Arrays.asList(restTemplate.getForObject(reviewServiceBaseUrl + "/piece/{name}", Review[].class, piece.getName()));
            piece.setReviews(reviews);
        }

        return Arrays.asList(pieces);
    }

    @GetMapping("/piece")
    public List<Piece> getPieceList() {
        List<Piece> pieces = Arrays.asList(restTemplate.getForObject(pieceServiceBaseUrl + "/", Piece[].class));
        return pieces; //not including instruments or reviews
    }
}
