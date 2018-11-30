package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty protected String kind;
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected int size;
	@JsonProperty protected Square captainQuarter;
	@JsonProperty protected boolean isArmorDown = false;
	@JsonProperty protected boolean sunkChecked = false;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	


	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<Square> squares){occupiedSquares = squares;}

	public Square getCaptainQuarter(){return captainQuarter;}

	//TODO : place the captainquarter
	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}
		placeCaptainQuarter(col, row, isVertical);

	}

	public void placeCaptainQuarter(char col, int row, boolean isVertical){

	}



	public String getKind() {
		return kind;
	}


	public Result attack(int x, char y) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		//if the square created is part of the occupied square of ship
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		//return miss
		var attackedSquare = square.get();
		//if already hit it is invalid
		if (attackedSquare.isHit()) {
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			return result;
		}

		//check for the case that hit the armored captain quarter
		if(attackedSquare.equals(captainQuarter) && !isArmorDown){
			isArmorDown = true;
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.MISS);
			return result;
		}

		//set it to hit
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		//set the result
		result.setShip(this);
		if (isSunk()) {
			result.setResult(AtackStatus.SUNK);
		} else {
			result.setResult(AtackStatus.HIT);
		}
		return result;
	}
    //check if captain Quarter is Hit
	@JsonIgnore
	public boolean isSunk() {
		return (getOccupiedSquares().stream().allMatch(s -> s.isHit())) || (getOccupiedSquares().stream().anyMatch(s -> s.isHit() && s.equals(captainQuarter)));
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}

	public boolean isSunkChecked() {return sunkChecked;}

	public void checkSunk() {
		sunkChecked = true;
	}
}
