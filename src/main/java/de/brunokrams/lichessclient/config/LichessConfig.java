package de.brunokrams.lichessclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LichessConfig {

    public static final String LICHESS_BASE_URL = "https://lichess.org";
    public static final String LICHESS_CLIENT_ID = "DE_BRUNOKRAMS_LICHESS_CLIENT";

    public static class OAuth {
        public static final String AUTHORIZATION_ENDPOINT = LICHESS_BASE_URL + "/oauth/authorize";

        public static final String TOKEN_ENDPOINT = LICHESS_BASE_URL + "/api/token";
        public static final String REDIRECT_ENDPOINT = "/lichess-authorization-code";
    }

    @Bean(name = "lichessRestTemplate")
    public RestTemplate lichessRestTemplate() {
        return new RestTemplate();
    }
}
