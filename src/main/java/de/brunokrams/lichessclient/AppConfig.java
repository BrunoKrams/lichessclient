package de.brunokrams.lichessclient;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

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
    @Bean
    public DataLine.Info dataLineInfo() {
        return new DataLine.Info(TargetDataLine.class, audioFormat());
    }


}
