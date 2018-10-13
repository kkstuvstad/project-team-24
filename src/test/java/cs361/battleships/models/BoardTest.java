package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"),4,'I',false));
    }

    @Test
    public void testValidPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("BATTLESHIP"),4,'C',false));
    }

    @Test
    public void testValidAttack(){
        Board board = new Board();
        board.placeShip(new Ship("DESTROYER"),5,'F',true);
        board.placeShip(new Ship("MINESWEEPER"),5,'G',true);
        board.placeShip(new Ship("BATTLESHIP"),5,'E',true);
        board.attack(5,'G');
        assertTrue(board.getAttacks().get(0).getResult() == AtackStatus.HIT);
        assertTrue(board.attack(5,'G').getResult() == AtackStatus.INVALID);
        board.attack(1,'D');
        assertTrue(board.getAttacks().get(1).getResult() == AtackStatus.MISS);
        board.attack(6,'G');
        assertTrue(board.getAttacks().get(2).getResult() == AtackStatus.SUNK);
        board.attack(5,'F');
        board.attack(6,'F');
        board.attack(7,'F');
        assertTrue(board.getAttacks().get(5).getResult() == AtackStatus.SUNK);
        board.attack(5,'E');
        board.attack(6,'E');
        board.attack(7,'E');
        board.attack(8,'E');
        assertTrue(board.getAttacks().get(9).getResult() == AtackStatus.SURRENDER);
    }
}
