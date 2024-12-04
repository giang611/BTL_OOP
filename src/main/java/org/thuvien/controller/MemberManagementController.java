package org.thuvien.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.thuvien.models.Member;
import org.thuvien.service.MemberService;
import org.thuvien.utils.SessionManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MemberManagementController {

    @FXML
    private TextField searchField;


    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableColumn<Member, Integer> idColumn;

    @FXML
    private TableColumn<Member, String> mssvColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private Label label;

    @FXML
    private TableColumn<Member, String> phoneNumberColumn;

    @FXML
    private TableColumn<Member, LocalDate> createdAtColumn;
    @FXML
    private TableColumn<Member, LocalDate> birthdayColumn;


    @FXML
    private TableColumn<Member, Void> actionColumn;

    private ObservableList<Member> memberList;

    @Autowired
    private MemberService memberService;
    @FXML
    public void initialize() {
        Member member= SessionManager.getCurrentUser();
        if(member.getRole().equals("user")){
            label.setText("Sách đã mượn");
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mssvColumn.setCellValueFactory(new PropertyValueFactory<>("mssv"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        if(birthdayColumn!=null) {
            birthdayColumn.setCellValueFactory(cellData -> {
                Date date = cellData.getValue().getBirthdate();
                LocalDate localDate = (date != null) ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                return new SimpleObjectProperty<>(localDate);
            });

            birthdayColumn.setCellFactory(column -> new TableCell<Member, LocalDate>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            });
        }
        createdAtColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getCreatedAt();
            LocalDate localDate = (date != null) ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            return new SimpleObjectProperty<>(localDate);
        });

        createdAtColumn.setCellFactory(column -> new TableCell<Member, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        loadMemberData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch(newValue));
        actionColumn.setCellFactory(createActionCellFactory());
    }




    private void loadMemberData() {
        List<Member> members = memberService.findAllUser();
        memberList = FXCollections.observableArrayList(members);
        memberTable.setItems(memberList);
    }

    private Callback<TableColumn<Member, Void>, TableCell<Member, Void>> createActionCellFactory() {
        return column -> new TableCell<Member, Void>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            {
                ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/edit.png")));
                editIcon.setFitWidth(24);
                editIcon.setFitHeight(24);
                editButton.setGraphic(editIcon);

                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.png")));
                deleteIcon.setFitWidth(24);
                deleteIcon.setFitHeight(24);
                deleteButton.setGraphic(deleteIcon);

                editButton.setOnAction(event -> handleEditMember(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> handleDeleteMember(getTableView().getItems().get(getIndex())));

                String buttonStyle = "-fx-background-color: transparent; -fx-padding: 5px; -fx-border-width: 0; -fx-cursor: hand;";
                editButton.setStyle(buttonStyle);
                deleteButton.setStyle(buttonStyle);

                setButtonEffect(editButton);
                setButtonEffect(deleteButton);
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

    private void handleEditMember(Member member) {
        ScreenController.switchScreenEditMember((Stage) memberTable.getScene().getWindow(), "/dialog/edit_member.fxml", member);
    }

    private void handleDeleteMember(Member member) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa thành viên này?");
        alert.setContentText("Tên: " + member.getName());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    memberService.deleteMember(member);
                    memberList.remove(member);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Thành công");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Thành viên đã được xóa!");
                    successAlert.show();
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Lỗi");
                    errorAlert.setHeaderText("Không thể xóa thành viên");
                    errorAlert.show();
                }
            }
        });
    }

    private void handleSearch(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        List<Member> filteredList = memberList.stream()
                .filter(member -> member.getName().toLowerCase().contains(lowerCaseKeyword) ||
                        member.getMssv().toLowerCase().contains(lowerCaseKeyword) ||
                        member.getPhoneNumber().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
        memberTable.setItems(FXCollections.observableArrayList(filteredList));
    }

}
