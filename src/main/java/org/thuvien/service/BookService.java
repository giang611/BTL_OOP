package org.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<BookDTO> ListbookDTO() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
        for (Book book : books) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setName(book.getName());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setQuantity(book.getQuantity());
            bookDTOs.add(bookDTO);
        }
        return bookDTOs;
    }

}
