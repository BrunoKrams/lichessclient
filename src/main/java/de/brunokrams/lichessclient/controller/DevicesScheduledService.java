package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.lichess.LichessService;
import de.brunokrams.lichessclient.model.recording.Device;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevicesScheduledService extends ScheduledService<List<Device>> {

    private final DevicesManager devicesManager;
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    @Autowired
    public DevicesScheduledService(DevicesManager devicesManager) {
        super();
        this.devicesManager = devicesManager;
        this.setPeriod(Duration.seconds(UPDATE_INTERVAL_IN_SECONDS));
    }

    @Override
    protected Task<List<Device>> createTask() {
        return new Task<>() {
            @Override
            protected List<Device> call() {
                return devicesManager.getDevices();
            }
        };
    }
}


