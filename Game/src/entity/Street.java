package entity;

import entity.players.Player;
import utils.Config;

import java.awt.*;
import java.util.HashMap;

public class Street {
    private final Player[] owners;
    private final HashMap<Integer, Integer> tileIndexes;
    public final Color streetColor;

    public Street(Color streetColor) {
        this.streetColor = streetColor;
        owners = new Player[Config.STREET_CAPACITY];
        tileIndexes = new HashMap<>();
    }

    public void addOwner(Player player, int tileIndex) {
        if (tileIndexes.containsKey(tileIndex)) {
            owners[tileIndexes.get(tileIndex)] = player;
            return;
        }
        for (int i = 0; i < owners.length; i++) {
            if (owners[i] == null) {
                owners[i] = player;
                tileIndexes.put(tileIndex, i);
                return;
            }
        }
    }

    public boolean canBeUpgraded(Player player) {
        for (Player owner : owners) {
            if (player != owner) {
                return false;
            }
        }
        return true;
    }
}
