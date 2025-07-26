package de.brunokrams.lichessclient.view;

import de.brunokrams.lichessclient.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SceneSwitcher {

    private Stage stage;
    private Scene main;
    private Scene login;

    public void init(Stage stage, ConfigurableApplicationContext configurableApplicationContext) throws IOException {
        this.stage = stage;
        this.main = createScene("view/main.fxml", configurableApplicationContext);
        this.login = createScene("view/login.fxml", configurableApplicationContext);
    }

    private Scene createScene(String pathToView, ConfigurableApplicationContext configurableApplicationContext) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(pathToView));
        fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        return scene;
    }

    public void displayLogin() {
        stage.setScene(login);
    }

    public void displayMain() {
        stage.setScene(main);
    }
}

