package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.models.Member;
import org.thuvien.models.Rating;
import org.thuvien.repository.RatingRepository;
import org.thuvien.repository.BookRepository;
import org.thuvien.utils.SessionManager;

import java.io.ByteArrayInputStream;
import java.util.Date;

@Controller
public class AppreciateBookDialogController {

    @FXML
    private ComboBox<String> ratingComboBox;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private ImageView bookImage;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private BookDTO currentBook;
    public void setBook(BookDTO currentBook) {
        this.currentBook = currentBook;

        Book book = bookRepository.findById(currentBook.getId()).orElse(null);
        if (book != null) {
            byte[] imageBytes = book.getImage();
            if (imageBytes != null) {
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                bookImage.setImage(image);
            } else {
                bookImage.setImage(null);
            }
        }
    }



    @FXML
    public void submitReview() {
        String selectedRating = ratingComboBox.getValue();
        String reviewText = reviewTextArea.getText();

        if (selectedRating == null || selectedRating.isEmpty()) {
            System.out.println("Vui lòng chọn điểm đánh giá!");
            return;
        }

        if (reviewText == null || reviewText.trim().isEmpty()) {
            System.out.println("Vui lòng nhập nhận xét!");
            return;
        }

        try {
            Member currentMember= SessionManager.getCurrentUser();
            Rating rating = new Rating();
            rating.setDocumentId(currentBook.getId());
            rating.setRating(Integer.parseInt(selectedRating));
            rating.setReview(reviewText);
            rating.setCreatedAt(new Date());
            rating.setMember(currentMember);

            ratingRepository.save(rating);
            System.out.println("Đánh giá đã được lưu thành công!");

            ratingComboBox.getSelectionModel().clearSelection();
            reviewTextArea.clear();

        } catch (Exception e) {
            System.err.println("Lỗi khi lưu đánh giá: " + e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        Stage currentStage = (Stage) bookImage.getScene().getWindow();
        ScreenController.switchScreen(currentStage, "/home/mix.fxml");
    }
}
