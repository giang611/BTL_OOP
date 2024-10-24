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

    // List để lưu trữ các đánh giá và nhận xét
    private final List<Review> reviews = new ArrayList<>();

    // Lớp nội bộ để lưu trữ dữ liệu đánh giá
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

    // Phương thức xử lý khi người dùng nhấn nút "Gửi"
    @FXML
    public void submitReview() {
        String rating = ratingComboBox.getValue();
        String review = reviewTextArea.getText();

        if (rating != null && !review.isEmpty()) {
            // Tạo một đối tượng Review mới và thêm vào danh sách
            Review newReview = new Review(rating, review);
            reviews.add(newReview);

            // Hiển thị đánh giá mới trong VBox
            Label reviewLabel = new Label("Đánh giá: " + rating + " sao\n" + review);
            reviewsVBox.getChildren().add(reviewLabel);

            // Xóa các trường nhập liệu sau khi gửi
            ratingComboBox.setValue(null);
            reviewTextArea.clear();
        } else {
            // Nếu không nhập đủ thông tin, có thể hiển thị thông báo lỗi
            System.out.println("Vui lòng nhập đầy đủ thông tin.");
        }
    }
}
