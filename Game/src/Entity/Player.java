package Entity;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int cash;
    private int tileIndex;

    private boolean isBankrupt;
    private boolean isSkippingMove;

    private boolean isInJail;

    private int jailEscapeCards;

    private final List<Integer> privateTiles;

    public Player() {
        this.cash = 10000;
        this.tileIndex = 0;
        privateTiles = new ArrayList<Integer>();
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

    public boolean hasTile(int tileIndex) {
        return privateTiles.contains(tileIndex);
    }

    public void addTile(int tileIndex) {
        privateTiles.add(tileIndex);
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
}
