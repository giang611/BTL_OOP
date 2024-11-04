package org.thuvien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.Application;
import org.thuvien.dto.BookDTO;
import org.thuvien.service.BookService;
import org.thuvien.service.GoogleBooksService;

import java.io.IOException;
import java.util.List;

@Controller
public class BookManagerController {
@FXML
private HBox mainLayout;
    @FXML
    private TableView<BookDTO> bookTable;
    @FXML
    private TableColumn<BookDTO, String> idColumn;
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

    @Autowired
    private BookService bookService;

    @Autowired
    private GoogleBooksService googleBooksService;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadBooks();

        // Thiết lập cellFactory cho cột hành động
        actionColumn.setCellFactory(new Callback<TableColumn<BookDTO, Void>, TableCell<BookDTO, Void>>() {
            @Override
            public TableCell<BookDTO, Void> call(final TableColumn<BookDTO, Void> param) {
                return new TableCell<BookDTO, Void>() {
                    private final Button viewButton = new Button();
                    private final Button editButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        // Đặt hình ảnh cho các nút
                        ImageView viewIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/view.png")));
                        viewIcon.setFitWidth(24);   // Đặt chiều rộng icon
                        viewIcon.setFitHeight(24);  // Đặt chiều cao icon
                        viewButton.setGraphic(viewIcon);

                        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/edit.png")));
                        editIcon.setFitWidth(24);   // Đặt chiều rộng icon
                        editIcon.setFitHeight(24);  // Đặt chiều cao icon
                        editButton.setGraphic(editIcon);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.png")));
                        deleteIcon.setFitWidth(24);   // Đặt chiều rộng icon
                        deleteIcon.setFitHeight(24);  // Đặt chiều cao icon
                        deleteButton.setGraphic(deleteIcon);

                        // Áp dụng kiểu dáng cho các nút
                        String buttonStyle = "-fx-background-color: transparent; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;";
                        viewButton.setStyle(buttonStyle);
                        editButton.setStyle(buttonStyle);
                        deleteButton.setStyle(buttonStyle);

                        // Thiết lập hiệu ứng sáng cho nút khi nhấn
                        setButtonEffect(viewButton);
                        setButtonEffect(editButton);
                        setButtonEffect(deleteButton);

                        // Đặt sự kiện cho các nút
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
        // Logic tìm kiếm sách
    }

    // Xử lý logic cho nút xem
    private void handleViewBook(BookDTO book) {
        // Logic xem thông tin sách
    }

    // Xử lý logic cho nút sửa
    private void handleEditBook(BookDTO book) {
        // Logic sửa sách
    }

    // Xử lý logic cho nút xóa
    private void handleDeleteBook(BookDTO book) {
        // Logic xóa sách
    }
    public VBox loadFXMLVbox(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource( fxmlFile));
        loader.setControllerFactory(Application.getApplicationContext()::getBean);
        return loader.load();
    }
    public void loadContentVbox(String fxmlFile) {
        try {
            VBox newContent = loadFXMLVbox(fxmlFile);
            if (mainLayout.getChildren().size() > 1) {
                mainLayout.getChildren().remove(1);
            }
            mainLayout.getChildren().add(newContent);
        } catch (IOException e) {
            System.err.println("Lỗi khi tải FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddBook() {
        ScreenController.switchScreen((Stage) addButton.getScene().getWindow(), "/dialog/add_book.fxml");
    }
}
