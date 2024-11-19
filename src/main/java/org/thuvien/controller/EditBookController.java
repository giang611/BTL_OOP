package org.thuvien.controller;

import com.sun.prism.impl.Disposer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    @FXML
    private ImageView bookImageView;

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
            byte[] imageBytes = bookEdit.getImage();
            if (imageBytes != null) {
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                bookImageView.setImage(image);
            } else {
                bookImageView.setImage(null);
            }
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
            if (bookImageView.getImage() != null) {
                byte[] imageBytes = imageToByteArray(bookImageView.getImage());
                book.setImage(imageBytes);
            }

            bookRepository.save(book);
            showAlert("Thông báo", "Thông tin sách đã được lưu thành công!");
            ScreenController.switchScreen((Stage) titleField.getScene().getWindow(), "/home/mix.fxml");
        }
    }

    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() ||
                genreField.getText().trim().isEmpty() || publisherField.getText().trim().isEmpty() ||
                quantityField.getText().trim().isEmpty()) {
            showAlert("Invalid Input", "Vui lòng điền đầy đủ thông tin");
            return false;
        }

        try {
            Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Quantity", "Vui lòng điền số lượng hợp lệ .");
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
    @FXML
    private void handleImageSelect(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) bookImageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            bookImageView.setImage(new Image(file.toURI().toString()));
        }
    }
    private byte[] imageToByteArray(Image image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

}
