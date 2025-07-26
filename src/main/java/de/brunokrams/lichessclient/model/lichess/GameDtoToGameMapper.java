package de.brunokrams.lichessclient.model.lichess;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import org.springframework.stereotype.Component;

@Component
public class GameDtoToGameMapper {

    public Game gameDtoToGame(GameDto gameDto) {
        Player white = new Player(gameDto.getPlayers().getWhite().getUser().getName());
        Player black = new Player(gameDto.getPlayers().getBlack().getUser().getName());
        return new Game(gameDto.getId(), white, black, gameDto.getVariant(), gameDto.getSpeed());
    }

}
