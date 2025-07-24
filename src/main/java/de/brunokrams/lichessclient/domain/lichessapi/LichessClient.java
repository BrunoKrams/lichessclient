package de.brunokrams.lichessclient.domain.lichessapi;

import de.brunokrams.lichessclient.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class LichessClient {

    private static final String LICHESS_BASE_URL = "https://lichess.org";
    private static final String ONGOING_GAMES_ENDPOINT_TEMPLATE = "/api/user/%s/current-game";
    private static final String MAKE_MOVE_ENDPOINT_TEMPLATE = "/api/board/game/%s/move/%s";

    private final HttpClient httpClient;

    @Autowired
    public LichessClient(@Qualifier("lichessHttpClient") HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public String getIdOfOngoingGame(User user) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI(LICHESS_BASE_URL + String.format(ONGOING_GAMES_ENDPOINT_TEMPLATE, user.getId()));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

//        private final ObjectMapper objectMapper;
//
//        private final HttpClient httpClient;
//
//        public LichessClient(ObjectMapper objectMapper, HttpClient httpClient) {
//            this.objectMapper = objectMapper;
//            this.httpClient = httpClient;
//        }
//
//        public String getOngoingGames(String user) throws URISyntaxException, IOException {
//            URI uri = new URIBuilder(LICHESS_URL + String.format(ONGOING_GAMES_ENDPOINT_TEMPLATE, user))
//                    .addParameter("moves", "false")
//                    .addParameter("clock", "false")
//                    .build();
//            HttpGet request = new HttpGet(uri);
//            request.setHeader("accept", "application/json");
//            String response = httpClient.execute(request, classicHttpResponse -> EntityUtils.toString(classicHttpResponse.getEntity()));
//
//            JsonNode responeJson = objectMapper.readTree(response);
//            return responeJson.at("/id").asText();
//        }
//
//        @Override
//        public void perform(Move move) {
//            try {
//                String gameId = getOngoingGames("brunokrams");
//                String moveString = move.toAlgebraicNotation();
//                HttpPost request = new HttpPost(LICHESS_URL + String.format(MAKE_MOVE_ENDPOINT_TEMPLATE, gameId, moveString));
//                request.setHeader("Authorization", "Bearer " + Credentials.LICHESS_API_KEY);
//                request.setHeader("accept", "application/json");
//
//                String response = httpClient.execute(request, classicHttpResponse -> EntityUtils.toString(classicHttpResponse.getEntity()));
//                System.out.println(response);
//            } catch (URISyntaxException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        public void perform(String move) {
//            try {
//                String gameId = getOngoingGames("brunokrams");
//                HttpPost request = new HttpPost(LICHESS_URL + String.format(MAKE_MOVE_ENDPOINT_TEMPLATE, gameId, move));
//                request.setHeader("Authorization", "Bearer " + Credentials.LICHESS_API_KEY);
//                request.setHeader("accept", "application/json");
//
//                String response = httpClient.execute(request, classicHttpResponse -> EntityUtils.toString(classicHttpResponse.getEntity()));
//                System.out.println(response);
//            } catch (URISyntaxException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
