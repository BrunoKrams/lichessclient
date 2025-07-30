package de.brunokrams.lichessclient.model.lichess.api.getmyprofile;

import de.brunokrams.lichessclient.model.Session;
import de.brunokrams.lichessclient.model.lichess.api.obtainaccesstoken.ObtainAccessTokenDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static de.brunokrams.lichessclient.config.LichessConfig.LICHESS_BASE_URL;
import static de.brunokrams.lichessclient.config.LichessConfig.LICHESS_CLIENT_ID;

@Component
public class ObtainAccessToken {

    private static final String OBTAIN_ACCESS_TOKEN_ENDPOINT = "/api/token";

    private final RestTemplate restTemplate;
    private final Session session;

    public ObtainAccessToken(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, Session session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }

    public ObtainAccessTokenDto submit(String authorizationCode, String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", authorizationCode);
        map.add("grant_type", "authorization_code");
        map.add("client_id", LICHESS_CLIENT_ID);
        map.add("redirect_uri", redirectUri);
        map.add("code_verifier", session.getCodeVerifier());

        URI uri = UriComponentsBuilder
                .fromUriString(LICHESS_BASE_URL)
                .path(OBTAIN_ACCESS_TOKEN_ENDPOINT)
                .build()
                .toUri();
        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(map, headers, HttpMethod.POST, uri);
        ResponseEntity<ObtainAccessTokenDto> response = restTemplate.exchange(requestEntity, ObtainAccessTokenDto.class);
        return response.getBody();
    }

}
