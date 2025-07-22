package de.brunokrams.lichessclient.model.chess;

import java.util.ArrayList;
import java.util.List;

import static de.brunokrams.lichessclient.model.chess.Piece.*;

class PieceMove implements Move {

    private final Piece piece;
    private final File originFile;
    private final Field originField;
    private final MoveType moveType;
    private final Field targetField;

    PieceMove(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField) {
        this.piece = piece;
        this.originFile = originFile;
        this.originField = originField;
        this.moveType = moveType;
        this.targetField = targetField;
        if (originFile != null && originField != null) {
            throw new IllegalArgumentException("Both origin file and field are set.");
        }
        if (piece == KNIGHT || piece == BISHOP || piece == ROOK) {
            List<Field> reachableFields;
            if (originFile != null) {
                reachableFields = new ArrayList<>();
                for (Field field : originFile.getFields()) {
                    reachableFields.addAll(piece.getReachableFields(field));
                }
                if (!reachableFields.contains(targetField)) {
                    throw new IllegalArgumentException("Target field is not reachable from field.");
                }
            }
            if (originField != null) {
                reachableFields = piece.getReachableFields(originField);
                if (!reachableFields.contains(targetField)) {
                    throw new IllegalArgumentException("Target field is not reachable from file.");
                }
            }
        }
        if (piece == KING) {
            if (originFile != null) {
                throw new IllegalArgumentException("File provided though piece is unique.");
            }
            if (originField != null) {
                throw new IllegalArgumentException("Field provided though piece is unique.");
            }
        }
        if (piece == QUEEN) {
            if (originFile != null) {
                throw new IllegalArgumentException("File provided though piece is unique.");
            }
            if (originField != null) {
                throw new IllegalArgumentException("Field provided though piece is unique.");
            }
        }
    }

    @Override
    public String asSan() {
        return piece.asSan()
                + (originField != null ? originField.asSan() : "")
                + (originFile != null ? originFile.asSan() : "")
                + moveType.asSan()
                + targetField.asSan();
    }
}
