package de.brunokrams.lichessclient.controller;

import com.sun.tools.javac.Main;
import de.brunokrams.lichessclient.AppConfig;
import de.brunokrams.lichessclient.model.AudioRecorder;
import de.brunokrams.lichessclient.model.Device;
import de.brunokrams.lichessclient.model.DevicesManager;
import de.brunokrams.lichessclient.model.Recording;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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

    @Autowired
    AudioFormat audioFormat;

    private void initAudioRecorder() {
        audioRecorder.setRecordingReadyListener(recording -> {
            logger.info("Recording finished");
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File outFile = new File("recording_" + timestamp + ".wav");
            try (ByteArrayInputStream bais = new ByteArrayInputStream(recording.getData());
                 AudioInputStream audioInputStream = new AudioInputStream(bais, audioFormat, recording.getData().length / audioFormat.getFrameSize())) {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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