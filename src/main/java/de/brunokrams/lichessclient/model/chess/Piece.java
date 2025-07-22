package de.brunokrams.lichessclient.model.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Piece {

    KNIGHT("N") {
        @Override
        public List<Field> getReachableFields(Field field) {
            List<Field> result = new ArrayList<>();
            List<Offsets> allOffsets = List.of(
                    new Offsets(-2, -1),
                    new Offsets(-2, +1),
                    new Offsets(-1, -2),
                    new Offsets(-1, +2),
                    new Offsets(+1, -2),
                    new Offsets(+1, +2),
                    new Offsets(+2, -1),
                    new Offsets(+2, +1)
            );
            char originFileChar = field.getFile().getSanChar();
            int originRankIndex = field.getRank().getIndex();
            for (Offsets offsets : allOffsets) {
                try {
                    char fileChar = (char) (originFileChar + offsets.fileOffset);
                    int rankIndex = originRankIndex + offsets.rankOffset;
                    result.add(Field.fromFileAndRank(File.fromChar(fileChar), Rank.fromIndex(rankIndex)));
                } catch (Exception e) {
                    // Nothing to do here. Just a move which is outside of the chess board.
                }
            }
            return result;
        }

        private class Offsets {
            private final int fileOffset;
            private final int rankOffset;

            private Offsets(int fileOffset, int rankOffset) {
                this.fileOffset = fileOffset;
                this.rankOffset = rankOffset;
            }

            public int getFileOffset() {
                return fileOffset;
            }

            public int getRankOffset() {
                return rankOffset;
            }
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

