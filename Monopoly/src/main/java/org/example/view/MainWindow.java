package org.example.view;

import org.example.entity.tiles.Tile;
import org.example.game.*;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private GameView view;
    public final GameWorld gameWorld;
    public final Game game;
    public final NextTurnButton nextTurnButton;
    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        nextTurnButton = new NextTurnButton();
        view = new GameView(this.getWidth(), this.getHeight(), nextTurnButton);
        game = new Game(view);
        gameWorld = game.getWorld();
        view.setWorld(gameWorld);

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
                if (nextTurnButton.getBounds().contains(x, y)) {
                    do {
                        game.nextTurn();
                    } while (game.getWorld().getActivePlayer().endTurn());
                    return;
                }
                for (Tile tile : gameWorld.getMap()) {
                    if (tile.getBounds().contains(x, y)) {
                        game.showTile(tile);
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