package de.brunokrams.lichessclient.domain.lichessapi;

import de.brunokrams.lichessclient.AppConfig;
import de.brunokrams.lichessclient.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class LichessClientTest {

    @Autowired
    LichessClient lichessClient;

    @Test
    void getGames() {
        // given
        String gameId = "B1qQgkzG";

        // when
        Game game = lichessClient.getGame(gameId);

        // then
        assertThat(game.getWhite().getName()).isEqualTo("Zeethedragon");
        assertThat(game.getBlack().getName()).isEqualTo("brunokrams");
    }

}
