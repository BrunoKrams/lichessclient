package de.brunokrams.lichessclient.model.speechtouci;

import com.github.bhlangonijr.chesslib.move.Move;
import de.brunokrams.lichessclient.model.recording.Recording;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpenAiSpeechToUci implements SpeechToUci {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions;

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiChatOptions openAiChatOptions;
    private final SystemMessage systemMessage;

    @Autowired
    public OpenAiSpeechToUci(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel, OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions, OpenAiChatModel openAiChatModel, OpenAiChatOptions openAiChatOptions, SystemMessage systemMessage) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.openAiAudioTranscriptionOptions = openAiAudioTranscriptionOptions;
        this.openAiChatModel = openAiChatModel;
        this.openAiChatOptions = openAiChatOptions;
        this.systemMessage = systemMessage;
    }

    @Override
    public String speechToUci(Recording recording, List<Move> legalMoves) {
        AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(new ByteArrayResource(recording.getData()), openAiAudioTranscriptionOptions);
        String moveAsText = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt).getResult().getOutput();
        System.out.println(moveAsText);
        UserMessage userMessage = UserMessage.builder().text(moveAsText + "Possible moves:" + legalMoves.stream().map(Move::toString).collect(Collectors.joining(" "))).media().build();
        Prompt prompt = Prompt.builder().messages(systemMessage, userMessage).chatOptions(openAiChatOptions).build();
        System.out.println(userMessage);
        return openAiChatModel.call(prompt).getResult().getOutput().getText();
    }
}
