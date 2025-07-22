package de.brunokrams.lichessclient;

import org.springframework.beans.factory.annotation.Value;

public class LichessConfig {

    @Value("${lichess.api-key}")
    private String lichessApiKey;



}
