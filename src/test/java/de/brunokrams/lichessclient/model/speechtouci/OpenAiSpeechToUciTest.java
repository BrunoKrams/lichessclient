package de.brunokrams.lichessclient.model.speechtouci;

import com.github.bhlangonijr.chesslib.Board;
import de.brunokrams.lichessclient.AppConfig;
import de.brunokrams.lichessclient.model.recording.Recording;
import javafx.application.HostServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class OpenAiSpeechToUciTest {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @MockitoBean
    ServletWebServerApplicationContext servletWebServerApplicationContext;

    @MockitoBean
    HostServices hostServices;

    @Autowired
    private OpenAiSpeechToUci openAiSpeechToUci;

    @Test
    void speechToUci_worksAsExpected() throws IOException, URISyntaxException {
        // given
        assertThat(apiKey).withFailMessage("This test requires an open ai api key to be set.").isNotEqualTo("dummy");
        URL resource = getClass().getClassLoader().getResource("springer_b1_c3.m4a");
        Path path = Paths.get(resource.toURI());
        Recording recording = new Recording(Files.readAllBytes(path));

        Board board = new Board();
        board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        String result = openAiSpeechToUci.speechToUci(recording, board.legalMoves());

        // then
        assertThat(result).isEqualTo("b1c3");
    }
}
