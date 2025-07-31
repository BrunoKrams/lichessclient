package de.brunokrams.lichessclient.model.lichess.api.exportonegame;

import de.brunokrams.lichessclient.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static de.brunokrams.lichessclient.config.LichessConfig.LICHESS_BASE_URL;

@Component
public class ExportOneGame {

    private static final String EXPORT_ONE_GAME_ENDPOINT_TEMPLATE = "/game/export/{gameId}";


    private final RestTemplate restTemplate;
    private final ExportOneGameDtoToGameMapper exportOneGameDtoToGameMapper;

    @Autowired
    public ExportOneGame(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, ExportOneGameDtoToGameMapper exportOneGameDtoToGameMapper) {
        this.restTemplate = restTemplate;
        this.exportOneGameDtoToGameMapper = exportOneGameDtoToGameMapper;
    }

    public Game submit(String gameId) {
        URI uri = UriComponentsBuilder
                .fromUriString(LICHESS_BASE_URL)
                .path(EXPORT_ONE_GAME_ENDPOINT_TEMPLATE)
                .buildAndExpand(gameId)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);
        ExportOneGameDto exportOneGameDto = restTemplate.exchange(requestEntity, ExportOneGameDto.class).getBody();
        return exportOneGameDtoToGameMapper.gameDtoToGame(exportOneGameDto);
    }
}
