package de.brunokrams.lichessclient.model.lichessapi;

import org.springframework.stereotype.Component;

@Component
public class LichessClient {

//        private static final String LICHESS_URL = "https://lichess.org";
//        private static final String ONGOING_GAMES_ENDPOINT_TEMPLATE = "/api/user/%s/current-game";
//        private static final String MAKE_MOVE_ENDPOINT_TEMPLATE = "/api/board/game/%s/move/%s";
//
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
