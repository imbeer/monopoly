import GameWorld.GameWorld;
import View.MainWindow;

public class Application {
    public static void main(String[] args) {
        GameWorld gw = new GameWorld();
        MainWindow window = new MainWindow(gw);
    }
}