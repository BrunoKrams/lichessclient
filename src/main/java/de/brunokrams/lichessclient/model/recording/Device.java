package de.brunokrams.lichessclient.model.recording;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import java.util.Objects;

public class Device {

    private final Mixer mixer;
    private final String name;

    public Device(Mixer mixer) {
        if (mixer == null) {
            throw new IllegalArgumentException("mixer must not be null.");
        }
        this.mixer = mixer;
        this.name = mixer.getMixerInfo().getName();
    }

    public String getName() {
        return name;
    }

    public TargetDataLine getTargetDataLine(DataLine.Info info) throws LineUnavailableException {
        return (TargetDataLine) mixer.getLine(info);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(name, device.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
