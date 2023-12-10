package View;

import Entity.Players.Player;
import Entity.Tiles.Tile;
import Game.DiceRoll;
import Game.GameWorld;
import Utils.Config;
import Utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameView extends JPanel {
    private GameWorld world;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final Rectangle2D FIELD_BOUNDS;
    private final Rectangle2D LEFT_MENU_BOUNDS;
    private final Rectangle2D RIGHT_MENU_BOUNDS;
    private final int TILES_IN_ROW;
    private final NextTurnButton BUTTON;

    public GameView(int panelWidth, int panelHeight, NextTurnButton button) {
        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;
        TILES_IN_ROW = Config.MAP_SIZE / 4 + 1;
        double tileWidth = getTileHeight();
        double fieldBoundHeight = tileWidth * ((double) TILES_IN_ROW);
        double leftX = DrawUtils.getNewBoundCentered(PANEL_WIDTH, fieldBoundHeight);
        double leftY = DrawUtils.getNewBoundCentered(PANEL_HEIGHT, fieldBoundHeight);
        FIELD_BOUNDS = new Rectangle2D.Double(leftX, leftY, fieldBoundHeight, fieldBoundHeight);
        LEFT_MENU_BOUNDS = new Rectangle2D.Double(50, leftY, leftX, fieldBoundHeight);
        RIGHT_MENU_BOUNDS = new Rectangle2D.Double(leftX + fieldBoundHeight, leftY, leftX, fieldBoundHeight);
        BUTTON = button;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackGround(g2d);
        drawButton(g2d);
        if (world == null) {
            return;
        }
        if (!world.isStarted()){
            return;
        }
        drawTiles(g2d);
        drawPlayers(g2d);
        drawMenu(g2d);
        drawDice(g2d);
    }

    private int getTileHeight() {
        return (int) (PANEL_HEIGHT / (TILES_IN_ROW) * 0.9);
    }

    private void drawBackGround(Graphics2D g2d){
        Color dark = new Color(69, 86, 61);
        Color light = new Color(88, 107, 77);
        for (int x = 0; x < PANEL_WIDTH; x += 100) {
            for (int y = 0; y < PANEL_HEIGHT; y+=100) {
                g2d.setColor(dark);
                g2d.fillRect(x, y, 50, 50);
                g2d.fillRect(x + 50, y + 50, 50, 50);

                g2d.setColor(light);
                g2d.fillRect(x + 50, y, 50, 50);
                g2d.fillRect(x, y + 50, 50, 50);
            }
        }
    }

    private void drawTiles(Graphics2D g2d) {
        for (Tile tile : world.getMap()) {
            tile.draw(g2d, FIELD_BOUNDS, TILES_IN_ROW);
        }
    }

    private void drawPlayers(Graphics2D g2d) {
        for (int i = 0; i < world.getPlayers().length; i++) {
            Player player = world.getPlayers()[i];
            int index = player.getTileIndex();
            Rectangle2D bounds = world.getMap()[index].getBounds();
            bounds = DrawUtils.getVerticalPartOfBounds(bounds, 0.3, 0.7);
            bounds = DrawUtils.getHorizontalPartOfBounds(bounds, 0.3, 0.7);
            double xChange = -20 + i * 10;
            player.draw(g2d, new Rectangle2D.Double(bounds.getX() + xChange, bounds.getY(), bounds.getWidth(), bounds.getHeight()));
        }
    }

    private void drawMenu(Graphics2D g2d) {
        for (int i = 0; i < world.getPlayers().length; i++) {
            Rectangle2D playerBounds = DrawUtils.getVerticalPartOfBounds(LEFT_MENU_BOUNDS, 0.25 * i, 0.25 * (i + 1));
            playerBounds = DrawUtils.getHorizontalPartOfBounds(playerBounds, 0, 0.5);


            if (i == world.getActivePlayerIndex()) {
                g2d.setColor(new Color(166, 220, 139));
                g2d.fill(playerBounds);
            }


            double yIcon = DrawUtils.getNewBoundCentered(playerBounds.getHeight(), getTileHeight()) + playerBounds.getY();
            double xIcon = LEFT_MENU_BOUNDS.getX() + (double) getTileHeight() / 5;
            Rectangle2D iconPlayerBounds = new Rectangle2D.Double(xIcon, yIcon, getTileHeight(), getTileHeight());
            Rectangle2D informationBounds = new Rectangle2D.Double(2 * xIcon + getTileHeight(), yIcon, playerBounds.getWidth() - 2 * xIcon + getTileHeight(), getTileHeight());

            Player player = world.getPlayers()[i];
            player.draw(g2d, iconPlayerBounds);
            g2d.setColor(Color.white);
            DrawUtils.drawText(DrawUtils.NAME, "Player " + (i + 1) + " name: " +  player.NAME, g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0, 0.3));
            DrawUtils.drawText(DrawUtils.PRICE, "Cash: " + player.getCash(), g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0.4, 0.7));
            DrawUtils.drawText(DrawUtils.PRICE, "Escape cards: " + player.getJailEscapeCards(), g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0.8, 1));
        }
    }

    private void drawDice(Graphics2D g2d) {
        DiceRoll roll = world.getActiveDiceRoll();
        double width = (double) (getTileHeight() * 3) / 2;
        double xLeft = DrawUtils.getNewBoundCentered(RIGHT_MENU_BOUNDS.getWidth(), width) + RIGHT_MENU_BOUNDS.getX();
        Rectangle2D diceBounds = new Rectangle2D.Double(xLeft, RIGHT_MENU_BOUNDS.getY(), width, (double) getTileHeight() / 2);
        Rectangle2D firstDice = new Rectangle2D.Double(diceBounds.getX(), diceBounds.getY(), (double) getTileHeight() / 2, (double) getTileHeight() / 2);
        Rectangle2D secondDice = new Rectangle2D.Double(diceBounds.getMaxX() - (double) getTileHeight() / 2, diceBounds.getY(), (double) getTileHeight() / 2, (double) getTileHeight() / 2);

        if (roll != null) {
            g2d.setColor(Color.white);
            g2d.fill(firstDice);
            g2d.fill(secondDice);
            g2d.setColor(Color.black);
            DrawUtils.drawCenteredText(DrawUtils.NAME, String.valueOf(roll.getFirstRoll()), g2d, firstDice);
            DrawUtils.drawCenteredText(DrawUtils.NAME, String.valueOf(roll.getSecondRoll()), g2d, secondDice);
        }
    }

    private void drawButton(Graphics2D g2d) {
        double leftX = RIGHT_MENU_BOUNDS.getMaxX() - 2 * getTileHeight();
        double leftY = RIGHT_MENU_BOUNDS.getMaxY() - (double) getTileHeight() / 2;
        Rectangle2D buttonBounds = new Rectangle2D.Double(leftX, leftY, 2 * getTileHeight(), (double) getTileHeight() / 2);
        BUTTON.draw(g2d, buttonBounds);
    }
    public GameWorld getWorld() {
        return world;
    }

    public void setWorld(GameWorld world) {
        this.world = world;
    }
}
