package Game;


import Entity.Players.Bot;
import Entity.Players.Player;
import Entity.Tiles.*;
import Utils.Config;
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
        map = new Tile[Config.MAP_SIZE];
        map[Config.START_INDEX] = new StartTile(Config.START_INDEX);
        map[Config.JAIL_INDEX] = new JailTile(Config.JAIL_INDEX);
        map[Config.PARK_INDEX] = new ParkingTile(Config.PARK_INDEX);
        map[Config.GO_TO_JAIL_INDEX] = new GoToJailTile(Config.GO_TO_JAIL_INDEX, JAIL_SYSTEM);

        for (int i = 0; i <= Config.MAP_SIZE - Config.MAP_SIZE / 4; i += Config.MAP_SIZE / 4) {
            int index = i + ThreadLocalRandom.current().nextInt(1, Config.MAP_SIZE / 4);
            map[index] = new ChanceTile(index, JAIL_SYSTEM);
        }

        File names = new File("src/Assets/names.txt");
        try {
            Scanner scanner = new Scanner(names);
            for (int index = 0; index < Config.MAP_SIZE; index++) {
                if (map[index] == null) {
                    map[index] = new Tile(scanner.nextLine(), index * 10, index * 5, index);
                }
            }
        } catch (FileNotFoundException e) {
            for (int index = 0; index < Config.MAP_SIZE; index++) {
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
                players[index] = new Bot(Config.START_CASH, Config.START_INDEX, name, colors[index]);
            } else {
                players[index] = new Player(Config.START_CASH, Config.START_INDEX, name, colors[index]);
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