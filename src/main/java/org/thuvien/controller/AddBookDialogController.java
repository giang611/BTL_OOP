package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.service.BookService;
@Controller
public class AddBookDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField quantityField;

    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        BookDTO newBook = new BookDTO();
        newBook.setName(name);
        newBook.setAuthor(author);
        newBook.setDescription(description);
        newBook.setQuantity(quantity);

      //  bookService.addBook(newBook);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
