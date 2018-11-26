package cs361.battleships.models;

public class SubBoard extends Board {

    public SubBoard(){
        super();
        maxShip = 1;
    }

    @Override
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        ShipFactory factory = new ShipFactory();
        if (ships.size() >= maxShip) {
            return false;
        }
        if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
            return false;
        }
        //System.out.println(ship.getKind());
        if(!ship.getKind().equals("SUBMARINE")){
            return false;
        }
        final var placedShip = factory.getShip(ship.getKind());
        placedShip.place(y, x, isVertical);
        if (ships.stream().anyMatch(s -> GameHelper.overlaps(placedShip, s))) {
            return false;
        }
        if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
            return false;
        }
        ships.add(placedShip);
        return true;
    }
}
