package org.thuvien.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thuvien.models.Borrow;
import org.thuvien.service.BorrowService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BorrowManagementController {

    @FXML
    private TextField searchField;

    @FXML
    private Button borrowButton;
    @FXML
    private TableColumn<Borrow, Void> actionColumn;

    @FXML
    private TableView<Borrow> bookTable;

    @FXML
    private TableColumn<Borrow, Integer> serialColumn;

    @FXML
    private TableColumn<Borrow, String> nameColumn;

    @FXML
    private TableColumn<Borrow, String> librarianColumn;
    @FXML
    private TableColumn<Borrow, String> quantityColumn;

    @FXML
    private TableColumn<Borrow, LocalDate> borrowDateColumn;

    @FXML
    private TableColumn<Borrow, LocalDate> returnDateColumn;

    @FXML
    private TableColumn<Borrow, String> statusColumn;

    private ObservableList<Borrow> borrowList;

    @Autowired
    private BorrowService borrowService;

    @FXML
    public void initialize() {
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMember().getName()));
        librarianColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibrarian()));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        borrowDateColumn.setCellFactory(createDateCellFactory(formatter));
        returnDateColumn.setCellFactory(createDateCellFactory(formatter));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadBorrowData();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch(newValue));
        actionColumn.setCellFactory(new Callback<TableColumn<Borrow, Void>, TableCell<Borrow, Void>>() {
            @Override
            public TableCell<Borrow, Void> call(final TableColumn<Borrow, Void> param) {
                return new TableCell<Borrow, Void>() {
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
                        editButton.setStyle(buttonStyle);
                        deleteButton.setStyle(buttonStyle);


                        setButtonEffect(editButton);
                        setButtonEffect(deleteButton);
                        editButton.setOnAction(event -> handleEditBorrow(getTableView().getItems().get(getIndex())));
                        deleteButton.setOnAction(event -> handleDeleteBorrow(getTableView().getItems().get(getIndex())));
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
                            HBox actionButtons = new HBox(10, editButton, deleteButton);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });
    }


    private Callback<TableColumn<Borrow, LocalDate>, javafx.scene.control.TableCell<Borrow, LocalDate>> createDateCellFactory(DateTimeFormatter formatter) {
        return column -> new TextFieldTableCell<Borrow, LocalDate>() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        };
    }
    private void handleEditBorrow(Borrow borrow) {
        ScreenController.switchScreenEditBorrow((Stage) borrowButton.getScene().getWindow(), "/dialog/edit_borrow.fxml",borrow);
    }


    private void handleDeleteBorrow(Borrow borrow) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Xác nhận xóa");
        confirmationDialog.setHeaderText("Bạn có chắc chắn muốn xóa bản ghi mượn này?");
        confirmationDialog.setContentText("Hành động này không thể hoàn tác!");

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    borrowService.deleteBorrow(borrow.getId());

                    borrowList.remove(borrow);
                    bookTable.refresh();

                    showAlert(Alert.AlertType.INFORMATION, "Xóa thành công", "Bản ghi đã được xóa.");
                } catch (Exception e) {

                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa bản ghi!");
                }
            }
        });
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void loadBorrowData() {
        List<Borrow> borrows = borrowService.findAllBorrows();
        borrowList = FXCollections.observableArrayList(borrows);
        bookTable.setItems(borrowList);
    }

    private void handleSearch(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        List<Borrow> filteredList = borrowList.stream()
                .filter(borrow -> borrow.getMember().getName().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
        bookTable.setItems(FXCollections.observableArrayList(filteredList));
    }
    @FXML
    private void handleBorrow() {
        ScreenController.switchScreen((Stage) borrowButton.getScene().getWindow(), "/dialog/BorrowRecord.fxml");
    }
}
