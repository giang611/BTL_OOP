package org.thuvien.Screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.thuvien.Application;

@Component
public class ScreenController {
    public static void switchScreen(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlPath));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 600, 525));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

