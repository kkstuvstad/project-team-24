package cs361.battleships.models;

public class Destroyer extends Ship {
    public Destroyer(){
        super();
        kind = "DESTROYER";
        size = 3;
    }

    public void placeCaptainQuarter(char col, int row, boolean isVertical){
        if(isVertical){
            captainQuarter = new Square(row + 1,col);
        }
        else{
            captainQuarter = new Square(row, (char) (col + 1));
        }
    }
}
