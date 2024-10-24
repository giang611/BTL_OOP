package org.thuvien.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {

    @FXML
    private Button btnManageStaff;

    @FXML
    private Button btnManageCategories;

    @FXML
    private Button btnManageLoans;

    @FXML
    private Button btnManageReaders;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnReports;

    @FXML
    private ImageView imgStaff;

    @FXML
    private ImageView imgBook;

    @FXML
    private ImageView imgLoans;

    @FXML
    private ImageView imgReaders;

    @FXML
    private ImageView imgSearch;

    @FXML
    private ImageView imgLogout;

    @FXML
    private void handleManageStaff(MouseEvent event) {
        System.out.println("Chức năng Quản lý Nhân viên đã được chọn.");
    }

    @FXML
    private void handleBook(MouseEvent event) {
        ScreenController.switchScreen((Stage) btnSearch.getScene().getWindow(), "/home/mix.fxml");
    }

    @FXML
    private void handleManageLoans(MouseEvent event) {
        System.out.println("Chức năng Quản lý Mượn Trả đã được chọn.");
    }

    @FXML
    private void handleManageReaders(MouseEvent event) {
        System.out.println("Chức năng Quản lý Độc giả đã được chọn.");
    }

    @FXML
    private void handleSearch(MouseEvent event) {
        System.out.println("Chức năng Tìm kiếm đã được chọn.");
    }

    @FXML
    private void handleLogout(MouseEvent event) {
        System.out.println("Chức năng Thống kê Báo cáo đã được chọn.");
    }
}
