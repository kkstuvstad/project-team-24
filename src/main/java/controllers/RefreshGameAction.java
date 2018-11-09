package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class RefreshGameAction {
    @JsonProperty private Game game;
    @JsonProperty private boolean isRefresh;
    public Game getGame() {
        return game;
    }

    public boolean getRefresh(){return isRefresh;}
}
