package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.lichess.LichessOAuthService;
import de.brunokrams.lichessclient.model.lichess.LichessService;
import de.brunokrams.lichessclient.model.recording.AudioRecorder;
import de.brunokrams.lichessclient.model.recording.Device;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import de.brunokrams.lichessclient.model.speechtosan.SpeechToSan;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static javafx.beans.binding.Bindings.isNotNull;

@Component
public class SettingsController {

    @FXML
    private ListView<Game> ongoingGamesListView;
    @FXML
    private ListView<Device> devicesListView;
    @FXML
    private ToggleButton recordingButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label userLabel;

    private final DevicesManager devicesManager;
    private final AudioRecorder audioRecorder;
    private final SpeechToSan speechToSan;

    private final OngoingGamesScheduledService ongoingGamesScheduledService;
    private final DevicesScheduledService devicesScheduledService;

    private final LichessService lichessService;
    private final LichessOAuthService lichessOAuthService;

    private final BooleanProperty isRecording = new SimpleBooleanProperty(false);

    @Autowired
    public SettingsController(DevicesManager devicesManager, AudioRecorder audioRecorder, SpeechToSan speechToSan, OngoingGamesScheduledService ongoingGamesScheduledService, DevicesScheduledService devicesScheduledService, LichessService lichessService, LichessOAuthService lichessOAuthService) {
        this.devicesManager = devicesManager;
        this.audioRecorder = audioRecorder;
        this.speechToSan = speechToSan;
        this.ongoingGamesScheduledService = ongoingGamesScheduledService;
        this.devicesScheduledService = devicesScheduledService;
        this.lichessService = lichessService;
        this.lichessOAuthService = lichessOAuthService;
        assert lichessOAuthService.isAuthenticated();
    }

    @FXML
    public void initialize() {
        initAudioRecorder();
        initRecordingButton();
        initDevicesListView();
        initOngoingGamesListView();
        initUserLabel();
    }

    private void initUserLabel() {
        userLabel.setText("Logged in as user " + lichessService.getActivePlayer().getName());
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

    private void initRecordingButton() {
        recordingButton.disableProperty().bind(isNotNull(devicesListView.getSelectionModel().selectedItemProperty())
                .and(isNotNull(ongoingGamesListView.getSelectionModel().selectedItemProperty())));
        recordingButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            isRecording.setValue(isSelected);
            if (isSelected) {
                audioRecorder.start(0.3d, 1000, devicesManager.getActive());
            } else {
                audioRecorder.stop();
            }
        });
    }

    private interface Displayer<T> {
        String display(T value);
    }

    private interface SelectionCallback<T> {
        void selected(T item);
    }

    private void initOngoingGamesListView() {
        initListView(ongoingGamesListView,
                ongoingGamesScheduledService,
                game -> game.getWhite().getName() + "-" + game.getBlack().getName() + " (" + game.getVariant() + ", " + game.getSpeed() + ")",
                lichessService::setActiveGame
        );
    }

    private void initDevicesListView() {
        initListView(devicesListView,
                devicesScheduledService,
                Device::getName,
                devicesManager::setActive
        );
    }

    private <T> void initListView(ListView<T> listView, ScheduledService<List<T>> service, Displayer<T> displayer, SelectionCallback<T> selectionCallback) {
        ObservableList<T> items = FXCollections.observableArrayList();
        listView.setItems(items);
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(displayer.display(item));
                }
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null || !newSelection.equals(oldSelection)) {
                audioRecorder.stop();
                recordingButton.setSelected(false);
                selectionCallback.selected(newSelection);
            }
        });
        service.setOnSucceeded(event -> {
            T selected = listView.getSelectionModel().getSelectedItem();
            items.setAll(service.getValue());
            if (items.contains(selected)) {
                listView.getSelectionModel().select(selected);
            } else {
                listView.getSelectionModel().clearSelection();
            }
        });

        service.start();
    }
}