package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Member;
import org.thuvien.service.MemberService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class EditMemberController {

    @FXML
    private TextField mssvField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker createDatePicker;

    @FXML
    private DatePicker birthDatePicker;




    @Autowired
    private MemberService memberService;

    private Member currentMember;

    public void setMember(Member member) {
        this.currentMember = member;
        mssvField.setText(member.getMssv());
        nameField.setText(member.getName());
        phoneField.setText(member.getPhoneNumber());
        Date createdAt = member.getCreatedAt();

        LocalDate localCreatedAt = createdAt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        createDatePicker.setValue(localCreatedAt);
        LocalDate localBirthday = member.getBirthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        birthDatePicker.setValue(localBirthday);
    }

    @FXML
    public void handleSave() {
        try {
            String mssv = mssvField.getText();
            String name = nameField.getText();
            String phoneNumber = phoneField.getText();

            if (mssv.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.ERROR);
                return;
            }

            currentMember.setMssv(mssv);
            currentMember.setName(name);
            currentMember.setPhoneNumber(phoneNumber);

            memberService.save(currentMember);

            showAlert("Thành công", "Cập nhật thông tin thành viên thành công!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi lưu thông tin: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleCancel() {
        ScreenController.switchScreen((Stage) phoneField.getScene().getWindow(), "/home/mixMemberManagement.fxml");
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
