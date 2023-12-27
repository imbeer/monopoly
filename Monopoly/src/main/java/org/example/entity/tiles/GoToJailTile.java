package org.example.entity.tiles;

import org.example.entity.players.Player;
import org.example.game.JailSystem;
import org.example.utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Класс потомок tile. Означает клетку, отправляющую в тюрьму. Отличается отрисовкой и действием при наступлении на клетку.
 * Отправляет игрока в тюрьму с помощью Jailsystem при наступлении на клетку.
 */
public class GoToJailTile extends Tile{
    private final JailSystem jailSystem;

    public GoToJailTile(int index, JailSystem system) {
        super("GO TO JAIL", 0, 0, index, null);
        this.jailSystem = system;
    }

    @Override
    public void action(Player player) {
        jailSystem.goToJail(player);
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
        double tileHeight = fieldBounds.getHeight() / tilesInRow;
        double xLeft = fieldBounds.getX() + (tilesInRow - 1) * tileWidth;
        double yLeft = fieldBounds.getY();

        bounds = new Rectangle2D.Double(xLeft, yLeft, tileWidth, tileHeight);
    }
}
