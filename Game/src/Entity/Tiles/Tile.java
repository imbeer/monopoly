package Entity.Tiles;

import Entity.Players.Player;
import Entity.Street;
import Utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Tile {
    private int rent;
    public final String NAME;
    public final int PRICE;
    public final int INDEX;
    private Player owner;
    private final Street STREET;
    protected Rectangle2D bounds;

    public Tile(String name, int price, int rent, int index, Street street) {
        this.rent = rent;
        NAME = name;
        PRICE = price;
        INDEX = index;
        STREET = street;
    }
    public void action(Player player) {
        if (hasOwner()) {
            player.sendCash(owner, rent);
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

    public void upgrade() {
        owner.payCash(PRICE);
        rent *= 2;
    }

    public boolean canBeUpgraded(Player player) {
        if (player == owner) {
            return STREET.canBeUpgraded(player);
        }
        return false;
    }

    public boolean hasOwner() {
        return owner != null;
    }
    public void setOwner(Player player) {
        owner = player;
        STREET.addOwner(player);
    }
    public Player getOwner() {
        return owner;
    }

    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        drawInBounds(g, bounds);
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

    public void drawInBounds(Graphics2D g, Rectangle2D bounds) {
        String second = PRICE + " $";
        String third = rent + " $";

        g.setColor(STREET.STREET_COLOR);
        g.fill(bounds);

        float thickness = 5;
        double innerX = bounds.getX() + thickness;
        double innerY = bounds.getY() + thickness;
        double innerWidth = bounds.getWidth() - 2 * thickness;
        double innerHeight = bounds.getHeight() - 2 * thickness;
        Rectangle2D innerRect = new Rectangle2D.Double(innerX, innerY, innerWidth, innerHeight);

        g.setColor(Color.white);
        g.fill(innerRect);
        g.setColor(getColor());
        g.fill(DrawUtils.getVerticalPartOfBounds(innerRect, 0, 0.4));
        g.setColor(Color.black);
        DrawUtils.drawCenteredText(DrawUtils.NAME, NAME, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0, 0.4));
        DrawUtils.drawCenteredText(DrawUtils.PRICE, second, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0.5, 0.7));
        DrawUtils.drawCenteredText(DrawUtils.PRICE, third, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0.7, 1));
    }
}
