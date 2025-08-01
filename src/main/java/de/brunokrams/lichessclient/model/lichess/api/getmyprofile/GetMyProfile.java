package de.brunokrams.lichessclient.model.lichess.api.getmyprofile;

import de.brunokrams.lichessclient.model.Player;
import de.brunokrams.lichessclient.model.Session;
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
public class GetMyProfile {

    private static final String GET_MY_PROFILE_ENDPOINT = "/api/account";

    private final RestTemplate restTemplate;
    private final Session session;
    private final GetMyProfileDtoToPlayerMapper getMyProfileDtoToPlayerMapper;

    @Autowired
    public GetMyProfile(@Qualifier("lichessRestTemplate") RestTemplate restTemplate, Session session, GetMyProfileDtoToPlayerMapper getMyProfileDtoToPlayerMapper) {
        this.restTemplate = restTemplate;
        this.session = session;
        this.getMyProfileDtoToPlayerMapper = getMyProfileDtoToPlayerMapper;
    }

    public Player submit() {
        URI uri = UriComponentsBuilder
                .fromUriString(LICHESS_BASE_URL)
                .path(GET_MY_PROFILE_ENDPOINT)
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        httpHeaders.setBearerAuth(session.getAccessToken());
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);
        GetMyProfileDto getMyProfileDto = restTemplate.exchange(requestEntity, GetMyProfileDto.class).getBody();
        return getMyProfileDtoToPlayerMapper.map(getMyProfileDto);
    }

}
