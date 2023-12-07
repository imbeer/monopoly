package View;

import Entity.Player;
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
    private final Rectangle2D MENU_BOUNDS;
    private final int TILES_IN_ROW;



    public GameView(GameWorld world, int panelWidth, int panelHeight) {
        this.world = world;
        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;
        TILES_IN_ROW = GameWorld.MAP_SIZE / 4 + 1;
        double tileWidth = (double) getTileHeight();
        double fieldBoundHeight = tileWidth * ((double) TILES_IN_ROW);
        //double fieldBoundWidth = (double) getTileWidth() * ((double) TILES_IN_ROW);
        double leftX = DrawUtils.getNewBoundCentered(PANEL_WIDTH, fieldBoundHeight);
        double leftY = DrawUtils.getNewBoundCentered(PANEL_HEIGHT, fieldBoundHeight);
        FIELD_BOUNDS = new Rectangle2D.Double(leftX, leftY, fieldBoundHeight, fieldBoundHeight);
        MENU_BOUNDS = new Rectangle2D.Double(leftX + tileWidth, leftY + tileWidth, fieldBoundHeight - 2 * tileWidth, fieldBoundHeight - 2 * tileWidth);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackGround(g2d);
        if (!world.isStarted()){
            return;
        }
        drawTiles(g2d);
        drawPlayers(g2d);
        drawMenu(g2d);
    }



    private int getTileHeight() {
        return PANEL_HEIGHT / (TILES_IN_ROW);
    }

    private void drawBackGround(Graphics2D g2d){
        g2d.setColor(new Color(123, 222, 109));
        g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void drawTiles(Graphics2D g2d) {
        for (Tile tile : world.getMap()) {
            tile.draw(g2d, FIELD_BOUNDS, TILES_IN_ROW);
        }
    }

    private void drawPlayers(Graphics2D g2d) {
        for (Player player : world.getPlayers()) {
            int index = player.getTileIndex();
            Rectangle2D bounds = world.getMap()[index].getBounds();
            bounds = DrawUtils.getHorizontalPartOfBounds(bounds, 0.3, 0.7);
            bounds = DrawUtils.getVerticalPartOfBounds(bounds, 0.3, 0.7);
            player.draw(g2d, bounds);
        }
    }

    private void drawMenu(Graphics2D g2d) {
        for (int i = 0; i < world.getPlayers().length; i++) {
            Rectangle2D playerBounds = DrawUtils.getHorizontalPartOfBounds(MENU_BOUNDS, 0.25 * i, 0.25 * (i + 1));
            playerBounds = DrawUtils.getVerticalPartOfBounds(playerBounds, 0, 0.5);

            double yIcon = DrawUtils.getNewBoundCentered(playerBounds.getHeight(), getTileHeight()) + playerBounds.getY();
            double xIcon = MENU_BOUNDS.getX() + (double) getTileHeight() / 5;
            Rectangle2D iconPlayerBounds = new Rectangle2D.Double(xIcon, yIcon, getTileHeight(), getTileHeight());
            Rectangle2D informationBounds = new Rectangle2D.Double(2 * xIcon + getTileHeight(), yIcon, playerBounds.getWidth() - 2 * xIcon + getTileHeight(), getTileHeight());

            Player player = world.getPlayers()[i];
            player.draw(g2d, iconPlayerBounds);
            DrawUtils.drawText(DrawUtils.NAME, player.NAME, g2d, DrawUtils.getHorizontalPartOfBounds(informationBounds, 0, 0.3));
            DrawUtils.drawText(DrawUtils.PRICE, String.valueOf(player.getCash()), g2d, DrawUtils.getHorizontalPartOfBounds(informationBounds, 0.4, 0.7));
        }
    }
}
