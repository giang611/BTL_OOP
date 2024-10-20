package org.prj.projectt.Entity;

import java.time.LocalDate;

public class Thesis extends Document {
    private String university;
    private String degree;

    private Thesis(Builder builder) {
        super(builder.id, builder.title, builder.author, builder.description, builder.qrCode, builder.createdAt);
        this.university = builder.university;
        this.degree = builder.degree;
    }

    public String getUniversity() {
        return university;
    }

    public String getDegree() {
        return degree;
    }

    @Override
    public void printInfo() {
        System.out.println("Thesis Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("University: " + university);
        System.out.println("Degree: " + degree);
        System.out.println("Description: " + getDescription());
        System.out.println("QR Code: " + getQrCode());
    }

    public static class Builder {
        private int id;
        private String isbn;
        private String title;
        private String author;
        private String description;
        private String qrCode;
        private LocalDate createdAt;
        private String university;
        private String degree;

        public Builder(int id) {
            this.id = id;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
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

        public Builder createdAt(LocalDate createdAt) {
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

        public Thesis build() {
            return new Thesis(this);
        }
    }
}

