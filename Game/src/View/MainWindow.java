package View;

import Entity.Tiles.Tile;
import Game.*;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private GameView view;
    public final GameWorld WORLD;
    public final Game GAME;
    public final NextTurnButton BUTTON;
    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        BUTTON = new NextTurnButton();
        view = new GameView(this.getWidth(), this.getHeight(), BUTTON);
        GAME = new Game(view);
        WORLD = GAME.getWorld();
        view.setWorld(WORLD);

        this.add(view);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (MessageBoxProxy.getAnswer("exit?", "")) {
                        dispose();
                    }
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (BUTTON.getBounds().contains(x, y)) {
                    do {
                        GAME.nextTurn();
                    } while (GAME.getWorld().getActivePlayer().endTurn());
                    return;
                }
                for (Tile tile : WORLD.getMap()) {
                    if (tile.getBounds().contains(x, y)) {
                        MessageBoxProxy.drawTileInformation(tile);
                    }
                }
            }
        });
        setVisible(true);
    }

    public GameView getView() {
        return view;
    }

    public void setView(GameView view) {
        this.view = view;
    }
}