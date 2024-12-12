package com.minju.reviewproject.service;

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

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 multipartfile 추가
    @Transactional
    public void addReview(Long productId, MultipartFile image, ReviewRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));

        if (reviewRepository.existsByProductIdAndUserId(productId, requestDto.getUserId())) {
            throw new IllegalStateException("사용자가 이미 이 제품에 대한 리뷰를 작성했습니다");
        }

        List<MultipartFile> files;

        // 파일 처리를 위한 Board 객체 생성
//        Board board = new Board(
//                requestDto.getMember(),
//                requestDto.getTitle(),
//                requestDto.getContent()
//        );
//
//        List<Photo> photoList = fileHandler.parseFileInfo(files);
//
//        // 파일이 존재할 때에만 처리
//        if(!photoList.isEmpty()) {
//            for(Photo photo : photoList) {
//                // 파일을 DB에 저장
//                board.addPhoto(photoRepository.save(photo));
//            }
//        }
//
//        return boardRepository.save(board).getId();

        String imageUrl = null;
        if (image != null) {
            imageUrl = "/dummy/image/url"; // 더미 데이터
        }

        Review review = new Review(requestDto, product, imageUrl);
        reviewRepository.save(review);
    }

    // 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));

        List<Review> reviews = reviewRepository.findReviews(productId, cursor, size);
        int totalCount = reviewRepository.countByProductId(productId);
        float score = reviewRepository.getAverageScore(productId);

        Long newCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

//        List<ReviewDto> reviewDtos = reviews.stream()
//                .map(ReviewDto::fromEntity)
//                .collect(Collectors.toList());

        return new ReviewResponseDto(reviewDtos, totalCount, score, newCursor);
    }

    private float calculateNewAverage(float currentAvg, int totalReviews, int newScore) {
        return ((currentAvg * totalReviews) + newScore) / (totalReviews + 1);
    }
}
