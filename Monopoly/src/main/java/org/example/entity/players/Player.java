package org.example.entity.players;

import org.example.utils.DrawUtils;
import org.example.view.MessageBoxProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Класс игрока. Игрок знает свой денежный счет, количество карточек выхода из тюрьмы, координаты на поле и несколько статусов состояния.
 */
public class Player {
    private final ImageIcon PLAYER_ICON = new ImageIcon("src/main/resources/monopoly_player.png");
    /**
     * Имя игрока
     */
    public final String name;
    /**
     * Текущий денежный счет игрока
     */
    private int cash;
    /**
     * Текущие координаты игрока на поле
     */
    private int tileIndex;
    /**
     * Состояние, показывающее обанкротился ли игрок
     */
    private boolean isBankrupt;
    /**
     * Состояние, показывающее пропускает ли ход игрок
     */
    private boolean isSkippingMove;
    /**
     * Состояние, показывающее, в тюрьме ли игрок
     */
    private boolean isInJail;
    /**
     * Количество карт бесплатного выхода из тюрьмы
     */
    private int jailEscapeCards;
    public final Color playerColor;

    /**
     * Создает экземпляр класса игрока
     * @param cash начальное количество денег
     * @param tileIndex начальные координаты
     * @param name имя игрока
     * @param playerColor его цвет
     */

    public Player(int cash, int tileIndex, String name, Color playerColor) {
        this.cash = cash;
        this.name = name;
        this.playerColor = playerColor;
        this.tileIndex = tileIndex;
        this.isBankrupt = false;
        this.isSkippingMove = false;
        this.isInJail = false;
        this.jailEscapeCards = 0;
    }

    /**
     * Метод выдает решение игрока по поводу того или иного случая. Используется в качестве ключевой механики игры.
     * @param message сообщение, которое увидит игрок в качестве вопроса
     * @param title название окошка
     * @return true, если игрок согласен с вопросом, false, если не согласен
     */
    public boolean ask(String message, String title) {
        return MessageBoxProxy.getAnswer(message, title);
    }

    /**
     * Метод заканчивает ход игрока.
     * @return true, если ход нужно закончить автоматически, false, если ждать ответа игрока.
     */
    public boolean endTurn() {
        return false;
    }

    public int getCash() {
        return cash;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }

    /**
     * Метод, который отправляет деньги другому игроку
     * @param receiver игрок получатель денег
     * @param cash количество денег
     */
    public void sendCash(Player receiver, int cash) {
        if (isBankrupt) {
            return;
        }
        payCash(cash);
        receiver.addCash(cash);
    }

    /**
     * Метод, который отнимает у игрока количество денег. Если деньги заканчиваются, то игрок становится банкротом.
     * @param cash деньги, которые нужно снять
     */
    public void payCash(int cash) {
        if (this.cash - cash <= 0) {
            this.isBankrupt = true;
        }
        this.cash -= cash;
    }

    public void setSkippingMove(boolean skippingMove) {
        isSkippingMove = skippingMove;
    }

    public boolean isSkippingMove() {
        return isSkippingMove;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public void addCash(int cash) {
        if (!isBankrupt) {
            this.cash += cash;
        }
    }

    public boolean hasJailEscapeCards() {
        return 0 < jailEscapeCards;
    }

    public int getJailEscapeCards() {
        return jailEscapeCards;
    }

    public void addJailEscapeCard() {
        jailEscapeCards++;
    }

    /**
     * Метод, который использует одну из карточек бесплатного выхода из тюрьмы
     */
    public void useJailEscapeCard() {
        jailEscapeCards--;
    }

    public void draw(Graphics2D g2d, Rectangle2D bounds) {
        Image image = getImage();
        BufferedImage bufferedImage = new BufferedImage(
                PLAYER_ICON.getIconWidth(),
                PLAYER_ICON.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.drawImage(image, 0, 0, null);
        DrawUtils.colorImage(bufferedImage, playerColor);
        imageGraphics.dispose();
        bufferedImage = DrawUtils.scale(bufferedImage, (int) bounds.getWidth(), (int) bounds.getHeight());


        g2d.drawImage(bufferedImage, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public Image getImage() {
        return PLAYER_ICON.getImage();
    }
}