package de.brunokrams.lichessclient.domain.lichessapi;

public class PlayersDto {
    private PlayerDto white;
    private PlayerDto black;

    public PlayerDto getWhite() {
        return white;
    }

    public void setWhite(PlayerDto white) {
        this.white = white;
    }

    public PlayerDto getBlack() {
        return black;
    }

    public void setBlack(PlayerDto black) {
        this.black = black;
    }

}
