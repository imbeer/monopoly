package Entity.Tiles;

import Entity.Player;

public class JailTile extends Tile{
    protected JailTile(String name, int price, int rent, Street street) {
        super(name, price, rent, street);
    }

    @Override
    public void action(Player player) {
        player.setInJail(true);
    }
}
