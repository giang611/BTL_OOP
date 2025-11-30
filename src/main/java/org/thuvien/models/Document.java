package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    protected Date createdAt;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Borrow> borrows;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

   private String author;

    @Column(name = "categories")
    private String categories;

    public abstract void printInfo();

    public Document() {}

    public Document(int id, String name, String author, String description, Date createdAt, int quantity, String categories, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.categories = categories;
        this.author = author;
        this.image = image;
    }


}
