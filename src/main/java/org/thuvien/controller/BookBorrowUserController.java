package org.thuvien.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Book;
import org.thuvien.models.Borrow;
import org.thuvien.models.Member;
import org.thuvien.repository.BookRepository;
import org.thuvien.repository.BorrowRepository;
import org.thuvien.repository.MemberRepository;
import org.thuvien.utils.SessionManager;

import java.time.LocalDate;

@Controller
public class BookBorrowUserController {

    @FXML
    private Label bookNameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label quantityLabel;

    @FXML
    private TextField borrowerNameTextField;
    @FXML
    private TextField borrowerPhoneTextField;
    @FXML
    private DatePicker returnDatePicker;
    @FXML
    private TextField borrowQuantityTextField;
    @FXML
    private TextField borrowerMssvTextField;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private MemberRepository memberRepository;

    private BookDTO selectedBook;

    @FXML
    private void initialize() {
        Member member = SessionManager.getCurrentUser();
        borrowerMssvTextField.setText(member.getMssv());
        borrowerNameTextField.setText(member.getName());
        borrowerPhoneTextField.setText(member.getPhoneNumber());

    }

    public void setBorrowRecord(BookDTO book) {
        this.selectedBook = book;

        if (selectedBook != null) {
            bookNameLabel.setText(selectedBook.getName());
            authorLabel.setText(selectedBook.getAuthor());
            statusLabel.setText(selectedBook.getQuantity() > 0 ? "Còn" : "Hết");
            quantityLabel.setText(String.valueOf(selectedBook.getQuantity()));
        }
    }

    @FXML
    public void saveBorrowRecord(ActionEvent event) {
        String borrowerName = borrowerNameTextField.getText();
        String borrowerPhone = borrowerPhoneTextField.getText();
        String borrowerMssv = borrowerMssvTextField.getText();
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = returnDatePicker.getValue();
        Member currentUser = SessionManager.getCurrentUser();

        int borrowQuantity;

        if (borrowerName.isEmpty() || borrowerPhone.isEmpty() || borrowDate == null || returnDate == null || borrowerMssv.isEmpty()) {
            showAlert("Thiếu thông tin", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        try {
            borrowQuantity = Integer.parseInt(borrowQuantityTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi định dạng", "Số lượng mượn phải là số nguyên.");
            return;
        }

        if (selectedBook == null) {
            showAlert("Chưa chọn sách", "Vui lòng chọn sách trước khi mượn.");
            return;
        }

        if (borrowQuantity > selectedBook.getQuantity()) {
            showAlert("Số lượng không đủ", "Không đủ số lượng sách để mượn.");
            return;
        }
Book book=bookRepository.findById(selectedBook.getId()).get();
        book.setQuantity(selectedBook.getQuantity() - borrowQuantity);
        bookRepository.save(book);

        Member member = SessionManager.getCurrentUser();

        Borrow borrowRecord = new Borrow();
        borrowRecord.setDocument(book);
        borrowRecord.setMember(member);
        borrowRecord.setBorrowDate(borrowDate);
        borrowRecord.setReturnDate(returnDate);
        borrowRecord.setQuantity(borrowQuantity);
        borrowRecord.setStatus("borrowed");
        borrowRecord.setLibrarian(currentUser.getName());
        borrowRepository.save(borrowRecord);

        showAlert("Ghi nhận thành công", "Thông tin mượn sách đã được lưu.");
        ScreenController.switchScreen((Stage) bookNameLabel.getScene().getWindow(), "/home/mixBookLoanAdmin.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void Back() {
        ScreenController.switchScreen((Stage)  bookNameLabel.getScene().getWindow(), "/home/mixBookLoanAdmin.fxml");
    }
}
