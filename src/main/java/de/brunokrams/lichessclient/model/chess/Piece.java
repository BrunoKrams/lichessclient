package de.brunokrams.lichessclient.model.chess;

import java.util.Arrays;
import java.util.List;

public enum Piece {

    KNIGHT('N'),
    BISHOP('B'),
    ROOK('R'),
    QUEEN('Q'),
    KING('K');

    public static final List<Piece> PROMOTABLE = List.of(KNIGHT, BISHOP, ROOK, QUEEN);


    private final char san;

    Piece(char san) {
        this.san = san;
    }

    public String asSan() {
        return "" + san;
    }

    public static Piece fromChar(char c) {
        return Arrays.stream(values())
                .filter(piece -> piece.san == c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}

