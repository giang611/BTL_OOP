package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Rating;
import org.thuvien.repository.RatingRepository;

import java.util.List;

@Controller
public class RatingsViewController {

    @FXML
    private VBox ratingsContainer;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;


    @Autowired
    private RatingRepository ratingRepository;

    @FXML
    public void initialize() {
        loadRatingsFromDatabase();
        searchButton.setOnAction(event -> handleSearch());

    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadRatingsFromDatabase();
            return;
        }

        List<Rating> ratings = ratingRepository.findAll();
        ratingsContainer.getChildren().clear();

        ratings.stream()
                .filter(rating -> rating.getDocument().getName().toLowerCase().contains(keyword))
                .forEach(this::addReviewToContainer);
    }


    private void loadRatingsFromDatabase() {
        List<Rating> ratings = ratingRepository.findAll();
        ratings.forEach(this::addReviewToContainer);
    }

    private void addReviewToContainer(Rating rating) {
        VBox reviewBox = new VBox();
        reviewBox.getStyleClass().add("review-box");

        HBox header = new HBox();
        header.getStyleClass().add("review-header");
        header.setSpacing(10);

        VBox userInfo = new VBox();
        Label nameLabel = new Label(rating.getMember().getName());
        nameLabel.getStyleClass().add("user-info");
        Label dateLabel = new Label(rating.getCreatedAt().toString());
        dateLabel.getStyleClass().add("date-info");
        userInfo.getChildren().addAll(nameLabel, dateLabel);

        Label bookTitleLabel = new Label("Tên sách: " + rating.getDocument().getName());
        bookTitleLabel.getStyleClass().add("book-title");

        header.getChildren().add(userInfo);

        HBox ratingBox = new HBox();
        ratingBox.getStyleClass().add("rating-box");
        Label starLabel = new Label();
        int ratingValue = rating.getRating();

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < ratingValue; i++) {
            stars.append("★");
        }
        for (int i = ratingValue; i < 5; i++) {
            stars.append("☆");
        }
        starLabel.setText(stars.toString());
        starLabel.getStyleClass().add("star-rating");

        ratingBox.getChildren().add(starLabel);

        Label reviewContent = new Label(rating.getReview());
        reviewContent.getStyleClass().add("review-content");

        reviewBox.getChildren().addAll(header, bookTitleLabel, ratingBox, reviewContent);
        ratingsContainer.getChildren().add(reviewBox);
    }
}
