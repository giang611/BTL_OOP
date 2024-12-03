package org.thuvien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.dto.BookDTO;
import org.thuvien.models.Member;
import org.thuvien.repository.BookRepository;
import org.thuvien.service.BookService;
import org.thuvien.service.GoogleBooksService;
import org.thuvien.utils.SessionManager;

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
    private TableColumn<BookDTO, String> quantity;
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
    @FXML
    private Label bookLabel;
    @Autowired
    private BookService bookService;

    @Autowired
    private GoogleBooksService googleBooksService;
    @Autowired
    private BookRepository bookRepository;

    @FXML
    public void initialize() {
        Member member = SessionManager.getCurrentUser();
        if (member.getRole().equals("user")) {
            bookLabel.setText("Kho sách");
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));

        loadBooks();

        actionColumn.setCellFactory(new Callback<TableColumn<BookDTO, Void>, TableCell<BookDTO, Void>>() {
            @Override
            public TableCell<BookDTO, Void> call(final TableColumn<BookDTO, Void> param) {
                Member currentUser = SessionManager.getCurrentUser();
                if (currentUser.getRole().equals("admin")) {
                    return createAdminActionsCell();
                }
                return createUserActionsCell();
            }
        });
    }

    private TableCell<BookDTO, Void> createAdminActionsCell() {
        return new TableCell<BookDTO, Void>() {
            private final Button viewButton = new Button();
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            {
                setupButton(viewButton, "/icons/view.png", event -> handleViewBook(getTableView().getItems().get(getIndex())));
                setupButton(editButton, "/icons/edit.png", event -> handleBorrowBook(getTableView().getItems().get(getIndex())));
                setupButton(deleteButton, "/icons/delete.png", event -> handleDeleteBook(getTableView().getItems().get(getIndex())));
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

    private TableCell<BookDTO, Void> createUserActionsCell() {
        return new TableCell<BookDTO, Void>() {
            private final Button viewButton = new Button();
            private final Button borrowButton = new Button();
            private final Button appreciateButton = new Button();

            {
                setupButton(viewButton, "/icons/view.png", event -> handleViewBook(getTableView().getItems().get(getIndex())));
                setupButton(borrowButton, "/icons/borrow.png", event -> handleBorrowBook(getTableView().getItems().get(getIndex())));
                setupButton(appreciateButton, "/icons/appreciation.png", event -> handleAppreciateBook(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(10, viewButton, borrowButton, appreciateButton);
                    setGraphic(actionButtons);
                }
            }
        };
    }

    private void setupButton(Button button, String iconPath, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        button.setGraphic(icon);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;");
        button.setOnAction(action);
        button.setOnMousePressed(event -> button.setStyle("-fx-background-color: #B2DFDB; -fx-padding: 5px;"));
        button.setOnMouseReleased(event -> button.setStyle("-fx-background-color: transparent; -fx-padding: 5px;"));
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
        ScreenController.switchScreenBook((Stage) addButton.getScene().getWindow(), "/dialog/viewbook.fxml", book);
    }

    private void handleBorrowBook(BookDTO book) {
        Member member = SessionManager.getCurrentUser();
        if (member.getRole().equals("user"))
            ScreenController.switchScreenEditBorrowUser((Stage) addButton.getScene().getWindow(), "/dialog/borrow_user.fxml", book);
        else
            ScreenController.switchScreenBookEdit((Stage) addButton.getScene().getWindow(), "/dialog/edit_book.fxml", book);
    }

    private void handleAppreciateBook(BookDTO book) {
        ScreenController.switchScreenBookAp((Stage) addButton.getScene().getWindow(), "/dialog/AppreciateBook.fxml", book);
    }

    private void handleDeleteBook(BookDTO book) {
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeleteAlert.setTitle("Xóa sách");
        confirmDeleteAlert.setHeaderText("Bạn chắc chắn muốn xóa?");
        confirmDeleteAlert.setContentText("Tên sách: " + book.getName());

        confirmDeleteAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                bookService.deleteBook(book);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Xóa thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Sách đã được xóa thành công.");
                successAlert.show();

                loadBooks();
            }
        });
    }

    @FXML
    private void handleAddBook() {
        ScreenController.switchScreen((Stage) addButton.getScene().getWindow(), "/dialog/add_book.fxml");
    }
}
