package org.thuvien.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LibraryController {
    @FXML
    private HBox mainLayout;
    @FXML
    private Button btHome;

    @FXML
    private Button btManageBooks;
    @FXML
    private Button btBorrowBooks;
    @FXML
    private Button btManageMember;
    @FXML
    private Button btReview;


    // Danh sách các nút điều hướng
    private List<Button> buttons;

    @FXML
    private void initialize() {
        // Thêm các nút vào danh sách để dễ quản lý
        buttons = new ArrayList<>();
        buttons.add(btHome);
        buttons.add(btManageBooks);
        buttons.add(btBorrowBooks);
        buttons.add(btManageMember);
        buttons.add(btReview);

        // Khởi tạo trạng thái ban đầu cho các nút (chưa được chọn)
        resetButtonStyles();
    }

    // Đặt lại style cho tất cả các nút về trạng thái chưa chọn
    private void resetButtonStyles() {
        for (Button button : buttons) {
            button.getStyleClass().remove("button-selected");
            button.getStyleClass().add("button");  // Gán lại lớp CSS ban đầu
        }
    }


    public void loadContent(String fxmlFile) {
        try {
            AnchorPane newContent = FXMLLoader.load(getClass().getResource("/" + fxmlFile));
            if (mainLayout.getChildren().size() > 1) {
                mainLayout.getChildren().remove(1);
            }
            mainLayout.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadContent1(String fxmlFile) {
        try {
            VBox newContent = FXMLLoader.load(getClass().getResource("/" + fxmlFile));
            if (mainLayout.getChildren().size() > 1) {
                mainLayout.getChildren().remove(1);
            }
            mainLayout.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHome(ActionEvent event) {
        ScreenController.switchScreen((Stage) mainLayout.getScene().getWindow(), "/home/menu.fxml");
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        loadContent("home/bookManagement.fxml");
        resetButtonStyles();
        btManageBooks.getStyleClass().add("button-selected");
    }

    @FXML
    private void handleBorrowBooks(ActionEvent event) {
        resetButtonStyles();
        btBorrowBooks.getStyleClass().add("button-selected");
    }
    @FXML
    private void handleManageMembers(ActionEvent event) {
        resetButtonStyles();
        btManageMember.getStyleClass().add("button-selected");
    }
    @FXML
    private void handleReview(ActionEvent event) {
        resetButtonStyles();
        btReview.getStyleClass().add("button-selected");
        loadContent1("home/review.fxml");
    }



}
