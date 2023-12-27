package org.example.game;

import org.example.entity.players.Player;
import org.example.utils.Config;
import org.example.view.MessageBoxProxy;

public class JailSystem {
    public JailSystem() {
    }

    public void goToJail(Player player) {
        MessageBoxProxy.showMessage("The verdict is in - you're going to jail", "uh??");
        player.setInJail(true);
        player.setTileIndex(Config.JAIL_INDEX);
    }

}
