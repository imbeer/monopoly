package Game;

import Entity.Players.Player;
import View.GameView;
import View.MainWindow;
import View.MessageBoxProxy;

public class Game {
    private final GameView view;
    private final GameWorld world;
    private final MainWindow window;
    private final JailSystem jailSystem;

    public Game() {
        jailSystem = new JailSystem();
        world = new GameWorld(jailSystem);
        window = new MainWindow(world);
        view = window.getView();
    }

    public void start() {
        while (MessageBoxProxy.getAnswer("new game?", "HI")) {
            world.start();
            while (world.hasNotBankruptPlayers()) {
                nextTurn();
            }
            MessageBoxProxy.showMessage(world.getWinner().NAME, "winner:");
        }
        window.dispose();
    }

    public void nextTurn() {
        world.nextPlayer();
        Player activePlayer = world.getActivePlayer();
        view.repaint();

        if (activePlayer.isSkippingMove()) {
            activePlayer.setSkippingMove(false);
            return;
        }
        int count = 0;
        do {
            DiceRoll roll = null;
            if (activePlayer.isInJail()) {
                roll = handleJail();
                view.repaint();
                if (activePlayer.isInJail()) {
                    return;
                }
            }
            if (roll == null) {
                roll = new DiceRoll();
            }

            movePlayer(roll);
            view.repaint();
            if (roll.isDouble()) {
                count++;
            } else {
                return;
            }
        } while (count < 3);

        jailSystem.goToJail(activePlayer);
        view.repaint();
    }

    public void movePlayer(DiceRoll roll) {
        int step = roll.getSum();
        Player activePlayer = world.getActivePlayer();

        int newIndex = (activePlayer.getTileIndex() + step) % GameWorld.MAP_SIZE;

        if (newIndex < activePlayer.getTileIndex()) {
            activePlayer.addCash(GameWorld.ROUND_CASH);
        }

        activePlayer.setTileIndex(newIndex);
        view.repaint();
        world.getMap()[newIndex].action(activePlayer);
    }

    private DiceRoll handleJail() {
        Player activePlayer = world.getActivePlayer();
        if (activePlayer.hasJailEscapeCards()) {
            boolean answer = activePlayer.ask("cards?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.useJailEscapeCard();
                return null;
            }
        }
        if (activePlayer.getCash() >= GameWorld.JAIL_CASH) {
            boolean answer = activePlayer.ask("money?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.payCash(GameWorld.JAIL_CASH);
                return null;
            }
        }

        DiceRoll roll = new DiceRoll();
        if (roll.isDouble()) {
            MessageBoxProxy.showMessage("YAY", "");
            activePlayer.setInJail(false);
            return roll;
        } else {
            MessageBoxProxy.showMessage("NAY", "");
            return null;
        }
    }

}
