package org.thuvien.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.Application;
import org.thuvien.models.Member;
import org.thuvien.service.MemberService;
import javafx.scene.control.Alert;
import org.thuvien.utils.SessionManager;

import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {
   @Autowired
   private MemberService memberService;
    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String phone = phoneField.getText();
        String password = passwordField.getText();
        Optional<Member> optionalMember =memberService.getMemberByPhone(phone);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(password)) {
                if(member.getRole().equals("admin")) {
                    ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/home/menu_admin.fxml");
                }
                else{
                    ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/home/menu_user.fxml");
                }
                SessionManager.setCurrentUser(member);
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi Đăng Nhập", "Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi Đăng Nhập", "Tên đăng nhập hoặc mật khẩu không đúng.");
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRegister(ActionEvent event) {

        ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/login/register.fxml");
    }
    public static void loadView(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/login/login.fxml"));
            loader.setControllerFactory(Application.getApplicationContext()::getBean);

            Parent view = loader.load();
            stage.setScene(new Scene(view));


            LoginController controller = loader.getController();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
