package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SonarTest {

    private Board board;

    @Before
    public void setUp() { board = new Board(); }

    @Test
    public void noShipSunk(){
        boolean result = board.sonar(1,'A');
        assertFalse(result);
    }

    @Test
    public void testSonar() {
        board.placeShip(new Ship("BATTLESHIP"),9,'B',false);
        board.placeShip(new Ship("DESTROYER"),1,'A',false);
        board.attack(1,'A');
        board.attack(1,'B');
        assertEquals(board.attack(1,'B').getResult(), AtackStatus.SUNK);
        board.placeShip(new Ship("MINESWEEPER"),5,'D',false);
        boolean result = board.sonar(5,'D');
        assertTrue(result);
        List <Result> results = board.getSonars();
        assertTrue(results.get(0).getResult() == AtackStatus.OCCUPIED);
        assertTrue(results.get(7).getResult() == AtackStatus.OCCUPIED);
        assertTrue(results.get(4).getResult() == AtackStatus.EMPTY);
        assertTrue(results.get(2).getResult() == AtackStatus.EMPTY);
    }

    @Test
    public void invalidSonarPlacement() {
        assertFalse(board.sonar(12,'E'));
        assertFalse(board.sonar(5,'L'));
        assertFalse(board.sonar(13,'Q'));
    }

}
