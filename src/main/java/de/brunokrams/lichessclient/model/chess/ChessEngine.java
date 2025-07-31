package de.brunokrams.lichessclient.model.chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChessEngine {

    public List<Move> getlegalMoves(String positionFen) {
        Board board = new Board();
        board.loadFromFen(positionFen);
        return board.legalMoves();
    }


}
