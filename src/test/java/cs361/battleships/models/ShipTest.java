package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

    private ShipFactory factory;

    @Before
    public void setUp() {
        factory = new ShipFactory();
    }

    @Test
    public void testPlaceMinesweeperHorizontaly() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceMinesweeperVertically() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerHorizontaly() {
        Ship minesweeper = factory.getShip("DESTROYER");
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerVertically() {
        Ship minesweeper = factory.getShip("DESTROYER");
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipHorizontaly() {
        Ship minesweeper = factory.getShip("BATTLESHIP");
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        expected.add(new Square(1, 'D'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipVertically() {
        Ship minesweeper = factory.getShip("BATTLESHIP");
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        expected.add(new Square(4, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testShipOverlaps() {
        Ship minesweeper1 = factory.getShip("MINESWEEPER");
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = factory.getShip("MINESWEEPER");
        minesweeper2.place('A', 1, true);

        assertTrue(GameHelper.overlaps(minesweeper2, minesweeper1));
    }

    @Test
    public void testShipsDontOverlap() {
        Ship minesweeper1 = factory.getShip("MINESWEEPER");
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = factory.getShip("MINESWEEPER");
        minesweeper2.place('C', 2, true);

        assertFalse(GameHelper.overlaps(minesweeper2, minesweeper1));
    }

    @Test
    public void testIsAtLocation() {
        Ship minesweeper = factory.getShip("BATTLESHIP");
        minesweeper.place('A', 1, true);

        assertTrue(GameHelper.isAtLocation(new Square(1, 'A'), minesweeper));
        assertTrue(GameHelper.isAtLocation(new Square(2, 'A'), minesweeper));
    }

    @Test
    public void testHit() {
        Ship minesweeper = factory.getShip("BATTLESHIP");
        minesweeper.place('A', 1, true);

        Result result = minesweeper.attack(1, 'A');
        assertEquals(AtackStatus.HIT, result.getResult());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(1, 'A'), result.getLocation());
    }

    @Test
    public void testSink() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        minesweeper.place('A', 1, true);

        minesweeper.attack(1, 'A');
        Result result = minesweeper.attack(2, 'A');

        //assertEquals(AtackStatus.SUNK, result.getResult());
        assertEquals(minesweeper, result.getShip());
        assertEquals(new Square(2, 'A'), result.getLocation());
    }

    @Test
    public void testOverlapsBug() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        Ship destroyer = factory.getShip("DESTROYER");
        minesweeper.place('C', 5, false);
        destroyer.place('C', 5, false);
        assertTrue(GameHelper.overlaps(destroyer, minesweeper));
    }

    @Test
    public void testAttackSameSquareTwice() {
        Ship minesweeper = factory.getShip("MINESWEEPER");
        minesweeper.place('A', 1, true);
        var result = minesweeper.attack(2, 'A');
        assertEquals(AtackStatus.HIT, result.getResult());
        result = minesweeper.attack(2, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testEquals() {
        Ship minesweeper1 = factory.getShip("MINESWEEPER");
        minesweeper1.place('A', 1, true);
        Ship minesweeper2 = factory.getShip("MINESWEEPER");
        minesweeper2.place('A', 1, true);
        //assertTrue(minesweeper1.equals(minesweeper2));
        assertEquals(minesweeper1.hashCode(), minesweeper2.hashCode());
    }

    @Test
    public void testQuarter(){
        Ship destroyer1 = factory.getShip("DESTROYER");
        destroyer1.place('A', 1, true);
        var result1= destroyer1.attack(1, 'A');
        assertEquals(AtackStatus.HIT, result1.getResult());

        Ship destroyer2 = factory.getShip("DESTROYER");
        destroyer2.place('B',1,false);
        var result2 = destroyer2.attack(1,'C');
        assertEquals(AtackStatus.MISS, result2.getResult());

    }

}
