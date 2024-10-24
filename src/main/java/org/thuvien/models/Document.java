package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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
    private String isbn;
    @Column(nullable = false)
    private String author;
    @Lob
    private String description;
    @Column(name = "qr_code")
    private String qrCode;

    @Column(name="quantity")
    private int quantity;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Borrow> borrows;
    @ManyToMany
    @JoinTable(
            name = "document_category",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Document() {}

    public Document(int id,String name, String isbn, String author,
                    String description, String qrCode, Date createdAt,int quantity) {
        this.id = id;
        this.isbn = isbn;
        this.name=name;
        this.author = author;
        this.description = description;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
        this.quantity = quantity;
    }
    public abstract void printInfo();
}

