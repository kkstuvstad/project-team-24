package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
            Game game = g.getGame();
            String kind = g.getShipType();
            Ship ship;
            if(kind.equals("MINESWEEPER")){
                ship = new Minesweeper();
            }
            else if(kind.equals("DESTROYER")){
                ship = new Destroyer();
            }
            else if(kind.equals("BATTLESHIP")){
                ship = new Battleship();
            }
            else{
                ship = new Submarine();
            }
            boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical(),g.isSubBoard());
            if (result) {
                return Results.json().render(game);
            } else {
                return Results.badRequest();
            }
    }


    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        if(game.getPlayersBoard().getNumShipsSunk() > 0){

        }
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result sonar(Context context, AttackGameAction g){
        Game game = g.getGame();
        boolean result = game.sonar(g.getActionRow(), g.getActionColumn());
        if(result){
            return Results.json().render(game);
        }
        else{
            return Results.badRequest();
        }
    }

    public Result refreshOpponent(Context context, RefreshGameAction g){
        Game game = g.getGame();
        return Results.json().render(game);
    }


}
