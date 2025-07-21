package de.brunokrams.lichessclient.model;

import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Component
public class AudioRecorder {

    private static final Logger logger = Logger.getLogger(AudioRecorder.class.getName());
    private static final int BUFFER_SIZE = 4096;

    private final AudioFormat audioFormat;
    private final AtomicBoolean running = new AtomicBoolean(false);

    private RecordingReadyListener recordingReadyListener = recording -> {};
    private RecordingStartedListener recordingStartedListener = () -> {};

    private TargetDataLine targetDataLine;
    private ByteArrayOutputStream byteArrayOutputStream;
    private boolean recording;
    private long lastSoundTime;

    public AudioRecorder(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public interface RecordingReadyListener {
        void onRecordingReady(Recording recording);
    }

    public interface RecordingStartedListener {
        void onRecordingStarted();
    }

    public void start(double threshold, int silenceMillisBeforeStop, Device device) {
        if (running.get()) return;
        System.out.println("Device: " + device.getName());
        running.set(true);

        Thread monitorThread = new Thread(() -> {
            try {
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                targetDataLine = device.getTargetDataLine(info);
                targetDataLine.open(audioFormat);
                System.out.println(targetDataLine.getFormat());
                targetDataLine.start();

                byte[] buffer = new byte[BUFFER_SIZE];
                byteArrayOutputStream = new ByteArrayOutputStream();
                lastSoundTime = System.currentTimeMillis();

                while (running.get()) {
                    int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        double level = calculateSoundLevel(buffer, bytesRead);
                        System.out.println(level);
                        if (level > threshold) {
                            if (!recording) {
                                startRecording();
                            }
                            byteArrayOutputStream.write(buffer, 0, bytesRead);
                            lastSoundTime = System.currentTimeMillis();
                        } else if (recording) {
                            long silenceDuration = System.currentTimeMillis() - lastSoundTime;
                            if (silenceDuration > silenceMillisBeforeStop) {
                                stopRecording();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.warning("Failed to capture input on device " + device.getName() + ". Root cause: " + e.getMessage());
            } finally {
                if (targetDataLine != null) {
                    targetDataLine.stop();
                    targetDataLine.close();
                }
            }
        }, "AudioMonitorThread");

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

    private void startRecording() {
        byteArrayOutputStream.reset();
        recording = true;
        recordingStartedListener.onRecordingStarted();
    }

    private void stopRecording() {
        recording = false;
        byte[] audioData = byteArrayOutputStream.toByteArray();
        recordingReadyListener.onRecordingReady(new Recording(audioData));
    }

    private double calculateSoundLevel(byte[] buffer, int length) {
        long sum = 0;
        int sampleCount = 0;

        // Process bytes two at a time (16-bit samples), big endian
        for (int i = 0; i < length - 1; i += 2) {
            int high = buffer[i];       // signed byte, high byte first
            int low = buffer[i + 1] & 0xFF;  // unsigned byte, low byte second
            int sample = (high << 8) | low;

            sum += sample * sample;
            sampleCount++;
        }

        if (sampleCount == 0) return 0;

        double rms = Math.sqrt(sum / (double) sampleCount);
        return rms / 32768.0;  // Normalize RMS to [0,1]
    }

    public void setRecordingReadyListener(RecordingReadyListener listener) {
        if (listener == null) throw new IllegalArgumentException("RecordingReadyListener must not be null.");
        this.recordingReadyListener = listener;
    }

    public void setRecordingStartedListener(RecordingStartedListener listener) {
        if (listener == null) throw new IllegalArgumentException("RecordingStartedListener must not be null.");
        this.recordingStartedListener = listener;
    }
}
