package de.brunokrams.lichessclient.model;

public class Recording {

    private final byte[] recordingBytes;

    public Recording(byte[] recordingBytes) {
        this.recordingBytes = recordingBytes;
    }

    public byte[] getData() {
        return recordingBytes;
    }
}
