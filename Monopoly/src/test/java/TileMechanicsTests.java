import org.example.entity.players.Player;
import org.example.entity.Street;
import org.example.entity.tiles.Tile;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TileMechanicsTests {

    @Test
    public void buyTest() {
        Street street = new Street(Color.black);
        Tile tile1 = new Tile("tileName1", 100, 50, 0, street);
        Player player1 = new Player(200, 0, "name", Color.black);
        tile1.action(player1);
        assertTrue(tile1.hasOwner());
        assertSame(player1, tile1.getOwner());
        assertEquals(100, player1.getCash());



        Tile tile2 = new Tile("tileName2", 50, 50, 1, street);
        player1.setTileIndex(1);
        tile2.action(player1);
    }

    @Test
    public void payRentTest() {
        Street street = new Street(Color.black);
        Tile tile1 = new Tile("tileName1", 100, 50, 0, street);

        Player player1 = new Player(100, 0, "name", Color.black);
        Player player2 = new Player(100, 0, "name1", Color.black);

        tile1.setOwner(player1);
        tile1.action(player2);
        assertSame(player1, tile1.getOwner());
        assertEquals(50, player2.getCash());
        assertEquals(150, player1.getCash());
    }

    @Test
    public void upgradeTest() {
        Street street = new Street(Color.black);
        Tile tile1 = new Tile("tileName1", 100, 50, 0, street);
        Tile tile2 = new Tile("tileName2", 100, 50, 1, street);

        Player player1 = new Player(200, 0, "name", Color.black);
        Player player2 = new Player(100, 0, "name1", Color.black);

        tile1.setOwner(player1);
        assertFalse(tile1.canBeUpgraded(player1));
        tile2.setOwner(player2);
        assertFalse(tile1.canBeUpgraded(player1));
        assertFalse(tile2.canBeUpgraded(player1));
        assertFalse(tile1.canBeUpgraded(player2));
        assertFalse(tile2.canBeUpgraded(player2));

        tile2.setOwner(player1);

        assertTrue(tile1.canBeUpgraded(player1));
        assertTrue(tile2.canBeUpgraded(player1));
        assertFalse(tile1.canBeUpgraded(player2));
        assertFalse(tile2.canBeUpgraded(player2));

        assertEquals(50, tile1.getRent());

        tile1.upgrade();

        assertEquals(100, player1.getCash());
        assertEquals(100, tile1.getRent());

        tile1.action(player2);

        assertEquals(200, player1.getCash());
        assertEquals(0, player2.getCash());
        assertTrue(player2.isBankrupt());
    }
}
