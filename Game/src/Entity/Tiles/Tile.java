package Entity.Tiles;

import Entity.Player;

public class Tile {

    public enum Street {
        BROWN,
        LIGHT_BLUE,
        PURPLE,
        ORANGE,
        RED,
        YELLOW,
        GREEN,
        BLUE

    }

    public final String NAME;

    public final int PRICE;
    public final int RENT;

    public final Street STREET;

    private Player owner;

    protected Tile(String name, int price, int rent, Street street) {
        NAME = name;
        PRICE = price;
        RENT = rent;
        STREET = street;
    }
    public void action(Player player) {
        if (hasOwner()) {
            player.sendCash(owner, RENT);
        }
    }

    public boolean hasOwner() {
        return owner != null;
    }
    public void setOwner(Player player) {
        owner = player;
    }
    public Player getOwner() {
        return owner;
    }
}
