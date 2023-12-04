package Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private int cash;
    private int tileIndex;
    public final String NAME;

    private boolean isBankrupt;
    private boolean isSkippingMove;

    private boolean isInJail;

    private int jailEscapeCards;

    private final Set<Integer> privateTiles;

    public Player(int startCash, String name) {
        cash = startCash;
        NAME = name;
        tileIndex = 0;
        privateTiles = new HashSet<>();
        isBankrupt = false;
        isSkippingMove = false;
        isInJail = false;
        jailEscapeCards = 0;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }

    public void sendCash(Player receiver, int cash) {
        payCash(cash);
        receiver.cash += cash;
    }

    public void payCash(int cash) {
        this.isBankrupt = (this.cash - cash > 0);
        this.cash -= cash;
    }

    public void setSkippingMove(boolean skippingMove) {
        isSkippingMove = skippingMove;
    }

    public boolean isSkippingMove() {
        return isSkippingMove;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public void addCash(int cash) {
        this.cash += cash;
    }

    public boolean hasJailEscapeCards() {
        return 0 < jailEscapeCards;
    }

    public void addJailEscapeCard() {
        jailEscapeCards++;
    }

    public void useJailEscapeCard() {
        jailEscapeCards--;
    }

    public void addTile(int tileIndex) {
        privateTiles.add(tileIndex);
    }
}
