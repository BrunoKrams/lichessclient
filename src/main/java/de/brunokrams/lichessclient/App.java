package de.brunokrams.lichessclient;

import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class App extends Application {

    private static final Logger log = LoggerFactory.getLogger(App.class);
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
        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage, springContext);
        springContext.getBeanFactory().registerSingleton("sceneSwitcher", sceneSwitcher);
        stage.setTitle("Lichess Client");
        sceneSwitcher.showLogin();
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