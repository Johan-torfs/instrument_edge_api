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

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/piece/{name}")
    public Piece getPieceWithReviewsAndPartsAndInstrument(@PathVariable String name) {
        Piece[] pieces = restTemplate.getForObject("http://" + pieceServiceBaseUrl + "/name/{name}", Piece[].class, name);
        if (pieces == null || pieces.length < 1) {
            return null;
        }
        Piece piece = pieces[0];
        for (Part part : piece.getParts()) {
            part.setInstrument(restTemplate.getForObject("http://" + instrumentServiceBaseUrl + "/instrument/{id}", Instrument.class, part.getInstrumentId()));
        }
        List<Review> reviews = Arrays.asList(restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/piece/{id}", Review[].class, piece.getId()));
        piece.setReviews(reviews);
        return piece;
    }

    @GetMapping("/piece")
    public List<Piece> getPieceList() {
        List<Piece> pieces = Arrays.asList(restTemplate.getForObject("http://" + pieceServiceBaseUrl + "/", Piece[].class));
        return pieces; //not including instruments or reviews
    }
}
