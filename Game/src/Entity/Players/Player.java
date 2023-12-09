package Entity.Players;

import Utils.DrawUtils;
import View.MessageBoxProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player {
    public final String NAME;
    private int cash;
    private int tileIndex;
    private boolean isBankrupt;
    private boolean isSkippingMove;
    private boolean isInJail;
    private int jailEscapeCards;
    private final ImageIcon PLAYER_ICON = new ImageIcon("src/Assets/monopoly_player.png");
    public final Color PLAYER_COLOR;

    public Player(int cash, int tileIndex, String name, Color playerColor) {
        this.cash = cash;
        this.NAME = name;
        this.PLAYER_COLOR = playerColor;
        this.tileIndex = tileIndex;
        this.isBankrupt = false;
        this.isSkippingMove = false;
        this.isInJail = false;
        this.jailEscapeCards = 0;
    }

    public boolean ask(String message, String title) {
        return MessageBoxProxy.getAnswer(message, title);
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

    public void sendCash(Player receiver, int cash) {
        payCash(cash);
        receiver.addCash(cash);
    }

    public void payCash(int cash) {
        if (this.cash - cash < 0) {
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
        DrawUtils.colorImage(bufferedImage, PLAYER_COLOR);
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