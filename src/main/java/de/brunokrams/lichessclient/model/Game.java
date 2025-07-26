package de.brunokrams.lichessclient.model;

public class Game {

    private final String id;
    private final Player white;
    private final Player black;
    private final String variant;
    private final String speed;

    public Game(String id, Player white, Player black, String variant, String speed) {
        this.id = id;
        this.white = white;
        this.black = black;
        this.variant = variant;
        this.speed = speed;
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
}
