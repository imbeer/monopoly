package GameWorld;


import Entity.Player;
import Entity.Tiles.*;
import View.GameView;
import View.MessageBoxProxy;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;


public class GameWorld {
    private Tile[] map;
    private Player[] players;
    private int playerIndex = -1;
    private boolean isStarted = false;
    public static final int START_CASH = 10000;
    public static final int ROUND_CASH = 200;
    public static final int JAIL_CASH = 100;
    public static final int MAP_SIZE = 25;
    public static final int START_INDEX = 0;
    public static final int JAIL_INDEX = 6;
    public static final int PARK_INDEX = 12;
    public static final int GO_TO_JAIL_INDEX = 18;
    private final JailSystem JAIL_SYSTEM = new JailSystem(this);
    private GameView view;

    public GameWorld() {
    }

    public void start(GameView view) {
        fillMap();
        fillPlayers();
        isStarted = true;
        this.view = view;
        view.repaint();
    }

    private void nextPlayer() {
        playerIndex++;
        playerIndex = playerIndex % 4;
    }

    public void movePlayer(DiceRoll roll) {
        int step = roll.getSum();
        Player activePlayer = getActivePlayer();

        int newIndex = (activePlayer.getTileIndex() + step) % MAP_SIZE;

        if (newIndex < activePlayer.getTileIndex()) {
            activePlayer.addCash(ROUND_CASH);
        }

        activePlayer.setTileIndex(newIndex);
        view.repaint();
        map[newIndex].action(activePlayer);
    }

    private Player getActivePlayer() {
        return players[playerIndex];
    }

    public void nextTurn() {
        nextPlayer();
        Player activePlayer = getActivePlayer();
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

        goToJail(activePlayer);
    }

    public void goToJail(Player player) {
        player.setInJail(true);
        player.setTileIndex(JAIL_INDEX);
        MessageBoxProxy.showMessage("GO TO HORNY JAIL", "uh??");
        view.repaint();
    }

    private DiceRoll handleJail() {
        Player activePlayer = getActivePlayer();
        if (activePlayer.hasJailEscapeCards()) {
            boolean answer = MessageBoxProxy.getAnswer("cards?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.useJailEscapeCard();
                return null;
            }
        }
        if (activePlayer.getCash() >= JAIL_CASH) {
            boolean answer = MessageBoxProxy.getAnswer("money?", "");
            if (answer) {
                activePlayer.setInJail(false);
                activePlayer.payCash(JAIL_CASH);
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

    private void fillMap() {
        map = new Tile[MAP_SIZE];
        map[START_INDEX] = new StartTile(START_INDEX);
        map[JAIL_INDEX] = new JailTile(JAIL_INDEX);
        map[PARK_INDEX] = new ParkingTile(PARK_INDEX);
        map[GO_TO_JAIL_INDEX] = new GoToJailTile(GO_TO_JAIL_INDEX, JAIL_SYSTEM);

        for (int i = 0; i <= MAP_SIZE - MAP_SIZE / 4; i += MAP_SIZE / 4) {
            int index = i + ThreadLocalRandom.current().nextInt(1, MAP_SIZE / 4);
            map[index] = new ChanceTile(index, JAIL_SYSTEM);
        }

        for (int index = 0; index < MAP_SIZE; index++) {
            if (map[index] == null) {
                map[index] = new Tile("Lorem Ipsum " + index, index * 10, index * 5, index);
            }
        }
    }

    private void fillPlayers() {
        players = new Player[4];
        Color[] colors = new Color[]{
                new Color(246, 91, 91),
                new Color(91, 246, 94),
                new Color(246, 210, 91),
                new Color(91, 145, 246)
        };

        for (int index = 0; index < players.length; index++) {
            String name = MessageBoxProxy.getStringAnswer("name?", "Player " + (index + 1));
            players[index] = new Player(START_CASH, START_INDEX, name, colors[index]);
        }
    }

    public Tile[] getMap() {
        return map;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean hasNotBankruptPlayers() {
        return Arrays.stream(players).filter(Player::isBankrupt).count() < 3;
    }

    public Player getWinner() {
        return Arrays.stream(players).max(Comparator.comparingInt(Player::getCash)).orElse(null);
    }
}
