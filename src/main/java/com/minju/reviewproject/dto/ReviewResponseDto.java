package com.minju.reviewproject.dto;

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
}
