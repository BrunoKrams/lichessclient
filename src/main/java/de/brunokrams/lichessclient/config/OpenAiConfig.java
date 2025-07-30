package de.brunokrams.lichessclient.config;


import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat.TEXT;

@Configuration
public class OpenAiConfig {

    @Bean
    public OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions() {
        return OpenAiAudioTranscriptionOptions.builder()
                .language("de")
                .prompt("This is a chess move in german language.")
                .temperature(0.5f)
                .responseFormat(TEXT)
                .model("whisper-1")
                .build();
    }

    @Bean
    public SystemMessage systemMessage() {
        String text = """
                "You are a machine that translates text into SAN.
                 Here SAN stands for "Standard algebraic notation" from chess.
                 Your task is to translate text into a valid SAN-String. The text is in german language.
                 Examples:
                 "König auf a4" should translate to "Ka4".
                 "Kurze Rochade" should translate to "0-0".
                 "Springer schlägt e7" should translate to "Nxe7".
                 "Der e-Läufer geht nach c6" should translate to "Bec6".
                 "Bauer auf a8 mit Umwandlung zur Dame" should translate to "a8+Q".
                 Please use only the characters "K" for König, "Q" for Dame, "R" for Turm, "B" for Läufer and "N" for Springer.
                 If you are unable to translate the text into a valid chess move please only return the string "ERROR".
                """;
        return SystemMessage.builder().text(text).build();
    }

    @Bean
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_1).temperature(0.2).build();
    }
}