package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.models.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
}
