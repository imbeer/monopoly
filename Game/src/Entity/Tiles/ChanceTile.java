package Entity.Tiles;

import Entity.Players.Player;
import Game.JailSystem;
import Utils.DrawUtils;
import View.MessageBoxProxy;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

public class ChanceTile extends Tile {
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
                MessageBoxProxy.showMessage("random says - pay taxes please", "title");
                player.payCash(ThreadLocalRandom.current().nextInt(5, 21) * 10);
            },
            (player, system) -> {
                MessageBoxProxy.showMessage("random says - go to jail", "title");
                system.goToJail(player);
            },
            (player, system) -> {
                MessageBoxProxy.showMessage("random says - free money", "title");
                player.addCash(ThreadLocalRandom.current().nextInt(5, 21) * 10);
            }

    };


    public ChanceTile(int index, JailSystem system) {
        super("CHANCE", 0, 0, index);
        SYSTEM = system;
    }

    public void action(Player player) {
        CHANCES[ThreadLocalRandom.current().nextInt(0, CHANCES.length)].action(player, SYSTEM);
    }

    @Override
    public void draw(Graphics2D g, Rectangle2D fieldBounds, int tilesInRow) {
        if (bounds == null) {
            fillTileBounds(fieldBounds, tilesInRow);
        }
        g.setColor(new Color(255, 194, 36));
        g.fill(bounds);
        g.setColor(new Color(0, 0, 0));
        DrawUtils.drawText(DrawUtils.NAME, NAME, g, bounds);
    }

}