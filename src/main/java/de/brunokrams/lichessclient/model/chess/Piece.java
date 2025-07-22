package de.brunokrams.lichessclient.model.chess;

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
            throw new RuntimeException("Not yet implemented.");
        }
    }, ROOK("R") {
        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented.");
        }
    }, QUEEN("Q") {
        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented.");
        }
    }, KING("K") {
        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented.");
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

