package cs361.battleships.models;

public class ShipFactory {
    public Ship getShip(String shipType){
        if(shipType == null){
            return null;
        }
        if(shipType.equalsIgnoreCase("MINESWEEPER")){
            return new Minesweeper();
        }
        else if(shipType.equalsIgnoreCase("DESTROYER")){
            return new Destroyer();
        }
        else if(shipType.equalsIgnoreCase("BATTLESHIP")){
            return new Battleship();
        }
        else if(shipType.equalsIgnoreCase("SUBMARINE")){
            return new Submarine();
        }

        return null;
    }
}
