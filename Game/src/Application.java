import View.MainWindow;

public class Application {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.GAME.start();
        window.getView().repaint();
    }
}