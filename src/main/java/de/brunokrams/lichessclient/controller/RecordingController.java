package de.brunokrams.lichessclient.controller;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import de.brunokrams.lichessclient.model.chess.ChessEngine;
import de.brunokrams.lichessclient.model.lichess.LichessService;
import de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames.GetMyOngoingGames;
import de.brunokrams.lichessclient.model.lichess.api.makeaboardmove.MakeABoardMove;
import de.brunokrams.lichessclient.model.recording.AudioRecorder;
import de.brunokrams.lichessclient.model.recording.DevicesManager;
import de.brunokrams.lichessclient.model.speechtouci.SpeechToUci;
import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RecordingController {

    private final LichessService lichessService;
    private final SceneSwitcher sceneSwitcher;
    private final AudioRecorder audioRecorder;
    private final DevicesManager devicesManager;
    private final SpeechToUci speechToUci;
    private final MakeABoardMove makeABoardMove;
    private final ChessEngine chessEngine;
    private final GetMyOngoingGames getMyOngoingGames;

    @FXML
    private Label userLabel;

    @FXML
    private Label statusLabel;

    @Autowired
    public RecordingController(LichessService lichessService, SceneSwitcher sceneSwitcher, AudioRecorder audioRecorder, DevicesManager devicesManager, SpeechToUci speechToUci, MakeABoardMove makeABoardMove, ChessEngine chessEngine, GetMyOngoingGames getMyOngoingGames) {
        this.lichessService = lichessService;
        this.sceneSwitcher = sceneSwitcher;
        this.audioRecorder = audioRecorder;
        this.devicesManager = devicesManager;
        this.speechToUci = speechToUci;
        this.makeABoardMove = makeABoardMove;
        this.chessEngine = chessEngine;
        this.getMyOngoingGames = getMyOngoingGames;
    }

    @FXML
    public void initialize() {
        initUserLabel();
        audioRecorder.start(0.3d, 1000, devicesManager.getActive());
        audioRecorder.setRecordingStartedListener(() -> displayStatusText("Voice detected. Started recording."));
        audioRecorder.setRecordingReadyListener(recording -> {
            displayStatusText("Recording ready. Transforming to uci.");
            String fen = getMyOngoingGames
                    .submit(lichessService.getActivePlayer())
                    .stream()
                    .filter(game -> game.getId().equals(lichessService.getActiveGame().getId()))
                    .findFirst()
                    .orElseThrow().getFen();
            List<Move> legalMoves = chessEngine.getlegalMoves(fen);
            String uci = speechToUci.speechToUci(recording, legalMoves);
            if (uci.contains("ERROR")) {
                displayStatusText("Did not understand correctly. Please try again.");
            } else {
                displayStatusText("Making move " + uci);
                makeABoardMove.submit(lichessService.getActiveGame(), uci);
            }
        });
    }

    public void cancel() throws IOException {
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
