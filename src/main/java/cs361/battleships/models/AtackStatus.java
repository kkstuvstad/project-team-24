package cs361.battleships.models;

public enum AtackStatus {

	/**
	 * The result if an attack results in a miss.
	 */
	MISS,

	/**
	 * The result if an attack results in a hit on an enemy ship.
	 */
	HIT,

	/**
	 * THe result if an attack sinks the enemy ship
	 */
	SUNK,

	/**
	 * The results if an attack results in the defeat of the opponent (a
	 * surrender).
	 */
	SURRENDER,
	
	/**
	 * The result if the coordinates given are invalid.
	 */
	INVALID,

	/**
	 * The result, if a sonar pulse is used and the square is occupied by a ship
	 */
	OCCUPIED,

	/**
	 * The result, if a sonar pulse is used and the square is not occupied by a ship
	 */
	EMPTY,
}
