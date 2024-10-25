package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
