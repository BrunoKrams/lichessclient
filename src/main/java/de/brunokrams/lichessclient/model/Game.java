package de.brunokrams.lichessclient.model;

import java.util.Objects;

public class Game {

    private final String id;
    private final Player white;
    private final Player black;
    private final String variant;
    private final String speed;
    private final String fen;

    public Game(String id, Player white, Player black, String variant, String speed, String fen) {
        this.id = id;
        this.white = white;
        this.black = black;
        this.variant = variant;
        this.speed = speed;
        this.fen = fen;
    }

    public String getId() {
        return id;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public String getVariant() {
        return variant;
    }

    public String getSpeed() {
        return speed;
    }

    public String getFen() {
        return fen;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(white, game.white) && Objects.equals(black, game.black) && Objects.equals(variant, game.variant) && Objects.equals(speed, game.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, white, black, variant, speed);
    }

}
