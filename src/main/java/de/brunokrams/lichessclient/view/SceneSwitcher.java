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
    private ConfigurableApplicationContext configurableApplicationContext;

    public void init(Stage stage, ConfigurableApplicationContext configurableApplicationContext) {
        this.stage = stage;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    public void displayLogin() throws IOException {
        stage.setScene(createLoginScene());
    }

    public void displaySettings() throws IOException {
        stage.setScene(createSettingsScene());
    }

    public void displayRecording() throws IOException {
        stage.setScene(createRecordingScene());
    }

    private Scene createLoginScene() throws IOException {
        return createScene("view/login.fxml", configurableApplicationContext);
    }

    private Scene createSettingsScene() throws IOException {
        return createScene("view/settings.fxml", configurableApplicationContext);
    }

    private Scene createRecordingScene() throws IOException {
        return createScene("view/recording.fxml", configurableApplicationContext);
    }

    private Scene createScene(String pathToView, ConfigurableApplicationContext configurableApplicationContext) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(pathToView));
        fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        return scene;
    }
}

