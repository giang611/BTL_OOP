package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class BookManagerController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> bookTable;

    @FXML
    private void handleSearch() {

    }

    @FXML
    private void handleAddBook() {
    }
}
