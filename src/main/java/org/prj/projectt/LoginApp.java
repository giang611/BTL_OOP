package org.prj.projectt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.prj.projectt.Entity.check;
import org.prj.projectt.Entity.check2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.management.MXBean;
import java.io.IOException;

@Configuration(proxyBeanMethods = false)
@SpringBootApplication
public class LoginApp extends Application {
private ConfigurableApplicationContext context;
@Autowired
private check2 a;
@Override
public void init() throws Exception {
    this.context= SpringApplication.run(LoginApp.class);
}

@Override
public void stop() throws Exception {
        this.context.close();
}
    @Override
    public void start(Stage primaryStage) throws IOException {
        a = context.getBean(check2.class);
    a.chekck2();
    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root,600,375);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Quản lý thư viện");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
