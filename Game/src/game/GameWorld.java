package game;


import entity.players.Bot;
import entity.players.Player;
import entity.Street;
import entity.tiles.*;
import utils.Config;
import view.MessageBoxProxy;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GameWorld {
    private Tile[] map;
    private Player[] players;
    private Street[] streets;
    private LinkedList<Player> activePlayers;
    private ListIterator<Player> playerIterator;
    private Player activePlayer;
    private DiceRoll activeDiceRoll;
    private boolean isStarted = false;
    private final JailSystem jailSystem;

    public GameWorld(JailSystem jailSystem) {
        this.jailSystem = jailSystem;
    }

    public void start() {
        fillStreets();
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
            nextPlayer();
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public int getActivePlayerIndex() {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == activePlayer) {
                return i;
            }
        }
        return 5;
    }

    public void fillMap() {
        map = new Tile[Config.MAP_SIZE];
        map[Config.START_INDEX] = new StartTile(Config.START_INDEX);
        map[Config.JAIL_INDEX] = new JailTile(Config.JAIL_INDEX);
        map[Config.PARK_INDEX] = new ParkingTile(Config.PARK_INDEX);
        map[Config.GO_TO_JAIL_INDEX] = new GoToJailTile(Config.GO_TO_JAIL_INDEX, jailSystem);

        for (int i = 0; i <= Config.MAP_SIZE - Config.MAP_SIZE / 4; i += Config.MAP_SIZE / 4) {
            int index = i + ThreadLocalRandom.current().nextInt(1, Config.MAP_SIZE / 4);
            map[index] = new ChanceTile(index, jailSystem);
        }

        File names = new File("src/Assets/names.txt");
        int streetsCounter = 0;
        int streetsIndex = 0;
        try {
            Scanner scanner = new Scanner(names);
            for (int index = 0; index < Config.MAP_SIZE; index++) {
                if (map[index] == null) {
                    map[index] = new Tile(scanner.nextLine(), index * 10, index * 5, index, streets[streetsIndex]);
                    streetsCounter++;
                    if (streetsCounter == 2) {
                        streetsCounter = 0;
                        streetsIndex++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            for (int index = 0; index < Config.MAP_SIZE; index++) {
                if (map[index] == null) {
                    map[index] = new Tile("Lorem Ipsum " + index, index * 10, index * 5, index, streets[streetsIndex]);
                    streetsCounter++;
                    if (streetsCounter == 2) {
                        streetsCounter = 0;
                        streetsIndex++;
                    }
                }
            }
        }
    }

    public void fillPlayers() {
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

    public void fillStreets() {
        streets = new Street[] {
                new Street(new Color(221, 203, 129)),
                new Street(new Color(219, 165, 127)),
                new Street(new Color(177, 225, 138)),
                new Street(new Color(128, 220, 195)),
                new Street(new Color(227, 133, 133)),
                new Street(new Color(223, 130, 180)),
                new Street(new Color(198, 131, 224)),
                new Street(new Color(132, 154, 225))
        };
    }

    public Tile[] getMap() {
        return map;
    }

    public Player[] getPlayers() {
        return players;
    }
    public void setPlayers(Player[] players) {
        this.players = players;
        activePlayers = new LinkedList<>(Arrays.asList(players));
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isGameOver() {
        return Arrays.stream(players).filter(Player::isBankrupt).count() > 2;
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