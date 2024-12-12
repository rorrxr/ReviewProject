package com.minju.reviewproject.repository;

import com.minju.reviewproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    //  회원별 상품을 조회하는 메서드 추가
//    Page<Product> findAllByUser(User user, Pageable pageable);

}
