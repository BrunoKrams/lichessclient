package de.brunokrams.lichessclient;

import de.brunokrams.lichessclient.model.lichess.LichessOAuthService;
import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class App extends Application {

    private ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        configurableApplicationContext = new SpringApplicationBuilder(AppConfig.class)
                .web(WebApplicationType.SERVLET)
                .initializers(applicationContext -> applicationContext.getBeanFactory().registerSingleton("hostServices", getHostServices()))
                .run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        SceneSwitcher sceneSwitcher = configurableApplicationContext.getBean("sceneSwitcher", SceneSwitcher.class);
        sceneSwitcher.init(stage, configurableApplicationContext);
        LichessOAuthService lichessOAuthService = configurableApplicationContext.getBean(LichessOAuthService.class);
        if (lichessOAuthService.isAuthenticated()) {
            sceneSwitcher.displaySettings();
        } else {
            sceneSwitcher.displayLogin();
        }
        stage.show();
    }

    @Override
    public void stop() {
        configurableApplicationContext.close();
    }


}