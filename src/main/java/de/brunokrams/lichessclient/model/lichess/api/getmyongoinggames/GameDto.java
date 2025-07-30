package de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames;

class GameDto {
    private String gameId;
    private String color;
    private OpponentDto opponent;
    private String speed;
    private VariantDto variant;

    // Getters and Setters
    // (You can use Lombok @Data if desired)

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public OpponentDto getOpponent() {
        return opponent;
    }

    public void setOpponent(OpponentDto opponent) {
        this.opponent = opponent;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public VariantDto getVariant() {
        return variant;
    }

    public void setVariant(VariantDto variant) {
        this.variant = variant;
    }
}
