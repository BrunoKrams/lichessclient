package de.brunokrams.lichessclient;

public class LichessClientException extends RuntimeException {
    public LichessClientException(String message) {
        super(message);
    }

    public LichessClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
