package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Borrow> borrows;

   private String author;

    // Lưu trữ thể loại dưới dạng chuỗi, phân tách bằng dấu phẩy
    @Column(name = "categories")
    private String categories;

    public Document() {}

    public Document(int id, String name,String author, String description, String qrCode, Date createdAt, int quantity, String categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.categories = categories;
        this.author=author;
    }

    public abstract void printInfo();
}
