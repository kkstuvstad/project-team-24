package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private Board board;
    private ShipFactory factory;
    private SubBoard subBoard;

    @Before
    public void setUp() {
        board = new Board();
        factory = new ShipFactory();
        subBoard = new SubBoard();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(factory.getShip("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(factory.getShip("MINESWEEPER"), 1, 'A', true));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(factory.getShip("MINESWEEPER"), 1, 'A', true);
        Result result = board.attack(2, 'E');
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(2, 'A');
        assertEquals(AtackStatus.HIT, result.getResult());
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true);
        board.attack(1, 'A');
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A');
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testSurrender() {
        board.placeShip(factory.getShip("MINESWEEPER"), 1, 'A', true);
        var result = board.attack(1, 'A');
        assertEquals(AtackStatus.SURRENDER, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(factory.getShip("MINESWEEPER"), 1, 'A', true));
        assertFalse(board.placeShip(factory.getShip("MINESWEEPER"), 5, 'D', true));

    }

    @Test
    public void testCantPlaceMoreThan3Ships() {
        assertTrue(board.placeShip(factory.getShip("MINESWEEPER"), 1, 'A', true));
        assertTrue(board.placeShip(factory.getShip("BATTLESHIP"), 5, 'D', true));
        assertTrue(board.placeShip(factory.getShip("DESTROYER"), 6, 'A', false));
        assertFalse(board.placeShip(factory.getShip("MINESWEEPER"), 8, 'A', false));

    }

    @Test
    public void testPlaceMoreThan1ShipForSubBoard(){
        assertTrue(subBoard.placeShip(factory.getShip("SUBMARINE"),5,'D',false));
        assertFalse(subBoard.placeShip(factory.getShip("MINESWEEPER"),8,'A',false));
    }

    @Test
    public void testCantPlaceNormalShipOnSubBoard(){
        assertFalse(subBoard.placeShip(factory.getShip("DESTROYER"),5,'D',false));
    }



}
