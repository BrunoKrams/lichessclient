package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.recording.AudioRecorder;
import de.brunokrams.lichessclient.model.recording.Device;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Label recordingLabel;

    private final DevicesManager devicesManager;
    private final AudioRecorder audioRecorder;

    private final BooleanProperty isRecording = new SimpleBooleanProperty(false);

    @Autowired
    public MainController(DevicesManager devicesManager, AudioRecorder audioRecorder) {
        this.devicesManager = devicesManager;
        this.audioRecorder = audioRecorder;
    }

    @FXML
    public void initialize() {
        initAudioRecorder();
        initDevicesManager();
        initRecordingButton();
        initRecordingLabel();
        initDevicesListView();
        initOngoingGamesListView();
    }

    private void initAudioRecorder() {
        audioRecorder.setRecordingReadyListener(recording -> logger.info("Recording finished"));
        audioRecorder.setRecordingStartedListener(() -> logger.info("Voice detected. Started recording."));
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

    private void initRecordingLabel() {
        recordingLabel.visibleProperty().bind(isRecording);
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