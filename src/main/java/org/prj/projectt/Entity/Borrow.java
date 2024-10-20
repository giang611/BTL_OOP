package org.prj.projectt.Entity;

import java.time.LocalDate;

public class Borrow {
    private int id;
    private int memberId;
    private int documentId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;

    public Borrow() {}

    public Borrow(int id, int memberId, int documentId,
                  LocalDate borrowDate, LocalDate returnDate,
                  String status) {
        this.id = id;
        this.memberId = memberId;
        this.documentId = documentId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

