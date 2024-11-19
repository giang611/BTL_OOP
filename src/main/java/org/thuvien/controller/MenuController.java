package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {

    @FXML
    private Button btnManageStaff;

    @FXML
    private Button BtnManageMembers;

    @FXML
    private Button btnManageLoans;

    @FXML
    private Button btnManageBook;

    @FXML
    private Button btnReview;

    @FXML
    private Button btnLogout;

    @FXML
    private void handleManageStaff(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/dialog/BorrowRecord.fxml");
    }

    @FXML
    private void handleBook(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mix.fxml");
    }

    @FXML
    private void handleManageLoans(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixBookLoan.fxml");
    }

    @FXML
    private void handleManageReaders(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixMemberManagement.fxml");
    }

    @FXML
    private void handleReview(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixReview.fxml");
    }

    @FXML
    private void handleLogout(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/login/login.fxml");
    }
}
