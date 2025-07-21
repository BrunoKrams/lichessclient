package de.brunokrams.lichessclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class App extends Application {

    private AnnotationConfigApplicationContext springContext;

    @Override
    public void init() throws IOException {
        springContext = new AnnotationConfigApplicationContext();
        springContext.getEnvironment().getPropertySources().addLast(new ResourcePropertySource("classpath:application.properties"));
        springContext.register(AppConfig.class);
        springContext.refresh();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setTitle("Lichess Client");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}