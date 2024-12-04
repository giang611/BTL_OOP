package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Member;
import org.thuvien.utils.SessionManager;

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
    private void handleView()
    {
     Member member= SessionManager.getCurrentUser();
     if(member.getRole().equals("admin"))
     {
         ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mix.fxml");
     }
     else {
         ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixViewUser.fxml");
     }
    }

    @FXML
    private void handleSearch(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixBookSearch.fxml");
    }

    @FXML
    private void handleBook(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mix.fxml");
    }

    @FXML
    private void handleManageLoans(MouseEvent event) {
       Member member= SessionManager.getCurrentUser();
       if(member.getRole().equals("admin")){
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixBookLoanAdmin.fxml");
       }
       else {
           ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixBookLoanUser.fxml");
              }
    }

    @FXML
    private void handleManageReaders(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixMemberManagement.fxml");
    }

    @FXML
    private void handleReview(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/home/mixRatingsView.fxml");
    }

    @FXML
    private void handleLogout(MouseEvent event) {
        SessionManager.clearSession();
        ScreenController.switchScreen((Stage) btnReview.getScene().getWindow(), "/login/login.fxml");
    }
}
