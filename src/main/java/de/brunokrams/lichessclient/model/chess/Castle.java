package de.brunokrams.lichessclient.model.chess;

public class Castle {

    private Castle() {

    }

    public static final Move SHORT = new Move() {
        @Override
        public String asSan() {
            return "0-0";
        }
    };

    public static final Move LONG = new Move() {
        @Override
        public String asSan() {
            return "0-0-0";
        }
    };
}
