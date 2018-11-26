package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    //@JsonProperty private Board playersSubBoard = new Board();
    //@JsonProperty private Board opponentSubBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

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

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(GameHelper.randRow(), GameHelper.randCol());
        } while(opponentAttackResult.getResult() == INVALID);

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

}
