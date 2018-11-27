package cs361.battleships.models;

public class Battleship extends Ship {
    public Battleship(){
        super();
        kind = "BATTLESHIP";
        size = 4;
    }

    public void placeCaptainQuarter(char col, int row, boolean isVertical){
        if(isVertical){
            captainQuarter = new Square(row + 2, col);
        }
        else{
            captainQuarter = new Square(row, (char) (col + 2));
        }
    }
}
