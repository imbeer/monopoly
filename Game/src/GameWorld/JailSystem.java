package GameWorld;

import Entity.Player;

public class JailSystem {
    private final GameWorld gameWorld;

    public JailSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void goToJail(Player player) {
        gameWorld.goToJail(player);
    }

}
