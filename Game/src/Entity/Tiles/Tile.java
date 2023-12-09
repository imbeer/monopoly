package Entity.Tiles;

import Entity.Players.Player;
import Utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Tile {
    public final String NAME;
    public final int PRICE;
    public final int RENT;
    public final int INDEX;
    private Player owner;
    protected Rectangle2D bounds;

    public Tile(String name, int price, int rent, int index) {
        NAME = name;
        PRICE = price;
        RENT = rent;
        INDEX = index;
    }
    public void action(Player player) {
        if (hasOwner()) {
            player.sendCash(owner, RENT);
        } else {
            if (player.getCash() >= PRICE) {
                boolean answer = player.ask("Do you want to buy " + NAME + "?", "BUY");
                if (answer) {
                    player.payCash(PRICE);
                    setOwner(player);
                }
            }
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

    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        g.setColor(new Color(255, 255, 255));
        g.fill(bounds);
        g.setColor(getColor());
        g.fill(DrawUtils.getVerticalPartOfBounds(bounds, 0, 0.4));
        g.setColor(new Color(0, 0, 0));
        DrawUtils.drawCenteredText(DrawUtils.NAME, NAME, g, DrawUtils.getVerticalPartOfBounds(bounds, 0, 0.4));
        String second;
        String third;
        if (!hasOwner()) {
            second = PRICE + " $";
            third = "";
        } else {
            second = "owner is " + owner.NAME;
            third = RENT + " $";
        }
        DrawUtils.drawCenteredText(DrawUtils.PRICE, second, g, DrawUtils.getVerticalPartOfBounds(bounds, 0.5, 0.7));
        DrawUtils.drawCenteredText(DrawUtils.PRICE, third, g, DrawUtils.getVerticalPartOfBounds(bounds, 0.7, 1));
        g.setColor(getColor());
    }

    protected void fillTileBounds(Rectangle2D fieldBounds, int tilesInRow) {
        double tileWidth = fieldBounds.getWidth() / tilesInRow;
        double tileHeight = fieldBounds.getHeight() / tilesInRow;;
        double xLeft = fieldBounds.getX();
        double yLeft = fieldBounds.getY();

        int rowIndex = INDEX % (tilesInRow - 1);

        if (INDEX <= tilesInRow - 1) {
            xLeft += (tilesInRow - 1 - rowIndex) * tileWidth;
            yLeft += (tilesInRow - 1) * tileHeight;
        } else if (INDEX <= 2 * (tilesInRow - 1)) {
            yLeft += (tilesInRow - 1 - rowIndex) * tileHeight;
        } else if (INDEX <= 3 * (tilesInRow - 1)) {
            xLeft += (rowIndex) * tileWidth;
        } else if (INDEX <= 4 * (tilesInRow - 1)){
            xLeft += (tilesInRow - 1) * tileWidth;
            yLeft += (rowIndex) * tileHeight;
        }

        bounds = new Rectangle2D.Double(xLeft, yLeft, tileWidth, tileHeight);
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public Color getColor() {
        if (!hasOwner()) {
            return new Color(255, 255, 255);
        } else {
            return owner.PLAYER_COLOR;
        }
    }
}
