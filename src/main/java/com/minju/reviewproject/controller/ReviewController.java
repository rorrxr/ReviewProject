package com.minju.reviewproject.controller;

import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import com.minju.reviewproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(value = "/{productId}/reviews")
    public ResponseEntity<ReviewResponseDto> getReviews(@PathVariable Long productId,
                                                        @RequestParam(required = false, defaultValue = "0") Long cursor,
                                                        @RequestParam(defaultValue = "10") int size) {
        if (size <= 0 || size > 100) {
            return ResponseEntity.badRequest().body(null);
        }
        ReviewResponseDto response = reviewService.getReviews(productId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{productId}/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(@PathVariable Long productId,
                                             @Valid @RequestPart ReviewRequestDto requestDto,
                                             @RequestPart(value = "image", required = false) MultipartFile file) {
        reviewService.addReview(productId, file, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
