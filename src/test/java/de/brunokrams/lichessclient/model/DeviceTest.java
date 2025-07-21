package de.brunokrams.lichessclient.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeviceTest {

    @Test
    void constructor_throwsException_whenMixerIsNull() {
        // when/then
        assertThatThrownBy(() -> new Device(null)).isInstanceOf(IllegalArgumentException.class);
    }

}
