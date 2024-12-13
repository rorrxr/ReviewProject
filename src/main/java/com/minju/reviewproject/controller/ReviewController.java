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
                                                        @RequestParam(required = false) long cursor,
                                                        @RequestParam(defaultValue = "10") int size) {
        ReviewResponseDto response = reviewService.getReviews(productId, cursor, size);
        return ResponseEntity.ok(response);
    }

//    @PostMapping(value = "/{productId}/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Void> createReview(@PathVariable Long productId,
//                                             @Valid @RequestPart(value = "requestDto") ReviewRequestDto requestDto,
//                                             @RequestPart(value = "image", required = false) MultipartFile file) {
//        reviewService.addReview(productId, file, requestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping(value = "/{productId}/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(
            @PathVariable Long productId,
            @RequestPart(value = "requestDto") @Valid ReviewRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile file) {
        reviewService.addReview(productId, file, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
