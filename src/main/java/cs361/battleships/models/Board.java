package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty protected List<Ship> ships;
	@JsonProperty protected List<Result> attacks;
	@JsonProperty protected List<Result> sonars;
	@JsonProperty protected int numShipsSunk;
	@JsonProperty protected int maxShip;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		numShipsSunk = 0;
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		sonars = new ArrayList<>();
		maxShip = 4;
	}

	public int getNumShipsSunk(){
		return numShipsSunk;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		ShipFactory factory = new ShipFactory();
		if (ships.size() >= maxShip) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		System.out.println(ship.getKind());
		final var placedShip = factory.getShip(ship.getKind());
		placedShip.place(y, x, isVertical);
		if (ships.stream().anyMatch(s -> GameHelper.overlaps(placedShip, s))) {
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

	private List<Result> sonar(Square s) {

		List <Result> results = new ArrayList<>();
		List <Square> squares = new ArrayList<>();

		GameHelper.addSonarSquares(s, squares);

		for(int i = 0; i < squares.size(); i++) {
			Square temp = squares.get(i);
			//var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(temp)).collect(Collectors.toList());
			var attackResult = new Result(temp);
			if(temp.isOutOfBounds())
				attackResult.setResult(AtackStatus.INVALID);
			else if (attacks.stream().anyMatch(r -> r.getLocation().equals(temp))){
				attackResult.setResult(AtackStatus.INVALID);
			}

			else if (ships.stream().anyMatch(ship -> GameHelper.isAtLocation(temp, ship)))
				attackResult.setResult(AtackStatus.OCCUPIED);
			else
				attackResult.setResult(AtackStatus.EMPTY);

			if(attackResult.getResult() != AtackStatus.INVALID)
				results.add(attackResult);
		}

		return results;
	}

	private Result attack(Square s) {
		if (attacks.stream().anyMatch(r -> r.getLocation().equals(s))) {
			if(ships.stream().anyMatch(ship -> ship.getCaptainQuarter().equals(s))){
				var missedAttacks = attacks.stream().filter(r -> r.getLocation().equals(s)).collect(Collectors.toList());
				missedAttacks.stream().forEach(r -> r.setResult(AtackStatus.SUNK));
			}
			else {
				var attackResult = new Result(s);
				attackResult.setResult(AtackStatus.INVALID);
				return attackResult;
			}
		}
		var shipsAtLocation = ships.stream().filter(ship -> GameHelper.isAtLocation(s, ship)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}

		var hitShip = shipsAtLocation.get(0);
		var attackResult = hitShip.attack(s.getRow(), s.getColumn());
		if (attackResult.getResult() == AtackStatus.SUNK) {
			numShipsSunk++;
			hitShip.getOccupiedSquares().stream().forEach(square -> attacks.add(attack(square)));
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}
	public boolean sonar(int x, char y){
		if(numShipsSunk == 0)
			return false;
		List <Result> results = sonar(new Square(x,y));
		if(results.get(0).getResult() == AtackStatus.INVALID) {
			return false;
		}
		else {
			for(int i = 0; i < results.size(); i++){
				sonars.add(results.get(i));
			}
			return true;
		}
	}

	public List<Ship> getShips() {
		return ships;
	}

	public List<Result> getSonars() {return sonars;}

	public void setMaxShip(int max){
		maxShip = max;
	}
}
