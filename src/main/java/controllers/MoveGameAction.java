package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveGameAction {
    @JsonProperty private Game game;
    @JsonProperty private char dir;

    public Game getGame(){ return game;}

    public char getDir(){return dir;}
}
