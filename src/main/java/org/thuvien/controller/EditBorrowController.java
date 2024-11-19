package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Borrow;
import org.thuvien.models.Document;
import org.thuvien.models.Member;
import org.thuvien.repository.BookRepository;
import org.thuvien.repository.BorrowRepository;
import org.thuvien.repository.MemberRepository;
import org.thuvien.service.BorrowService;
import org.thuvien.service.MemberService;

import java.time.LocalDate;

@Controller
public class EditBorrowController {

    @FXML
    private TextField serialField;

    @FXML
    private TextField borrowerField;

    @FXML
    private TextField librarianField;

    @FXML
    private DatePicker borrowDatePicker;

    @FXML
    private DatePicker returnDatePicker;
    @FXML
    private TextField quantityField;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private BookRepository bookRepository;

    public void setBorrowRecord(Borrow borrowRecord) {
        serialField.setText(String.valueOf(borrowRecord.getId()));
        borrowerField.setText(borrowRecord.getMember().getName());
        librarianField.setText(borrowRecord.getLibrarian());
        borrowDatePicker.setValue(borrowRecord.getBorrowDate());
        returnDatePicker.setValue(borrowRecord.getReturnDate());
        quantityField.setText(String.valueOf(borrowRecord.getQuantity()));
    }

    @FXML
    public void handleSave() {
        try {
            String serial = serialField.getText();
            String borrower = borrowerField.getText();
            String librarian = librarianField.getText();
            String quantity = quantityField.getText();
            LocalDate borrowDate = borrowDatePicker.getValue();
            LocalDate returnDate = returnDatePicker.getValue();
            Borrow borrow = borrowRepository.findById(Integer.valueOf(serial)).get();
            if (serial.isEmpty() || borrower.isEmpty() || librarian.isEmpty() || borrowDate == null || returnDate == null) {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.ERROR);
                return;
            }
            if (!borrowDate.isBefore(returnDate)) {
                showAlert("Lỗi", "Ngày mượn phải sau ngày trả!", Alert.AlertType.ERROR);
                return;
            }
            try {
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 0) {
                    showAlert("Lỗi", "Số lượng mượn phải lớn hơn 0!", Alert.AlertType.ERROR);
                    return;
                }
                borrow.setQuantity(quantityInt);
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Số lượng mượn phải là một số nguyên hợp lệ!", Alert.AlertType.ERROR);
                return;
            }
            borrow.setId(Integer.parseInt(serial));
            borrow.setBorrowDate(borrowDate);
            borrow.setReturnDate(returnDate);
            borrow.setStatus("Borrowed");
            borrow.setQuantity(Integer.parseInt(quantity));
            borrow.setLibrarian(librarian);
            borrowRepository.save(borrow);

            showAlert("Thành công", "Lưu phiếu mượn thành công!", Alert.AlertType.INFORMATION);


        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số serial phải là một số nguyên hợp lệ!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        ScreenController.switchScreen((Stage) borrowerField.getScene().getWindow(), "/home/mixBookLoan.fxml");
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleCancel() {
        ScreenController.switchScreen((Stage) borrowerField.getScene().getWindow(), "/home/mixBookLoan.fxml");
    }


}
