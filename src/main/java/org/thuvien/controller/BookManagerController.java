package org.thuvien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;
import org.thuvien.service.GoogleBooksService;
import java.util.List;

@Controller
public class BookManagerController {
    @FXML
    private TableView<BookDTO> bookTable;
    @FXML
    private TableColumn<BookDTO, String> idColumn;
    @FXML
    private TableColumn<BookDTO, String> genreColumn;
    @FXML
    private TableColumn<BookDTO, String> nameColumn;
    @FXML
    private TableColumn<BookDTO, String> authorColumn;
    @FXML
    private TableColumn<BookDTO, String> descriptionColumn;
    @FXML
    private TableColumn<BookDTO, Void> actionColumn;
    @FXML
    private Button addButton;
    @FXML
    private TextField titleSearchField;
    @FXML
    private TextField authorSearchField;
    @FXML
    private TextField genreSearchField;
    @Autowired
    private BookService bookService;

    @Autowired
    private GoogleBooksService googleBooksService;
    @Autowired
    private BookRepository bookRepository;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));


        loadBooks();


        actionColumn.setCellFactory(new Callback<TableColumn<BookDTO, Void>, TableCell<BookDTO, Void>>() {
            @Override
            public TableCell<BookDTO, Void> call(final TableColumn<BookDTO, Void> param) {
                return new TableCell<BookDTO, Void>() {
                    private final Button viewButton = new Button();
                    private final Button editButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        ImageView viewIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/view.png")));
                        viewIcon.setFitWidth(24);
                        viewIcon.setFitHeight(24);
                        viewButton.setGraphic(viewIcon);

                        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/edit.png")));
                        editIcon.setFitWidth(24);
                        editIcon.setFitHeight(24);
                        editButton.setGraphic(editIcon);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.png")));
                        deleteIcon.setFitWidth(24);
                        deleteIcon.setFitHeight(24);
                        deleteButton.setGraphic(deleteIcon);

                        String buttonStyle = "-fx-background-color: transparent; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;";
                        viewButton.setStyle(buttonStyle);
                        editButton.setStyle(buttonStyle);
                        deleteButton.setStyle(buttonStyle);

                        setButtonEffect(viewButton);
                        setButtonEffect(editButton);
                        setButtonEffect(deleteButton);
                        viewButton.setOnAction(event -> handleViewBook(getTableView().getItems().get(getIndex())));
                        editButton.setOnAction(event -> handleEditBook(getTableView().getItems().get(getIndex())));
                        deleteButton.setOnAction(event -> handleDeleteBook(getTableView().getItems().get(getIndex())));
                    }

                    private void setButtonEffect(Button button) {
                        button.setOnMousePressed(event -> button.setStyle("-fx-background-color: #B2DFDB; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;"));
                        button.setOnMouseReleased(event -> button.setStyle("-fx-background-color: transparent; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(10, viewButton, editButton, deleteButton);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });
    }

    private void loadBooks() {
            List<BookDTO> books = bookService.ListbookDTO();
            ObservableList<BookDTO> bookList = FXCollections.observableArrayList(books);
            bookTable.setItems(bookList);

    }

    @FXML
    private void handleSearch() {
        String name = titleSearchField.getText().trim();
        String author = authorSearchField.getText().trim();
        String genre = genreSearchField.getText().trim();

        List<BookDTO> filteredBooks = bookService.searchBooks(
                name.isEmpty() ? null : name,
                author.isEmpty() ? null : author,
                genre.isEmpty() ? null : genre
        );


        ObservableList<BookDTO> searchResults = FXCollections.observableArrayList(filteredBooks);
        bookTable.setItems(searchResults);
    }



    private void handleViewBook(BookDTO book) {
        ScreenController.switchScreenBook((Stage) addButton.getScene().getWindow(), "/home/viewbook.fxml",book);
    }


    private void handleEditBook(BookDTO book) {

    }


    private void handleDeleteBook(BookDTO book) {

    }

    @FXML
    private void handleAddBook() {
        ScreenController.switchScreen((Stage) addButton.getScene().getWindow(), "/dialog/add_book.fxml");
    }
}
