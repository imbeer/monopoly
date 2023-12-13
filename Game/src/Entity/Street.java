package Entity;

import Entity.Players.Player;
import Utils.Config;

import java.awt.*;
import java.util.HashMap;

public class Street {
    private final Player[] OWNERS;
    private final HashMap<Integer, Integer> TILE_INDEXES;
    public final Color STREET_COLOR;

    public Street(Color streetColor) {
        STREET_COLOR = streetColor;
        OWNERS = new Player[Config.STREET_CAPACITY];
        TILE_INDEXES = new HashMap<>();
    }

    public void addOwner(Player player, int tileIndex) {
        if (TILE_INDEXES.containsKey(tileIndex)) {
            OWNERS[TILE_INDEXES.get(tileIndex)] = player;
            return;
        }
        for (int i = 0; i < OWNERS.length; i++) {
            if (OWNERS[i] == null) {
                OWNERS[i] = player;
                TILE_INDEXES.put(tileIndex, i);
                return;
            }
        }
    }

    public boolean canBeUpgraded(Player player) {
        for (Player owner : OWNERS) {
            if (player != owner) {
                return false;
            }
        }
        return true;
    }
}
