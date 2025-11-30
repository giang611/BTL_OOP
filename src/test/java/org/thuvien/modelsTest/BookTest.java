package org.thuvien.modelsTest;

import org.junit.jupiter.api.Test;
import org.thuvien.models.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBuilder_ShouldConstructBookCorrectly() {
        String name = "Test Book";
        String author = "Test Author";
        int quantity = 10;
        String desc = "Description";

        Book book = new Book.Builder()
                .name(name)
                .author(author)
                .quantity(quantity)
                .description(desc)
                .categories("Science")
                .build();

        assertEquals(name, book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals(quantity, book.getQuantity());
        assertEquals(desc, book.getDescription());
        assertEquals("Science", book.getCategories());
    }

    @Test
    void testPrePersist_ShouldSetCreatedAt() {
        Book book = new Book();
        assertNull(book.getCreatedAt());

        book.setCreatedAt(); // Giả lập gọi PrePersist

        assertNotNull(book.getCreatedAt());
    }
}