package org.thuvien.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Book;
import org.thuvien.models.Borrow;
import org.thuvien.models.Member;
import org.thuvien.repository.BookRepository;
import org.thuvien.repository.BorrowRepository;
import org.thuvien.repository.MemberRepository;
import org.thuvien.utils.SessionManager;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class BookBorrowController {

    @FXML
    private TextField serialTextField;
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
    private DatePicker borrowDatePicker;
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

    @FXML
    private void loadBookInfo() {
        String serial = serialTextField.getText();

        try {
            Optional<Book> book = bookRepository.findById(Integer.parseInt(serial));

            if (book.isPresent()) {
                Book foundBook = book.get();
                bookNameLabel.setText(foundBook.getName());
                authorLabel.setText(foundBook.getAuthor());
                statusLabel.setText(foundBook.getQuantity() > 0 ? "Còn" : "Hết");
                quantityLabel.setText(String.valueOf(foundBook.getQuantity()));
            } else {
                showAlert("Không tìm thấy sách", "Mã serial không tồn tại.");
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi định dạng", "Mã serial phải là số nguyên.");
        }
    }

    @FXML
    public void saveBorrowRecord(ActionEvent event) {
        String serial = serialTextField.getText();
        String borrowerName = borrowerNameTextField.getText();
        String borrowerPhone = borrowerPhoneTextField.getText();
        String borrowerMssv = borrowerMssvTextField.getText();
        LocalDate borrowDate = borrowDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();
        Member currentUser = SessionManager.getCurrentUser();


        int borrowQuantity;
        if (borrowDate.isAfter(returnDate)) {
            showAlert("Lỗi ngày mượn/trả", "Ngày mượn không được lớn hơn ngày trả.");
            return;
        }
        if (serial.isEmpty() || borrowerName.isEmpty() || borrowerPhone.isEmpty() || borrowDate == null || returnDate == null||borrowerMssv.isEmpty()) {
            showAlert("Thiếu thông tin", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        try {
            borrowQuantity = Integer.parseInt(borrowQuantityTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi định dạng", "Số lượng mượn phải là số nguyên.");
            return;
        }

        Optional<Book> bookOpt = bookRepository.findById(Integer.parseInt(serial));
        if (!bookOpt.isPresent()) {
            showAlert("Không tìm thấy sách", "Mã serial không tồn tại.");
            return;
        }

        Book book = bookOpt.get();

        if (borrowQuantity > book.getQuantity()) {
            showAlert("Số lượng không đủ", "Không đủ số lượng sách để mượn.");
            return;
        }

        book.setQuantity(book.getQuantity() - borrowQuantity);
        bookRepository.save(book);

        Member member = new Member();
        member.setName(borrowerName);
        member.setPhoneNumber(borrowerPhone);
        member.setMssv(borrowerMssv);
        member.setRole("user");
        memberRepository.save(member);

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
    public void Back()
    {
        ScreenController.switchScreen((Stage) borrowDatePicker.getScene().getWindow(), "/home/mixBookLoanAdmin.fxml");
    }
}
