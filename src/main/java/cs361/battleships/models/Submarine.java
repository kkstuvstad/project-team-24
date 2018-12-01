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

    @Override
    public void place(char col, int row, boolean isVertical) {
        for (int i=0; i<size; i++) {
            if (isVertical) {
                occupiedSquares.add(new Square(row+i, col));
            } else {
                occupiedSquares.add(new Square(row, (char) (col + i)));
            }
        }
        placeCaptainQuarter(col, row, isVertical);
        if(isVertical){
            occupiedSquares.add(new Square(row + 2,(char)(col + 1)));
        }
        else{
            occupiedSquares.add(new Square(row - 1,(char)(col + 2)));
        }
    }
}
