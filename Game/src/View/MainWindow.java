package View;

import GameWorld.GameWorld;

import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {
    private final GameView view;
    private final GameWorld world;

    public MainWindow(GameWorld world) {
        this.world = world;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        view = new GameView(world, this.getWidth(), this.getHeight());
        this.add(view);

//        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
//        this.getContentPane().setCursor(blankCursor);
    }
}