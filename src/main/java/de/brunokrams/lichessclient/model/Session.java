package de.brunokrams.lichessclient.model;

import de.brunokrams.lichessclient.model.recording.Device;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Session {
    private String codeChallenge;
    private String codeVerifier;

    private String accessToken;

    private Device activeDevice;
    private LocalDateTime accessTokenExpiryDate;

    private Player activePlayer;
    private Game game;

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public void setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public void setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Device getActiveDevice() {
        return activeDevice;
    }

    public void setActiveDevice(Device activeDevice) {
        this.activeDevice = activeDevice;
    }

    public void setAccessTokenExpiryDate(LocalDateTime accessTokenExpiryDate) {
        this.accessTokenExpiryDate = accessTokenExpiryDate;
    }

    public LocalDateTime getAccessTokenExpiryDate() {
        return accessTokenExpiryDate;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getActivePlayer()  {
        return activePlayer;
    }

    public void setActiveGame(Game game) {
        this.game = game;
    }

    public Game getActiveGame() {
        return game;
    }
}
