package org.thuvien.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.thuvien.service.MemberService;
import org.thuvien.models.Member;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Controller
public class RegisterController {
@Autowired
private MemberService memberService;
    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField  name;
    @FXML
    private TextField  mssvField;
    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private void handleRegister(ActionEvent event) {
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String name = this.name.getText();
        String mssv = this.mssvField.getText();
        LocalDate birthDate = this.birthDatePicker.getValue();
        Date birthday=Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Optional<Member> existingMember = memberService.getMemberByPhone(phone);
         Member existingMember2 = memberService.getMemberByMssv(mssv);
        if (phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()||name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mật khẩu không khớp.");
            return;
        }
        if(existingMember.isPresent()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Số điện thoại đã được đăng ký.");
            return;
        }
        if(existingMember2!=null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã số sinh viên đã tồn tại");
            return;
        }
        Member newMember = new Member(name, mssv,phone, password);
        newMember.setRole("user");
        newMember.setBirthdate(birthday);
        memberService.createMember(newMember);

        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký thành công!");
        ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/login/login.fxml");
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        phoneField.clear();
        passwordField.clear();
        ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/login/login.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
