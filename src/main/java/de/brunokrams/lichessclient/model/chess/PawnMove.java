package de.brunokrams.lichessclient.model.chess;

import java.util.List;

import static de.brunokrams.lichessclient.model.chess.File.*;
import static de.brunokrams.lichessclient.model.chess.MoveType.MOVE;
import static de.brunokrams.lichessclient.model.chess.MoveType.TAKE;
import static de.brunokrams.lichessclient.model.chess.Piece.KING;
import static de.brunokrams.lichessclient.model.chess.Rank.EIGHT;

public class PawnMove implements Move {

    private final File file;
    private final MoveType moveType;
    private final Field targetField;
    private final Piece promotionPiece;
    private final boolean enPassant;

    PawnMove(File file, MoveType moveType, Field targetField, Piece promotionPiece, boolean enPassant) {
        this.file = file;
        this.moveType = moveType;
        this.targetField = targetField;
        this.promotionPiece = promotionPiece;
        this.enPassant = enPassant;
        if (moveType == MOVE) {
            if (file != null) {
                throw new IllegalArgumentException("File is set for non capturing move.");
            }
            if (enPassant) {
                throw new IllegalArgumentException("En passant is set for non capturing move.");
            }
        }
        if (moveType == TAKE) {
            if (file == null) {
                throw new IllegalArgumentException("File is not set for capturing move.");
            }
            if (!getReachableFiles(file).contains(targetField.getFile())) {
                throw new IllegalArgumentException("Target field is unreachable.");
            }
        }
        if (targetField.getRank() == EIGHT && promotionPiece == null) {
            throw new IllegalArgumentException("Pawn on eighth rank but no promotion piece provided.");
        }
        if (promotionPiece != null && targetField.getRank() != EIGHT) {
            throw new IllegalArgumentException("Promotion piece provided though pawn is not on eighth rank.");
        }
        if (promotionPiece == KING) {
            throw new IllegalArgumentException("Promotion to king is not possible.");
        }
    }

    @Override
    public String asSan() {
        return (file != null ? file.asSan() : "")
                + moveType.asSan()
                + targetField.asSan()
                + (promotionPiece != null ? "=" + promotionPiece.asSan() : "")
                + (enPassant ? " e.p." : "");
    }

    public List<File> getReachableFiles(File file) {
        switch (file) {
            case A -> {
                return List.of(B);
            }
            case B -> {
                return List.of(A, C);
            }
            case C -> {
                return List.of(B, D);
            }
            case D -> {
                return List.of(C, E);
            }
            case E -> {
                return List.of(D, F);
            }
            case F -> {
                return List.of(E, G);
            }
            case G -> {
                return List.of(F, H);
            }
            case H -> {
                return List.of(G);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + file);
        }
    }

}
