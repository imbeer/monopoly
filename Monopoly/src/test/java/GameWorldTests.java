import org.example.game.Game;
import org.example.game.DiceRoll;
import org.example.game.GameWorld;
import org.example.game.JailSystem;
import org.example.utils.Config;
import org.example.view.GameView;
import org.example.view.NextTurnButton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameWorldTests {

    @Test
    public void gameOverTest() {
        JailSystem system = new JailSystem();
        GameWorld world = new GameWorld(system);
        world.start();

        assertTrue(world.isStarted());
        assertFalse(world.isGameOver());

        world.nextPlayer();
        world.getActivePlayer().payCash(Config.START_CASH);
        assertFalse(world.isGameOver());

        world.getPlayers()[1].payCash(Config.START_CASH);
        world.getPlayers()[2].payCash(Config.START_CASH);

        assertTrue(world.isGameOver());
        assertEquals(world.getPlayers()[3] , world.getWinner());
    }

    @Test
    public void activePlayerCycleTest() {
        JailSystem system = new JailSystem();
        GameWorld world = new GameWorld(system);
        world.start();

        world.nextPlayer(); // 0
        assertEquals(0, world.getActivePlayerIndex());
        world.getActivePlayer().payCash(Config.START_CASH);
        world.nextPlayer(); // 1
        assertEquals(1, world.getActivePlayerIndex());
        world.nextPlayer(); // 2
        assertEquals(2, world.getActivePlayerIndex());
        world.nextPlayer(); // 3
        assertEquals(3, world.getActivePlayerIndex());
        world.nextPlayer(); // 1
        assertEquals(1, world.getActivePlayerIndex());
        world.getPlayers()[2].payCash(world.getPlayers()[2].getCash());
        world.nextPlayer();
        assertEquals(3, world.getActivePlayerIndex());
    }

    @Test
    public void moveTest() {
        Game game = new Game(new GameView(0, 0, new NextTurnButton()));
        game.start();
        game.getWorld().nextPlayer();
        DiceRoll roll = new DiceRoll(0, Config.GO_TO_JAIL_INDEX); // проверка тюрьмы
        game.movePlayer(roll);
        assertTrue(game.getWorld().getActivePlayer().isInJail());
        assertEquals(Config.JAIL_INDEX,
                game.getWorld().getActivePlayer().getTileIndex());

        game.getWorld().nextPlayer();
        DiceRoll roll2 = new DiceRoll(1, 1); // проверка просто хода
        game.movePlayer(roll2);
        assertEquals(2,
                game.getWorld().getActivePlayer().getTileIndex());

        game.getWorld().nextPlayer();
        game.getWorld().getActivePlayer().setTileIndex(Config.MAP_SIZE - 1);
        DiceRoll roll3 = new DiceRoll(0, 1); // проверка добавления денег при завершении круга
        game.movePlayer(roll3);
        assertEquals(0,
                game.getWorld().getActivePlayer().getTileIndex());
        assertEquals(Config.START_CASH + Config.ROUND_CASH,
                game.getWorld().getActivePlayer().getCash());
    }

}
