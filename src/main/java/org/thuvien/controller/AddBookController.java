package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookAddDTO;
import org.thuvien.service.BookService;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

@Controller
public class AddBookController {

    @Autowired
    private BookService bookService;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker publishedDatePicker;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;
    @FXML
    private Button uploadButton;

    @FXML
    private ImageView imageView;

    private byte[] imageBytes;

    @FXML
    public void initialize() {

        addButton.setOnAction(event -> addBook());
        cancelButton.setOnAction(event -> closeWindow());
        uploadButton.setOnAction(event -> chooseImage());
    }

    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (file != null) {
            try (InputStream fileInputStream = new FileInputStream(file);
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                imageBytes = buffer.toByteArray();

                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addBook() {
        String title = titleField.getText();
        String authors = authorField.getText();
        String category = categoryField.getText();
        String publisher = publisherField.getText();
        LocalDate publishedDate = publishedDatePicker.getValue();
        String description = descriptionArea.getText();
        String quantityText = quantityField.getText();

        if (title.isEmpty() || authors.isEmpty() || category == null || publisher.isEmpty() || publishedDate == null || description.isEmpty() || quantityText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin sách !");
            alert.showAndWait();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Số lượng phải là một số hợp lệ!");
            alert.showAndWait();
            return;
        }

        BookAddDTO bookDTO = new BookAddDTO();
        bookDTO.setTitle(title);
        bookDTO.setAuthor(authors);
        bookDTO.setPublisher(publisher);
        bookDTO.setPublishedDate(Date.valueOf(publishedDate));
        bookDTO.setDescription(description);
        bookDTO.setQuantity(quantity);
        bookDTO.setCategory(category);
        bookDTO.setImage(imageBytes);

        bookService.addBook(bookDTO);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText("Đã thêm sách thành công!");
        alert.showAndWait();

        ScreenController.switchScreen((Stage) addButton.getScene().getWindow(), "/home/mix.fxml");
    }

    private void closeWindow() {
        ScreenController.switchScreen((Stage) addButton.getScene().getWindow(), "/home/mix.fxml");

    }
}
