package org.prj.projectt.Entity;

import java.time.LocalDateTime;

public class Rating {
    private int id;
    private int memberId;
    private int documentId;
    private int rating;
    private String review;
    private LocalDateTime createdAt;

    public Rating() {}

    public Rating(int id, int memberId, int documentId,
                  int rating, String review, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.documentId = documentId;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

