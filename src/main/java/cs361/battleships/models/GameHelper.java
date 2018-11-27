package cs361.battleships.models;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameHelper {
    public static boolean isAtLocation(Square location, Ship ship) {
        return ship.getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
    }

    public static boolean overlaps(Ship other, Ship ship) {
        Set<Square> thisSquares = Set.copyOf(ship.getOccupiedSquares());
        Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
        Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
        return intersection.size() != 0;
    }

    public static void addSonarSquares(Square s, List<Square> squares) {
        squares.add(s);
        squares.add(new Square(s.getRow()+2, s.getColumn()));
        squares.add(new Square(s.getRow()+1, s.getColumn()));
        squares.add(new Square(s.getRow()+1, (char)(s.getColumn()-1)));
        squares.add(new Square(s.getRow()+1, (char)(s.getColumn()+1)));
        squares.add(new Square(s.getRow(), (char)(s.getColumn()-2)));
        squares.add(new Square(s.getRow(), (char)(s.getColumn()-1)));
        squares.add(new Square(s.getRow(), (char)(s.getColumn()+1)));
        squares.add(new Square(s.getRow(), (char)(s.getColumn()+2)));
        squares.add(new Square(s.getRow()-1, s.getColumn()));
        squares.add(new Square(s.getRow()-1, (char)(s.getColumn()-1)));
        squares.add(new Square(s.getRow()-1, (char)(s.getColumn()+1)));
        squares.add(new Square(s.getRow()-2, s.getColumn()));
    }

    public static char randCol() {
        int random = new Random().nextInt(10);
        return (char) ('A' + random);
    }

    public static int randRow() {
        return  new Random().nextInt(10) + 1;
    }

    public static boolean randVertical() {
        return new Random().nextBoolean();
    }

    public static boolean randSub() {
        return new Random().nextBoolean();
    }
}
