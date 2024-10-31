package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.BookGoogle;
import org.thuvien.service.GoogleBooksService;

import java.util.List;

@Controller
public class BookSearchController {

    @FXML
    private TextField txtSearchQuery;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<BookGoogle> tableBooks;

    @FXML
    private TableColumn<BookGoogle, String> colTitle;

    @FXML
    private TableColumn<BookGoogle, String> colAuthor;

    @FXML
    private TableColumn<BookGoogle, String> colDescription;
    @FXML TableColumn<BookGoogle, String> colIsbn;

    @Autowired
    private GoogleBooksService googleBooksService;

    @FXML
    private void initialize() {
        // Gán giá trị cho các cột
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    }

    @FXML
    private void handleSearchBooks() {
        String query = txtSearchQuery.getText().trim();
        if (!query.isEmpty()) {
            List<BookGoogle> bookList = googleBooksService.getBookInfo(query);
            ObservableList<BookGoogle> observableBookList = FXCollections.observableArrayList(bookList);
            tableBooks.setItems(observableBookList);
        }
    }
}
