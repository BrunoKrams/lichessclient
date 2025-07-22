package de.brunokrams.lichessclient.model.chess;

import java.util.Arrays;
import java.util.List;

public interface Piece {

    String asSan();
    List<Field> getReachableFields(Field field);

    public static final Piece KNIGHT = new Piece() {
        @Override
        public String asSan() {
            return "N";
        }

        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented");
        }
    };

    public static final Piece BISHOP = new Piece() {
        @Override
        public String asSan() {
            return "B";
        }

        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented");
        }
    };

    public static final Piece ROOK = new Piece() {
        @Override
        public String asSan() {
            return "R";
        }

        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented");
        }
    };

    public static final Piece QUEEN = new Piece() {
        @Override
        public String asSan() {
            return "Q";
        }

        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented");
        }
    };

    public static final Piece KING = new Piece() {
        @Override
        public String asSan() {
            return "K";
        }

        @Override
        public List<Field> getReachableFields(Field field) {
            throw new RuntimeException("Not yet implemented");
        }
    };


}

