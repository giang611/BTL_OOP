package org.thuvien.controller;

import com.sun.prism.impl.Disposer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Controller
public class EditBookController {

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

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private BookDTO currentBook;

    public void setBook(BookDTO book) {
        this.currentBook = book;
        loadBookDetails();
    }

    @FXML
    private void initialize() {
    }

    private void loadBookDetails() {
        Book bookEdit=bookRepository.findById(currentBook.getId()).get();
        if (currentBook != null) {
            titleField.setText(bookEdit.getName());
            authorField.setText(bookEdit.getAuthor());
            genreField.setText(String.join(", ", bookEdit.getCategories()));
            publisherField.setText(bookEdit.getPublisher());
            publishedYearField.setText(String.valueOf(bookEdit.getPublishedDate().getYear()));
            quantityField.setText(String.valueOf(bookEdit.getQuantity()));
            descriptionField.setText(bookEdit.getDescription());
        }
    }

    @FXML
    private void handleSave() {
        if (!validateInput()) {
            return;
        }

        Optional<Book> bookOptional = bookRepository.findById(currentBook.getId());
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setName(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setCategories(genreField.getText());
            book.setPublisher(publisherField.getText());
            book.setQuantity(Integer.parseInt(quantityField.getText()));
            book.setDescription(descriptionField.getText());
            bookRepository.save(book);
        }
    }

    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() ||
                genreField.getText().trim().isEmpty() || publisherField.getText().trim().isEmpty() ||
                quantityField.getText().trim().isEmpty()) {
            showAlert("Invalid Input", "Please fill in all required fields.");
            return false;
        }

        try {
            Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Quantity", "Please enter a valid quantity.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleCancel() {
        ScreenController.switchScreen((Stage) titleField.getScene().getWindow(), "/home/mix.fxml");
    }
    @FXML
    private void handleBack() {
        ScreenController.switchScreen((Stage) titleField.getScene().getWindow(), "/home/mix.fxml");
    }
}
