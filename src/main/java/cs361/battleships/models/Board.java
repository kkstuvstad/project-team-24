package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.AttackGameAction;

import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty private List<Ship> boardShips;
	@JsonProperty private List<Result> attacks;
	private int sunkShips;



	/*
   DO NOT change the signature of this method. It is used by the grading scripts.
    */
	public Board() {

		boardShips = new ArrayList<>();
		attacks = new ArrayList<>();
		sunkShips = 0;

	}

	/*
   DO NOT change the signature of this method. It is used by the grading scripts.
    */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {

		//you can only place ships when there are two or less on the board
		if (boardShips.size() > 2){
			return false;
		}
		//make sure there are no duplicate ships
		for(int i = 0; i < boardShips.size(); i++){
			if(ship.getShipName().equals(boardShips.get(i).getShipName())){
				return false;
			}
		}
		//make sure its placed in the grid and ship doesn't go off
		if((x >= 1 && x<= 10) && (y >= 'A' && y<= 'J')){
			if(isVertical){
				if(x + ship.getLength() - 1 > 10){
					return false;
				}
				for(int i = 0; i < ship.getLength(); i++){
					ship.setOccupiedSquare(new Square(x+i,y));
				}
			}
			else {
				if (y + ship.getLength() - 1 > 'J'){
					return false;
				}
				for (int i = 0; i < ship.getLength(); i++){
					ship.setOccupiedSquare(new Square(x,(char)(y+i)));
				}
			}
		}
		else{
			return false;
		}
		//make sure the new ship doesn't try to take an occupied square
		for(int i = 0; i < ship.getLength(); i++){
			Square shipSq = ship.getOccupiedSquares().get(i);
			for (int j = 0; j < boardShips.size(); j++){
				Ship boardship = boardShips.get(j);
				for (int k = 0; k < boardship.getLength(); k++){
					Square boardshipSq = boardship.getOccupiedSquares().get(k);
					if(shipSq.getRow() == boardshipSq.getRow() && shipSq.getColumn() == boardshipSq.getColumn()){
						return false;
					}
				}
			}
		}


		boardShips.add(ship);
		return true;
	}
	/*
   DO NOT change the signature of this method. It is used by the grading scripts.
    */
	public Result attack(int x, char y) {
		Result attack = new Result();
		Ship attackedShip = null;
		//default status is miss
		attack.setResult(AtackStatus.MISS);
		//make sure ships are set
		if(boardShips.size() != 3){
			attack.setResult(AtackStatus.INVALID);
			return attack;
		}
		//Check if the attack is out of grid
		if(x > 10 || y > 'J'){
			attack.setResult(AtackStatus.INVALID);
			return attack;
		}
		//Check if the attack already exist
		for (int i = 0;i<attacks.size();i++){
			if(attacks.get(i).getLocation().getColumn() == y && attacks.get(i).getLocation().getRow() == x){
				attack.setResult(AtackStatus.INVALID);
				return attack;
			}
		}

		//Check if HIT
		//Loop the ships
		for(int i = 0;i<boardShips.size();i++){
			//Loop the occupied square of ships
			for(int j=0;j<boardShips.get(i).getOccupiedSquares().size();j++){
				if(boardShips.get(i).getOccupiedSquares().get(j).getRow() == x && boardShips.get(i).getOccupiedSquares().get(j).getColumn() == y){
					attack.setResult(AtackStatus.HIT);
					//Set ship for the result at this point
					attackedShip = boardShips.get(i);
				}
			}
		}



		//Then Check for Sink
		if(attack.getResult() == AtackStatus.HIT){
			attackedShip.incNumHits();
			if(attackedShip.getNumHits() == attackedShip.getLength()){
				attack.setResult(AtackStatus.SUNK);
				sunkShips++;
			}
		}
		//Then Check for surrender
		if(attack.getResult() == AtackStatus.SUNK){
			if(sunkShips == 3){
				attack.setResult(AtackStatus.SURRENDER);
			}
		}

		//System.out.print(x + " " + y + ": " + status);
		//System.out.print("\n");
		//attack.setResult(status);
		attack.setShip(attackedShip);
		attacks.add(attack);
		return attack;
	}
	public List<Ship> getShips() {
		return boardShips;
	}
	public void setShips(List<Ship> ships) {
		this.boardShips = ships;
	}
	public List<Result> getAttacks() {
		return this.attacks;
	}
	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}