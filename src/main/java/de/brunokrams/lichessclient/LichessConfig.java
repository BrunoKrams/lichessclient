package de.brunokrams.lichessclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

public class LichessConfig {

    @Value("${lichess.api-key}")
    private String lichessApiKey;

    @Bean
    public RestTemplate lichessRestTemplate() {
        return new RestTemplate();
    }

}
