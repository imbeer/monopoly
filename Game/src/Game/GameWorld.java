package Game;


import Entity.Players.Bot;
import Entity.Players.Player;
import Entity.Tiles.*;
import View.MessageBoxProxy;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GameWorld {
    private Tile[] map;
    private Player[] players;
    private LinkedList<Player> activePlayers;
    private ListIterator<Player> playerIterator;
    private Player activePlayer;
    private DiceRoll activeDiceRoll;
    private boolean isStarted = false;
    public static final int START_CASH = 1000;
    public static final int ROUND_CASH = 50;
    public static final int JAIL_CASH = 50;
    public static final int MAP_SIZE = 24;
    public static final int START_INDEX = 0;
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
        if (!playerIterator.hasNext()) {
            playerIterator = activePlayers.listIterator(0);
        }
        activePlayer = playerIterator.next();
        if (activePlayer.isBankrupt()) {
            playerIterator.remove();
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public int getActivePlayerIndex() {
        return playerIterator.nextIndex() - 1;
    }

    private void fillMap() {
        map = new Tile[MAP_SIZE];
        map[START_INDEX] = new StartTile(START_INDEX);
        map[JailSystem.JAIL_INDEX] = new JailTile(JailSystem.JAIL_INDEX);
        map[PARK_INDEX] = new ParkingTile(PARK_INDEX);
        map[GO_TO_JAIL_INDEX] = new GoToJailTile(GO_TO_JAIL_INDEX, JAIL_SYSTEM);

        for (int i = 0; i <= MAP_SIZE - MAP_SIZE / 4; i += MAP_SIZE / 4) {
            int index = i + ThreadLocalRandom.current().nextInt(1, MAP_SIZE / 4);
            map[index] = new ChanceTile(index, JAIL_SYSTEM);
        }

        File names = new File("src/Assets/names.txt");
        try {
            Scanner scanner = new Scanner(names);
            for (int index = 0; index < MAP_SIZE; index++) {
                if (map[index] == null) {
                    map[index] = new Tile(scanner.nextLine(), index * 10, index * 5, index);
                }
            }
        } catch (FileNotFoundException e) {
            for (int index = 0; index < MAP_SIZE; index++) {
                if (map[index] == null) {
                    map[index] = new Tile("Lorem Ipsum " + index, index * 10, index * 5, index);
                }
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
            if (MessageBoxProxy.getAnswer("is " + name + " bot?", "NEW PLAYER")) {
                players[index] = new Bot(START_CASH, START_INDEX, name, colors[index]);
            } else {
                players[index] = new Player(START_CASH, START_INDEX, name, colors[index]);
            }
        }
        activePlayers = new LinkedList<>(Arrays.asList(players));
        playerIterator = activePlayers.listIterator(0);
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
        return activePlayers.size() > 1;
    }

    public Player getWinner() {
        return Arrays.stream(players).max(Comparator.comparingInt(Player::getCash)).orElse(null);
    }

    public DiceRoll getActiveDiceRoll() {
        return activeDiceRoll;
    }

    public void setActiveDiceRoll(DiceRoll activeDiceRoll) {
        this.activeDiceRoll = activeDiceRoll;
    }
}