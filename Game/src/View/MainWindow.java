package View;

import Entity.Tiles.Tile;
import Game.GameWorld;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private GameView view;
    public MainWindow(GameWorld gameWorld) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        view = new GameView(gameWorld, this.getWidth(), this.getHeight());
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
                for (Tile tile : gameWorld.getMap()) {
                    if (tile.getBounds().contains(x, y)) {
                        MessageBoxProxy.drawTileInformation(tile);
                    }
                }
            }
        });
    }

    public GameView getView() {
        return view;
    }

    public void setView(GameView view) {
        this.view = view;
    }
}