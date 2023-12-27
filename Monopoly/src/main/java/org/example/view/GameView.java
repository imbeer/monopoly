package org.example.view;

import org.example.entity.players.Player;
import org.example.entity.tiles.Tile;
import org.example.game.DiceRoll;
import org.example.game.GameWorld;
import org.example.utils.Config;
import org.example.utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Компонент, отвечающий за отрисовку всей игры. Наследуется от JPanel. Знает информацию об игровом мире.
 */
public class GameView extends JPanel {
    /**
     * Игровой мир
     */
    private GameWorld world;
    private final int panelWidth;
    private final int panelHeight;
    /**
     * Периметр поля клеточек
     */
    private final Rectangle2D fieldBounds;
    /**
     * Левая часть панели до поля
     */
    private final Rectangle2D leftMenuBounds;
    /**
     * Правая часть панели после поля
     */
    private final Rectangle2D rightMenuBounds;
    /**
     * Количество клеток в ряду
     */
    private final int tilesInRow;
    /**
     * Кнопка следующего хода
     */
    private final NextTurnButton nextTurnButton;

    public GameView(int panelWidth, int panelHeight, NextTurnButton button) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        tilesInRow = Config.MAP_SIZE / 4 + 1;
        double tileWidth = getTileHeight();
        double fieldBoundHeight = tileWidth * ((double) tilesInRow);
        double leftX = DrawUtils.getNewBoundCentered(this.panelWidth, fieldBoundHeight);
        double leftY = DrawUtils.getNewBoundCentered(this.panelHeight, fieldBoundHeight);
        fieldBounds = new Rectangle2D.Double(leftX, leftY, fieldBoundHeight, fieldBoundHeight);
        leftMenuBounds = new Rectangle2D.Double(50, leftY, leftX, fieldBoundHeight);
        rightMenuBounds = new Rectangle2D.Double(leftX + fieldBoundHeight, leftY, leftX, fieldBoundHeight);
        nextTurnButton = button;
    }

    /**
     * Переопределенный метод, отвечающий за отрисовку компонента
     * @param g
     */
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
        return (int) (panelHeight / (tilesInRow) * 0.9);
    }

    /**
     * Отрисовывает фон из клеточек
     * @param g2d
     */
    private void drawBackGround(Graphics2D g2d){
        Color dark = new Color(69, 86, 61);
        Color light = new Color(88, 107, 77);
        for (int x = 0; x < panelWidth; x += 100) {
            for (int y = 0; y < panelHeight; y+=100) {
                g2d.setColor(dark);
                g2d.fillRect(x, y, 50, 50);
                g2d.fillRect(x + 50, y + 50, 50, 50);

                g2d.setColor(light);
                g2d.fillRect(x + 50, y, 50, 50);
                g2d.fillRect(x, y + 50, 50, 50);
            }
        }
    }

    /**
     * Рисует все клетки из игрового мира
     * @param g2d
     */
    private void drawTiles(Graphics2D g2d) {
        for (Tile tile : world.getMap()) {
            tile.draw(g2d, fieldBounds, tilesInRow);
        }
    }

    /**
     * Рисует всех игроков из игрового мира
     * @param g2d
     */
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

    /**
     * Рисует меню (список игроков)
     * @param g2d
     */
    private void drawMenu(Graphics2D g2d) {
        for (int i = 0; i < world.getPlayers().length; i++) {
            Rectangle2D playerBounds = DrawUtils.getVerticalPartOfBounds(leftMenuBounds, 0.25 * i, 0.25 * (i + 1));
            playerBounds = DrawUtils.getHorizontalPartOfBounds(playerBounds, 0, 0.5);


            if (i == world.getActivePlayerIndex()) {
                g2d.setColor(new Color(166, 220, 139));
                g2d.fill(playerBounds);
            }


            double yIcon = DrawUtils.getNewBoundCentered(playerBounds.getHeight(), getTileHeight()) + playerBounds.getY();
            double xIcon = leftMenuBounds.getX() + (double) getTileHeight() / 5;
            Rectangle2D iconPlayerBounds = new Rectangle2D.Double(xIcon, yIcon, getTileHeight(), getTileHeight());
            Rectangle2D informationBounds = new Rectangle2D.Double(2 * xIcon + getTileHeight(), yIcon, playerBounds.getWidth() - 2 * xIcon + getTileHeight(), getTileHeight());

            Player player = world.getPlayers()[i];
            player.draw(g2d, iconPlayerBounds);
            g2d.setColor(Color.white);
            DrawUtils.drawText(DrawUtils.NAME, "Player " + (i + 1) + " name: " +  player.name, g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0, 0.3));
            DrawUtils.drawText(DrawUtils.PRICE, "Cash: " + player.getCash(), g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0.4, 0.7));
            DrawUtils.drawText(DrawUtils.PRICE, "Escape cards: " + player.getJailEscapeCards(), g2d, DrawUtils.getVerticalPartOfBounds(informationBounds, 0.8, 1));
        }
    }

    /**
     * Рисует последние выпавшие кубики из игрового мира
     * @param g2d
     */
    private void drawDice(Graphics2D g2d) {
        DiceRoll roll = world.getActiveDiceRoll();
        double width = (double) (getTileHeight() * 3) / 2;
        double xLeft = DrawUtils.getNewBoundCentered(rightMenuBounds.getWidth(), width) + rightMenuBounds.getX();
        Rectangle2D diceBounds = new Rectangle2D.Double(xLeft, rightMenuBounds.getY(), width, (double) getTileHeight() / 2);
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

    /**
     * Рисует кнопку следующего хода
     * @param g2d
     */
    private void drawButton(Graphics2D g2d) {
        double leftX = rightMenuBounds.getMaxX() - 2 * getTileHeight();
        double leftY = rightMenuBounds.getMaxY() - (double) getTileHeight() / 2;
        Rectangle2D buttonBounds = new Rectangle2D.Double(leftX, leftY, 2 * getTileHeight(), (double) getTileHeight() / 2);
        nextTurnButton.draw(g2d, buttonBounds);
    }

    public void setWorld(GameWorld world) {
        this.world = world;
    }
}
