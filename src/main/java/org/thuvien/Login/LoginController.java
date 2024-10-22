package org.thuvien.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thuvien.Application;
import org.thuvien.Entity.Book;
import org.thuvien.JPA;
import org.thuvien.Screen.ScreenController;

import java.io.IOException;
import java.util.List;

@Component
public class LoginController {
    @Autowired
    private JPA a;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        ScreenController.switchScreen((Stage) usernameField.getScene().getWindow(), "/menu.fxml");

    }

    @FXML
    private void handleRegister(ActionEvent event) {
        ScreenController.switchScreen((Stage) usernameField.getScene().getWindow(), "/register.fxml");
List<Book> tmp=a.findAll();
    }
    public static void loadView(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/login.fxml"));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);

            Parent view = loader.load();
            stage.setScene(new Scene(view,600,375));


            LoginController controller = loader.getController();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
