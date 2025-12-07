package org.thuvien.unitTest.TestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.dto.BookAddDTO;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testListbookDTO_ShouldMapEntityToDTO() {
        Book book = new Book.Builder()
                .name("Clean Code")
                .author("Robert C. Martin")
                .description("Sách về code sạch")
                .quantity(5)
                .categories("IT")
                .build();
        book.setId(1);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.ListbookDTO();

        assertEquals(1, result.size());
        BookDTO dto = result.get(0);
        assertEquals(1, dto.getId());
        assertEquals("Clean Code", dto.getName());
        assertEquals("Robert C. Martin", dto.getAuthor());
        assertEquals(5, dto.getQuantity());
        assertEquals("IT", dto.getCategories());
    }

    @Test
    void testAddBook_ShouldSaveCorrectly() {
        BookAddDTO dto = new BookAddDTO();
        dto.setTitle("New Book");
        dto.setAuthor("New Author");
        dto.setPublisher("Publisher");
        dto.setPublishedDate(Date.valueOf(LocalDate.now()));
        dto.setDescription("Description");
        dto.setQuantity(10);
        dto.setCategory("Fiction");
        dto.setImage(new byte[]{1, 2, 3});
        bookService.addBook(dto);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book savedBook = captor.getValue();

        assertEquals("New Book", savedBook.getName());
        assertEquals("New Author", savedBook.getAuthor());
        assertEquals(10, savedBook.getQuantity());
        assertNotNull(savedBook.getImage());
    }

    @Test
    void testSearchBooks_ShouldReturnResults() {
        Book book = new Book.Builder().name("Java Programming").build();
        book.setId(2);
        when(bookRepository.searchBooks("Java", null, null))
                .thenReturn(Collections.singletonList(book));

        List<BookDTO> result = bookService.searchBooks("Java", null, null);

        assertEquals(1, result.size());
        assertEquals("Java Programming", result.get(0).getName());
    }

    @Test
    void testDeleteBook_ShouldCallDeleteById() {
        BookDTO dto = new BookDTO();
        dto.setId(99);

        bookService.deleteBook(dto);

        verify(bookRepository).deleteById(99);
    }

    @Test
    void testListBookDTO_EmptyDatabase() {
        // Trường hợp database không có sách nào
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.ListbookDTO();

        // Kỳ vọng: Trả về list rỗng, không được null và không lỗi
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchBooks_NoMatchFound() {
        // Trường hợp tìm kiếm nhưng không thấy
        when(bookRepository.searchBooks("KhongTonTai", null, null))
                .thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.searchBooks("KhongTonTai", null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListBookDTO_HandleNullFields() {
        // Trường hợp sách trong DB bị thiếu dữ liệu (ví dụ author null)
        Book book = new Book.Builder().name("Book No Author").build();
        // author mặc định là null nếu không set

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.ListbookDTO();

        assertEquals(1, result.size());
        assertNull(result.get(0).getAuthor()); // Đảm bảo code không crash khi field null
    }
}