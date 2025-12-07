package FunctionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC03_SearchBookTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("TC-03-01: Tìm kiếm theo Tên sách")
    void testSearch_ByTitle() {
        String name = "Java";
        Book book = new Book();
        book.setName("Java Basic");
        when(bookRepository.searchBooks(name, null, null)).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.searchBooks(name, null, null);

        assertEquals(1, result.size());
        assertEquals("Java Basic ", result.get(0).getName());
    }

    @Test
    @DisplayName("TC-03-04: Tìm kiếm theo Tác giả")
    void testSearch_ByAuthor() {
        String author = "Uncle Bob";
        Book book = new Book();
        book.setAuthor("Uncle Bob");
        when(bookRepository.searchBooks(null, author, null)).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.searchBooks(null, author, null);

        assertFalse(result.isEmpty());
        assertEquals("Uncle Bob", result.get(0).getAuthor());
    }

    @Test
    @DisplayName("TC-03-05: Tìm kiếm theo Thể loại")
    void testSearch_ByCategory() {
        String genre = "IT";
        Book book = new Book();
        book.setCategories("IT Books");
        when(bookRepository.searchBooks(null, null, genre)).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.searchBooks(null, null, genre);

        assertFalse(result.isEmpty());
        assertEquals("IT Books", result.get(0).getCategories());
    }
}