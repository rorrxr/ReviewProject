package com.minju.reviewproject.entity;

import com.minju.reviewproject.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Long userId;

    private int score;

    private String content;

    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Review(ReviewRequestDto requestDto, Product product, String imageUrl) {
        this.product = product;
        this.userId = requestDto.getUserId();
        this.score = requestDto.getScore();
        this.content = requestDto.getContent();
        this.imageUrl = imageUrl;
    }
}
