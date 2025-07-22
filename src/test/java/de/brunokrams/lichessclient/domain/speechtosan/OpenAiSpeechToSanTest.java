package de.brunokrams.lichessclient.domain.speechtosan;

import de.brunokrams.lichessclient.AppConfig;
import de.brunokrams.lichessclient.domain.recording.Recording;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class OpenAiSpeechToSanTest {

    @Autowired
    private OpenAiSpeechToSan openAiSpeechToSan;

    @Test
    void speechToSan_worksAsExpected() throws IOException {
        // given
        ClassLoader classLoader = SpeechToSan.class.getClassLoader();
        Path path = Paths.get(classLoader.getResource("Ka4.mp3").getPath());
        Recording recording = new Recording(Files.readAllBytes(path));

        // when
        String result = openAiSpeechToSan.speechToSan(recording);

        // then
        assertThat(result).isEqualTo("Ka4");
    }
}
