package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;


@Controller
public class ViewBookController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    @FXML
    private ImageView bookImageView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea authorField;
    @FXML
    private TextArea genreField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField publishedYearField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField creationDateField;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    private BookDTO currentBook;

    @FXML
    public void initialize() {
        displayBookDetails();
    }

    public void setBook(BookDTO book) {
        this.currentBook = book;
        displayBookDetails();
    }

    private void displayBookDetails() {
        if (currentBook != null) {
            Book bookView = bookRepository.findById(currentBook.getId()).get();

            titleField.setText(bookView.getName());
            authorField.setText(bookView.getAuthor());
            genreField.setText(String.join(", ", bookView.getCategories()));
            publisherField.setText(bookView.getPublisher());
            publishedYearField.setText(DATE_FORMAT.format(bookView.getPublishedDate()));
            creationDateField.setText(DATE_FORMAT.format(bookView.getCreatedAt()));
            quantityField.setText(String.valueOf(bookView.getQuantity()));
            descriptionField.setText(bookView.getDescription());
            byte[] imageBytes = bookView.getImage();
            if (imageBytes != null) {
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                bookImageView.setImage(image);
            } else {
                bookImageView.setImage(null);
            }
        }
    }

    @FXML
    private void handleBack() {
         ScreenController.switchScreen((Stage) quantityField.getScene().getWindow(), "/home/mix.fxml");
    }
}
