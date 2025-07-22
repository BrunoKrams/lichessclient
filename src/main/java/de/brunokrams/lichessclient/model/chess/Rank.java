package de.brunokrams.lichessclient.model.chess;

import java.util.Arrays;
import java.util.List;

public enum Rank {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);

    private final int index;

    Rank(int index) {
        this.index = index;
    }

    public String asSan() {
        return "" + index;
    }

    public int getIndex() {
        return index;
    }

    public List<Field> getFields() {
        return Arrays.stream(Field.values()).filter(field -> field.getRank() == this).toList();
    }

    public static Rank fromIndex(int index) {
        return Arrays.stream(values())
                .filter(rank -> rank.index == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Index was " + index + ". Must be between 1 and 8"));
    }
}
