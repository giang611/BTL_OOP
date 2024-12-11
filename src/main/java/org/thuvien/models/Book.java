package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="books")
@Getter
@Setter
public class Book extends Document {

    @Column(nullable = false)
    private String publisher;
    @Column()
    private Date publishedDate;

    public Book(Builder builder) {
        super(builder.id,builder.name,builder.author, builder.description, builder.createdAt, builder.quantity,builder.categories,builder.image);
        this.publisher = builder.publisher;
        this.publishedDate = builder.publishedDate;
    }

    public Book() {
    }

    public String getPublisher() {
        return publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    @Override
    public void printInfo() {
    }

    public static class Builder {
        private int id;
        private String name;
        private String isbn;
        private String author;
        private String description;
        private String qrCode;
        private Date createdAt;
        private String publisher;
        private Date publishedDate;
        private int quantity;
        private String categories;
        private byte[] image;

        public Builder name(String name) {
       this.name = name;
       return this;
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

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publishedDate(Date publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }
        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder categories(String categories) {
            this.categories = categories;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
    @PrePersist
    public void setCreatedAt() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }
}
