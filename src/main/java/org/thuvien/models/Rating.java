package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


    public Rating(int id, int documentId,
                  int rating, String review, Date createdAt) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
    }

}

