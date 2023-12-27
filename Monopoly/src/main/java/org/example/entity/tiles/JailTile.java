package org.example.entity.tiles;

import org.example.entity.players.Player;
import org.example.utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class JailTile extends Tile{
    public JailTile(int index) {
        super("JAIL", 0, 0, index, null);
    }

    @Override
    public void action(Player player) {}

    @Override
    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        g.setColor(new Color(255, 255, 255));
        g.fill(bounds);
        g.setColor(new Color(0, 0, 0));
        DrawUtils.drawCenteredText(DrawUtils.NAME, NAME, g, bounds);
    }
    @Override
    protected void fillTileBounds(Rectangle2D fieldBounds, int tilesInRow) {
        double tileWidth = fieldBounds.getWidth() / tilesInRow;
        double tileHeight = fieldBounds.getHeight() / tilesInRow;;
        double xLeft = fieldBounds.getX();
        double yLeft = fieldBounds.getY() + (tilesInRow - 1) * tileHeight;

        bounds = new Rectangle2D.Double(xLeft, yLeft, tileWidth, tileHeight);
    }
}
