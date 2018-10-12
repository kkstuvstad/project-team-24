package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        //assertFalse(board.placeShip(new Ship("DESTROYER"), 9, 'C', true));
        boolean a =board.placeShip(new Ship("DESTROYER"), 8, 'C', true);
        boolean b =board.placeShip(new Ship("MINESWEEPER"),9,'B',true);
        boolean c =board.placeShip(new Ship("DESTROYER"),8,'C',true);
        boolean d =board.placeShip(new Ship("MINESWEEPER"),9,'A',false);
        boolean e =board.placeShip(new Ship("MINESWEEPER"),1,'A',false);
        System.out.print(a+"\n");
        System.out.print(b+"\n");
        System.out.print(c+"\n");
        System.out.print(d+"\n");
        System.out.print(e+"\n");
        //board.placeShip(new Ship("MINESWEEPER"))

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
        board.attack(1,'A');
        board.attack(1,'B');

    }


}
