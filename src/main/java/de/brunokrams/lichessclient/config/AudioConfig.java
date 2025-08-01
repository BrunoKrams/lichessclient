package de.brunokrams.lichessclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

@Configuration
public class AudioConfig {

    @Bean
    public AudioFormat audioFormat() {
        return new AudioFormat(16000.0f, 16, 1, true, false);
    }

    @Bean
    public DataLine.Info dataLineInfo() {
        return new DataLine.Info(TargetDataLine.class, audioFormat());
    }

}
