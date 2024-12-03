package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.models.Borrow;

import javax.transaction.Transactional;
import java.util.List;


public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    public List<Borrow> findByMemberId(int memberId);
}
