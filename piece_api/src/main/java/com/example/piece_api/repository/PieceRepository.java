package com.example.piece_api.repository;

import com.example.piece_api.model.Piece;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceRepository extends MongoRepository<Piece, String> {
    Piece findPieceById(String id);
    List<Piece> findPieceByName(String name);
    List<Piece> findPieceByInstrument(String name);
    List<Piece> findPieceByComposer(String name);
    List<Piece> findPieceByPeriod(String name);
}
