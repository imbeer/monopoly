package org.example.entity.tiles;

import org.example.entity.players.Player;
import org.example.entity.Street;
import org.example.utils.DrawUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Базовый класс клетки с карты. Клетку можно купить, она знает своего хозяина, свою цену, арендную плату, координату, улицу, на которой расположена и название.
 */
public class Tile {
    /**
     * Название клетки.
     */
    public final String name;
    /**
     * Цена покупки и улучшения клетки
     */
    public final int price;
    /**
     * Координата клетки
     */
    public final int index;
    /**
     * Улица, которой принадлежит клетка
     */
    private final Street street;
    /**
     * Арендная плата при попадании на клетку
     */
    private int rent;
    /**
     * Текущий хозяин клетки
     */
    private Player owner;
    protected Rectangle2D bounds;

    /**
     * Создает экземпляр клетки с заданными параметрами.
     * @param name имя
     * @param price цена покупки и улучшения
     * @param rent арендная плата
     * @param index индекс
     * @param street улица
     */
    public Tile(String name, int price, int rent, int index, Street street) {
        this.rent = rent;
        this.name = name;
        this.price = price;
        this.index = index;
        this.street = street;
    }

    /**
     * Метод вызывается при попадании на клетку игроком. Выполняет следующие инструкции:
     * <ul>
     *     <li>Проверяются координаты игрока и клетки для избежания ошибки</li>
     *     <li>Если у клетки есть хозяин, игрок платит ему арендную плату</li>
     *     <li>Если у клетки нет хозяина, игроку предлагается купить клетку</li>
     *     <li>Если игрок покупает клетку, он становится хозяином</li>
     * </ul>
     * @param player игрок, попавший на клетку
     */
    public void action(Player player) {
        if (player.getTileIndex() != index) {
            return;
        }
        if (hasOwner()) {
            player.sendCash(owner, rent);
        } else {
            if (player.getCash() >= price) {
                boolean answer = player.ask("Do you want to buy " + name + "?", "BUY");
                if (answer) {
                    player.payCash(price);
                    setOwner(player);
                }
            }
        }
    }

    /**
     * Метод, позволяющий <b>улучшить</b> клетку. С хозяина списывается цена, арендная плата повышается вдвое.
     */
    public void upgrade() {
        owner.payCash(price);
        rent *= 2;
    }

    /**
     * Метод показывает, может ли клетка быть улучшена игроком. Для этого игроку необходимо быть хозяином клетки и иметь все клетки на улице.
     * @param player игрок, для которого спрашивается, можно ли улучшить клетку
     * @return true, если можно, false, если нельзя
     */
    public boolean canBeUpgraded(Player player) {
        if (player == owner) {
            return street.canBeUpgraded(player);
        }
        return false;
    }

    public boolean hasOwner() {
        return owner != null;
    }
    public void setOwner(Player player) {
        owner = player;
        street.addOwner(player, index);
    }
    public Player getOwner() {
        return owner;
    }

    public int getRent() {
        return rent;
    }

    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        drawInBounds(g, bounds);
    }

    /**
     * Метод, заполняющий физические координаты клетки при отрисовке. Координаты высчитываются по индексу клетки и всему периметру поля.
     * Для этого определяется на какой из четырех сторон находится клетка, а затем присваиваются нужные координаты.
     * @param fieldBounds периметр поля
     * @param tilesInRow количество клеток на одной стороне
     */
    protected void fillTileBounds(Rectangle2D fieldBounds, int tilesInRow) {
        double tileWidth = fieldBounds.getWidth() / tilesInRow;
        double tileHeight = fieldBounds.getHeight() / tilesInRow;;
        double xLeft = fieldBounds.getX();
        double yLeft = fieldBounds.getY();

        int rowIndex = index % (tilesInRow - 1);

        if (index <= tilesInRow - 1) {
            xLeft += (tilesInRow - 1 - rowIndex) * tileWidth;
            yLeft += (tilesInRow - 1) * tileHeight;
        } else if (index <= 2 * (tilesInRow - 1)) {
            yLeft += (tilesInRow - 1 - rowIndex) * tileHeight;
        } else if (index <= 3 * (tilesInRow - 1)) {
            xLeft += (rowIndex) * tileWidth;
        } else if (index <= 4 * (tilesInRow - 1)){
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
            return owner.playerColor;
        }
    }

    public void drawInBounds(Graphics2D g, Rectangle2D bounds) {
        String second = price + " $";
        String third = rent + " $";

        g.setColor(street.streetColor);
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
        DrawUtils.drawCenteredText(DrawUtils.NAME, name, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0, 0.4));
        DrawUtils.drawCenteredText(DrawUtils.PRICE, second, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0.5, 0.7));
        DrawUtils.drawCenteredText(DrawUtils.PRICE, third, g, DrawUtils.getVerticalPartOfBounds(innerRect, 0.7, 1));
    }
}
