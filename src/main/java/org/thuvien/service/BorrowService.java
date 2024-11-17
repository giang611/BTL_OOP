package org.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuvien.models.Borrow;
import org.thuvien.repository.BorrowRepository;

import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    public List<Borrow> findAllBorrows() {
        return borrowRepository.findAll();
    }
}
