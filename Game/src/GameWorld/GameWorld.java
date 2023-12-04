package GameWorld;


import Entity.Player;
import Entity.Tiles.Tile;
import View.MessageBoxProxy;

public class GameWorld {
    private Tile[] map;
    private final int MAP_SIZE = 10; // todo: change number
    private Player[] players;
    private int playerIndex;
    private static final int ROUND_CASH = 200;
    private static final int JAIL_CASH = 100;


    private int jailIndex;

    public GameWorld() {
    }

    public Player getPlayer(int index) {
        index = index % players.length;
        return players[index];
    }

    public Player nextPlayer() {
        return getPlayer(playerIndex++);
    }

    public void movePlayer(DiceRoll roll) {
        int step = roll.getSum();
        Player activePlayer = getActivePlayer();

        int newIndex = (activePlayer.getTileIndex() + step) % MAP_SIZE;

        if (newIndex < activePlayer.getTileIndex()) {
            activePlayer.addCash(ROUND_CASH);
        }
        if (!activePlayer.isSkippingMove()) {
            activePlayer.setTileIndex(newIndex);

            map[newIndex].action(activePlayer);

        } else {
            activePlayer.setSkippingMove(false);
        }
    }

    public void buyTile() {
        int tileIndex = getActivePlayer().getTileIndex();
        if (!hasOwner(tileIndex)) {
            getActivePlayer().payCash(map[tileIndex].PRICE);
            getActivePlayer().addTile(tileIndex);
        }
    }

    public Player getOwner(int tileIndex) {
        for (Player ch : players) {
            if (ch.hasTile(tileIndex))
                return ch;
        }
        return null;
    }

    private boolean hasOwner(int tileIndex) {
        return getOwner(tileIndex) != null;
    }

    private Player getActivePlayer() {
        return players[playerIndex];
    }

    public void nextTurn() {
        nextPlayer();
        Player activePlayer = getActivePlayer();
        int count = 0;
        do {
            DiceRoll roll = null;
            if (activePlayer.isInJail()) {
                roll = handleJail();
                if (activePlayer.isInJail()) {
                    return;
                }
            }
            if (roll == null) {
                roll = new DiceRoll();
            }

            movePlayer(roll);

            if (roll.isDouble()) {
                count++;
            } else {
                return;
            }
        } while (count < 3);
        //todo: horny jail

    }

    private void goToJail() {

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

}
