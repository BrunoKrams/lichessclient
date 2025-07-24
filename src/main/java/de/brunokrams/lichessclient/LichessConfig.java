package de.brunokrams.lichessclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class LichessConfig {

    @Value("${lichess.api-key}")
    private String lichessApiKey;

    @Bean(name = "lichessHttpClient")
    public HttpClient lichessHttpClient() {
        return HttpClient.newBuilder().build();
    }

}
