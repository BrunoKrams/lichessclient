package de.brunokrams.lichessclient.model.lichess.api.getmyongoinggames;

import java.util.List;

class GetMyOngoingGamesDto {

    private List<GameDto> nowPlaying;

    public List<GameDto> getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(List<GameDto> nowPlaying) {
        this.nowPlaying = nowPlaying;
    }
}



