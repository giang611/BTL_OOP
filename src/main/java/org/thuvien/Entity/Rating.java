package org.thuvien.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int documentId;
    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String review;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Member member;
    public Rating() {}

    public Rating(int id, int documentId,
                  int rating, String review, LocalDateTime createdAt) {
        this.id = id;
        this.documentId = documentId;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
    }

}

