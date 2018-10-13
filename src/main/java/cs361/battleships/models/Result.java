package cs361.battleships.models;

public class Result {

	private AttackStatus res;
	private Ship ship;
	private Square sq;


	public AttackStatus getResult() {
		return res;
	}


	//public Result(int x, char y){
	//	sq = new Square(x,y);
	//}

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
