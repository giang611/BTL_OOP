package org.prj.projectt.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.prj.projectt.Screen.ScreenController;
import org.springframework.stereotype.Component;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        ScreenController.switchScreen((Stage) usernameField.getScene().getWindow(), "/org/prj/projectt/menu.fxml");

    }

    @FXML
    private void handleRegister(ActionEvent event) {
        ScreenController.switchScreen((Stage) usernameField.getScene().getWindow(), "/org/prj/projectt/register.fxml");

    }
}
