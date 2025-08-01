package de.brunokrams.lichessclient.model.lichess.api.obtainaccesstoken;

import de.brunokrams.lichessclient.model.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ObtainAccessToken {

    @Value("${lichess.config.baseurl}")
    private String lichessBaseUrl;

    @Value("${lichess.config.clientid}")
    private String lichessClientId;

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

        System.out.println(lichessClientId);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", authorizationCode);
        map.add("grant_type", "authorization_code");
        map.add("client_id", lichessClientId);
        map.add("redirect_uri", redirectUri);
        map.add("code_verifier", session.getCodeVerifier());

        URI uri = UriComponentsBuilder
                .fromUriString(lichessBaseUrl)
                .path(OBTAIN_ACCESS_TOKEN_ENDPOINT)
                .build()
                .toUri();
        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(map, headers, HttpMethod.POST, uri);
        ResponseEntity<ObtainAccessTokenDto> response = restTemplate.exchange(requestEntity, ObtainAccessTokenDto.class);
        return response.getBody();
    }

}
