package com.example.review_api;

import com.example.review_api.model.Review;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class ReviewModelUnitTests {

    @Test
    void setId_method() throws Exception {
        Review review = new Review();
        review.setId("Test");
        Assertions.assertEquals("Test", review.getId());
    }

    @Test
    void setRating_method() throws Exception {
        Review review = new Review();
        review.setRating(17);
        Assertions.assertEquals(17, review.getRating());
    }

    @Test
    void setComment_method() throws Exception {
        Review review = new Review();
        review.setComment("Test");
        Assertions.assertEquals("Test", review.getComment());
    }

    @Test
    void setPieceName_method() throws Exception {
        Review review = new Review();
        review.setPieceName("Test");
        Assertions.assertEquals("Test", review.getPieceName());
    }
}
