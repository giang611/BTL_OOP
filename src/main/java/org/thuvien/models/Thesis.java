package org.thuvien.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
public class Thesis extends Document {
    @Column(nullable = false)
    private String university;
    @Column(nullable = false)
    private String degree;

    private Thesis(Builder builder) {
        super(builder.id,builder.name,builder.isbn, builder.author, builder.description, builder.qrCode, builder.createdAt,builder.quantity);
        this.university = builder.university;
        this.degree = builder.degree;
    }
public Thesis() {}
    public String getUniversity() {
        return university;
    }

    public String getDegree() {
        return degree;
    }

    @Override
    public void printInfo() {
        System.out.println("Author: " + getAuthor());
        System.out.println("University: " + university);
        System.out.println("Degree: " + degree);
        System.out.println("Description: " + getDescription());
        System.out.println("QR Code: " + getQrCode());
    }

    public static class Builder {
        private int id;
        private String name;
        private String isbn;
        private String title;
        private String author;
        private String description;
        private String qrCode;
        private Date createdAt;
        private String university;
        private String degree;
        private int quantity;

        public Builder(int id) {
            this.id = id;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }


        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder qrCode(String qrCode) {
            this.qrCode = qrCode;
            return this;
        }

        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder university(String university) {
            this.university = university;
            return this;
        }

        public Builder degree(String degree) {
            this.degree = degree;
            return this;
        }
        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Thesis build() {
            return new Thesis(this);
        }
    }
}

