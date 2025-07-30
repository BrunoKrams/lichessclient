package de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetMyOngoingGamesDtoToGamesMapper {

    List<Game> map(GetMyOngoingGamesDto getMyOngoingGamesDto, Player player) {
        List<Game> result = new ArrayList<>();
        for (GameDto gameDto : getMyOngoingGamesDto.getNowPlaying()) {
            result.add(map(gameDto, player));
        }
        return result;
    }

    private Game map(GameDto gameDto, Player player) {
        Player opponent = new Player(gameDto.getOpponent().getUsername());
        Player white;
        Player black;
        if ("white".equals(gameDto.getColor())) {
            white = player;
            black = opponent;
        } else {
            white = opponent;
            black = player;
        }
        return new Game(gameDto.getGameId(), white, black, gameDto.getVariant().getName(), gameDto.getSpeed());
    }

}
