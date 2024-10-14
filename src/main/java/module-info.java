module org.prj.projectt {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.prj.projectt;
    exports org.prj.projectt.Login;
    opens org.prj.projectt.Login to javafx.fxml;
}
