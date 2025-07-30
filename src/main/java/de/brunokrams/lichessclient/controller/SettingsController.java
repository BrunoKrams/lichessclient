package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.lichess.LichessOAuthService;
import de.brunokrams.lichessclient.model.lichess.LichessService;
import de.brunokrams.lichessclient.model.recording.AudioRecorder;
import de.brunokrams.lichessclient.model.recording.Device;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import de.brunokrams.lichessclient.model.speechtosan.SpeechToSan;
import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static javafx.beans.binding.Bindings.isNotNull;

@Component
public class SettingsController {

    @FXML
    private ListView<Game> ongoingGamesListView;
    @FXML
    private ListView<Device> devicesListView;
    @FXML
    private Button startButton;
    @FXML
    private Label userLabel;

    private final DevicesManager devicesManager;

    private final OngoingGamesScheduledService ongoingGamesScheduledService;
    private final DevicesScheduledService devicesScheduledService;
    private final SceneSwitcher sceneSwitcher;

    private final LichessService lichessService;

    @Autowired
    public SettingsController(DevicesManager devicesManager, OngoingGamesScheduledService ongoingGamesScheduledService, DevicesScheduledService devicesScheduledService, LichessService lichessService, LichessOAuthService lichessOAuthService, SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
        assert lichessOAuthService.isAuthenticated();
        this.devicesManager = devicesManager;
        this.ongoingGamesScheduledService = ongoingGamesScheduledService;
        this.devicesScheduledService = devicesScheduledService;
        this.lichessService = lichessService;
    }

    @FXML
    public void initialize() {
        initStartButton();
        initDevicesListView();
        initOngoingGamesListView();
        initUserLabel();
    }

    private void initStartButton() {
        startButton.disableProperty().bind(
                devicesListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(ongoingGamesListView.getSelectionModel().selectedItemProperty().isNull())
        );
    }

    private void initUserLabel() {
        userLabel.setText("Logged in as user " + lichessService.getActivePlayer().getName());
    }

    public void start(ActionEvent actionEvent) throws IOException {
        devicesScheduledService.cancel();
        ongoingGamesScheduledService.cancel();
        sceneSwitcher.displayRecording();
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
        service.restart();
    }
}