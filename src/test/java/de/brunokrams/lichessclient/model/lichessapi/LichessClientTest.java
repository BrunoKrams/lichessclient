package de.brunokrams.lichessclient.model.lichessapi;

import de.brunokrams.lichessclient.LichessConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LichessConfig.class)
class LichessClientTest {

    @Autowired
    LichessClient lichessClient;

    @Test
    void dummy() {
//        lichessClient.getOngoingGames();
    }

}
