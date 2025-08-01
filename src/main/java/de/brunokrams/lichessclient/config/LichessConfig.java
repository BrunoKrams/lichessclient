package de.brunokrams.lichessclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LichessConfig {

    public static class OAuth {

        private OAuth() {
        }
        public static final String REDIRECT_ENDPOINT = "/lichess-authorization-code";
    }

    @Bean(name = "lichessRestTemplate")
    public RestTemplate lichessRestTemplate() {
        return new RestTemplate();
    }
}
