package org.thuvien.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String mssv;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(name="birth_date")
    private Date birthday;
    @Column()
    private String password;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private String role;
    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
    @Column
    private String gender;


    public Member() {}

    public Member( String name, String mssv, String phoneNumber,
                  String password) {
        this.name = name;
        this.mssv = mssv;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Borrow> borrows;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;
    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Date.from(Instant.now());
    }

}

