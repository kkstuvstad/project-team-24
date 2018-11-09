package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Ship {

	@JsonProperty private String kind;
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int size;
	@JsonProperty private Square captainQuarter;
	@JsonProperty private boolean isArmorDown = false;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this();
		this.kind = kind;
		switch(kind) {
			case "MINESWEEPER":
				isArmorDown = true;
				size = 2;
				break;
			case "DESTROYER":
				size = 3;
				break;
			case "BATTLESHIP":
				size = 4;
				break;
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

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
		//Vertical:
		//Minesweeper: place where it is
		//Destroyer: Place 1 block below
		//Battleship: Place 2 blocks below
		//Horizontal Down below:
		//Reverse
		if(isVertical) {
			switch (kind) {
				case "MINESWEEPER":
					captainQuarter = new Square(row, col);
					break;
				case "DESTROYER":
					captainQuarter = new Square(row + 1,col);
					break;
				case "BATTLESHIP":
					captainQuarter = new Square(row + 2, col);
					break;
			}
		}
		else{
			switch (kind) {
				case "MINESWEEPER":
					captainQuarter = new Square(row, col);
					break;
				case "DESTROYER":
					captainQuarter = new Square(row, (char) (col + 1));
					break;
				case "BATTLESHIP":
					captainQuarter = new Square(row, (char) (col + 2));
					break;
			}
		}

	}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
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
}
