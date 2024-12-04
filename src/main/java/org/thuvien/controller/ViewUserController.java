package org.thuvien.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Member;
import org.thuvien.service.MemberService;
import org.thuvien.utils.SessionManager;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ViewUserController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField mssvField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField registrationDateField;
    @FXML
    private TextField genderField;
    @FXML
    private ImageView userImageView;
    @FXML
    private Button saveInfoButton;
    @FXML
    private Button chooseImageButton;

    @Autowired
    private MemberService memberService;

    private byte[] uploadedImage;

    public ViewUserController() {
        this.memberService = new MemberService();
    }

    @FXML
    public void initialize() {
        Member member = SessionManager.getCurrentUser();

        if (member != null) {
            fullNameField.setText(member.getName());
            mssvField.setText(member.getMssv());
            phoneField.setText(member.getPhoneNumber());
            dobField.setText(formatDate(member.getBirthdate()));
            genderField.setText(formatGender(member.getGender()));
            registrationDateField.setText(formatDate(member.getCreatedAt()));

            if (member.getImage() != null) {
                Image image = new Image(new ByteArrayInputStream(member.getImage()));
                userImageView.setImage(image);
            }
        }

        chooseImageButton.setOnAction(event -> handleChooseImage());
        saveInfoButton.setOnAction(event -> handleSaveInfo());
    }

    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                userImageView.setImage(image);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputStream);
                uploadedImage = outputStream.toByteArray();
            } catch (Exception e) {
                showAlert("Lỗi", "Không thể tải ảnh. Vui lòng thử lại!");
            }
        }
    }

    private void handleSaveInfo() {
        Member member = SessionManager.getCurrentUser();

        if (member != null) {
            try {
                Date birthday = parseDate(dobField.getText());
                if (birthday == null || !isValidBirthday(birthday)) {
                    showAlert("Lỗi", "Ngày sinh không hợp lệ. Vui lòng nhập đúng định dạng 'dd/MM/yyyy' và đảm bảo ngày này không lớn hơn ngày hiện tại.");
                    return;
                }

                String genderInput = genderField.getText().trim();
                if (!isValidGender(genderInput)) {
                    showAlert("Lỗi", "Giới tính không hợp lệ. Vui lòng chỉ nhập 'Nam', 'Nữ' hoặc 'Không xác định'.");
                    return;
                }

                member.setName(fullNameField.getText());
                member.setMssv(mssvField.getText());
                member.setPhoneNumber(phoneField.getText());
                member.setBirthdate(birthday);
                member.setGender(parseGender(genderInput));

                if (uploadedImage != null) {
                    member.setImage(uploadedImage);
                }

                memberService.updateMember(member);
                showAlert("Thành công", "Thông tin người dùng đã được cập nhật!");
            } catch (Exception e) {
                showAlert("Lỗi", "Có lỗi xảy ra khi lưu thông tin. Vui lòng thử lại!");
            }
        }
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private boolean isValidBirthday(Date birthday) {
        return birthday.before(new Date());
    }

    private boolean isValidGender(String gender) {
        return "Nam".equalsIgnoreCase(gender) ||
                "Nữ".equalsIgnoreCase(gender) ||
                "Không xác định".equalsIgnoreCase(gender);
    }

    private String parseGender(String genderStr) {
        if ("Nam".equalsIgnoreCase(genderStr)) {
            return "male";
        } else if ("Nữ".equalsIgnoreCase(genderStr)) {
            return "female";
        } else if ("Không xác định".equalsIgnoreCase(genderStr)) {
            return "unknown";
        }
        return null;
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return date != null ? sdf.format(date) : "";
    }

    private String formatGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return "Nam";
        } else if ("female".equalsIgnoreCase(gender)) {
            return "Nữ";
        } else if ("unknown".equalsIgnoreCase(gender)) {
            return "Không xác định";
        }
        return "";
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
