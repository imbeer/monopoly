package Entity;

import Entity.Players.Player;
import Utils.Config;

import java.awt.*;

public class Street {
    private final Player[] OWNERS;
    public final Color STREET_COLOR;

    public Street(Color streetColor) {
        STREET_COLOR = streetColor;
        OWNERS = new Player[Config.STREET_CAPACITY];
    }

    public void addOwner(Player player) {
        for (int i = 0; i < OWNERS.length; i++) {
            if (OWNERS[i] == null) {
                OWNERS[i] = player;
                return;
            }
        }
    }

    public boolean canBeUpgraded(Player player) {
        for (Player owner : OWNERS) {
//            if (owner == null) {
//                return false;
//            }
            if (player != owner) {
                return false;
            }
        }
        return true;
    }
}
