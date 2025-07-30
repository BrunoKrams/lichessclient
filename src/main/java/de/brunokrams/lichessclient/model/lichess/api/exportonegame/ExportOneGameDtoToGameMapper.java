package de.brunokrams.lichessclient.model.lichess.api.exportonegame;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import org.springframework.stereotype.Component;

@Component
class ExportOneGameDtoToGameMapper {

    public Game gameDtoToGame(ExportOneGameDto exportOneGameDto) {
        Player white = new Player(exportOneGameDto.getPlayers().getWhite().getUser().getName());
        Player black = new Player(exportOneGameDto.getPlayers().getBlack().getUser().getName());
        return new Game(exportOneGameDto.getId(), white, black, exportOneGameDto.getVariant(), exportOneGameDto.getSpeed());
    }

}
