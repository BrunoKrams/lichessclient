package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.lichess.LichessService;
import de.brunokrams.lichessclient.model.recording.AudioRecorder;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import de.brunokrams.lichessclient.model.speechtosan.SpeechToSan;
import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RecordingController {

    private final LichessService lichessService;
    private final SceneSwitcher sceneSwitcher;
    private final AudioRecorder audioRecorder;
    private final DevicesManager devicesManager;
    private final SpeechToSan speechToSan;

    @FXML
    private Label userLabel;

    @FXML
    private Label statusLabel;

    @Autowired
    public RecordingController(LichessService lichessService, SceneSwitcher sceneSwitcher, AudioRecorder audioRecorder, DevicesManager devicesManager, SpeechToSan speechToSan) {
        this.lichessService = lichessService;
        this.sceneSwitcher = sceneSwitcher;
        this.audioRecorder = audioRecorder;
        this.devicesManager = devicesManager;
        this.speechToSan = speechToSan;
    }

    @FXML
    public void initialize() {
        initUserLabel();
        audioRecorder.start(0.3d, 1000, devicesManager.getActive());
        audioRecorder.setRecordingStartedListener(() -> displayStatusText("Voice detected. Started recording."));
        audioRecorder.setRecordingReadyListener(recording -> {
            displayStatusText("Recording ready. Transforming to san.");
            String san = speechToSan.speechToSan(recording);
            if (san.contains("ERROR")) {
                displayStatusText("Did not understand correctly. Please try again.");
            }
            displayStatusText("Making move " + san);
        });
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        audioRecorder.stop();
        sceneSwitcher.displaySettings();
    }

    private void initUserLabel() {
        userLabel.setText("Logged in as user " + lichessService.getActivePlayer().getName());
    }

    private void displayStatusText(String text) {
        Platform.runLater(() -> statusLabel.setText(text));
    }

}
