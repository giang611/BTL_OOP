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
import org.thuvien.Application;
import org.thuvien.models.Member;
import org.thuvien.utils.SessionManager;

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
    @FXML
    private Button btSearchBook;


    private List<Button> buttons;


    @FXML
    private void initialize() {
        buttons = new ArrayList<>();
        buttons.add(btHome);
        buttons.add(btManageBooks);
        buttons.add(btBorrowBooks);
        buttons.add(btManageMember);
        buttons.add(btReview);
        buttons.add(btSearchBook);
        Member currentMember = SessionManager.getCurrentUser();
        if(currentMember.getRole().equals("user"))
        {
            btManageBooks.setText("Thông tin");
            btManageMember.setText("Sách đã mượn");
            btBorrowBooks.setText("Kho sách");

        }

        resetButtonStyles();
    }

    private void resetButtonStyles() {
        for (Button button : buttons) {
            button.getStyleClass().remove("button-selected");
            button.getStyleClass().add("button");
        }
    }

    public AnchorPane loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(Application.getApplicationContext()::getBean);
        return loader.load();
    }
    public void loadContent(String fxmlFile) {
        try {
            AnchorPane newContent = loadFXML(fxmlFile);
            if (mainLayout.getChildren().size() > 1) {
                mainLayout.getChildren().remove(1);
            }
            mainLayout.getChildren().add(newContent);
        } catch (IOException e) {
            System.err.println("Lỗi khi tải FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }
    @FXML
    private void handleManageBooks(ActionEvent event) {
        Member currentMember=SessionManager.getCurrentUser();
        if(currentMember.getRole().equals("admin")) {
           loadContent("/home/bookManagement.fxml");
       }
       else {
           loadContent("/home/view_user.fxml");
       }
        resetButtonStyles();
        btManageBooks.getStyleClass().add("button-selected");
    }

    public VBox loadFXMLVbox(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource( fxmlFile));
        loader.setControllerFactory(Application.getApplicationContext()::getBean);
        return loader.load();
    }

    public void loadContentVbox(String fxmlFile) {
        try {
            VBox newContent = loadFXMLVbox(fxmlFile);
            if (mainLayout.getChildren().size() > 1) {
                mainLayout.getChildren().remove(1);
            }
            mainLayout.getChildren().add(newContent);
        } catch (IOException e) {
            System.err.println("Lỗi khi tải FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReview(ActionEvent event) {
        resetButtonStyles();
        btReview.getStyleClass().add("button-selected");
        loadContentVbox("/home/review.fxml");
    }


    @FXML
    private void handleHome(ActionEvent event) {
        resetButtonStyles();
        Member member=SessionManager.getCurrentUser();
        if(member.getRole().equals("admin")) {


            ScreenController.switchScreen((Stage) mainLayout.getScene().getWindow(), "/home/menu_admin.fxml");
        }
        else{
            ScreenController.switchScreen((Stage) mainLayout.getScene().getWindow(), "/home/menu_user.fxml");
        }
        }


    @FXML
    private void handleBorrowBooks(ActionEvent event) {
        Member member=SessionManager.getCurrentUser();
        if(member.getRole().equals("admin")) {
            loadContent("/home/BookLoanManagementAdmin.fxml");
        }
        else{
            loadContent("/home/bookManagement.fxml");
        }
        resetButtonStyles();
        btBorrowBooks.getStyleClass().add("button-selected");
    }
    @FXML
    private void handleManageMembers(ActionEvent event) {
        resetButtonStyles();
        btManageMember.getStyleClass().add("button-selected");
        loadContent("/home/MemberManagement.fxml");

    }
    @FXML
    private void handleSearch(ActionEvent event) {
        resetButtonStyles();
        btSearchBook.getStyleClass().add("button-selected");
        loadContentVbox("/home/bookSearch.fxml");

    }




}
