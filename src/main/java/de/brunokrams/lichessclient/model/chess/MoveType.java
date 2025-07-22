package de.brunokrams.lichessclient.model.chess;

public enum MoveType {
    TAKE("x"), MOVE("");

    private String sanString;

    MoveType(String sanString) {
        this.sanString = sanString;
    }

    public String asSan() {
        return this.sanString;
    }
}
