import Game.GameWorld;
import Game.JailSystem;
import Utils.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameWorldTests {

    @Test
    public void gameOverTest() {
        JailSystem system = new JailSystem();
        GameWorld world = new GameWorld(system);
        world.start();

        Assertions.assertTrue(world.isStarted());
        Assertions.assertFalse(world.isGameOver());

        world.nextPlayer();
        world.getActivePlayer().payCash(Config.START_CASH);
        Assertions.assertFalse(world.isGameOver());

        world.getPlayers()[1].payCash(Config.START_CASH);
        world.getPlayers()[2].payCash(Config.START_CASH);

        Assertions.assertTrue(world.isGameOver());
        Assertions.assertEquals(world.getPlayers()[3] , world.getWinner());
    }

    @Test
    public void activePlayerCycleTest() {
        JailSystem system = new JailSystem();
        GameWorld world = new GameWorld(system);
        world.start();

        world.nextPlayer(); // 0
        Assertions.assertEquals(0, world.getActivePlayerIndex());
        world.getActivePlayer().payCash(Config.START_CASH);
        world.nextPlayer(); // 1
        Assertions.assertEquals(1, world.getActivePlayerIndex());
        world.nextPlayer(); // 2
        Assertions.assertEquals(2, world.getActivePlayerIndex());
        world.nextPlayer(); // 3
        Assertions.assertEquals(3, world.getActivePlayerIndex());
        world.nextPlayer(); // 1
        Assertions.assertEquals(1, world.getActivePlayerIndex());
        world.getPlayers()[2].payCash(Config.START_CASH);
        world.nextPlayer();
        Assertions.assertEquals(3, world.getActivePlayerIndex());
    }


}
