import Game.Game;
import Game.DiceRoll;
import Game.GameWorld;
import Game.JailSystem;
import Utils.Config;
import View.GameView;
import View.NextTurnButton;
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
        world.getPlayers()[2].payCash(world.getPlayers()[2].getCash());
        world.nextPlayer();
        Assertions.assertEquals(3, world.getActivePlayerIndex());
    }

    @Test
    public void moveTest() {
        Game game = new Game(new GameView(0, 0, new NextTurnButton()));
        game.start();
        game.getWorld().nextPlayer();
        DiceRoll roll = new DiceRoll(0, Config.GO_TO_JAIL_INDEX); // проверка тюрьмы
        game.movePlayer(roll);
        Assertions.assertTrue(game.getWorld().getActivePlayer().isInJail());
        Assertions.assertEquals(Config.JAIL_INDEX,
                game.getWorld().getActivePlayer().getTileIndex());

        game.getWorld().nextPlayer();
        DiceRoll roll2 = new DiceRoll(1, 1); // проверка просто хода
        game.movePlayer(roll2);
        Assertions.assertEquals(2,
                game.getWorld().getActivePlayer().getTileIndex());

        game.getWorld().nextPlayer();
        game.getWorld().getActivePlayer().setTileIndex(Config.MAP_SIZE - 1);
        DiceRoll roll3 = new DiceRoll(0, 1); // проверка добавления денег при завершении круга
        game.movePlayer(roll3);
        Assertions.assertEquals(0,
                game.getWorld().getActivePlayer().getTileIndex());
        Assertions.assertEquals(Config.START_CASH + Config.ROUND_CASH,
                game.getWorld().getActivePlayer().getCash());
    }

}
