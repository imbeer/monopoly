package org.example;
import org.example.view.MainWindow;

public class Application {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.game.start();
        window.getView().repaint();
    }
}