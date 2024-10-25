package org.thuvien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.service.BookService;

import java.util.List;

@Controller
public class BookManagerController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<BookDTO> bookTable;
    @FXML
    private TableColumn<BookDTO, Integer> idColumn;
    @FXML
    private TableColumn<BookDTO, String> nameColumn;
    @FXML
    private TableColumn<BookDTO, String> authorColumn;
    @FXML
    private TableColumn<BookDTO, String> descriptionColumn;
    @FXML
    private TableColumn<BookDTO, Integer> quantity;

    @Autowired
    private BookService bookService;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        if (bookService != null) {
            List<BookDTO> books = bookService.ListbookDTO();
            ObservableList<BookDTO> bookList = FXCollections.observableArrayList(books);
            bookTable.setItems(bookList);
        } else {
            System.err.println("BookService chưa được khởi tạo.");
        }
    }

    @FXML
    private void handleSearch() {
    }

    @FXML
    private void handleAddBook() {
    }
}
