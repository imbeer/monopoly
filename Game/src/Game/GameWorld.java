package Game;


import Entity.Players.Player;
import Entity.Tiles.*;
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
    public static final int MAP_SIZE = 24;
    public static final int START_INDEX = 0;
    public static final int JAIL_INDEX = 6;
    public static final int PARK_INDEX = 12;
    public static final int GO_TO_JAIL_INDEX = 18;
    private final JailSystem JAIL_SYSTEM;

    public GameWorld(JailSystem jailSystem) {
        JAIL_SYSTEM = jailSystem;
    }

    public void start() {
        fillMap();
        fillPlayers();
        isStarted = true;
    }

    public void nextPlayer() {
        playerIndex++;
        playerIndex = playerIndex % 4;
    }

    public Player getActivePlayer() {
        return players[playerIndex];
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
