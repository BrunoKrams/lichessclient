package de.brunokrams.lichessclient.controller;

import jakarta.servlet.http.HttpServletRequest;
import javafx.fxml.FXML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class LoginController {

    private static final String CLIENT_ID = "BRUNOKRAMS_LICHESS_CLIENT";
    private static final String AUTHORIZATION_ENDPOINT = "https://lichess.org/oauth2/authorize";
    private static final String TOKEN_ENDPOINT = "https://lichess.org/oauth2/token";
    private static final String REDIRECT_URI = "http://localhost:8080/token_callback";
    private static final String SCOPE = "openid profile";

    @GetMapping("/token_callback")
    public void tokenCallback(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
    }

    @FXML
    public void login() throws Exception {
        runOAuthFlow();
    }

    private String codeVerifier;

    private void runOAuthFlow() throws Exception {
        // Step 1: Generate PKCE parameters
        codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);

        // Step 2: Build Auth URL
        String authUrl = String.format(
                "%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s&code_challenge=%s&code_challenge_method=S256",
                AUTHORIZATION_ENDPOINT,
                URLEncoder.encode(CLIENT_ID, "UTF-8"),
                URLEncoder.encode(REDIRECT_URI, "UTF-8"),
                URLEncoder.encode(SCOPE, "UTF-8"),
                codeChallenge
        );



    }

    private void exchangeCodeForToken(String code) throws IOException, InterruptedException {
        Charset defaultCharset = Charset.defaultCharset();
        String body = String.format(
                "grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&code_verifier=%s",
                URLEncoder.encode(code, defaultCharset),
                URLEncoder.encode(REDIRECT_URI, defaultCharset),
                URLEncoder.encode(CLIENT_ID, defaultCharset),
                URLEncoder.encode(codeVerifier, defaultCharset)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_ENDPOINT))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Token response: " + response.body());
    }

    private String generateCodeVerifier() {
        byte[] code = new byte[32];
        new SecureRandom().nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }

    private String generateCodeChallenge(String codeVerifier) throws Exception {
        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    private String extractQueryParam(String query, String key) {
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2 && parts[0].equals(key)) {
                return parts[1];
            }
        }
        return null;
    }
}



