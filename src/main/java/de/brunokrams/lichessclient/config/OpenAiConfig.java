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
                "You are a machine that translates text into UCI where UCI stands is the "Universal chess interface".
                 Your task is to translate text into a valid UCI-String. The text is in german language.
                 After the text which describes the move you will find a list of possible moves - prefixed with the string "Possible moves:" and seperated by a single space.
                 Please pick one of those in your response.
                 Note that in the list of possible moves the piece is always omitted. For example "Springer b1 auf c3" should result in b1c3.
                If you are unable to translate the text into a valid chess move please only return the string "ERROR:" followed by a description of the error.
                """;
        return SystemMessage.builder().text(text).build();
    }

    @Bean
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_1).temperature(0.2).build();
    }
}