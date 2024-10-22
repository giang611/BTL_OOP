package org.thuvien.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy ="categories")
    private List<Document> documents;
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
public Category() {}
}

