package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow")
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

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int quantity;

    public Borrow() {}

    public Borrow(Member member, Document document, LocalDate borrowDate, LocalDate returnDate,
                  String status, int quantity) {
        this.member = member;
        this.document = document;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.quantity = quantity;
    }
}
