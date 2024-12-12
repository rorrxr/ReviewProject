package com.minju.reviewproject.controller;

import com.minju.reviewproject.dto.ReviewRequestDto;
import com.minju.reviewproject.dto.ReviewResponseDto;
import com.minju.reviewproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(value = "/products/{productId}/review")
    public ResponseEntity<ReviewResponseDto> getReviews(@PathVariable Long productId,
                                                        @RequestParam(required = false) long cursor,
                                                        @RequestParam(defaultValue = "10") int size) {
        ReviewResponseDto response = reviewService.getReviews(productId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/products/{productId}/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(@PathVariable Long productId,
                                             @Validated @RequestPart ReviewRequestDto requestDto,
                                             @RequestPart(value = "image", required = false) MultipartFile file) {
        reviewService.addReview(productId, file, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
