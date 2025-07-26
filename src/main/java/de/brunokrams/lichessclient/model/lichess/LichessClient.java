package de.brunokrams.lichessclient.model.lichess;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class LichessClient {

    private static final String LICHESS_BASE_URL = "https://lichess.org";
    private static final String EXPORT_ONE_GAME_ENDPOINT_TEMPLATE = "/game/export/{gameId}";

    private final RestTemplate restTemplate;
    private final GameDtoToGameMapper gameDtoToGameMapper;

    @Autowired
    public LichessClient(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, GameDtoToGameMapper gameDtoToGameMapper) {
        this.restTemplate = restTemplate;
        this.gameDtoToGameMapper = gameDtoToGameMapper;
    }

    public List<Game> getOngoingGames(Player user) {
        return null;
    }

    public Game getGame(String gameId) {
        URI uri = UriComponentsBuilder
                .fromUriString(LICHESS_BASE_URL)
                .path(EXPORT_ONE_GAME_ENDPOINT_TEMPLATE)
                .buildAndExpand(gameId).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);
        GameDto gameDto = restTemplate.exchange(requestEntity, GameDto.class).getBody();
        return gameDtoToGameMapper.gameDtoToGame(gameDto);

    }
}
