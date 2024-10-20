package org.prj.projectt.Screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;


public class ScreenController {
    public static void switchScreen(Stage stage, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(ScreenController.class.getResource(fxmlPath));
            stage.setScene(new Scene(root,600,525));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
