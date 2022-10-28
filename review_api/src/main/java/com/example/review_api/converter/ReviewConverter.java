package com.example.review_api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.review_api.dto.ReviewDTO;
import com.example.review_api.model.Review;

@Component
public class ReviewConverter {
    public ReviewDTO convertEntityToDto(Review review) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(review, ReviewDTO.class);
    }

    public Review convertDtoToEntity(ReviewDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Review.class);
    }
}
