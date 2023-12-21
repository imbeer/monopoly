package entity.players;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Bot extends Player{
    private final ImageIcon BOT_ICON = new ImageIcon("src/Assets/monopoly_bot.png");

    public Bot(int cash, int tileIndex, String name, Color playerColor) {
        super(cash, tileIndex, name, playerColor);
    }

    @Override
    public boolean ask(String message, String title) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public boolean endTurn() {
        return true;
    }

    @Override
    public Image getImage() {
        return BOT_ICON.getImage();
    }
}
