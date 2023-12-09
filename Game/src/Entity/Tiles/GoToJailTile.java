package Entity.Tiles;

import Entity.Players.Player;
import Game.JailSystem;
import Utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GoToJailTile extends Tile{
    private final JailSystem SYSTEM;

    public GoToJailTile(int index, JailSystem system) {
        super("GO TO JAIL", 0, 0, index);
        SYSTEM = system;
    }

    @Override
    public void action(Player player) {
        SYSTEM.goToJail(player);
    }

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
        double tileHeight = fieldBounds.getHeight() / tilesInRow;
        double xLeft = fieldBounds.getX() + (tilesInRow - 1) * tileWidth;
        double yLeft = fieldBounds.getY();

        bounds = new Rectangle2D.Double(xLeft, yLeft, tileWidth, tileHeight);
    }
}
