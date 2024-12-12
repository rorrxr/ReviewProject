package com.minju.reviewproject.repository;

import com.minju.reviewproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND (:cursor IS NULL OR r.id < :cursor) ORDER BY r.createdAt DESC")
    List<Review> findReviews(@Param("productId") Long productId, @Param("cursor") Long cursor, int limit);

    int countByProductId(Long productId);

    @Query("SELECT COALESCE(AVG(r.score), 0) FROM Review r WHERE r.product.id = :productId")
    float getAverageScore(@Param("productId") Long productId);

    boolean existsByProductIdAndUserId(Long productId, Long userId);
}

