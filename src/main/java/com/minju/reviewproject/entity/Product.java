package com.minju.reviewproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "product") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reviewCount;

    @Column(nullable = false)
    private Float score;

    // 상품 : 리뷰 = N : N
    // 연관 관계 설정
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Review review;
}
