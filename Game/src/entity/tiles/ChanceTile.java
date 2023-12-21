package entity.tiles;

import entity.players.Player;
import game.JailSystem;
import utils.DrawUtils;
import view.MessageBoxProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class ChanceTile extends Tile {
    private final ImageIcon CHANCE_ICON = new ImageIcon("src/Assets/monopoly_chance_tile.png");

    @FunctionalInterface
    private interface Chance {
        void action(Player player, JailSystem system);
    }

    private final JailSystem SYSTEM;

    private static final Chance[] CHANCES = new Chance[]{
            (player, system) -> {
                MessageBoxProxy.showMessage("random says - u have an opportunity to escape from jail", "title");
                player.addJailEscapeCard();
            },
            (player, system) -> {
                int cash = ThreadLocalRandom.current().nextInt(5, 21) * 10;
                MessageBoxProxy.showMessage("random says - pay " + cash + " taxes please", "title");
                player.payCash(cash);
            },
            (player, system) -> {
                MessageBoxProxy.showMessage("random says - go to jail", "title");
                system.goToJail(player);
            },
            (player, system) -> {
                int cash = ThreadLocalRandom.current().nextInt(5, 21) * 10;
                MessageBoxProxy.showMessage("random says - free " + cash + " money", "title");
                player.addCash(cash);
            }

    };


    public ChanceTile(int index, JailSystem system) {
        super("CHANCE", 0, 0, index, null);
        SYSTEM = system;
    }

    public void action(Player player) {
        CHANCES[ThreadLocalRandom.current().nextInt(0, CHANCES.length)].action(player, SYSTEM);
    }

    @Override
    public void drawInBounds(Graphics2D g, Rectangle2D bounds) {
        g.setColor(new Color(255, 243, 191));
        g.fill(bounds);
        Image image = CHANCE_ICON.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                CHANCE_ICON.getIconWidth(),
                CHANCE_ICON.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.drawImage(image, 0, 0, null);
        DrawUtils.colorImage(bufferedImage, Color.black);
        imageGraphics.dispose();
        bufferedImage = DrawUtils.scale(bufferedImage, (int) bounds.getWidth(), (int) bounds.getHeight());

        g.drawImage(bufferedImage, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
    }

}