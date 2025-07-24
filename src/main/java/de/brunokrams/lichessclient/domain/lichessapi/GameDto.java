package de.brunokrams.lichessclient.domain.lichessapi;

public class GameDto {

    private String id;
    private String variant;
    private String speed;
    private PlayersDto players;

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
}
