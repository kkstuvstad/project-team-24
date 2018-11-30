package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveTest {
    private Board board;
    private ShipFactory factory;
    private Game game;

    @Before
    public void setUp() {
        board = new Board();
        factory = new ShipFactory();
        game = new Game();
    }

    @Test
    public void noShipSunk(){
        boolean result = game.move('u');
        assertFalse(result);
    }

    @Test
    public void oneShipSunk(){
        game.getOpponentsBoard().numShipsSunk = 1;
        boolean result = game.move('u');
        assertFalse(result);
    }

    @Test
    public void testMove(){
        board.placeShip(factory.getShip("BATTLESHIP"),9,'B',false);
        board.placeShip(factory.getShip("DESTROYER"),1,'A',false);
        board.placeShip(factory.getShip("MINESWEEPER"),5,'D',false);
        board.placeShip(factory.getShip("SUBMARINE"),6,'F',false);
        board.move('u');
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(0).getRow() == 8);
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(1).getRow() == 8);
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(2).getRow() == 8);
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(0).getRow() == 8);
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(0).getRow() == 1);
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(1).getRow() == 1);
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(2).getRow() == 1);
        assertTrue(board.getShips().get(2).getOccupiedSquares().get(0).getRow() == 4);
        assertTrue(board.getShips().get(2).getOccupiedSquares().get(1).getRow() == 4);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(0).getRow() == 5);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(1).getRow() == 5);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(2).getRow() == 5);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(3).getRow() == 5);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(4).getRow() == 6);
        board.move('r');
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(0).getColumn() == 'C');
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(0).getColumn() == 'B');
        assertTrue(board.getShips().get(2).getOccupiedSquares().get(0).getColumn() == 'E');
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(0).getColumn() == 'G');
    }

    @Test public void testCollision(){
        board.placeShip(factory.getShip("DESTROYER"),10,'A',false);
        board.placeShip(factory.getShip("MINESWEEPER"),9,'A',false);
        board.placeShip(factory.getShip("BATTLESHIP"),5,'A',false);
        board.placeShip(factory.getShip("SUBMARINE"),5,'E',false);
        for(int i = 0; i < 4; i++)
            System.out.println(board.getShips().get(i).getOccupiedSquares().get(0).getRow());
        board.move('d');
        for(int i = 0; i < 4; i++)
            System.out.println(board.getShips().get(i).getOccupiedSquares().get(0).getRow());
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(0).getRow() == 10);
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(0).getRow() == 9);
        assertTrue(board.getShips().get(2).getOccupiedSquares().get(0).getRow() == 6);
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(0).getRow() == 6);
        board.move('l');
        assertTrue(board.getShips().get(0).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(board.getShips().get(1).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(board.getShips().get(2).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(0).getColumn() == 'E');

    }
}
