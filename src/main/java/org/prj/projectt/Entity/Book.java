package org.prj.projectt.Entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.time.LocalDate;
public class Book extends Document {
    private String publisher;
    private LocalDate publishedDate;

    private Book(Builder builder) {
        super(builder.id, builder.title, builder.author, builder.description, builder.qrCode, builder.createdAt);
        this.publisher = builder.publisher;
        this.publishedDate = builder.publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    @Override
    public void printInfo() {
        System.out.println("Book Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publisher: " + publisher);
        System.out.println("Published Date: " + publishedDate);
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
        private String publisher;
        private LocalDate publishedDate;

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

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publishedDate(LocalDate publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
