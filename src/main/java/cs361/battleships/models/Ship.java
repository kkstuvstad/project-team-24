package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String shipName;
	private int length;
	private int numHits;


	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		numHits = 0;
		if (kind.equals("MINESWEEPER")){
			occupiedSquares = new ArrayList<>(2);
			length = 2;
			shipName = "MINESWEEPER";
		}
		else if (kind.equals("DESTROYER")) {
			occupiedSquares = new ArrayList<>(3);
			length = 3;
			shipName = "DESTROYER";
		}
		else if (kind.equals("BATTLESHIP")){
			occupiedSquares = new ArrayList<>(4);
			length = 4;
			shipName = "BATTLESHIP";
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public String getShipName() {
		return shipName;
	}

	public int getLength() {
		return length;
	}

	public int getNumHits() {
		return numHits;
	}

	public void setOccupiedSquares(List<Square> occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

	public void setOccupiedSquare(Square sq){
		this.occupiedSquares.add(sq);
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setNumHits(int numHits) {
		this.numHits = numHits;
	}

	public void incNumHits() { this.numHits++; }

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
}
