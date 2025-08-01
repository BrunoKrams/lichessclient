package de.brunokrams.lichessclient.view;

import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Led extends StackPane {

    private static final int RADIUS = 100;

    private final Circle ledCircle;
    private static final Color offColor = Color.DARKGRAY;
    private final ObjectProperty<Color> onColor = new SimpleObjectProperty<>(Color.LIMEGREEN);
    private final PauseTransition pauseTransition;

    public Led() {
        ledCircle = new Circle(RADIUS);
        ledCircle.setFill(offColor);
        getChildren().add(ledCircle);
        pauseTransition = new PauseTransition(Duration.millis(200));
        pauseTransition.setOnFinished(e -> ledCircle.setFill(offColor));
    }

    public void blink() {
        ledCircle.setFill(onColor.get());
        pauseTransition.playFromStart();
    }
}
