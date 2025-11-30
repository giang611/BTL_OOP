package org.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuvien.dto.BookAddDTO;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public List<BookDTO> ListbookDTO() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());


            bookDTO.setAuthor(book.getAuthor());

            bookDTO.setName(book.getAuthor());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setQuantity(book.getQuantity());
            bookDTO.setCategories(book.getCategories());
            bookDTOs.add(bookDTO);
        }

        return bookDTOs;
    }
    @Transactional
    public void addBook(BookAddDTO bookDTO) {
        Book book = new Book.Builder()
                .name(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .publishedDate(bookDTO.getPublishedDate())
                .description(bookDTO.getDescription())
                .quantity(bookDTO.getQuantity())
                .categories(bookDTO.getCategory())
                .image(bookDTO.getImage())
                .build();

        bookRepository.save(book);
    }
    public List<BookDTO> searchBooks(String name, String author, String genre) {
        List<Book> books = bookRepository.searchBooks(name, author, genre);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setCategories(book.getCategories());
        dto.setDescription(book.getDescription());
        return dto;
    }

    public void deleteBook(BookDTO bookDTO) {
        bookRepository.deleteById(bookDTO.getId());
    }


}

