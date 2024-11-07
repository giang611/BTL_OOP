package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ReviewController {

    @FXML
    private ComboBox<String> ratingComboBox;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private VBox reviewsVBox;

    private final List<Review> reviews = new ArrayList<>();

    public static class Review {
        private final String rating;
        private final String review;

        public Review(String rating, String review) {
            this.rating = rating;
            this.review = review;
        }

        public String getRating() {
            return rating;
        }

        public String getReview() {
            return review;
        }
    }

    @FXML
    public void submitReview() {
        String rating = ratingComboBox.getValue();
        String review = reviewTextArea.getText();

        if (rating != null && !review.isEmpty()) {
            Review newReview = new Review(rating, review);
            reviews.add(newReview);

            Label reviewLabel = new Label("Đánh giá: " + rating + " sao\n" + review);
            reviewsVBox.getChildren().add(reviewLabel);

            ratingComboBox.setValue(null);
            reviewTextArea.clear();
        } else {
            System.out.println("Vui lòng nhập đầy đủ thông tin.");
        }
    }
}
