package de.brunokrams.lichessclient.model.lichess;

import de.brunokrams.lichessclient.model.Session;
import de.brunokrams.lichessclient.model.lichess.api.getmyprofile.GetMyProfile;
import de.brunokrams.lichessclient.model.lichess.api.obtainaccesstoken.ObtainAccessToken;
import de.brunokrams.lichessclient.model.lichess.api.obtainaccesstoken.ObtainAccessTokenDto;
import javafx.application.HostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import static de.brunokrams.lichessclient.config.LichessConfig.OAuth.*;

@Service
public class LichessOAuthService {

    @Value("${lichess.config.baseurl}")
    private String lichessBaseUrl;

    @Value("${lichess.config.clientid}")
    private String lichessClientId;

    private static final String AUTHORIZATION_ENDPOINT = "/oauth/authorize";

    private final ObtainAccessToken obtainAccessToken;
    private final GetMyProfile getMyProfile;
    private final HostServices hostServices;
    private final Session session;
    private final ServletWebServerApplicationContext servletWebServerApplicationContext;

    @Autowired
    public LichessOAuthService(ObtainAccessToken obtainAccessToken, GetMyProfile getMyProfile, HostServices hostServices, Session session, ServletWebServerApplicationContext servletWebServerApplicationContext) {
        this.obtainAccessToken = obtainAccessToken;
        this.getMyProfile = getMyProfile;
        this.hostServices = hostServices;
        this.session = session;
        this.servletWebServerApplicationContext = servletWebServerApplicationContext;
    }

    public void startPKCEFlow() throws NoSuchAlgorithmException {
        session.setCodeVerifier(generateCodeVerifier());
        session.setCodeChallenge(generateCodeChallenge(session.getCodeVerifier()));
        openLoginInBrowser(session.getCodeChallenge());
    }

    public void finishPKCEFlow(String authorizationCode) {
        ObtainAccessTokenDto obtainAccessTokenDto = obtainAccessToken.submit(authorizationCode, redirectUri());
        session.setAccessToken(obtainAccessTokenDto.getAccessToken());
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(obtainAccessTokenDto.getExpiresIn());
        session.setAccessTokenExpiryDate(expiryDate);
        session.setActivePlayer(getMyProfile.submit());
    }

    public boolean isAuthenticated() {
        return session.getAccessToken() != null
                && session.getAccessTokenExpiryDate() != null
                && session.getAccessTokenExpiryDate().isAfter(LocalDateTime.now());
    }

    private String generateCodeVerifier() {
        byte[] code = new byte[32];
        new SecureRandom().nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }

    private String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    private void openLoginInBrowser(String codeChallenge) {
        URI uri = UriComponentsBuilder
                .fromPath(lichessBaseUrl + AUTHORIZATION_ENDPOINT)
                .queryParam("response_type", "code")
                .queryParam("client_id", lichessClientId)
                .queryParam("scope", "board:play")
                .queryParam("redirect_uri", redirectUri())
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .build()
                .toUri();
        hostServices.showDocument(uri.toString());
    }

    private String redirectUri() {
        return "http://localhost:" + servletWebServerApplicationContext.getWebServer().getPort() + REDIRECT_ENDPOINT;
    }
}

