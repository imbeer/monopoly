package Game;

import Entity.Players.Player;
import Utils.Config;
import View.MessageBoxProxy;

public class JailSystem {
    public JailSystem() {
    }

    public void goToJail(Player player) {
        MessageBoxProxy.showMessage("The verdict is in - you're going to jail", "uh??");
        player.setInJail(true);
        player.setTileIndex(Config.JAIL_INDEX);
    }

}
