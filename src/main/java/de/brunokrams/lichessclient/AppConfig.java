package de.brunokrams.lichessclient;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFormat;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public AudioFormat audioFormat() {
        return new AudioFormat(
                16000.0f,   // sample rate
                16,         // sample size in bits
                1,          // channels (mono)
                true,          // frame size = 2 bytes per sample
                false       // little endian
        );
    }

}
