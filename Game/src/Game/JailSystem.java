package Game;

import Entity.Players.Player;
import View.MessageBoxProxy;

public class JailSystem {

    public static final int JAIL_INDEX = 6;

    public JailSystem() {
    }

    public void goToJail(Player player) {
        MessageBoxProxy.showMessage("The verdict is in - you're going to jail", "uh??");
        player.setInJail(true);
        player.setTileIndex(JAIL_INDEX);
    }

}
