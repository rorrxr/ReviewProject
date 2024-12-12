package com.minju.reviewproject.dto;

import com.minju.reviewproject.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewDto {
    private Review review;
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

//    public static ReviewDto fromEntity(Review review) {
//        return new ReviewDto(
//                review.getId(),
//                review.getUserId(),
//                review.getScore(),
//                review.getContent(),
//                review.getImageUrl(),
//                review.getCreatedAt()
//        );
//    }
}
