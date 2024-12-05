package org.thuvien.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Borrow;
import org.thuvien.models.Member;
import org.thuvien.repository.BorrowRepository;
import org.thuvien.utils.SessionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class BorrowManagementUserController {

    @FXML
    private TableView<Borrow> bookTable;
    @FXML
    private TableColumn<Borrow, Integer> serialColumn;
    @FXML
    private TableColumn<Borrow, String> nameColumn;
    @FXML
    private TableColumn<Borrow, String> librarianColumn;
    @FXML
    private TableColumn<Borrow, LocalDate> borrowDateColumn;
    @FXML
    private TableColumn<Borrow, LocalDate> returnDateColumn;
    @FXML
    private TableColumn<Borrow, Integer> quantityColumn;
    @FXML
    private TableColumn<Borrow, String> statusColumn;
    @FXML
    private TextField searchField;

    @Autowired
    private BorrowRepository borrowRepository;

    private ObservableList<Borrow> borrowList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDocument().getName()));
        librarianColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibrarian()));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        borrowDateColumn.setCellFactory(createDateCellFactory(formatter));
        returnDateColumn.setCellFactory(createDateCellFactory(formatter));

        loadBorrowData();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBorrowData(newValue));
    }

    private void loadBorrowData() {
        Member member= SessionManager.getCurrentUser();
        borrowList.clear();
        borrowList.addAll(borrowRepository.findByMemberId(member.getId()));
        bookTable.setItems(borrowList);
    }

    private void filterBorrowData(String query) {
        if (query == null || query.isEmpty()) {
            bookTable.setItems(borrowList);
        } else {
            ObservableList<Borrow> filteredList = FXCollections.observableArrayList();
            for (Borrow borrow : borrowList) {
                if (borrow.getMember().getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(borrow);
                }
            }
            bookTable.setItems(filteredList);
        }
    }

    private <T> Callback<TableColumn<Borrow, T>, TableCell<Borrow, T>> createDateCellFactory(DateTimeFormatter formatter) {
        return column -> new TableCell<Borrow, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item instanceof LocalDate) {
                    setText(((LocalDate) item).format(formatter));
                } else {
                    setText(item.toString());
                }
            }
        };
    }

}
