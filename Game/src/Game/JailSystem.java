package Game;

import Entity.Players.Player;
import View.MessageBoxProxy;

public class JailSystem {

    public JailSystem() {
    }

    public void goToJail(Player player) {
        player.setInJail(true);
        player.setTileIndex(GameWorld.JAIL_INDEX);
        MessageBoxProxy.showMessage("The verdict is in - you're going to jail", "uh??");
    }

}
