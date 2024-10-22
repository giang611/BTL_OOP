package org.thuvien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.Entity.Book;

public interface JPA extends JpaRepository<Book, Integer> {
}
