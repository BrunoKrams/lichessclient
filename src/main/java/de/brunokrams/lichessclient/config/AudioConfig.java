package de.brunokrams.lichessclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

@Configuration
public class AudioConfig {

    @Value("${audio.config.format.samplerate}")
    private float sampleRate;

    @Value("${audio.config.format.samplesize}")
    private int sampleSize;

    @Value("${audio.config.format.channels}")
    private int channels;

    @Value("${audio.config.format.signed}")
    private boolean signed;

    @Value("${audio.config.format.bigendian}")
    private boolean bigEndian;


    @Bean
    public AudioFormat audioFormat() {
        return new AudioFormat(sampleRate, sampleSize, channels,signed, bigEndian);
    }

    @Bean
    public DataLine.Info dataLineInfo() {
        return new DataLine.Info(TargetDataLine.class, audioFormat());
    }

}
