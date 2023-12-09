package Entity.Tiles;

import Entity.Players.Player;
import Utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ParkingTile extends Tile{
    public ParkingTile(int index) {
        super("FREE PARKING", 0, 0, index);
    }

    public void action(Player player) {
        player.setSkippingMove(true);
    }

    @Override
    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        g.setColor(new Color(255, 255, 255));
        g.fill(bounds);
        g.setColor(new Color(0, 0, 0));
        DrawUtils.drawText(DrawUtils.NAME, NAME, g, bounds);
    }

    @Override
    protected void fillTileBounds(Rectangle2D fieldBounds, int tilesInRow) {
        double tileWidth = fieldBounds.getWidth() / tilesInRow;
        double tileHeight = fieldBounds.getHeight() / tilesInRow;;
        double xLeft = fieldBounds.getX();
        double yLeft = fieldBounds.getY();

        bounds = new Rectangle2D.Double(xLeft, yLeft, tileWidth, tileHeight);
    }
}
