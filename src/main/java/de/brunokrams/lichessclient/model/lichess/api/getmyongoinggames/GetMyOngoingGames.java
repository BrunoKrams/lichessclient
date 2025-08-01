package de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import de.brunokrams.lichessclient.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class GetMyOngoingGames {

    @Value("${lichess.config.baseurl}")
    private String lichessBaseUrl;

    private static final String GET_MY_ONGOING_GAMES_ENDPOINT = "/api/account/playing";

    private final RestTemplate restTemplate;
    private final Session session;
    private final GetMyOngoingGamesDtoToGamesMapper getMyOngoingGamesDtoToGamesMapper;

    @Autowired
    public GetMyOngoingGames(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, Session session, GetMyOngoingGamesDtoToGamesMapper getMyOngoingGamesDtoToGamesMapper) {
        this.restTemplate = restTemplate;
        this.session = session;
        this.getMyOngoingGamesDtoToGamesMapper = getMyOngoingGamesDtoToGamesMapper;
    }

    public List<Game> submit(Player player) {
        URI uri = UriComponentsBuilder
                .fromUriString(lichessBaseUrl)
                .path(GET_MY_ONGOING_GAMES_ENDPOINT)
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        httpHeaders.setBearerAuth(session.getAccessToken());
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);
        GetMyOngoingGamesDto getMyOngoingGamesDto = restTemplate.exchange(requestEntity, GetMyOngoingGamesDto.class).getBody();
        return getMyOngoingGamesDtoToGamesMapper.map(getMyOngoingGamesDto, player);
    }
}
