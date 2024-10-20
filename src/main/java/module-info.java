module org.prj.projectt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.context;
    requires java.management;
    exports org.prj.projectt;
    exports org.prj.projectt.Login;
    exports org.prj.projectt.Screen;
    opens org.prj.projectt.Login to javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    opens org.prj.projectt to spring.core, spring.beans, spring.context,spring.boot,spring.boot.autoconfigure;
    requires spring.core;
    exports org.prj.projectt.Entity;
    opens org.prj.projectt.Entity to spring.core;
}

