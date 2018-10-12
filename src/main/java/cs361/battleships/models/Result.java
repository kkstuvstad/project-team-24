package cs361.battleships.models;

public class Result {

	private AtackStatus res;
	private Ship ship;
	private Square sq;

	public AtackStatus getResult() {
		return res;
	}

	public void setResult(AtackStatus result) {
		res = result;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return sq;
	}

	public void setLocation(Square square) {
		sq = square;
	}
}
