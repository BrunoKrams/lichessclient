package de.brunokrams.lichessclient.model.lichess;

import de.brunokrams.lichessclient.config.LichessConfig;
import de.brunokrams.lichessclient.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class LichessClientTest {

    @Test
    void getGame() {
        // given
        LichessClient lichessClient = new LichessClient(new RestTemplate(),new GameDtoToGameMapper());
        String gameId = "B1qQgkzG";

        // when
        Game game = lichessClient.getGame(gameId);

        // then
        assertThat(game.getWhite().getName()).isEqualTo("Zeethedragon");
        assertThat(game.getBlack().getName()).isEqualTo("brunokrams");
    }

}
