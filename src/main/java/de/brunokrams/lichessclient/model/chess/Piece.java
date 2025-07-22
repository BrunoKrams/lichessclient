package de.brunokrams.lichessclient.model.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Piece {

    KNIGHT("N") {
        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented.");
        }
    }, BISHOP("B") {
        @Override
        public List<Field> getReachableFields(Field field) {
            return Diagonal.getContainingDiagonals(field).stream().flatMap(diagonal -> diagonal.getFields().stream()).filter(f -> !f.equals(field)).toList();
        }
    }, ROOK("R") {
        @Override
        public List<Field> getReachableFields(Field field) {
            List<Field> result = new ArrayList<>();
            result.addAll(field.getFile().getFields());
            result.addAll(field.getRank().getFields());
            result.removeAll(Collections.singleton(field));
            return result;
        }
    }, QUEEN("Q") {
        @Override
        public List<Field> getReachableFields(Field field) {
            List<Field> result = new ArrayList<>();
            result.addAll(BISHOP.getReachableFields(field));
            result.addAll(ROOK.getReachableFields(field));
            return result;
        }
    }, KING("K") {
        @Override
        public List<Field> getReachableFields(Field field) {
            List<Field> result = new ArrayList<>();
            char originFileChar = field.getFile().getSanChar();
            int originRankIndex = field.getRank().getIndex();
            for (char fileChar = (char) (originFileChar - 1); fileChar <= originFileChar + 1; fileChar++) {
                for (int rankIndex = originRankIndex - 1; rankIndex <= originRankIndex + 1; rankIndex++) {
                    try {
                        result.add(Field.fromFileAndRank(File.fromChar(fileChar), Rank.fromIndex(rankIndex)));
                    } catch (Exception e) {
                        // Nothing to do here. Just a move which is outside of the chess board.
                    }
                }
            }
            result.removeAll(Collections.singleton(field));
            return result;
        }
    };

    private final String sanString;

    Piece(String sanString) {
        this.sanString = sanString;
    }

    public String asSan() {
        return sanString;
    }

    public abstract List<Field> getReachableFields(Field field);
}

