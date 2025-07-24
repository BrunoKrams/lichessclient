package de.brunokrams.lichessclient.domain.lichessapi;

import de.brunokrams.lichessclient.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class LichessClient {

    private static final String LICHESS_BASE_URL = "https://lichess.org";
    private static final String EXPORT_ONE_GAME_ENDPOINT_TEMPLATE = "/game/export/%s";

    private final RestTemplate restTemplate;
    private final GameDtoToGameMapper gameDtoToGameMapper;

    @Autowired
    public LichessClient(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, GameDtoToGameMapper gameDtoToGameMapper) {
        this.restTemplate = restTemplate;
        this.gameDtoToGameMapper = gameDtoToGameMapper;
    }

    public Game getGame(String gameId) {
        try {
        URI uri = new URI(LICHESS_BASE_URL + String.format(EXPORT_ONE_GAME_ENDPOINT_TEMPLATE, gameId));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("accept", "application/json");
            RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET,uri);
            GameDto gameDto = restTemplate.exchange(requestEntity, GameDto.class).getBody();
            return gameDtoToGameMapper.gameDtoToGame(gameDto);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error when trying to get game: " + gameId, e);
        }
    }
}
