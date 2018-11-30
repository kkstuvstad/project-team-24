package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveTest {
    private ShipFactory factory;
    private Game game;

    @Before
    public void setUp() {
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
        game.getOpponentsBoard().numShipsSunk = 2;
        game.getPlayersBoard().placeShip(factory.getShip("BATTLESHIP"),9,'B',false);
        game.getPlayersBoard().placeShip(factory.getShip("DESTROYER"),1,'A',false);
        game.getPlayersBoard().placeShip(factory.getShip("MINESWEEPER"),5,'D',false);
        game.getPlayersBoard().placeShip(factory.getShip("SUBMARINE"),6,'F',false);
        game.move('u');
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getRow() == 8);
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(1).getRow() == 8);
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(2).getRow() == 8);
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getRow() == 8);
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getRow() == 1);
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(1).getRow() == 1);
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(2).getRow() == 1);
        assertTrue(game.getPlayersBoard().getShips().get(2).getOccupiedSquares().get(0).getRow() == 4);
        assertTrue(game.getPlayersBoard().getShips().get(2).getOccupiedSquares().get(1).getRow() == 4);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(0).getRow() == 5);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(1).getRow() == 5);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(2).getRow() == 5);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(3).getRow() == 5);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(4).getRow() == 6);
        game.move('r');
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getColumn() == 'C');
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getColumn() == 'B');
        assertTrue(game.getPlayersBoard().getShips().get(2).getOccupiedSquares().get(0).getColumn() == 'E');
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(0).getColumn() == 'G');
    }

    @Test public void testCollision(){
        game.getOpponentsBoard().numShipsSunk = 2;
        game.getPlayersBoard().placeShip(factory.getShip("DESTROYER"),10,'A',false);
        game.getPlayersBoard().placeShip(factory.getShip("MINESWEEPER"),9,'A',false);
        game.getPlayersBoard().placeShip(factory.getShip("BATTLESHIP"),5,'A',false);
        game.getPlayersBoard().placeShip(factory.getShip("SUBMARINE"),5,'E',false);
        for(int i = 0; i < 4; i++)
            System.out.println(game.getPlayersBoard().getShips().get(i).getOccupiedSquares().get(0).getRow());
        game.move('d');
        for(int i = 0; i < 4; i++)
            System.out.println(game.getPlayersBoard().getShips().get(i).getOccupiedSquares().get(0).getRow());
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getRow() == 10);
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getRow() == 9);
        assertTrue(game.getPlayersBoard().getShips().get(2).getOccupiedSquares().get(0).getRow() == 6);
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(0).getRow() == 6);
        game.move('l');
        assertTrue(game.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(game.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(game.getPlayersBoard().getShips().get(2).getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(game.getPlayersBoard().getShips().get(3).getOccupiedSquares().get(0).getColumn() == 'E');

    }
}
