package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
    @Column(nullable = false)
    private Date borrowDate;
    @Column(nullable = false)
    private Date returnDate;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private int quantity;

    public Borrow() {}

    public Borrow(int id,Date borrowDate, Date returnDate,
                  String status,int quantity) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.quantity = quantity;
    }
}

