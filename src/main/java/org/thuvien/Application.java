package org.thuvien;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.thuvien.Login.LoginController;

@Configuration
@SpringBootApplication
public class Application extends javafx.application.Application {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        this.applicationContext = SpringApplication.run(Application.class);
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginController.loadView(stage);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

