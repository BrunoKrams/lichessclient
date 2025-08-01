package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.lichess.LichessService;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OngoingGamesScheduledService extends ScheduledService<List<Game>> {

    private final LichessService lichessService;
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    @Autowired
    public OngoingGamesScheduledService(LichessService lichessService) {
        super();
        this.lichessService = lichessService;
        this.setPeriod(Duration.seconds(UPDATE_INTERVAL_IN_SECONDS));
    }

    @Override
    protected Task<List<Game>> createTask() {
        return new Task<>() {
            @Override
            protected List<Game> call() {
                return lichessService.getOngoingGames();
            }
        };
    }
}
