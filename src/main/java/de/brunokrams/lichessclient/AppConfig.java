package de.brunokrams.lichessclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.SecureRandom;

@SpringBootApplication
public class AppConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

}
