package de.brunokrams.lichessclient.domain.recording;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DevicesManager {

    private Device active;

    public List<Device> getDevices() {
        List<Device> result = new ArrayList<>();
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] targetLineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : targetLineInfos) {
                if (lineInfo instanceof DataLine.Info dataLineInfo && TargetDataLine.class.isAssignableFrom(dataLineInfo.getLineClass())) {
                    result.add(new Device(mixer));
                    break;
                }
            }
        }
        return result;
    }

    public void setActive(Device device) {
        this.active = device;
    }

    public Optional<Device> getDefault() {
        return getDevices().stream().filter(device -> device.getName().contains("default")).findFirst();
    }

    public Device getActive() {
        return active;
    }
}

