package com.minju.reviewproject.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long userId;
    private int score;
    private String content;
}
