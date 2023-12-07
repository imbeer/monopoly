import GameWorld.GameWorld;
import View.GameView;
import View.MainWindow;
import View.MessageBoxProxy;

public class Game {
    GameView view;
    GameWorld world;
    MainWindow window;

    public Game() {
        world = new GameWorld();
        window = new MainWindow(world);
        view = window.getView();
    }

    public void start() {
        while (MessageBoxProxy.getAnswer("new game?", "HI")) {
            world.start(view);
            while (world.hasNotBankruptPlayers()) {
                world.nextTurn();
            }
            MessageBoxProxy.showMessage(world.getWinner().NAME, "winner:");
        }
        window.dispose();
    }
}
