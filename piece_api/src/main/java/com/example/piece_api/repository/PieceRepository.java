package com.example.piece_api.repository;

import com.example.piece_api.model.Piece;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceRepository extends MongoRepository<Piece, String> {
    @Query("{\"name\": { $regex: ?0 }}")
    List<Piece> findPieceByNameRegex(String name);
    @Query("{\"parts.instrument\": ?0 }")
    List<Piece> findPieceByInstrument(String name);
    List<Piece> findPieceByComposer(String name);
    List<Piece> findPieceByPeriod(String name);
}
