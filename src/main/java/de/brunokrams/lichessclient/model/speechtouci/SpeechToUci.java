package de.brunokrams.lichessclient.model.speechtouci;

import com.github.bhlangonijr.chesslib.move.Move;
import de.brunokrams.lichessclient.model.recording.Recording;

import java.util.List;

public interface SpeechToUci {
    String speechToUci(Recording recording, List<Move> legalMoves);
}
