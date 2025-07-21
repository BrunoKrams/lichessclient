package de.brunokrams.lichessclient.model;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AudioRecorderTest {

    @Test
    void setRecordingReadyListener_throwsException_whenRecordingReadyListenerIsNull() {
        // given
        AudioRecorder audioRecorder = new AudioRecorder(new AudioFormat(16000.0f, 16, 1, true, true));

        // when / then
        assertThatThrownBy(()-> audioRecorder.setRecordingReadyListener(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void setRecordingStartedListener_throwsException_whenRecordingStartedListenerIsNull() {
        // given
        AudioRecorder audioRecorder = new AudioRecorder(new AudioFormat(16000.0f, 16, 1, true, true));

        // when / then
        assertThatThrownBy(()-> audioRecorder.setRecordingStartedListener(null)).isInstanceOf(IllegalArgumentException.class);

    }
}