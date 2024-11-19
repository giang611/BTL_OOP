package org.thuvien.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.thuvien.Application;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Borrow;
import org.thuvien.models.Member;

@Controller
public class ScreenController {
    public static void switchScreen(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void switchScreenBook(Stage stage, String fxmlPath, BookDTO book) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();

            ViewBookController controller = loader.getController();
            if (controller != null) {
                controller.setBook(book);
            }

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void switchScreenBookEdit(Stage stage, String fxmlPath, BookDTO book) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();

            EditBookController controller = loader.getController();
            if (controller != null) {
                controller.setBook(book);
            }

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void switchScreenEditBorrow(Stage stage, String fxmlPath, Borrow borrowRecord) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();

            EditBorrowController controller = loader.getController();
            if (controller != null) {
                controller.setBorrowRecord(borrowRecord);
            }

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void switchScreenEditMember(Stage stage, String fxmlPath, Member member) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();

            EditMemberController controller = loader.getController();
            if (controller != null) {
                controller.setMember(member);
            }

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

