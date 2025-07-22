package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.domain.recording.AudioRecorder;
import de.brunokrams.lichessclient.domain.recording.Device;
import de.brunokrams.lichessclient.domain.recording.DevicesManager;
import de.brunokrams.lichessclient.domain.speechtosan.SpeechToSan;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @FXML
    private ListView gamesListView;
    @FXML
    private ListView<Device> devicesListView;
    @FXML
    private ToggleButton recordingButton;
    @FXML
    private Label statusLabel;

    private final DevicesManager devicesManager;
    private final AudioRecorder audioRecorder;
    private final SpeechToSan speechToSan;

    private final BooleanProperty isRecording = new SimpleBooleanProperty(false);

    @Autowired
    public MainController(DevicesManager devicesManager, AudioRecorder audioRecorder, SpeechToSan speechToSan) {
        this.devicesManager = devicesManager;
        this.audioRecorder = audioRecorder;
        this.speechToSan = speechToSan;
    }

    @FXML
    public void initialize() {
        initAudioRecorder();
        initDevicesManager();
        initRecordingButton();
        initDevicesListView();
        initOngoingGamesListView();
    }

    private void initAudioRecorder() {
        audioRecorder.setRecordingStartedListener(() -> setStatusLabelText("Voice detected. Started recording."));
        audioRecorder.setRecordingReadyListener(recording -> {
            setStatusLabelText("Recording finished. Translating to SAN.");
            String sanMove = speechToSan.speechToSan(recording);
            setStatusLabelText(sanMove);
        });
    }

    private void setStatusLabelText(String text) {
        Platform.runLater(() -> statusLabel.setText(text));
    }

    private void initDevicesManager() {
        devicesManager.getDefault().ifPresent(devicesManager::setActive);
    }

    private void initRecordingButton() {
        recordingButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            isRecording.setValue(isSelected);
            if (isSelected) {
                audioRecorder.start(0.3d, 1000, devicesManager.getActive());
            } else {
                audioRecorder.stop();
            }
        });
    }

    private void initOngoingGamesListView() {
        // TODO implement
    }

    private void initDevicesListView() {
        ObservableList<Device> items = FXCollections.observableArrayList();
        devicesListView.setItems(items);
        devicesListView.setCellFactory(deviceListView -> new ListCell<>() {
            @Override
            protected void updateItem(Device device, boolean empty) {
                super.updateItem(device, empty);
                if (empty || device == null) {
                    setText(null);
                } else {
                    setText(device.getName());
                }
            }
        });
        devicesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (!newSelection.equals(oldSelection)) {
                devicesManager.setActive(newSelection);
            }
        });
        items.addAll(devicesManager.getDevices());
        devicesManager.getDefault().ifPresent(device -> devicesListView.getSelectionModel().select(device));
    }
}