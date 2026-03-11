package de.brunokrams.lichessclient.model.lichess.api.exportonegame;

import de.brunokrams.lichessclient.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class ExportOneGameTest {

    @Test
    void submit() {
        // given
        ExportOneGame exportOneGame = new ExportOneGame(new RestTemplate(),new ExportOneGameDtoToGameMapper());
        ReflectionTestUtils.setField(exportOneGame, "lichessBaseUrl", "https://lichess.org");
        String gameId = "B1qQgkzG";

        // when
        Game game = exportOneGame.submit(gameId);

        // then
        assertThat(game.getWhite().getName()).isEqualTo("Zeethedragon");
        assertThat(game.getBlack().getName()).isEqualTo("brunokrams");
    }

}
