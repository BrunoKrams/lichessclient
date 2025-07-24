package de.brunokrams.lichessclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class LichessConfig {

    @Bean(name = "lichessRestTemplate")
    public RestTemplate lichessRestTemplate() {
        return new RestTemplate();
    }

}
