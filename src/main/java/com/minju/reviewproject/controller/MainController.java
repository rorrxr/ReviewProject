package com.minju.reviewproject.controller;

import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import com.minju.reviewproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RestController
public class MainController {

    private final ReviewService reviewService;

    @GetMapping("/products/{productId}/review")
    public ResponseEntity<ReviewResponseDto> getReviews(@PathVariable Long productId,
                                                        @RequestParam(required = false) long cursor,
                                                        @RequestParam(defaultValue = "10") int size) {
        ReviewResponseDto response = reviewService.getReviews(productId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Void> addReview(
            @PathVariable Long productId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestPart ReviewRequestDto requestDto) {
        reviewService.addReview(productId, image, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
