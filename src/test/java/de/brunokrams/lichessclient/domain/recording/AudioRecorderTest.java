package de.brunokrams.lichessclient.domain.recording;

import de.brunokrams.lichessclient.AudioConfig;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AudioRecorderTest {

    private final AudioFormat audioFormat = new AudioConfig().audioFormat();
    private final DataLine.Info dataLineInfo = new AudioConfig().dataLineInfo();

    @Test
    void setRecordingReadyListener_throwsException_whenRecordingReadyListenerIsNull() {
        // given
        AudioRecorder audioRecorder = new AudioRecorder(audioFormat, dataLineInfo);

        // when / then
        assertThatThrownBy(() -> audioRecorder.setRecordingReadyListener(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void setRecordingStartedListener_throwsException_whenRecordingStartedListenerIsNull() {
        // given
        AudioRecorder audioRecorder = new AudioRecorder(audioFormat, dataLineInfo);

        // when / then
        assertThatThrownBy(() -> audioRecorder.setRecordingStartedListener(null)).isInstanceOf(IllegalArgumentException.class);

    }
}