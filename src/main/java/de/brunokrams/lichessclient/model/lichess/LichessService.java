package de.brunokrams.lichessclient.model.lichess;

import de.brunokrams.lichessclient.model.Game;
import de.brunokrams.lichessclient.model.Player;
import de.brunokrams.lichessclient.model.Session;
import de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames.GetMyOngoingGames;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LichessService {

    private final GetMyOngoingGames getMyOngoingGames;
    private final Session session;

    public LichessService(GetMyOngoingGames getMyOngoingGames, Session session) {
        this.getMyOngoingGames = getMyOngoingGames;
        this.session = session;
    }

    public List<Game> getOngoingGames() {
        Player player = session.getActivePlayer();
        return getMyOngoingGames.submit(player);
    }

    public void setActiveGame(Game game) {
        session.setActiveGame(game);
    }

    public Game getActiveGame() {
        return session.getActiveGame();    }
  public Player getActivePlayer() {
        return session.getActivePlayer();
    }

}
