package com.minju.reviewproject.service;

import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {
    public ReviewResponseDto getReviews(Long productId, int cursor, int size) {
        return null;
    }

    public void addReview(Long productId, MultipartFile image, ReviewRequestDto requestDto) {
    }
}
