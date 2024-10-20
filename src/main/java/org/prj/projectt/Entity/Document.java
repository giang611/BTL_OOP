package org.prj.projectt.Entity;

import java.time.LocalDate;

public abstract class Document {
    private int id;
    private String title;
    private String author;
    private String description;
    private String qrCode;
    private LocalDate createdAt;

    public Document() {}

    public Document(int id, String title, String author,
                    String description, String qrCode, LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public abstract void printInfo();
}

