package org.example.game;

import org.example.entity.players.Player;
import org.example.utils.Config;
import org.example.view.MessageBoxProxy;

/**
 * Класс, отвечающий за отправку игрока в тюрьму
 */
public class JailSystem {
    public JailSystem() {}

    /**
     * Отправляет игрока в тюрьму.
     * @param player обрабатываемый игрок
     */
    public void goToJail(Player player) {
        MessageBoxProxy.showMessage("The verdict is in - you're going to jail", "uh??");
        player.setInJail(true);
        player.setTileIndex(Config.JAIL_INDEX);
    }

}
