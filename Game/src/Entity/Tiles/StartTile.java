package Entity.Tiles;

import Entity.Player;

public class StartTile extends Tile{

    //private static final int CASH = 200;

    protected StartTile(String name, int price, int rent, Street street) {
        super(name, price, rent, street);
    }

    @Override
    public void action(Player player) {
        //player.addCash(CASH);
    }
}
