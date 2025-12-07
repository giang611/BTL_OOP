package org.thuvien.unitTest.TestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.models.Borrow;
import org.thuvien.repository.BorrowRepository;
import org.thuvien.service.BorrowService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;

    @InjectMocks
    private BorrowService borrowService;

    @Test
    void testFindAllBorrows() {
        Borrow b1 = new Borrow();
        Borrow b2 = new Borrow();
        when(borrowRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Borrow> result = borrowService.findAllBorrows();

        assertEquals(2, result.size());
        verify(borrowRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBorrow() {
        Borrow borrow = new Borrow();
        borrow.setStatus("borrowed");

        borrowService.updateBorrow(borrow);

        verify(borrowRepository, times(1)).save(borrow);
    }
}