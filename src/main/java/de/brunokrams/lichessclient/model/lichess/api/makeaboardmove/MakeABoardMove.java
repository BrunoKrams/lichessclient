package de.brunokrams.lichessclient.model.lichess.api.makeaboardmove;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Session;
import de.brunokrams.lichessclient.model.lichess.api.exportonegame.ExportOneGameDto;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static de.brunokrams.lichessclient.config.LichessConfig.LICHESS_BASE_URL;

@Component
public class MakeABoardMove {

    private static final String MAKE_A_BOARD_MOVE_ENDPOINT_TEMPLATE = "/api/board/game/{gameId}/move/{move}";

    private final RestTemplate restTemplate;
    private final Session session;

    public MakeABoardMove(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, Session session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }

    public boolean submit(Game game, String move) {
        URI uri = UriComponentsBuilder
                .fromUriString(LICHESS_BASE_URL)
                .path(MAKE_A_BOARD_MOVE_ENDPOINT_TEMPLATE)
                .buildAndExpand(game.getId(), move)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        httpHeaders.setBearerAuth(session.getAccessToken());
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.POST, uri);
        ResponseEntity<String> responseEntity  = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}
