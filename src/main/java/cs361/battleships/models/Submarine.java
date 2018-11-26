package cs361.battleships.models;

public class Submarine extends Ship {
    public Submarine(){
        super();
        kind = "SUBMARINE";
        size = 4;
    }

    @Override
    public void placeCaptainQuarter(char col, int row, boolean isVertical){
        if(isVertical){
            captainQuarter = new Square(row + 3,col);
        }
        else{
            captainQuarter = new Square(row, (char) (col + 3));
        }
    }
}
