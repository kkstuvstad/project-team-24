package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        //assertFalse(board.placeShip(new Ship("DESTROYER"), 9, 'C', true));
        board.placeShip(new Ship("DESTROYER"), 8, 'C', true);
        board.placeShip(new Ship("MINESWEEPER"),9,'B',true);
        board.attack(9,'C');
        board.attack(9,'C');
        board.attack(8,'C');
        board.attack(10,'C');
        board.attack(10,'C');
        board.attack(11,'C');
        board.attack(5,'J');
        board.attack(5,'K');
        board.attack(9,'B');
        board.attack(10,'B');
    }


}
