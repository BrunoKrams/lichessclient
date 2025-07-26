package de.brunokrams.lichessclient.model.speechtosan;

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
class OpenAiSpeechToSanTest {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @MockitoBean
    ServletWebServerApplicationContext servletWebServerApplicationContext;

    @MockitoBean
    HostServices hostServices;

    @Autowired
    private OpenAiSpeechToSan openAiSpeechToSan;

    @Test
    void speechToSan_worksAsExpected() throws IOException, URISyntaxException {
        // given
        assertThat(apiKey).withFailMessage("This test requires an open ai api key to be set.").isNotEqualTo("dummy");
        URL resource = getClass().getClassLoader().getResource("Ka4.mp3");
        Path path = Paths.get(resource.toURI());
        Recording recording = new Recording(Files.readAllBytes(path));

        // when
        String result = openAiSpeechToSan.speechToSan(recording);

        // then
        assertThat(result).isEqualTo("Ka4");
    }
}
