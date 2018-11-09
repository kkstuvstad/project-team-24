package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.size() >= 3) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		final var placedShip = new Ship(ship.getKind());
		placedShip.place(y, x, isVertical);
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			return false;
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		Result attackResult = attack(new Square(x, y));
		attacks.add(attackResult);
		return attackResult;
	}

	private Result attack(Square s) {
		//Trying to understand the meaning of the code below
		//if attack existed the attack is invalid
		if (attacks.stream().anyMatch(r -> r.getLocation().equals(s))) {
			//When there is a captain quarter in the square, still attack it
			if(ships.stream().anyMatch(ship -> ship.getCaptainQuarter().equals(s))){
				var missedAttacks = attacks.stream().filter(r -> r.getLocation().equals(s)).collect(Collectors.toList());
				missedAttacks.stream().forEach(r -> r.setResult(AtackStatus.SUNK));

				//attack(s);
			}
			else {
				var attackResult = new Result(s);
				attackResult.setResult(AtackStatus.INVALID);
				return attackResult;
			}
		}
		//Check if there is ship on the grid, if not return "MISS"
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}

		//hitShip is the ship that got hit
		var hitShip = shipsAtLocation.get(0);
		//go to attack(x,y) in Ship class
		var attackResult = hitShip.attack(s.getRow(), s.getColumn());
		if (attackResult.getResult() == AtackStatus.SUNK) {
			//if ship sunk, make sure all the squares are checked as hit
			hitShip.getOccupiedSquares().stream().forEach(square -> attacks.add(attack(square)));
			//check surrender
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}
	//filler until sonar is fully completed
	public Result sonar(int x, char y){
		System.out.println("SONAR ACTIVATED");
		Result result = attack(new Square(x,y));
		return result;
	}

	List<Ship> getShips() {
		return ships;
	}
}
