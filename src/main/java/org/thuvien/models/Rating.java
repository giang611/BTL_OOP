package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int documentId;
    @Column
    private int rating;
    @Column
    private String review;
    @Column
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Member member;
    public Rating() {}

    public Rating(int id, int documentId,
                  int rating, String review, Date createdAt) {
        this.id = id;
        this.documentId = documentId;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
    }

}

