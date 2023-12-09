package Game;

import Entity.Players.Player;
import Utils.Config;
import View.GameView;
import View.MessageBoxProxy;

import javax.swing.*;

public class Game {
    private final GameView view;
    private final GameWorld world;
    private final JailSystem jailSystem;

    public Game(GameView view) {
        jailSystem = new JailSystem();
        world = new GameWorld(jailSystem);
        this.view = view;
    }

    public void start() {
        world.start();
    }

    public void nextTurn() {
        world.nextPlayer();
        Player activePlayer = world.getActivePlayer();
        view.repaint();

        int count = 0;
        do {
            if (activePlayer.isSkippingMove()) {
                activePlayer.setSkippingMove(false);
                return;
            }

            DiceRoll roll = null;
            if (activePlayer.isInJail()) {
                roll = handleJail();
                view.repaint();
                if (activePlayer.isInJail()) {
                    return;
                }
            }
            if (roll == null) {
                roll = roll();
            }

            movePlayer(roll);
            view.repaint();
            if (roll.isDouble()) {
                count++;
            } else {
                return;
            }

            if (activePlayer.isInJail()) {
                view.repaint();
                return;
            }

        } while (count < 3);

        jailSystem.goToJail(activePlayer);
        view.repaint();
    }

    public void movePlayer(DiceRoll roll) {
        int step = roll.getSum();
        Player activePlayer = world.getActivePlayer();

        int newIndex = (activePlayer.getTileIndex() + step) % Config.MAP_SIZE;

        if (newIndex < activePlayer.getTileIndex()) {
            activePlayer.addCash(Config.ROUND_CASH);
        }

        activePlayer.setTileIndex(newIndex);
        view.repaint();
        world.getMap()[newIndex].action(activePlayer);
    }

    private DiceRoll handleJail() {
        Player activePlayer = world.getActivePlayer();
        if (activePlayer.hasJailEscapeCards()) {
            boolean answer = activePlayer.ask("Do you want to spend your jail card?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.useJailEscapeCard();
                return null;
            }
        }
        if (activePlayer.getCash() >= Config.JAIL_CASH) {
            boolean answer = activePlayer.ask("Spend " + Config.JAIL_CASH + " money to escape jail?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.payCash(Config.JAIL_CASH);
                return null;
            }
        }

        DiceRoll roll = roll();
        if (roll.isDouble()) {
            MessageBoxProxy.showMessage("Successfully escape jail because of good luck", "Escape Completed");
            activePlayer.setInJail(false);
            return roll;
        } else {
            MessageBoxProxy.showMessage("Unfortunately lack of luck", "Escape Failed");
            return null;
        }
    }

    private DiceRoll roll() {
        DiceRoll roll = new DiceRoll();
        world.setActiveDiceRoll(roll);
        view.repaint();
        return roll;
    }

    public GameWorld getWorld() {
        return world;
    }
}