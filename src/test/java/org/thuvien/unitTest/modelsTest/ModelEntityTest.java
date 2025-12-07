package org.thuvien.unitTest.modelsTest;

import org.junit.jupiter.api.Test;
import org.thuvien.models.Book;
import org.thuvien.models.Borrow;
import org.thuvien.models.Member;
import org.thuvien.models.Rating;

import java.time.LocalDate;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ModelEntityTest {

    @Test
    void testBorrowEntity() {
        Member member = new Member();
        member.setId(1);
        Book book = new Book();
        book.setId(10);

        Borrow borrow = new Borrow();
        borrow.setMember(member);
        borrow.setDocument(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(LocalDate.now().plusDays(7));
        borrow.setStatus("borrowed");
        borrow.setQuantity(2);

        assertNotNull(borrow.getMember());
        assertEquals(1, borrow.getMember().getId());
        assertEquals("borrowed", borrow.getStatus());
        assertEquals(2, borrow.getQuantity());
        assertTrue(borrow.getReturnDate().isAfter(borrow.getBorrowDate()) || borrow.getReturnDate().isEqual(borrow.getBorrowDate()));
    }

    @Test
    void testRatingEntity() {
        Rating rating = new Rating();
        rating.setRating(5);
        rating.setReview("Sách rất hay!");
        rating.setCreatedAt(new Date());

        assertEquals(5, rating.getRating());
        assertEquals("Sách rất hay!", rating.getReview());
        assertNotNull(rating.getCreatedAt());
    }
}