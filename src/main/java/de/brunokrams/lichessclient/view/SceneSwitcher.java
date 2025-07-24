package de.brunokrams.lichessclient.view;

import de.brunokrams.lichessclient.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class SceneSwitcher {

    private final Stage stage;
    private final ApplicationContext applicationContext;
    private final Scene main;
    private final Scene web;
    private final Scene login;

    public SceneSwitcher(Stage stage, ApplicationContext applicationContext) throws IOException {
        this.stage = stage;
        this.applicationContext = applicationContext;

        this.main = createScene("view/main.fxml");
        this.login = createScene("view/login.fxml");
        this.web = createScene("view/web.fxml");
    }

    private Scene createScene(String pathToView) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(pathToView));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        return scene;
    }

    public void showLogin() {
        stage.setScene(login);
    }
}
