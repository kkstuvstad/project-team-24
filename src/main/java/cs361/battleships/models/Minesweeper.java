package cs361.battleships.models;

public class Minesweeper extends Ship {

        public Minesweeper(){
                super();
                kind = "MINESWEEPER";
                isArmorDown = true;
                size = 2;
        }

        public void placeCaptainQuarter(char col, int row, boolean isVertical){
                captainQuarter = new Square(row, col);
        }

}
