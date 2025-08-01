package de.brunokrams.lichessclient.model.recording;

import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Component
public class AudioRecorder {

    private static final Logger logger = Logger.getLogger(AudioRecorder.class.getName());
    private static final int BUFFER_SIZE = 4096;

    private final AudioFormat audioFormat;
    private final DataLine.Info dataLineInfo;

    private final AtomicBoolean running = new AtomicBoolean(false);

    private RecordingReadyListener recordingReadyListener = recording -> {
    };
    private RecordingStartedListener recordingStartedListener = () -> {
    };

    private TargetDataLine targetDataLine;
    private ByteArrayOutputStream byteArrayOutputStream;
    private boolean recording;
    private long lastSoundTime;

    public AudioRecorder(AudioFormat audioFormat, DataLine.Info dataLineInfo) {
        this.audioFormat = audioFormat;
        this.dataLineInfo = dataLineInfo;
    }

    public interface RecordingReadyListener {
        void onRecordingReady(Recording recording);
    }

    public interface RecordingStartedListener {
        void onRecordingStarted();
    }


    public void start(double threshold, int silenceMillisBeforeStop, Device device) {
        if (running.get()) return;
        running.set(true);
        Thread monitorThread = new Thread(microphonListeningRunnable(device, threshold, silenceMillisBeforeStop), "AudioRecorderThread");
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    public void stop() {
        running.set(false);
        if (targetDataLine != null && targetDataLine.isOpen()) {
            targetDataLine.stop();
            targetDataLine.close();
        }
    }

    public void setRecordingReadyListener(RecordingReadyListener listener) {
        if (listener == null) throw new IllegalArgumentException("RecordingReadyListener must not be null.");
        this.recordingReadyListener = listener;
    }

    public void setRecordingStartedListener(RecordingStartedListener listener) {
        if (listener == null) throw new IllegalArgumentException("RecordingStartedListener must not be null.");
        this.recordingStartedListener = listener;
    }

    private Runnable microphonListeningRunnable(Device device, double threshold, int silenceMillisBeforeStop) {
        return () -> {
            try (
                    TargetDataLine line = device.getTargetDataLine(dataLineInfo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()
            ) {
                targetDataLine = line;
                byteArrayOutputStream = baos;
                line.open(audioFormat);
                line.start();
                byte[] buffer = new byte[BUFFER_SIZE];
                lastSoundTime = System.currentTimeMillis();
                record(threshold, silenceMillisBeforeStop, line, buffer, baos);
            } catch (Exception e) {
                logger.warning("Failed to capture input on device " + device.getName() + ". Root cause: " + e.getMessage());
            }
        };
    }

    private void record(double threshold, int silenceMillisBeforeStop, TargetDataLine line, byte[] buffer, ByteArrayOutputStream baos) {
        while (running.get()) {
            int bytesRead = line.read(buffer, 0, buffer.length);
            if (bytesRead > 0) {
                double level = calculateSoundLevel(buffer, bytesRead);
                if (level > threshold) {
                    if (!recording) {
                        startRecording();
                    }
                    baos.write(buffer, 0, bytesRead);
                    lastSoundTime = System.currentTimeMillis();
                } else if (recording) {
                    long silenceDuration = System.currentTimeMillis() - lastSoundTime;
                    if (silenceDuration > silenceMillisBeforeStop) {
                        stopRecording();
                    }
                }
            }
        }
    }

    private void startRecording() {
        byteArrayOutputStream.reset();
        recording = true;
        recordingStartedListener.onRecordingStarted();
    }

    private void stopRecording() {
        recording = false;
        try {
            byte[] rawAudioData = byteArrayOutputStream.toByteArray();
            AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(rawAudioData), audioFormat, rawAudioData.length / audioFormat.getFrameSize());
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, result);
            recordingReadyListener.onRecordingReady(new Recording(result.toByteArray()));
        } catch (IOException e) {
            logger.warning("Unable to transform sound. Root cause " + e.getMessage());
        }
    }

    private double calculateSoundLevel(byte[] buffer, int length) {
        long sum = 0;
        int sampleCount = 0;

        for (int i = 0; i < length - 1; i += 2) {
            int high = buffer[i];
            int low = buffer[i + 1] & 0xFF;
            long sample = (high << 8) | low;

            sum += sample * sample;
            sampleCount++;
        }

        if (sampleCount == 0) return 0;

        double rms = Math.sqrt(sum / (double) sampleCount);
        return rms / 32768.0;
    }

}
