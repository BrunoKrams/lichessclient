package de.brunokrams.lichessclient.model.lichess.api.getmyprofile;

import de.brunokrams.lichessclient.model.Player;
import org.springframework.stereotype.Component;

@Component
public class GetMyProfileDtoToPlayerMapper {

    public Player map(GetMyProfileDto getMyProfileDto) {
        if (getMyProfileDto == null) {
            return null;
        }
        return new Player(getMyProfileDto.getUsername());
    }

}
