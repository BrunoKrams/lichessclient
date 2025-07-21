package de.brunokrams.lichessclient.model.speechtosan;

import de.brunokrams.lichessclient.model.recording.Recording;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class OpenAiSpeechToSan implements SpeechToSan {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions;

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiChatOptions openAiChatOptions;
    private final SystemMessage systemMessage;

    @Autowired
    public OpenAiSpeechToSan(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel, OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions, OpenAiChatModel openAiChatModel, OpenAiChatOptions openAiChatOptions, SystemMessage systemMessage) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.openAiAudioTranscriptionOptions = openAiAudioTranscriptionOptions;
        this.openAiChatModel = openAiChatModel;
        this.openAiChatOptions = openAiChatOptions;
        this.systemMessage = systemMessage;
    }

    @Override
    public String speechToSan(Recording recording) {
        AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(new ByteArrayResource(recording.getData()), openAiAudioTranscriptionOptions);
        String moveAsText = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt).getResult().getOutput();
        UserMessage userMessage = UserMessage.builder().text(moveAsText).build();
        Prompt prompt = Prompt.builder().messages(systemMessage, userMessage).chatOptions(openAiChatOptions).build();
        return openAiChatModel.call(prompt).getResult().getOutput().getText();
    }
}
