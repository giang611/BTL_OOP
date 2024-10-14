package org.prj.projectt.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.prj.projectt.Screen.ScreenController;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đăng nhập");
        alert.setHeaderText(null);
        alert.setContentText("Xin chào, " + username + "!");
        alert.showAndWait();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        ScreenController.switchScreen((Stage) usernameField.getScene().getWindow(), "/org/prj/projectt/register.fxml");

    }
}
