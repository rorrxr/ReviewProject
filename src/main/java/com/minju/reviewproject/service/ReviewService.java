package com.minju.reviewproject.service;

import com.minju.reviewproject.dto.ReviewDto;
import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import com.minju.reviewproject.entity.Product;
import com.minju.reviewproject.entity.Review;
import com.minju.reviewproject.entity.User;
import com.minju.reviewproject.repository.ProductRepository;
import com.minju.reviewproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReview(Long productId, MultipartFile image, ReviewRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));

        if (reviewRepository.existsByProductIdAndUserId(productId, requestDto.getUserId())) {
            throw new IllegalStateException("User has already reviewed this product");
        }

        String imageUrl = null;
        if (image != null) {
            imageUrl = "/dummy/image/url"; // 더미 데이터
        }

        Review review = new Review(requestDto, product, imageUrl);
        reviewRepository.save(review);

        synchronized (product) {
            product.setReviewCount(product.getReviewCount() + 1);
            product.setScore(calculateNewAverage(product.getScore(), product.getReviewCount(), requestDto.getScore()));
            productRepository.save(product);
        }
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<Review> reviews = reviewRepository.findReviews(productId, cursor, size);
        int totalCount = reviewRepository.countByProductId(productId);
        float score = reviewRepository.getAverageScore(productId);

        Long newCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

        // Convert Review to ReviewDto
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getUserId(),
                        review.getScore(),
                        review.getContent(),
                        review.getImageUrl(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ReviewResponseDto(reviewDtos, totalCount, score, newCursor);
    }

    private float calculateNewAverage(float currentAvg, int totalReviews, int newScore) {
        return ((currentAvg * totalReviews) + newScore) / (totalReviews + 1);
    }
}
