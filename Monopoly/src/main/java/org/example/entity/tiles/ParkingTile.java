package org.example.entity.tiles;

import org.example.entity.players.Player;
import org.example.utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Класс потомок tile. Означает парковочную клетку. Отличается отрисовкой и действием при наступлении на клетку.
 * При наступлении на клетку игрок пропускает ход.
 */
public class ParkingTile extends Tile{
    public ParkingTile(int index) {
        super("FREE PARKING", 0, 0, index, null);
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
        DrawUtils.drawCenteredText(DrawUtils.NAME, name, g, bounds);
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
