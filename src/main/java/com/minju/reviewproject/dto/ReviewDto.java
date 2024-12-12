package com.minju.reviewproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long userId;
    private int score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    public ReviewDto(Long id, Long userId, int score, String content, String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}
