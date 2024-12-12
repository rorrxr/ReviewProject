package com.minju.reviewproject.service;

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

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Product product) {
        Review review = reviewRepository.save(new Review(requestDto, product));
        return new ReviewResponseDto(review);
    }

    @Transactional
    public void addReview(Long productId, MultipartFile image, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(productId)
                .orElseThrow(() -> new NullPointerException("Product not found"));

        String imageUrl = null;
        if (image != null) {
            // Dummy image URL handling
            imageUrl = "/dummy/image/url";
        }
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        Review review = reviewRepository.findById(productId)
                .orElseThrow(() -> new NullPointerException("Product not found"));

        List<Review> reviews = reviewRepository.findReviews(productId, cursor, size);
        int totalCount = reviewRepository.countByProductId(productId);
        float score = reviewRepository.getAverageScore(productId);

        Long newCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();
        return new ReviewResponseDto(review);
    }

    private float calculateNewAverage(float currentAvg, int totalReviews, int newScore) {
        return ((currentAvg * totalReviews) + newScore) / (totalReviews + 1);
    }
}

