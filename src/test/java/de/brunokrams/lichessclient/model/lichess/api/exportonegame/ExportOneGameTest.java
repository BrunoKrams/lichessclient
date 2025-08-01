package de.brunokrams.lichessclient.model.lichess.api.exportonegame;

import de.brunokrams.lichessclient.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class ExportOneGameTest {

    @Test
    void submit() {
        // given
        ExportOneGame exportOneGame = new ExportOneGame(new RestTemplate(),new ExportOneGameDtoToGameMapper());
        String gameId = "B1qQgkzG";

        // when
        Game game = exportOneGame.submit(gameId);

        // then
        assertThat(game.getWhite().getName()).isEqualTo("Zeethedragon");
        assertThat(game.getBlack().getName()).isEqualTo("brunokrams");
    }

}
