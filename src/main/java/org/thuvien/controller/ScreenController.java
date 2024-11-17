package org.thuvien.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.thuvien.Application;
import org.thuvien.dto.BookDTO;

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
    }

