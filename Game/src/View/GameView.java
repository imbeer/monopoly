package View;

import Entity.Tiles.Tile;
import GameWorld.GameWorld;
import Utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameView extends JPanel {
    private final GameWorld world;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final Rectangle2D FIELD_BOUNDS;
    private final int TILES_IN_ROW;



    public GameView(GameWorld world, int panelWidth, int panelHeight) {
        this.world = world;
        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;
        TILES_IN_ROW = GameWorld.MAP_SIZE / 4 + 1;
        double fieldBoundHeight = (double) getTileHeight() * ((double) TILES_IN_ROW);
        double fieldBoundWidth = (double) getTileWidth() * ((double) TILES_IN_ROW);
        double leftX = DrawUtils.getNewBoundCentered(PANEL_WIDTH, fieldBoundWidth);
        double leftY = DrawUtils.getNewBoundCentered(PANEL_HEIGHT, fieldBoundHeight);
        FIELD_BOUNDS = new Rectangle2D.Double(leftX, leftY, fieldBoundWidth, fieldBoundHeight);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(218, 246, 214));
        g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        for (Tile tile : world.getMap()) {
            tile.draw(g2d, FIELD_BOUNDS, TILES_IN_ROW);
        }

//        world.getMap()[10].draw(g2d, FIELD_BOUNDS, TILES_IN_ROW);
        //for (int i = )
        //drawTile(g2d, world.getMap()[0], 50, 50);
    }



    private int getTileHeight() {
        return PANEL_HEIGHT / (TILES_IN_ROW);
    }

    private int getTileWidth() {
        return PANEL_WIDTH / (TILES_IN_ROW);
    }



}
