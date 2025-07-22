package de.brunokrams.lichessclient.model.chess;

import java.util.Arrays;
import java.util.List;

public enum File {
    A('a'), B('b'), C('c'), D('d'), E('e'), F('f'), G('g'), H('h');

    private final char san;

    File(char san) {
        this.san = san;
    }

    public String asSan() {
        return "" + san;
    }

    public char getSanChar() {
        return this.san;
    }

    public List<Field> getFields() {
        return Arrays.stream(Field.values()).filter(field -> field.getFile() == this).toList();
    }

    public static File fromChar(char c) {
        return Arrays.stream(values())
                .filter(file -> file.san == c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Char was " + c + ". Must be between a and h"));
    }
}

