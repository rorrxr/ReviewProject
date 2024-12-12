package com.minju.reviewproject.service;

import com.minju.reviewproject.dto.ReviewDto;
import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import com.minju.reviewproject.entity.Product;
import com.minju.reviewproject.entity.Review;
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
        validateScore(requestDto.getScore());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));

        if (reviewRepository.existsByProductIdAndUserId(productId, requestDto.getUserId())) {
            throw new IllegalStateException("사용자가 이미 이 제품에 대한 리뷰를 작성했습니다");
        }

        String imageUrl = null;
        if (image != null) {
            imageUrl = "/dummy/image/url"; // 더미 데이터
        }

        synchronized (product) {
            Review review = new Review(requestDto, product, imageUrl);
            reviewRepository.save(review);

            int newReviewCount = product.getReviewCount() + 1;
            float newScore = calculateNewAverage(product.getScore(), product.getReviewCount(), requestDto.getScore());
            product.setReviewCount(newReviewCount);
            product.setScore(newScore);

            productRepository.save(product);
        }
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));

        List<Review> reviews = reviewRepository.findReviews(productId, cursor, size);
        int totalCount = reviewRepository.countByProductId(productId);
        float score = reviewRepository.getAverageScore(productId);

        Long newCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

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

    private void validateScore(int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("점수는 1에서 5 사이여야 합니다");
        }
    }

    private float calculateNewAverage(float currentAvg, int totalReviews, int newScore) {
        return ((currentAvg * totalReviews) + newScore) / (totalReviews + 1);
    }
}
