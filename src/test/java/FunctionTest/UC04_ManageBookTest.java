package FunctionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.dto.BookAddDTO;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC04_ManageBookTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("TC-04-01: Thêm sách mới")
    void testAddBook() {
        BookAddDTO dto = new BookAddDTO();
        dto.setTitle("New Book");
        dto.setQuantity(5);
        dto.setPublishedDate(Date.valueOf("2023-01-01"));

        bookService.addBook(dto);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("TC-04-05: Lấy danh sách sách (Convert Entity to DTO)")
    void testListBookDTO() {
        // Arrange
        Book book = new Book();
        book.setId(1);
        book.setName("Test Book");
        book.setAuthor("Test Author");
        book.setCategories("Test Cat");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        // Act
        List<BookDTO> result = bookService.ListbookDTO();

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(1).getId());
        assertEquals("Test Cat", result.get(1).getCategories());
    }

    @Test
    @DisplayName("TC-04-06: Xóa sách")
    void testDeleteBook() {
        BookDTO dto = new BookDTO();
        dto.setId(99);
        bookService.deleteBook(dto);
        verify(bookRepository, times(1)).deleteById(99);
    }
}