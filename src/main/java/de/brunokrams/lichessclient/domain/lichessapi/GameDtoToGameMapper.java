package de.brunokrams.lichessclient.domain.lichessapi;

import de.brunokrams.lichessclient.domain.Game;
import de.brunokrams.lichessclient.domain.Player;
import org.springframework.stereotype.Component;

@Component
public class GameDtoToGameMapper {

    public Game gameDtoToGame(GameDto gameDto) {
        Player white = new Player(gameDto.getPlayers().getWhite().getUser().getName());
        Player black = new Player(gameDto.getPlayers().getBlack().getUser().getName());
        return new Game(gameDto.getId(), white, black, gameDto.getVariant(), gameDto.getSpeed());
    }

}
