package com.minju.reviewproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "review") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Long reviewCount;

    public Review(String title, String content, String author, String date, String time, Float score, Long reviewCount) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.time = time;
        this.score = score;
        this.reviewCount = reviewCount;
    }

}
