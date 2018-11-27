package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    @JsonProperty private SubBoard playersSubBoard = new SubBoard();
    @JsonProperty private SubBoard opponentsSubBoard = new SubBoard();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical,boolean isSubBoard) {
        if(isSubBoard) {
            boolean successful = playersSubBoard.placeShip(ship, x, y, isVertical);
            if (!successful) {
                return false;
            }
            else{
                playersBoard.setMaxShip(3);
            }
        }
        else{
            boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
            if (!successful)
                return false;
        }

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, GameHelper.randRow(), GameHelper.randCol(), GameHelper.randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }
        if(opponentsBoard.getNumShipsSunk()>0){
            playerAttack = opponentsSubBoard.attack(x,y);
        }

        opponentAttack();

        return true;
    }

    public boolean opponentAttack(){
        Result opponentAttackResult;
        int randrow;
        char randcol;
        do {
            randrow = GameHelper.randRow();
            randcol = GameHelper.randCol();
            opponentAttackResult = playersBoard.attack(randrow, randcol);
        } while(opponentAttackResult.getResult() == INVALID);
        if(playersBoard.getNumShipsSunk() > 0){
            opponentAttackResult = playersSubBoard.attack(randrow,randcol);
        }
        return true;
    }

    public boolean sonar(int x, char y){
        boolean playerAttack = opponentsBoard.sonar(x,y);
        if (!playerAttack){
            return false;
        }
        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(GameHelper.randRow(), GameHelper.randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;

    }

    public Board getPlayersBoard(){
        return playersBoard;
    }

}
