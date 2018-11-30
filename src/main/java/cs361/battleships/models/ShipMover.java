package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipMover {


    public ShipMover(){}

    public boolean move(char dir, List<Ship> ships){
        boolean result;
        if(dir == 'u')
            result = moveUp(ships);
        else if(dir == 'd')
            result = moveDown(ships);
        else if(dir == 'l')
            result = moveLeft(ships);
        else if(dir == 'r')
            result = moveRight(ships);
        else
            return false;

        return result;
    }

    private boolean moveUp(List<Ship> ships){
        List<Ship> newShips = new ArrayList<>();
        List<Square> squares;
        List<Square> newSquares;
        Ship ship;
        for(int i = 0; i < ships.size(); i++){
            ship = new Ship();
            squares = ships.get(i).getOccupiedSquares();
            newSquares = new ArrayList<>();
            for(int j = 0; j < squares.size(); j++){
                Square sq = squares.get(j);
                newSquares.add(new Square(sq.getRow()-1,sq.getColumn(),sq.isHit()));
            }
            if(newSquares.stream().anyMatch(s -> s.isOutOfBounds())){
                ship.setOccupiedSquares(ships.get(i).getOccupiedSquares());
                ship.setCaptainQuarter(ships.get(i).getCaptainQuarter());
                newShips.add(ship);
            }
            else{
                ship.setOccupiedSquares(newSquares);
                ship.setCaptainQuarter(new Square(ships.get(i).getCaptainQuarter().getRow()-1,ships.get(i).getCaptainQuarter().getColumn()));
                newShips.add(ship);
            }
        }
        for (int i = 0; i < ships.size(); i++){
            ship = newShips.get(i);
            newShips.remove(i);
            Ship finalShip = ship;
            if(newShips.stream().anyMatch(s -> GameHelper.overlaps(finalShip,s))){
                newShips.add(i,ships.get(i));
            }
            else{
                newShips.add(i,finalShip);
            }
        }
        for(int i = 0; i < ships.size(); i++) {
            ships.get(i).setOccupiedSquares(newShips.get(i).getOccupiedSquares());
            ships.get(i).setCaptainQuarter(newShips.get(i).getCaptainQuarter());
        }
        return true;
    }

    private boolean moveDown(List<Ship> ships){
        List<Ship> newShips = new ArrayList<>();
        List<Square> squares;
        List<Square> newSquares;
        Ship ship;
        for(int i = 0; i < ships.size(); i++){
            ship = new Ship();
            squares = ships.get(i).getOccupiedSquares();
            newSquares = new ArrayList<>();
            for(int j = 0; j < squares.size(); j++){
                Square sq = squares.get(j);
                newSquares.add(new Square(sq.getRow()+1,sq.getColumn(),sq.isHit()));
            }
            if(newSquares.stream().anyMatch(s -> s.isOutOfBounds())){
                ship.setOccupiedSquares(ships.get(i).getOccupiedSquares());
                ship.setCaptainQuarter(ships.get(i).getCaptainQuarter());
                newShips.add(ship);
            }
            else{
                ship.setOccupiedSquares(newSquares);
                ship.setCaptainQuarter(new Square(ships.get(i).getCaptainQuarter().getRow()+1,ships.get(i).getCaptainQuarter().getColumn()));
                newShips.add(ship);
            }
        }
        for (int i = 0; i < ships.size(); i++){
            ship = newShips.get(i);
            newShips.remove(i);
            Ship finalShip = ship;
            if(newShips.stream().anyMatch(s -> GameHelper.overlaps(finalShip,s))){
                newShips.add(i,ships.get(i));
            }
            else{
                newShips.add(i,finalShip);
            }
        }
        for(int i = 0; i < ships.size(); i++){
            ships.get(i).setOccupiedSquares(newShips.get(i).getOccupiedSquares());
            ships.get(i).setCaptainQuarter(newShips.get(i).getCaptainQuarter());
        }
        return true;
    }

    private boolean moveLeft(List<Ship> ships){
        List<Ship> newShips = new ArrayList<>();
        List<Square> squares;
        List<Square> newSquares;
        Ship ship;
        for(int i = 0; i < ships.size(); i++){
            ship = new Ship();
            squares = ships.get(i).getOccupiedSquares();
            newSquares = new ArrayList<>();
            for(int j = 0; j < squares.size(); j++){
                Square sq = squares.get(j);
                newSquares.add(new Square(sq.getRow(),(char)(sq.getColumn()-1),sq.isHit()));
            }
            if(newSquares.stream().anyMatch(s -> s.isOutOfBounds())){
                ship.setOccupiedSquares(ships.get(i).getOccupiedSquares());
                ship.setCaptainQuarter(ships.get(i).getCaptainQuarter());
                newShips.add(ship);
            }
            else{
                ship.setOccupiedSquares(newSquares);
                ship.setCaptainQuarter(new Square(ships.get(i).getCaptainQuarter().getRow(),(char)(ships.get(i).getCaptainQuarter().getColumn()-1)));
                newShips.add(ship);
            }
        }
        for (int i = 0; i < ships.size(); i++){
            ship = newShips.get(i);
            newShips.remove(i);
            Ship finalShip = ship;
            if(newShips.stream().anyMatch(s -> GameHelper.overlaps(finalShip,s))){
                newShips.add(i,ships.get(i));
            }
            else{
                newShips.add(i,finalShip);
            }
        }
        for(int i = 0; i < ships.size(); i++){
            ships.get(i).setOccupiedSquares(newShips.get(i).getOccupiedSquares());
            ships.get(i).setCaptainQuarter(newShips.get(i).getCaptainQuarter());
        }
        return true;
    }

    private boolean moveRight(List<Ship> ships){
        List<Ship> newShips = new ArrayList<>();
        List<Square> squares;
        List<Square> newSquares;
        Ship ship;
        for(int i = 0; i < ships.size(); i++){
            ship = new Ship();
            squares = ships.get(i).getOccupiedSquares();
            newSquares = new ArrayList<>();
            for(int j = 0; j < squares.size(); j++){
                Square sq = squares.get(j);
                newSquares.add(new Square(sq.getRow(),(char)(sq.getColumn()+1),sq.isHit()));
            }
            if(newSquares.stream().anyMatch(s -> s.isOutOfBounds())){
                ship.setOccupiedSquares(ships.get(i).getOccupiedSquares());
                ship.setCaptainQuarter(ships.get(i).getCaptainQuarter());
                newShips.add(ship);
            }
            else{
                ship.setOccupiedSquares(newSquares);
                ship.setCaptainQuarter(new Square(ships.get(i).getCaptainQuarter().getRow(),(char)(ships.get(i).getCaptainQuarter().getColumn()+1)));
                newShips.add(ship);
            }
        }
        for (int i = 0; i < ships.size(); i++){
            ship = newShips.get(i);
            newShips.remove(i);
            Ship finalShip = ship;
            if(newShips.stream().anyMatch(s -> GameHelper.overlaps(finalShip,s))){
                newShips.add(i,ships.get(i));
            }
            else{
                newShips.add(i,finalShip);
            }
        }
        for(int i = 0; i < ships.size(); i++){
            ships.get(i).setOccupiedSquares(newShips.get(i).getOccupiedSquares());
            ships.get(i).setCaptainQuarter(newShips.get(i).getCaptainQuarter());
        }
        return true;
    }
}
/*
for (int i = 0; i < ships.size(); i++){
            ship = newShips.get(i);
            boolean overlap = false;
            for(int k = 0; k < ship.getOccupiedSquares().size(); k++) {
                Square sq = ship.getOccupiedSquares().get(k);
                for (int j = 0; j < newShips.size(); j++) {
                    if(newShips.get(j).getOccupiedSquares().stream().anyMatch(s -> s.equals(sq))){
                        overlap = true;
                    }
                }
            }
            if(overlap)
                newShips.add(i,ships.get(i));
            else
                newShips.add(i,ship);

        }
 */