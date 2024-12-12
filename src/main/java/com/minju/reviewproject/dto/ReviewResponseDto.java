package com.minju.reviewproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private int totalCount;
    private float score;
    private Long cursor;
    private List<ReviewDto> reviews;

    public ReviewResponseDto(List<ReviewDto> reviews, int totalCount, float score, Long cursor) {
        this.reviews = reviews;
        this.totalCount = totalCount;
        this.score = score;
        this.cursor = cursor;
    }
}

