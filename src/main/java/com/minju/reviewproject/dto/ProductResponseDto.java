package com.minju.reviewproject.dto;

import com.minju.reviewproject.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private int reviewCount;
    private Float score;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.reviewCount = product.getReviewCount();
        this.score = product.getScore();
    }
}
