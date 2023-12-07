package Entity;

import Utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private int cash;
    private int tileIndex;
    public final String NAME;
    private boolean isBankrupt;
    private boolean isSkippingMove;
    private boolean isInJail;
    private int jailEscapeCards;
    private final Set<Integer> privateTiles;
    private final ImageIcon PLAYER_ICON = new ImageIcon("src/Assets/monopoly_player.png");
    private final Color PLAYER_COLOR;

    public Player(int startCash, String name, Color playerColor) {
        cash = startCash;
        NAME = name;
        PLAYER_COLOR = playerColor;
        tileIndex = 0;
        privateTiles = new HashSet<>();
        isBankrupt = false;
        isSkippingMove = false;
        isInJail = false;
        jailEscapeCards = 0;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }

    public void sendCash(Player receiver, int cash) {
        payCash(cash);
        receiver.cash += cash;
    }

    public void payCash(int cash) {
        this.isBankrupt = (this.cash - cash > 0);
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
        this.cash += cash;
    }

    public boolean hasJailEscapeCards() {
        return 0 < jailEscapeCards;
    }

    public void addJailEscapeCard() {
        jailEscapeCards++;
    }

    public void useJailEscapeCard() {
        jailEscapeCards--;
    }

    public void addTile(int tileIndex) {
        privateTiles.add(tileIndex);
    }

    public void draw(Graphics2D g2d, Rectangle2D bounds) {
        Image image = PLAYER_ICON.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                PLAYER_ICON.getIconWidth(),
                PLAYER_ICON.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.drawImage(image, 0, 0, null);
        DrawUtils.colorImage(bufferedImage, PLAYER_COLOR);
        imageGraphics.dispose();
        bufferedImage = DrawUtils.scale(bufferedImage, (int) bounds.getWidth(), (int) bounds.getHeight());


        g2d.drawImage(bufferedImage, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
    }
}