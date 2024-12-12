package com.minju.reviewproject.dto;

import com.minju.reviewproject.entity.Product;
import com.minju.reviewproject.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private int totalCount;
    private float score;
    private Long cursor;
    private List<ReviewDto> reviews;

    public ReviewResponseDto(Review review) {
        this.totalCount = getTotalCount();
        this.score = getScore();
        this.cursor = getCursor();
        this.reviews = getReviews();
    }
}
