package de.brunokrams.lichessclient.model.lichess.api.exportonegame;

public class ExportOneGameDto {

    private String id;
    private String variant;
    private String speed;
    private PlayersDto players;
    private String fen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public PlayersDto getPlayers() {
        return players;
    }

    public void setPlayers(PlayersDto players) {
        this.players = players;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }
}
